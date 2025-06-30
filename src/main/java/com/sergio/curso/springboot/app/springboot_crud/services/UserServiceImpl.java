package com.sergio.curso.springboot.app.springboot_crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sergio.curso.springboot.app.springboot_crud.entities.Role;
import com.sergio.curso.springboot.app.springboot_crud.entities.User;
import com.sergio.curso.springboot.app.springboot_crud.repositories.RoleRepository;
import com.sergio.curso.springboot.app.springboot_crud.repositories.UserRepository;

// esta clase utiliza las operaciones del userRepository
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    // inyectamos el password encoder marcado con bean
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true) // esto le dice a spring: Este método es solo de lectura, no va a modificar la base de datos
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        //todo usuario que se registre debe tener ROLE_USER
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        // esto es igual:  role -> roles.add(role) ==== roles::add
        optionalRoleUser.ifPresent(roles::add);

        if(user.isAdmin()) { //si es true

            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            // es igual: role -> roles.add(role) ==== roles::add
            optionalRoleAdmin.ifPresent(roles::add);
        }

        user.setRoles(roles); // le asignamos la lista de roles al user
        
        //encriptamos el password con el passwordEncoder
        // user.getPassword() es la contraseña del objeto user que se le pasa al metodo desde el request (que no esta codificado)
        // seteamos la contraseña encriptada en el user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user); // guardamos el usuario con todos los cambios
    }

}
