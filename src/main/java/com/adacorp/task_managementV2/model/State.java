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
@Table(name="state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State extends AbstractEntity implements Serializable {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", nullable = false)
    private String description ;

    // Un Role peut être affecté à 0.* une liste des Utilisateurs
    @OneToMany(mappedBy = "state")
    private List<Task> tasks = new ArrayList<>();

    // private List<Task> taskList = new ArrayList<>();
    // private Set<Task> taskSet = new HashSet<>();
    // private Map<T,V> taskMap = new HashMap<Long, Task>();
}
