package com.krupenko.MonitorSensorsStatistics.config;

import com.krupenko.MonitorSensorsStatistics.service.SensorStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final SensorStatisticsService sensorStatisticsService;

    @Scheduled(cron = "${app.cron}")
    public void scheduleSensorStatisticsCollection() {
        sensorStatisticsService.collectSensorData();
    }

}
