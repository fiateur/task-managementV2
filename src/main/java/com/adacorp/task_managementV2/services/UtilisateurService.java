package com.adacorp.task_managementV2.services;

import com.adacorp.task_managementV2.model.Utilisateur;
import org.springframework.data.repository.query.Param;

import java.util.Date;
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

    /**
     * FDE_77421 : Service du Batch de Désactivation des Comptes
     * param limitDays
     */
    void desActivateInactiveAccounts(int limitDays);

    /**
     * FDE_77421 : Service du Batch de Désactivation des Comptes
     * param limitDate
     */
    int desActivateInactiveAccountsWithLimitDate(Date limitDate);

    /**
     * FDE_77421 : Service du Batch de Réactivation des Comptes
     * param limitDays
     */
    void reActivateInactiveAccounts(int limitDays);

    /**
     * FDE_77421 : Service du Batch de Réactivation des Comptes
     * param limitDate
     */
    int reActivateInactiveAccountsWithLimitDate(Date limitDate);

}
