package com.adacorp.task_managementV2.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDto {

    private long id ;
    private String code;
    private String description ;
    private List<TaskDto> taskDtoList = new ArrayList<>();
}
