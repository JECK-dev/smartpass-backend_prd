package com.smartpass.smartpassbackend.controller;

import com.smartpass.smartpassbackend.model.Reclamo;
import com.smartpass.smartpassbackend.model.Vehiculo;
import com.smartpass.smartpassbackend.repository.VehiculoRepository;
import com.smartpass.smartpassbackend.service.ReclamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reclamos")
@CrossOrigin(origins = "http://localhost:4200")
public class ReclamoController {

    private final ReclamoService reclamoService;
    private final VehiculoRepository vehiculoRepository;

    public ReclamoController(ReclamoService reclamoService, VehiculoRepository vehiculoRepository) {
        this.reclamoService = reclamoService;
        this.vehiculoRepository = vehiculoRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearReclamo(
            @RequestParam("idCliente") Long idCliente,
            @RequestParam("idVehiculo") Integer idVehiculo,
            @RequestParam("idTipoReclamo") Integer idTipoReclamo,
            @RequestParam("detalle") String detalle,
            @RequestParam("estado") Integer estado,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo
    ) {
        try {
            Reclamo nuevo = reclamoService.crearReclamoConArchivo(
                    idCliente, idVehiculo, idTipoReclamo, detalle, estado, archivo
            );
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al registrar el reclamo."));
        }
    }

    @GetMapping("/{idCliente}")
    public List<Reclamo> listarPorCliente(@PathVariable Integer idCliente) {
        return reclamoService.listarPorCliente(idCliente);
    }

    @GetMapping("/vehi/{idCliente}")
    public List<Vehiculo> listarVehiculosPorClienteReclamo(@PathVariable Long idCliente) {
        return vehiculoRepository.findByCliente_IdCliente(idCliente);
    }

    @GetMapping("/resolucion-reclamo")
    public List<Reclamo> listarTodos() {
        return reclamoService.listarTodos();
    }

    @PutMapping("/{idReclamo}")
    public Reclamo actualizar(@PathVariable Integer idReclamo, @RequestBody Reclamo reclamo) {
        return reclamoService.actualizar(idReclamo, reclamo);
    }

}
