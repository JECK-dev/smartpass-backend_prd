package com.smartpass.smartpassbackend.service;

import com.smartpass.smartpassbackend.model.Cliente;
import com.smartpass.smartpassbackend.model.Contrato;
import com.smartpass.smartpassbackend.model.Vehiculo;
import com.smartpass.smartpassbackend.repository.CategoriaRepository;
import com.smartpass.smartpassbackend.repository.ContratoRepository;
import com.smartpass.smartpassbackend.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class NotificacionService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EmailService emailService;

    // Ejecuta cada hora (ajusta a fixedRate = 300000 para 5 minutos si quieres probar)
    @Scheduled(fixedRate = 300000) // cada 5 minutos
    public void verificarSaldos() {
        System.out.println("🕒 Iniciando verificación de saldos... " + java.time.LocalTime.now());

        try {
            // Buscar contratos prepago
            List<Contrato> contratosPrepago = contratoRepository.findByTipo("PRE");
            System.out.println("🔍 Contratos PRE encontrados: " + contratosPrepago.size());

            if (contratosPrepago.isEmpty()) {
                System.out.println("⚠️ No se encontraron contratos prepago. Fin del proceso.");
                return;
            }

            // Recorrer los contratos
            for (Contrato contrato : contratosPrepago) {
                if (contrato == null) continue;

                BigDecimal saldo = contrato.getSaldo();
                System.out.println("➡️ Contrato ID: " + contrato.getIdContrato() + " | Saldo: " + saldo);

                if (saldo == null) {
                    System.out.println("⚠️ Contrato " + contrato.getIdContrato() + " sin saldo definido. Omitido.");
                    continue;
                }

                // Vehículos asociados
                List<Vehiculo> vehiculos = vehiculoRepository.findByContratoId(contrato.getIdContrato());
                System.out.println("🚗 Vehículos asociados: " + (vehiculos != null ? vehiculos.size() : 0));

                if (vehiculos == null || vehiculos.isEmpty()) {
                    System.out.println("⚠️ Sin vehículos asociados. Omitiendo contrato.");
                    continue;
                }

                // Determinar la categoría más alta
                Integer idCategoriaMax = vehiculos.stream()
                        .map(Vehiculo::getCategoria)
                        .filter(Objects::nonNull)
                        .max(Comparator.naturalOrder())
                        .orElse(null);

                System.out.println("🏷️ Categoría máxima detectada: " + idCategoriaMax);

                if (idCategoriaMax == null) {
                    System.out.println("⚠️ No se encontró categoría válida. Omitiendo contrato.");
                    continue;
                }

                // Buscar el peaje correspondiente
                BigDecimal montoPeajeMax = categoriaRepository.findMontoPeajeByIdCategoria(idCategoriaMax);
                System.out.println("💰 Peaje máximo asociado: " + montoPeajeMax);

                if (montoPeajeMax == null) {
                    System.out.println("⚠️ No se encontró monto de peaje para categoría " + idCategoriaMax);
                    continue;
                }

                // Comparar saldo con peaje
                if (saldo.compareTo(montoPeajeMax) < 0) {
                    Cliente cliente = contrato.getCliente();
                    if (cliente != null && cliente.getCorreo() != null && !cliente.getCorreo().isBlank()) {
                        System.out.println("📩 Saldo insuficiente. Enviando alerta a: " + cliente.getCorreo());
                        try {
                            enviarAlerta(cliente, saldo, montoPeajeMax);
                            System.out.println("✅ Alerta enviada correctamente a: " + cliente.getCorreo());
                        } catch (Exception e) {
                            System.err.println("❌ Error al enviar correo a " + cliente.getCorreo() + ": " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("⚠️ Cliente sin correo. No se puede notificar.");
                    }
                } else {
                    System.out.println("✅ Contrato ID " + contrato.getIdContrato() + ": saldo suficiente.");
                }
            }

            System.out.println("🟢 Verificación finalizada correctamente a las " + java.time.LocalTime.now());
        } catch (Exception e) {
            System.err.println("❌ Error general en verificarSaldos: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void enviarAlerta(Cliente cliente, BigDecimal saldo, BigDecimal peajeNecesario) {
        String asunto = "⚠️ Saldo insuficiente en SmartPass";
        // Mostrar los valores tal cual, sin redondeo (toPlainString evita notación científica)
        String mensaje = "Hola " + cliente.getNombre() + ",\n\n" +
                "Tu saldo actual es: S/ " + saldo.toPlainString() + ".\n" +
                "El peaje más alto de tus vehículos cuesta: S/ " + peajeNecesario.toPlainString() + ".\n\n" +
                "Por favor recarga para evitar bloqueos.\n\n" +
                "Atentamente,\nSmartPass 🚗💨";

        emailService.enviarCorreo(cliente.getCorreo(), asunto, mensaje);
    }
}
