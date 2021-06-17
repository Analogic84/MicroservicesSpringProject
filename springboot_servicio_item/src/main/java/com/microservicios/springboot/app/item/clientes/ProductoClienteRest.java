package com.microservicios.springboot.app.item.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.microservicios.springboot.app.commons.models.entity.Producto;


import java.util.List;

@FeignClient(name="servicio-productos")
public interface ProductoClienteRest {


    @GetMapping("/listar")
    List<Producto> listar();

    @GetMapping("/ver/{id}")
    Producto detalle(@PathVariable(value = "id") Long id);

    @PostMapping("/crear")
    Producto crear(@RequestBody Producto producto);

    @PutMapping("/editar/{id}")
    Producto update(@RequestBody Producto producto, @PathVariable (value = "id") Long id );

    @DeleteMapping("/eliminar/{id}")
    void eliminiar(@PathVariable (value = "id") Long id);


}
