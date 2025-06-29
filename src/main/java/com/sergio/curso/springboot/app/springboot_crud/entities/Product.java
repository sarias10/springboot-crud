package com.sergio.curso.springboot.app.springboot_crud.entities;

import com.sergio.curso.springboot.app.springboot_crud.validation.IsExistsDb;
import com.sergio.curso.springboot.app.springboot_crud.validation.IsRequired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// si se cambia el pom.xml hay que volver a levantar
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @IsRequired // por si esta en simples comillas
    @IsExistsDb
    private String sku;

    // necesitamos la dependencia spring-boot-starter-validation para usar anotaciones como NotEmpty, sino da error
    //@NotEmpty(message = "{NotEmpty.product.name}") // @NotEmpty solo para strings
    //@NotBlank // valida que no sea vacio y que no tenga espacios en blanco
    @IsRequired(message = "{IsRequired.product.name}")
    @Size(min = 3, max = 20)
    private String name;

    @Min(value = 500, message = "{Min.product.price}")
    @NotNull(message = "{NotNull.product.price}") // @NotNull se usa para validar que tipos de objetos no sean null, pero no se usa para strings
    private Integer price;

    //@NotEmpty // @NotEmpty solo para strings
    //@NotBlank(message = "{NotBlank.product.description}")
    @IsRequired
    private String description;

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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    

    
}
