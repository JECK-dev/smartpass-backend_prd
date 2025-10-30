package com.smartpass.smartpassbackend.service;

import com.smartpass.smartpassbackend.model.Cliente;
import com.smartpass.smartpassbackend.model.Reclamo;
import com.smartpass.smartpassbackend.model.TipoReclamo;
import com.smartpass.smartpassbackend.model.Vehiculo;
import com.smartpass.smartpassbackend.repository.ReclamoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReclamoService {
    private final ReclamoRepository reclamoRepo;

    public ReclamoService(ReclamoRepository reclamoRepo) {
        this.reclamoRepo = reclamoRepo;
    }


    public Reclamo crearReclamoConArchivo(Long idCliente, Integer idVehiculo,
                                          Integer idTipoReclamo, String detalle,
                                          Integer estado, MultipartFile archivo) throws IOException {

        Reclamo reclamo = new Reclamo();

        // 🔹 Asignar relaciones correctamente
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        reclamo.setCliente(cliente);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(idVehiculo);
        reclamo.setVehiculo(vehiculo);

        TipoReclamo tipoReclamo = new TipoReclamo();
        tipoReclamo.setIdTipoReclamo(idTipoReclamo);
        reclamo.setTipoReclamo(tipoReclamo);

        // 🔹 Campos simples
        reclamo.setDetalle(detalle);
        reclamo.setEstado(estado);
        reclamo.setFechaCreacion(LocalDateTime.now());

        // 🔹 Guardar archivo si se adjuntó
        if (archivo != null && !archivo.isEmpty()) {
            Path carpeta = Paths.get("uploads/reclamos");
            Files.createDirectories(carpeta);

            String nombreArchivo = System.currentTimeMillis() + "_" + archivo.getOriginalFilename();
            Path rutaArchivo = carpeta.resolve(nombreArchivo);
            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            reclamo.setArchivo(nombreArchivo);
        }

        return reclamoRepo.save(reclamo);
    }



    public List<Reclamo> listarPorCliente(Integer idCliente) {
        return reclamoRepo.findByClienteIdCliente(idCliente);
    }

    public List<Reclamo> listarTodos() {
        return reclamoRepo.findAll();
    }

    public Reclamo actualizar(Integer id, Reclamo reclamoActualizado) {
        return reclamoRepo.findById(id).map(r -> {
            r.setEstado(reclamoActualizado.getEstado());
            r.setRespuesta(reclamoActualizado.getRespuesta());
            r.setFechaResolucion(LocalDateTime.now());
            return reclamoRepo.save(r);
        }).orElseThrow(() -> new RuntimeException("Reclamo no encontrado"));
    }

}
    
