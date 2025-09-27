package com.adacorp.task_managementV2.repository;

import com.adacorp.task_managementV2.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    @Query(nativeQuery = true, value = "select * FROM utilisateur u WHERE u.email = :email ")
    Utilisateur findOneByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "select * FROM utilisateur u WHERE u.email = :email ")
    Optional<Utilisateur> findOptionalOneByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "select * FROM utilisateur u WHERE u.email = :email ")
    Optional<Utilisateur> findByEmail(@Param("email") String email);

    List<Utilisateur> findAllByEmail(String email);

    List<Utilisateur> findAllByFirstName(String firstName);

    @Query(nativeQuery = true, value = "select * FROM utilisateur u WHERE u.first_name LIKE %:first_name% ) ")
    List<Utilisateur> findAllByFirstNameLikeNativeQuery(@Param("first_name") String firstName);

    @Query(nativeQuery = true, value = "select * FROM utilisateur u WHERE u.first_name LIKE %:first_name% ) ")
    Optional<Utilisateur> findOneByFirstNameLikeNativeQuery(@Param("first_name") String firstName);

    boolean existsByEmail(String email);

    /**
     * Supprime toutes les utilisateurs identifiées
     * @param id les identifiants des utilisateurs à supprimer
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Utilisateur u WHERE u.id IN (?1)")
    public void delete(Long id);

    /**
     * Force à "actif" les Comptes des utilisateurs identifiées
     * @param ids les identifiants des utilisateurs à ne plus être authentifié
     */
    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.isAccountNonLocked = true WHERE u.id IN (?1)")
    public void activerCompteUtilisateur(long[] ids);

    /**
     * Force à "inactif" tous les utilisateurs identifiés
     * @param ids les identifiants des utilisateurs à désactiver
     */
    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.isAccountNonLocked = false WHERE u.id IN (?1)")
    public void desActiverCompteUtilisateur(long[] ids);

    /**
     * FDE_77421 : DAO du Batch de Désactivation des Comptes
     * param limitDays
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true , value = "UPDATE public.utilisateur SET is_account_non_locked = false " +
            "WHERE is_account_non_locked = true " +
            "AND (date_derniere_connexion IS NULL OR (CAST(CURRENT_DATE+1 AS DATE) - CAST(date_derniere_connexion AS DATE)) >= :limitDays)")
    void desActivateInactiveAccounts(int limitDays);

    /**
     * FDE_77421 : DAO du Batch de Désactivation des Comptes
     * param limitDate
     */
    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.isAccountNonLocked = false " +
            "WHERE u.isAccountNonLocked = true " +
            "AND (u.dateDerniereConnexion IS NULL OR u.dateDerniereConnexion < :limitDate)")
    int desActivateInactiveAccountsWithLimitDate(@Param("limitDate") Date limitDate);

    /**
     * FDE_77421 : DAO du Batch de Réactivation des Comptes
     * param limitDays
     */
    @Modifying
    @Transactional
    @Query(nativeQuery = true , value = "UPDATE public.utilisateur SET is_account_non_locked = true " +
            "WHERE is_account_non_locked = false " +
            "AND (date_derniere_connexion IS NOT NULL AND (CAST(CURRENT_DATE+1 AS DATE) - CAST(date_derniere_connexion AS DATE)) < :limitDays)")
    void reActivateInactiveAccounts(int limitDays);

    /**
     * FDE_77421 : DAO du Batch de Désactivation des Comptes
     * param limitDate
     */
    @Modifying
    @Transactional
    @Query("UPDATE Utilisateur u SET u.isAccountNonLocked = true " +
            "WHERE u.isAccountNonLocked = false " +
            "AND (u.dateDerniereConnexion IS NOT NULL AND u.dateDerniereConnexion > :limitDate)")
    int reActivateInactiveAccountsWithLimitDate(@Param("limitDate") Date limitDate);
}
