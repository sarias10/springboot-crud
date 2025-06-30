package com.sergio.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sergio.curso.springboot.app.springboot_crud.entities.User;
import com.sergio.curso.springboot.app.springboot_crud.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// le dice a spring que este metodo va a devolver directamente un JSON y no una vista HTML
/**
 * Esta clase está anotada con @RestController, lo que la convierte en un controlador REST.
 *
 * - Combina @Controller y @ResponseBody.
 * - Todos los métodos devuelven datos (normalmente en formato JSON) directamente en la respuesta.
 * - No se usan vistas (como JSP o Thymeleaf), ya que es para APIs RESTful.
 *
 * Ideal para servicios backend donde los endpoints solo devuelven datos a un frontend o cliente.
 */
@RestController
@RequestMapping("/api/users") // le agregamos una ruta base
public class UserController {

    @Autowired
    private UserService service;
    
    @GetMapping //ruta base
    public List<User> list() {
        return service.findAll();
    }

    @PostMapping
    // se pone ? porque podria devolver un map del metodo validation
    // @Valid le dice a Spring que valide automáticamente el objeto User que llega en el cuerpo del request (@RequestBody), según las anotaciones que tenga en sus campos (como @NotNull, @Size, etc.).
    // 1. Spring valida el objeto user al recibirlo.
    // 2. Si hay errores, los pone en BindingResult result.
    // 3. Tú puedes revisar esos errores con:
    // if (result.hasFieldErrors()) {
    // return validation(result);
    // }
    // 4. Si todo está bien, se guarda el user.
    /**
     * @Valid valida automáticamente el objeto User según las anotaciones en sus campos (@NotBlank, @Email, etc.).
     * Si hay errores, se guardan en BindingResult y se pueden manejar dentro del método.
     * Así evitamos guardar datos inválidos en la base de datos.
     */
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user)); // devuelve la respuesta del service
    }

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
