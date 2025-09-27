package com.adacorp.task_managementV2.model;

import com.adacorp.task_managementV2.auditConfig.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="utilisateur_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurTask extends AbstractEntity implements Serializable {

    private Date assignmentDate ;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur ;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task ;
}
