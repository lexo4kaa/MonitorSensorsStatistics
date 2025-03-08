package com.krupenko.MonitorSensorsStatistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SensorReadDto {
    private Integer id;
    private String name;
    private String model;
    private RangeReadDto range;
    private TypeReadDto type;
    private UnitReadDto unit;
    private String location;
    private String description;
}
