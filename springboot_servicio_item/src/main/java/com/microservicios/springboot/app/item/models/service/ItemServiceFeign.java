package com.microservicios.springboot.app.item.models.service;

import com.microservicios.springboot.app.item.clientes.ProductoClienteRest;
import com.microservicios.springboot.app.item.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.microservicios.springboot.app.commons.models.entity.Producto;


import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
@Primary   //se quita anotación para el uso de la impl con rest template
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.listar().stream().map( producto -> new Item( producto, 1 ) ).collect( Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item( clienteFeign.detalle(id), cantidad );
    }

    @Override
    public Producto save(Producto producto) {

        return clienteFeign.crear( producto );
    }

    @Override
    public Producto update(Producto producto, Long id) {

        return clienteFeign.update( producto, id );
    }

    @Override
    public void delete(Long id) {

        clienteFeign.eliminiar( id );
    }
}
