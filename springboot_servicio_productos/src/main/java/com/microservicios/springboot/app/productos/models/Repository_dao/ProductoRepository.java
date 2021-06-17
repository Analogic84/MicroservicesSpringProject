package com.microservicios.springboot.app.productos.models.Repository_dao;

import com.microservicios.springboot.app.commons.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;


public interface ProductoRepository extends CrudRepository <Producto, Long> {




}
