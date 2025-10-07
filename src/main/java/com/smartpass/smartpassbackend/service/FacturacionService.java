package com.smartpass.smartpassbackend.service;



import com.smartpass.smartpassbackend.model.*;
import com.smartpass.smartpassbackend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;



@Service
public class FacturacionService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    @Autowired
    private TransitoRepository transitoRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    public String generarFacturaPrepago(Long idTransito) {

        System.out.println("🕐 Ejecutando facturación automática PRE: " + LocalDateTime.now());

        Transito transito = transitoRepository.findById(idTransito)
                .orElseThrow(() -> new RuntimeException("Tránsito no encontrado"));

        Contrato contrato = transito.getContrato();
        Cliente cliente = contrato.getCliente();

        // Generar correlativo de 8 dígitos como string
        String correlativo = String.format("%08d", facturaRepository.count() + 1);






        Factura factura = new Factura();
        factura.setContrato(contrato);
        factura.setFechaComprobante(LocalDateTime.now());
        factura.setFechaComprobante(LocalDateTime.now());
        factura.setFechaCrucePeaje(transito.getFecha());
        factura.setMonto(transito.getMonto());
        factura.setIdTransito(transito.getTrId());
        factura.setIgv(transito.getIgv());
        factura.setCorrelativo(correlativo);
        factura.setSerie(transito.getVia()); // Asumimos que tr_via es la serie
        factura.setTipoFacturacion(contrato.getTipoFacturacion().getIdTipoFacturacion());

        // Datos del cliente
        factura.setCliente(cliente);
        factura.setNombre(cliente.getNombre()); // Asegúrate que sea String
        factura.setNumeroDocumento(cliente.getNumDocumento());

        // Guardar factura
        factura = facturaRepository.save(factura);



        // Detalle
        DetalleFactura detalle = new DetalleFactura();
        detalle.setFactura(factura);
        detalle.setTransito(transito);
        detalle.setMonto(transito.getMonto());
        detalle.setFechaCruce(transito.getFecha());
        detalleFacturaRepository.save(detalle);

        transito.setFacturado(true);
        transitoRepository.save(transito);

        return "Factura prepago generada con ID: " + factura.getIdfacturacion();
    }

    public String generarFacturaPospago(LocalDateTime inicio, LocalDateTime fin, Long idCliente) {
        List<Transito> transitos = transitoRepository.findByClienteAndFecha(idCliente, inicio, fin);

        if (transitos.isEmpty()) {
            return "No hay tránsitos pospago para facturar en el rango dado.";
        }

        transitos.stream()
                .map(t -> t.getContrato().getIdContrato())
                .distinct()
                .forEach(idContrato -> {
                    List<Transito> transitosContrato = transitos.stream()
                            .filter(t -> t.getContrato().getIdContrato().equals(idContrato))
                            .toList();

                    BigDecimal total = transitosContrato.stream()
                            .map(Transito::getMonto)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Factura factura = new Factura();
                    factura.setContrato(transitosContrato.get(0).getContrato());
                    factura.setFechaComprobante(LocalDateTime.now());
                    factura.setMonto(total);
                    factura = facturaRepository.save(factura);

                    for (Transito t : transitosContrato) {
                        DetalleFactura detalle = new DetalleFactura();
                        detalle.setFactura(factura);
                        detalle.setTransito(t);
                        detalle.setMonto(t.getMonto());
                        detalle.setFechaCruce(t.getFecha());
                        detalleFacturaRepository.save(detalle);

                        t.setFacturado(true);
                        transitoRepository.save(t);
                    }
                });

        return "Facturación pospago ejecutada exitosamente.";
    }

    @Scheduled(cron = "0 0 0 1,16 * ?")
    public void ejecutarFacturacionPospagoAutomatica() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime inicio;
        LocalDateTime fin;

        if (now.getDayOfMonth() == 1) {
            inicio = now.minusMonths(1).withDayOfMonth(16).withHour(0).withMinute(0);
            fin = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).getMonth().length(now.minusMonths(1).toLocalDate().isLeapYear())).withHour(23).withMinute(59);
        } else {
            inicio = now.withDayOfMonth(1).withHour(0).withMinute(0);
            fin = now.withDayOfMonth(15).withHour(23).withMinute(59);
        }

        List<Cliente> clientes = clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            generarFacturaPospago(inicio, fin, cliente.getIdCliente());
        }
    }

    public List<Factura> obtenerPorCliente(Long idCliente) {
        return facturaRepository.findByClienteId(idCliente);
    }




    @Scheduled(fixedRate = 60000)
    public void ejecutarFacturacionPrepagoPendiente() {
        List<Transito> transitosPendientes = transitoRepository.findByTipoContratoAndFacturadoFalse("PRE");
        for (Transito transito : transitosPendientes) {
            generarFacturaPrepago(transito.getTrId().longValue());
            System.out.println("✅ Factura generada automáticamente para tránsito ID: " + transito.getTrId());
        }
    }

}
