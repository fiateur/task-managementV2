package com.adacorp.task_managementV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private long id ;
    private String name ;
    private String description ;
    private Date dateCreation ;
    private StateDto stateDto ;

    private List<UtilisateurTaskDTo> utilisateurTaskDtoList = new ArrayList<>();
}
