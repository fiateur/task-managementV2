package com.adacorp.task_managementV2.repository;

import com.adacorp.task_managementV2.model.Habilitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabilitationRepository extends JpaRepository<Habilitation, Long> {
    List<Habilitation> findAllByCode(String code) ;

    @Query(nativeQuery = true , value = "select * FROM habilitation h WHERE h.code = :code ORDER BY h.creation_date DESC")
    List<Habilitation> findAllByCodeNativeQuery(@Param("code") String code) ;

    @Query(value = "FROM Habilitation h WHERE h.code = :code ORDER BY h.dateCreation DESC")
    List<Habilitation> findAllByCodeJpqlQuery(@Param("code") String code) ;

    @Query(nativeQuery = true , value = "select * FROM habilitation h WHERE h.code = :code AND h.libelle = :libelle ORDER BY h.creation_date DESC")
    List<Habilitation> findAllByCodeAndLibelleNativeQuery(@Param("code") String code, @Param("libelle") String libelle) ;
}
