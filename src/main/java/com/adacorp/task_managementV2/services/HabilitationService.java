package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.Habilitation;

import java.util.List;
import java.util.Optional;

public interface HabilitationService {

    void save(Habilitation habilitation) ;

    void update (Habilitation habilitation);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Habilitation habilitation);

    Optional<Habilitation> findByCode (String code);

    Optional<Habilitation> findOne (Long id);

    List<Habilitation> findAll();
}
