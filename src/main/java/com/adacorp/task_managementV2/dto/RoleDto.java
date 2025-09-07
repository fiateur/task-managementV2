package com.adacorp.task_managementV2.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private long id ;
    private String code ;
    private String libelle ;
    private List<UtilisateurDto> utilisateurDtoList = new ArrayList<>();
    private List<HabilitationRoleDto> habilitationRoleDtoList = new ArrayList<>();
}
