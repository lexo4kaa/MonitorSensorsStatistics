package com.krupenko.MonitorSensorsStatistics.service;

import com.krupenko.MonitorSensorsStatistics.database.entity.SensorStatistics;
import com.krupenko.MonitorSensorsStatistics.database.repository.SensorStatisticsRepository;
import com.krupenko.MonitorSensorsStatistics.dto.LoginDto;
import com.krupenko.MonitorSensorsStatistics.dto.SensorReadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorStatisticsService {

    private final RestTemplate restTemplate;
    private final SensorStatisticsRepository statisticsRepository;

    @Value("${app.username}")
    private String username;
    @Value("${app.password}")
    private String password;
    @Value("${app.api-domain}")
    private String apiDomain;

    public void collectSensorData() {
        ResponseEntity<String> loginResponse = sendLoginRequest();
        if (!loginResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Login response has {} status code. Response body:\n{}",
                    loginResponse.getStatusCode(), loginResponse.getBody());
            return;
        }

        List<String> cookies = loginResponse.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (CollectionUtils.isEmpty(cookies)) {
            log.error("Login response has no cookies");
            return;
        }

        ResponseEntity<List<SensorReadDto>> response = sendGetSensorsRequest(cookies);
        List<SensorReadDto> sensors = response.getBody();

        if (sensors != null) {
            save(sensors);
        }
    }

    private ResponseEntity<String> sendLoginRequest() {
        return restTemplate.exchange(apiDomain + "api/v1/login",
                HttpMethod.POST,
                new HttpEntity<>(new LoginDto(username, password)),
                String.class
        );
    }

    private ResponseEntity<List<SensorReadDto>> sendGetSensorsRequest(List<String> cookies) {
        String cookieString = String.join(";", cookies);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, cookieString);
        return restTemplate.exchange(apiDomain + "api/v1/sensors",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });
    }

    private void save(List<SensorReadDto> sensors) {
        int totalSensors = sensors.size();
        Map<String, Long> sensorsByType = sensors.stream()
                .collect(Collectors.groupingBy(sensor -> sensor.getType().getValue(), Collectors.counting()));

        SensorStatistics statistics = new SensorStatistics();
        statistics.setTotal(totalSensors);
        statistics.setCountByType(sensorsByType);
        statistics.setDate(new Date());
        statisticsRepository.save(statistics);
    }
}

