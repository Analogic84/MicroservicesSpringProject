package com.microservicios.springboot.app.commons.usuarios.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity                   //marcar como una clase de persistencia, mapeada a una tabla
@Table(name = "roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    si quiseramos implementar una relación bidireccional de muchos a muchos (mappedBy)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy ="roles")
    private List <Usuario> usuarios;
    no olvidar getter() y setter() Usuario
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(unique = true, length = 30)
    private String nombre;

    private static final long serialVersionUID = -5790704253908349704L;
}
