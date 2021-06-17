package com.microservicios.springboot.app.item.controllers;

import com.microservicios.springboot.app.item.models.Item;
import com.microservicios.springboot.app.item.models.service.ItemService;
import com.microservicios.springboot.app.commons.models.entity.Producto;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RefreshScope  //permite actualizar los componentes, controladores,clases anotadas con component o service,
               //que le estamos inyectando con @value configuraciones.(dependencia Actuator)
@RestController
public class ItemController {

    private static Logger log = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("serviceFeign")   //se cambian (serviceFeign) x (serviceRestTemplate) para hacer uso de impl segun se necesite.
    private ItemService itemService;

    @Value("${configuracion.texto}")
    private String texto;

    //métodos handler para listar los items y ver x el id
    @GetMapping("/listar")
    public List<Item> listar() {
        return itemService.findAll();
    }

    @HystrixCommand(fallbackMethod = "metodoAlternativo")
    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
        return itemService.findById( id, cantidad );
    }

    public Item metodoAlternativo(Long id, Integer cantidad){
        Item item = new Item();
        Producto producto = new Producto();

        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;

    }
    //La config texto,se despliega se muestra como respuesta con un metodo handler
   @GetMapping ("/obtener-config")
    public ResponseEntity<?> obtenerConfig(@Value("${server.port}") String puerto){  //pasamos como arg en el método el puerto

       log.info( texto );

       Map<String, String> json = new HashMap<>();
       json.put( "texto", texto );
       json.put( "puerto", puerto );

       if (env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals( "dev" )){
           json.put( "autor.nombre", env.getProperty( "configuracion.autor.nombre" ) );
           json.put( "autor.email", env.getProperty( "configuracion.autor.email" ) );

       }

       return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
   }

   @PostMapping("/crear")
   @ResponseStatus(HttpStatus.CREATED)
    public Producto crear (@RequestBody Producto producto ){

        return itemService.save( producto );
    }

    @PutMapping("/editar/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public  Producto editar (@RequestBody Producto producto, @PathVariable Long id){
        return itemService.update( producto, id );
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){

        itemService.delete( id );
    }

}
