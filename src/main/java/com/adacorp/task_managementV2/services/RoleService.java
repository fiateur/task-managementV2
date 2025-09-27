package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    void save(Role role) ;

    void update (Role role);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Role role);

    Optional<Role> findByCode (String code);

    Optional<Role> findOne (Long id);

    List<Role> findAll();

}
