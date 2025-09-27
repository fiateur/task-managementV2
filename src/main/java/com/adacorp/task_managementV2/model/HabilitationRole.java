package com.adacorp.task_managementV2.model;

import com.adacorp.task_managementV2.auditConfig.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name="habilitation_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabilitationRole extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "habilitation_id")
    private Habilitation habilitation ;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role ;

    private boolean activated ;
}
