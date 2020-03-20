package com.atiumaddict.springbatchdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfectionDto {
    private Long id;
    private String name;
    private String type;
    private String description;
}
