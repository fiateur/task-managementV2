package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.Task;
import com.adacorp.task_managementV2.model.Utilisateur;
import com.adacorp.task_managementV2.model.UtilisateurTask;

import java.util.List;
import java.util.Optional;

public interface UtilisateurTaskService {

    void save(UtilisateurTask utilisateurTask) ;

    void update (UtilisateurTask utilisateurTask);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(UtilisateurTask utilisateurTask);

    Optional<UtilisateurTask> findOne (Long id);

    Optional<UtilisateurTask> findByTask (Task task);

    List<UtilisateurTask> findAll();

    List<UtilisateurTask> findByUtilisateur (Utilisateur utilisateur);

}
