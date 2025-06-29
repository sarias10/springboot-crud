package com.sergio.curso.springboot.app.springboot_crud.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // relaciona esta clase con una tabla de la base de datos
@Table(name="roles") // nombre de la tabla
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // hace la tabla incremental
    private Long id;

    @Column(unique = true) // name es unico. en base de datos hay que poner UQ
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    
}
