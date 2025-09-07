package com.adacorp.task_managementV2.repository;

import com.adacorp.task_managementV2.model.State;
import com.adacorp.task_managementV2.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public static final String ELEVE_TETU = "TOTO" ;

    int countByState_Code (String code);
    long count();
    int countAllByState_Code(String stateCode);
    long countByState(State state);
    
    List<Task> findALlByState_CodeOrderByDateCreationDesc(String code) ;

    Optional<Task> findByName(String name) ;

    // Transformation du Replace regular String literal to TextBlocks to move concaténation
    @Query
            (
                nativeQuery = true ,
                value =
                """
                    SELECT * 
                    FROM task a
                    JOIN utilisateur_task b ON b.task_id = a.id 
                    JOIN state c ON c.id = a.state_id
                    WHERE a.state_id = :state_id
                    AND a.date_creation BETWEEN :dateDebut AND :dateFin
                    ORDER BY a.date_creation DESC
                """
            )
    List<Task> findAllByStateIdAndDateCreationBetweenNativeQuery(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin, @Param("state_id")  Long state_id) ;


    @Query(nativeQuery = true , value =
            " SELECT * FROM task a " +
            " JOIN state b ON b.id = a.state_id " +
            " JOIN utilisateur_task c ON c.task_id = a.id " +
            " JOIN utilisateur d ON d.id = c.utilisateur_id " +
            " WHERE a.date_creation BETWEEN :dateDebut AND :dateFin AND d.id = :utilisateur_id " +
            " ORDER BY a.date_creation DESC")
    List<Task> findAllByDateCreationBetweenJoinUtilisateurTaskNativeQuery(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin, @Param("utilisateur_id")  Long utilisateur_id) ;

    @Query(nativeQuery = true , value =
            " select * FROM task a " +
            " JOIN state b ON b.id = a.state_id " +
            " JOIN utilisateur_task c ON c.task_id = a.id " +
            " JOIN utilisateur u ON u.id = c.utilisateur_id " +
            " WHERE a.state_id = :state_id  and a.date_creation BETWEEN :dateDebut AND :dateFin AND u.id = :utilisateur_id " +
            " ORDER BY a.date_creation DESC")
    List<Task> findAllByStateIdAndDateCreationBetweenJoinUtilisateurTaskNativeQuery(@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin, @Param("state_id")  Long state_id, @Param("utilisateur_id")  Long utilisateur_id) ;



    @Query(nativeQuery = true , value =
            " select * FROM task a " +
            " JOIN state b ON b.id = a.state_id " +
            " JOIN utilisateur_task c ON c.task_id = a.id " +
            " JOIN utilisateur u ON u.id = c.utilisateur_id " +
            " WHERE a.state_id = :state_id  and a.date_creation BETWEEN :dateDebut AND :dateFin " +
            " ORDER BY a.date_creation DESC")
    List<Task> findAllByDateCreationBetweenNativeQuery (@Param("dateDebut") String dateDebut, @Param("dateFin") String dateFin ) ;

    @Query(value = "FROM Task a " +
            " JOIN UtilisateurTask b ON b.task.id = a.id " +
            " JOIN State c ON c.id = a.state.id " +
            " JOIN Utilisateur d ON d.id = b.utilisateur.id " +
            " WHERE a.dateCreation BETWEEN :dateDebut AND :dateFin ")
    List<Task> findAllByDateCreationBetweenQuery (@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin ) ;

    List<Task> findAllByDateCreationBetween (Date dateDebut, Date dateFin ) ;


}
