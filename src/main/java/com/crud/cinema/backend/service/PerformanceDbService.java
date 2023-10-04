package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PerformanceDbService {

    private final PerformanceRepository performanceRepository;


    public Performance savePerformance(final Performance performance) {
        return performanceRepository.save(performance);
    }
    public Performance getPerformanceWithId(long id) {
        return performanceRepository.findById(id).isPresent() ? performanceRepository.findById(id).get() : null;
    }
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }
    public boolean deletePerformanceById(long id) {
        performanceRepository.deleteById(id);
        return true;
    }

    public Set<Performance> getPerformancesWithId(String id) {
        return getAllPerformances().stream()
                .filter(per -> per.getId().toString().contains(id))
                .collect(Collectors.toSet());
    }

    public Set<Performance> getPerformancesWithMovie(String title) {
        String lowerCaseTitle = title.toLowerCase();
        return getAllPerformances().stream()
                .filter(per -> per.getMovie().getTitle().toLowerCase().contains(lowerCaseTitle))
                .collect(Collectors.toSet());
    }

    public Set<Performance> getPerformancesWithRoom(String roomName) {
        String lowerCaseRoomName = roomName.toLowerCase();
        return getAllPerformances().stream()
                .filter(per -> per.getRoom().getName().toLowerCase().contains(lowerCaseRoomName))
                .collect(Collectors.toSet());
    }

    public Set<Performance> getPerformancesWithDate(String date) {
        String lowerCaseDate = date.toLowerCase();
        return getAllPerformances().stream()
                .filter(per -> per.getDate().toLowerCase().contains(lowerCaseDate))
                .collect(Collectors.toSet());
    }

    public Set<Performance> getPerformancesWithTime(String time) {
        String lowerCaseTime = time.toLowerCase();
        return getAllPerformances().stream()
                .filter(per -> per.getTime().toLowerCase().contains(lowerCaseTime))
                .collect(Collectors.toSet());
    }

}
