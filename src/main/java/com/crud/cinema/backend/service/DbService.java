package com.crud.cinema.backend.service;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.EmployeeRepository;
import com.crud.cinema.backend.repository.MovieRepository;
import com.crud.cinema.backend.repository.PerformanceRepository;
import com.crud.cinema.backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {

    private final EmployeeRepository employeeRepository;
    private final MovieRepository movieRepository;
    private final PerformanceRepository performanceRepository;
    private final RoomRepository roomRepository;

    public Employee saveEmployee(final Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee getEmployeeWithId(long id) {
        return employeeRepository.findById(id).isPresent() ? employeeRepository.findById(id).get() : null;
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }
    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
    }
    public Movie getMovieWithId(long id) {
        return movieRepository.findById(id).isPresent() ? movieRepository.findById(id).get() : null;
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public void deleteMovieById(long id) {
        movieRepository.deleteById(id);
    }
    public Performance savePerformance(final Performance performance) {
        return performanceRepository.save(performance);
    }
    public Performance getPerformanceWithId(long id) {
        return performanceRepository.findById(id).isPresent() ? performanceRepository.findById(id).get() : null;
    }
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }
    public void deletePerformanceById(long id) {
        performanceRepository.deleteById(id);
    }
    public Room saveRoom(final Room room) {
        return roomRepository.save(room);
    }
    public Room getRoomWithId(long id) {
        return roomRepository.findById(id).isPresent() ? roomRepository.findById(id).get() : null;
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
    public void deleteRoomById(long id) {
        roomRepository.deleteById(id);
    }

}
