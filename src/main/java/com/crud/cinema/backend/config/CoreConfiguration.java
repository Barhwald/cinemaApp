package com.crud.cinema.backend.config;

import com.crud.cinema.backend.repository.EmployeeRepository;
import com.crud.cinema.backend.repository.MovieRepository;
import com.crud.cinema.backend.repository.PerformanceRepository;
import com.crud.cinema.backend.repository.RoomRepository;
import com.crud.cinema.backend.service.DbService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
