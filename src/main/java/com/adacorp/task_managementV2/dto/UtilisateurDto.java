package com.adacorp.task_managementV2.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDto {

    private long id ;
    private String firstName ;
    private String lastName ;
    private char sexe ;
    private String telephone ;
    private String email ;
    private String password ;
    private String confirmPassword ;
    private boolean isAccountNonLocked ;
    private RoleDto roleDto ;

    private List<UtilisateurTaskDTo> utilisateurTaskDtoList = new ArrayList<>();
}
