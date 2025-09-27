package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.model.Task;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    void save(Task task) ;

    void update (Task task);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Long id);

    //la surcharge de méthode (Overloading : même Type de Retour, même nom, Argument différents)
    void delete(Task task);

    Optional<Task> findByName (String code);

    Optional<Task> findOne (Long id);

    List<Task> findAll();

    // ---------------------------------------------------------------------------------

    long count();

    int countByStateCode (String code);

    int countAllByStateCode(String code);

    long countByState(State state);

    List<Task> findAllStateCode(String code);

    // ---------------------------------------------------------------------------------

    List<Task> findAllByStateIdAndDateCreationBetweenNativeQuery(String dateDebut, String dateFin, Long state_id) ;

    List<Task> findAllByDateCreationBetweenJoinUserTaskNativeQuery(String dateDebut, String dateFin, Long user_id) ;

    List<Task> findAllByStateIdAndDateCreationBetweenJoinUserTaskNativeQuery(String dateDebut, String dateFin, Long state_id, Long user_id) ;

    List<Task> findAllByDateCreationBetweenQuery (Date dateDebut, Date dateFin ) ;

    List<Task> findAllByDateCreationBetweenNativeQuery (String dateDebut, String dateFin ) ;

    // ---------------------------------------------------------------------------------


}
