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
public class UnitReadDto {
    private Integer id;
    private String value;
}
