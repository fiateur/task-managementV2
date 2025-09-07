package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.State;

import java.util.List;
import java.util.Optional;

public interface StateService {

    void save(State state) ;

    void update (State state);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(State state);

    Optional<State> findByCode (String code);

    Optional<State> findOne (Long id);

    List<State> findAll();
}
