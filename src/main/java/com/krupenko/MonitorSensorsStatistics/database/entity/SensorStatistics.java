package com.krupenko.MonitorSensorsStatistics.database.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sensor_statistics")
public class SensorStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int total;

    @ElementCollection
    @CollectionTable(name = "sensor_statistics_count_by_type",
            joinColumns = @JoinColumn(name = "sensor_statistics_id"))
    @MapKeyColumn(name = "type")
    private Map<String, Long> countByType;

    private Date date;
}

