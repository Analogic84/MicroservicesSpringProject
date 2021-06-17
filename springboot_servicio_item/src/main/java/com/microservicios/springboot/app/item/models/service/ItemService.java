package com.microservicios.springboot.app.item.models.service;

import com.microservicios.springboot.app.commons.models.entity.Producto;
import com.microservicios.springboot.app.item.models.Item;

import java.util.List;

public interface ItemService {

     List<Item> findAll();
     Item findById(Long id, Integer cantidad);

     Producto save(Producto producto);
     Producto update (Producto producto, Long id);
     void delete(Long id);
}
