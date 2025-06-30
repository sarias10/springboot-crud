package com.sergio.curso.springboot.app.springboot_crud.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity // relaciona esta clase con una tabla de la base de datos
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // hace la tabla incremental
    private Long id;

    @Column(unique = true) // username es unico
    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @NotBlank
    private String password;

    // relación unidireccional, cuando obtenemos los usuarios, quiero sus roles. No viceversa
    @ManyToMany
    @JoinTable( // para mapear la tabla intermedia
        name = "users_roles", // mismo nombre que en la base de datos
        joinColumns = @JoinColumn(name="user_id"), // las foreign key
        inverseJoinColumns = @JoinColumn(name="role_id"), // las foreign key,
        uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"})} // las clave user_id + role_id no se repite
    )
    // list de java util
    private List<Role> roles;

    // una bandera que manejamos en la base de datos
    // no es un campo o atributo de la tabla de la BD
    @Transient // ojo de jakarta
    private boolean admin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
}

