package com.krupenko.MonitorSensorsStatistics.database.repository;

import com.krupenko.MonitorSensorsStatistics.database.entity.SensorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorStatisticsRepository extends JpaRepository<SensorStatistics, Long> {
}

