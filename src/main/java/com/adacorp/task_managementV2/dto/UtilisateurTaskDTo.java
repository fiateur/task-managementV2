package com.adacorp.task_managementV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurTaskDTo {

    private long id ;
    private Date assignmentDate ;
    private UtilisateurDto utilisateurDto ;
    private TaskDto taskDto ;
}
