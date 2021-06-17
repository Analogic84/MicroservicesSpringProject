package com.microservicios.springboot.app.item.models.service;

import com.microservicios.springboot.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.microservicios.springboot.app.commons.models.entity.Producto;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service ("serviceRestTemplate") //para usar esta impl habilitar
public class ItemServiceImpl implements ItemService {

    @Autowired
    private RestTemplate clienteRest;

    @Override
    public List<Item> findAll() {                                                      //localhost:8001/listar
        List<Producto> productos = Arrays.asList( clienteRest.getForObject( "http://servicio-productos/listar", Producto[].class ) );
        return productos.stream().map( producto -> new Item( producto, 1 ) ).collect( Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put( "id", id.toString() );                //localhost:8001/ver/{id}
        Producto producto = clienteRest.getForObject( "http://servicio-productos/ver/{id}", Producto.class, pathVariables );
        return new Item( producto, cantidad );                  //desacoplamos los param
    }

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> body= new HttpEntity<Producto>( producto );
      ResponseEntity<Producto> response = clienteRest.exchange( "http://servicio-productos/crear",
              HttpMethod.POST, body, Producto.class );
      Producto productoResponse = response.getBody();
      return productoResponse;
    }

    @Override
    public Producto update(Producto producto, Long id) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put( "id", id.toString() );

        HttpEntity<Producto> body= new HttpEntity<Producto>( producto );
        ResponseEntity<Producto> response = clienteRest.exchange( "http://servicio-productos/editar/{id}",
                HttpMethod.PUT, body, Producto.class, pathVariables );

        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put( "id", id.toString() );

        clienteRest.delete( "http://servicio-productos/eliminar/{id}", pathVariables );

    }
}
