package com.adacorp.task_managementV2.model;

import com.adacorp.task_managementV2.auditConfig.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AbstractEntity implements Serializable {

    @Column(name = "code", nullable = false)
    private String code ;

    @Column(name = "libelle", nullable = false)
    private String libelle ;

    // Un Role peut être affecté à 0.* une liste de Utilisateurs
    @OneToMany(mappedBy = "role")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    // Un Role peut être affecté à 0.* une liste de HabilitationRole
    @OneToMany(mappedBy = "role")
    private List<HabilitationRole> habilitationRoleList = new ArrayList<>();

    // private List<Utilisateur> utilisateurList = new ArrayList<>();
    // private Set<Utilisateur> utilisateurSet = new HashSet<>();
    // private Map<T,V> utilisateurMap = new HashMap<Long, Utilisateur>();
}
