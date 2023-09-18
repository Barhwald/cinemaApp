package com.crud.cinema.backend.repository;

import com.crud.cinema.backend.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Override
    List<Room> findAll();
}
