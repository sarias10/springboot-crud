package com.sergio.curso.springboot.app.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sergio.curso.springboot.app.springboot_crud.entities.Product;

/**
 * Esta interfaz extiende CrudRepository, lo que le dice a Spring Data JPA
 * que debe generar automáticamente una implementación en tiempo de ejecución.
 *
 * Aunque CrudRepository está anotada con @NoRepositoryBean (lo que impide que
 * sea registrada directamente como un bean), las interfaces que la extienden
 * sí se registran automáticamente como beans si están en el paquete escaneado.
 *
 * Por eso, esta interfaz puede inyectarse con @Autowired sin necesidad
 * de una clase concreta que la implemente manualmente.
 *
 * Además, métodos como existsBySku() funcionan gracias al análisis
 * del nombre del método (query derivada), lo que permite que Spring
 * genere la consulta automáticamente sin escribir SQL.
 */

public interface ProductRepository extends CrudRepository<Product, Long>{
    
    boolean existsBySku(String sku); // metodo basado en el nombre
    
}
