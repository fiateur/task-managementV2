package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.*;

import java.util.List;
import java.util.Optional;

public interface HabilitationRoleService {

    void save(HabilitationRole habilitationRole) ;

    void update (HabilitationRole habilitationRole);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(HabilitationRole habilitationRole);

    Optional<HabilitationRole> findOne (HabilitationRole habilitationRole);

    List<HabilitationRole> findByRole (Role role);

    List<HabilitationRole> findByHabilitation (Habilitation habilitation);

    List<HabilitationRole> findAll();
}
