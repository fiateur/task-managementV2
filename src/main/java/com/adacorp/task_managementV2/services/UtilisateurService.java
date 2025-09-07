package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.Utilisateur;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {

    void save(Utilisateur utilisateur) ;

    void update (Utilisateur utilisateur);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Utilisateur utilisateur);

    Optional<Utilisateur> findOneByFirstName (String firstName);

    List<Utilisateur> findAllByFirstName (String firstName);

    Optional<Utilisateur> findOne (Long id);

    List<Utilisateur> findAll();

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

}
