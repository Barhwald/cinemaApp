package com.crud.cinema.frontend;

import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.repository.RoomRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/rooms")
public class RoomView extends VerticalLayout {

    private final RoomRepository roomRepository;
    private final Grid<Room> roomGrid = new Grid<>(Room.class);
    private final Button goToDashboard = new Button("Dashboard");

    @Autowired
    public RoomView(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;

        goToDashboard.addClickListener(e ->
                goToDashboard.getUI().ifPresent(ui ->
                        ui.navigate("")));

        roomGrid.setColumns("id", "seats", "employees", "performances");

        VerticalLayout mainContent = new VerticalLayout(roomGrid);
        mainContent.setSizeFull();
        roomGrid.setSizeFull();
        add(mainContent, goToDashboard);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        roomGrid.setItems(roomRepository.findAll());
    }

}
