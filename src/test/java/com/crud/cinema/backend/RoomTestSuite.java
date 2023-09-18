package com.crud.cinema.backend;

import com.crud.cinema.backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomTestSuite {

    @Autowired
    private RoomRepository roomRepository;

}
