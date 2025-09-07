package com.adacorp.task_managementV2.repository;

import com.adacorp.task_managementV2.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}
