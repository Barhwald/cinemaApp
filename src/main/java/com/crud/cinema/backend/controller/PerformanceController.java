package com.crud.cinema.backend.controller;

import com.crud.cinema.backend.domain.EmployeeDto;
import com.crud.cinema.backend.domain.PerformanceDto;
import com.crud.cinema.backend.facade.PerformanceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceFacade performanceFacade;

    @GetMapping
    public ResponseEntity<List<PerformanceDto>> getPerformances() {
        return ResponseEntity.ok(performanceFacade.getPerformancesList());
    }

    @GetMapping(value = "{performanceId}")
    public ResponseEntity<PerformanceDto> getPerformanceWithId(@PathVariable Long performanceId) {
        return ResponseEntity.ok(performanceFacade.getPerformanceWithId(performanceId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPerformance(@RequestBody PerformanceDto performanceDto) {
        performanceFacade.createPerformance(performanceDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<PerformanceDto> updatePerformance(@RequestBody PerformanceDto performanceDto) {
        return ResponseEntity.ok(performanceFacade.updatePerformance(performanceDto));
    }

    @DeleteMapping(value = "{performanceId}")
    public ResponseEntity<Void> deletePerformance(@PathVariable Long performanceId) {
        performanceFacade.deletePerformance(performanceId);
        return ResponseEntity.ok().build();
    }

}
