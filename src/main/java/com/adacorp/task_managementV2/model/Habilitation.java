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
@Table(name="habilitation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habilitation extends AbstractEntity implements Serializable {

    @Column(name = "code", nullable = false)
    private String code ;

    @Column(name = "libelle", nullable = false)
    private String libelle ;

    // Une Habilitation peut être affecté à 0.* une liste de HabilitationRole
    @OneToMany(mappedBy = "habilitation")
    private List<HabilitationRole> habilitationRoleList = new ArrayList<>();
}
