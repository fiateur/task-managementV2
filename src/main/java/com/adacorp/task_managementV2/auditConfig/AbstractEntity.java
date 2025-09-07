package com.adacorp.task_managementV2.auditConfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


/*
 *  @EntityListeners(AuditingEntityListener.class) reste à l'écoute de la classe
 *  pour remplir les champs de cette classe dans les tables
 */


@Data
@Builder
// C'est une classe qui permet de construire un objet (une classe) à partir d'un autre en exposant des méthodes qui contiennent les mêmes noms des attributs
// et renvoyant les mêmes méthodes avec une méthode à la fin qui s'appelle ObjetDTO.builder() qui va construire un objet avec des attributs passés en paramètre
// Cette annotation permet de créer un Builder en utilisant le Design Pattern @Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// Propriétés d'audit automatique (avec JPA Auditing) :
// @EntityListeners(AuditingEntityListener.class) elle écoute cette classe
// et à chaque fois qu'elle trouve @CreatedDate/@LastModifiedDate/@PrePersist
// elle va automatiquement créer et mettre à joue les objets entités dans les BD
public class AbstractEntity<U> implements Serializable
{
    @Id
    //@GeneratedValue
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate // Pour préciser automatiquement à Hibernate que c'est une date de création
    @Column(name = "creationDate", nullable = false, updatable = false)
    //@JsonIgnore // pas besoin de cet attribut lorsque j'invoque mon API.
    private Date dateCreation;

    @LastModifiedDate // Pour préciser automatiquement à Hibernate que c'est une date de modification
    @Column(name = "lastModifiedDate")
    //@JsonIgnore // pas besoin de cet attribut lorsque j'invoque mon API.
    private Date dateModification;

    @PrePersist
    void saveDate (){
        this.dateCreation =  new Date() ;
        this.dateModification =  new Date() ;
    }
}