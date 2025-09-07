package com.adacorp.task_managementV2.model;

import com.adacorp.task_managementV2.auditConfig.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name= "task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name ;

    @Column(name = "description", nullable = false)
    private String description ;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state ;

    @Column(name = "date_creation", nullable = false)
    private Date dateCreation ;

    // Une Habilitation peut être affecté à 0.* une liste de HabilitationRole
    @OneToMany(mappedBy = "task")
    private List<UtilisateurTask> utilisateurTaskList = new ArrayList<>();

    @PrePersist
    void saveDate (){
        this.dateCreation = new Date() ;
    }
}
