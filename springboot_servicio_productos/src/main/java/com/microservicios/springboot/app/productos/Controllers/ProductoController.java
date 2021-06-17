package com.microservicios.springboot.app.productos.Controllers;

import com.microservicios.springboot.app.commons.models.entity.Producto;
import com.microservicios.springboot.app.productos.models.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    //@Autowired
    //private Environment env;

    //balanceo de carga con Rest Template
    @Value("${server.port}")
    private Integer port;

    private IProductoService productoService;

    @Autowired
    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }


    @GetMapping("/listar")
    public List<Producto> listar() {
        return productoService.findAll().stream().map(producto -> {
            //producto.setPort(Integer.parseInt(env.getProperty("local.server.port"))); balanceo con Ribbon
            producto.setPort( port );
            return producto;
        }).collect( Collectors.toList() );
    }

    @GetMapping("/ver/{id}")
    public Producto detalle(@PathVariable Long id)  {
        Producto producto = productoService.findById( id );
       // producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        producto.setPort( port );   //se inyecta con value de forma automatica

        //implementación de un timeout de 2 seg
       /* try {
            Thread.sleep( 2000L );
        }catch (InterruptedException e){
            e.printStackTrace();
        }*/

        return producto;
    }

    @PostMapping("/crear")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto crear(@RequestBody Producto producto){
        return productoService.save( producto );

    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Producto editar(@RequestBody Producto producto, @PathVariable Long id){
        Producto productoDb= productoService.findById( id );

        productoDb.setNombre( producto.getNombre() );
        productoDb.setPrecio( producto.getPrecio() );

        return productoService.save( productoDb );

    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar( @PathVariable Long id){
         productoService.deleteById(id );

    }


}
