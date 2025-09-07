package com.adacorp.task_managementV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabilitationRoleDto {

    private HabilitationDto habilitationDto ;
    private RoleDto roleDto ;
    private boolean activated ;
}
