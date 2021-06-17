package com.microservicios.springboot.app.productos.models.service;

import com.microservicios.springboot.app.productos.models.Repository_dao.ProductoRepository;
import com.microservicios.springboot.app.commons.models.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return(List<Producto>)productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoRepository.save( producto );
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productoRepository.deleteById( id );
    }
}
