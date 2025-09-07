package com.adacorp.task_managementV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabilitationDto {

    private long id ;
    private String code ;
    private String libelle ;
    private List<HabilitationRoleDto> habilitationRoleDtoList = new ArrayList<>();
}
