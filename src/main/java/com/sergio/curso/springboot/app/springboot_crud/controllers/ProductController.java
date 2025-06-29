package com.sergio.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

// import com.sergio.curso.springboot.app.springboot_crud.ProductValidation;
import com.sergio.curso.springboot.app.springboot_crud.entities.Product;
import com.sergio.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


// si colocamos not null en todos los campos en workbench e intentamos crear un nuevo registro, da error 500 internal server error

@RestController
@RequestMapping("/api/products") // ruta base
                                 // siempre terminan sin slash
public class ProductController {

    @Autowired
    private ProductService service; // inyecta la interfaz pero esta interfaz la implementa productServiceImpl
    
    // esto es para validar con la clase ProductValidation
    // @Autowired
    // private ProductValidation validation;
    
    @GetMapping
    public List<Product> list() {
        return service.findAll();
    }
    
    // el ? significa que no sabe que tipo exacto devolver
    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> productOptional = service.findById(id);
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // necesitamos la dependencia spring-boot-starter-validation
    // el valid debe ir en el product porque tenemos que validar la clase entity product
    @PostMapping
    //@RequestBody le dice a Spring:
    //“Toma el JSON que viene en el cuerpo del request HTTP y conviértelo en un objeto Java”.
    // el BindingResult no puede ir en cualquier parte debe ir al lado del product y al comienzo(como segundo argumento) debe estar a la derecha al lado del objeto que vamos a validar. Se pusa para recibir los errores con el fin de mostrar los mensajes de error de una forma mas amigable
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) { // convierte este json a un objeto producto
        // esto es para validar con la clase ProductValidation
        //validation.validate(product, result); // bindingResult hereda de error
        if(result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));
    }

    // el valid debe ir en el product porque tenemos que validar la clase entity product
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) { // este id puede venir nulo
        // esto es para validar con la clase ProductValidation
        //validation.validate(product, result);
        if(result.hasFieldErrors()) {
            return validation(result);
        }
        Optional<Product> productOptional = service.update(id, product);
        if(productOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(productOptional.orElseThrow()); // save inserta cuando el id viene null
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = service.delete(id); // el delete tiene toda la lógica para borrar el producto
        if(productOptional.isPresent()){
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    // este metodo puede devolver Map<String, String> o signo de pregunta, mejor la segunda opción
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        
        result.getFieldErrors().forEach(err -> {
            // err.getField() al hacer post puede ser name, price o description
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        // es lo mismo que ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
        // es lo mismo que ResponseEntity.status(400).body(errors)
        return ResponseEntity.badRequest().body(errors);
    }
    
}
