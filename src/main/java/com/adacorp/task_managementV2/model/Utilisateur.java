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
@Table(name="utilisateur")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur extends AbstractEntity implements Serializable {

    @Column(name = "firstName", nullable = false)
    private String firstName ;

    @Column(name = "lastName", nullable = false)
    private String lastName ;

    @Column(name = "sexe", nullable = false)
    private char sexe ;

    @Column(name = "telephone", nullable = false)
    private String telephone ;

    @Column(name = "email", nullable = false)
    private String email ;

    @Column(name = "password", nullable = false)
    private String password ;

    @Transient
    private String confirmPassword ;

    @Column(name = "is_account_non_locked", nullable = false)
    private boolean isAccountNonLocked ;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role ;

    // Une Habilitation peut être affecté à 0.* une liste de HabilitationRole
    @OneToMany(mappedBy = "utilisateur")
    private List<UtilisateurTask> utilisateurTaskList = new ArrayList<>();

}
