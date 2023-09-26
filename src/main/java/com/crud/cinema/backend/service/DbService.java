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
import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<Employee> getEmployeesWithId(long id) {
        return getAllEmployees().stream()
                .filter(emp -> emp.getId().toString().contains(String.valueOf(id)))
                .collect(Collectors.toSet());
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
    public Set<Movie> getMoviesWithId(String id) {
        return getAllMovies().stream()
                .filter(mov -> mov.getId().toString().contains((id)))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithTitle(String title) {
        String lowerCaseTitle = title.toLowerCase();
        return getAllMovies().stream()
                .filter(mov -> mov.getTitle().toLowerCase().contains(lowerCaseTitle))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithDescription(String desc) {
        String lowerCaseDesc = desc.toLowerCase();
        return getAllMovies().stream()
                .filter(mov -> mov.getDescription().toLowerCase().contains(lowerCaseDesc))
                .collect(Collectors.toSet());
    }
    public Set<Movie> getMoviesWithYear(String year) {
        return getAllMovies().stream()
                .filter(mov -> mov.getYear().contains(year))
                .collect(Collectors.toSet());
    }
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    public boolean deleteMovieById(long id) {
        movieRepository.deleteById(id);
        return true;
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
    public boolean deleteRoomById(long id) {
        roomRepository.deleteById(id);
        return true;
    }

    public Set<Room> getRoomsWithId(String id) {
        return getAllRooms().stream()
                .filter(room -> room.getId().toString().contains(id))
                .collect(Collectors.toSet());
    }

    public Set<Room> getRoomsWithSeats(String seats) {
        return getAllRooms().stream()
                .filter(room -> room.getSeats().contains(seats))
                .collect(Collectors.toSet());
    }

    public Set<Room> getRoomsWithEmployees(String searchString) {
        return getAllRooms().stream()
                .filter(room -> room.getEmployees()
                        .stream()
                        .anyMatch(emp ->
                                emp.getFirstName().toLowerCase().contains(searchString.toLowerCase()) ||
                                        emp.getLastName().toLowerCase().contains(searchString.toLowerCase())))
                .collect(Collectors.toSet());
    }
}
