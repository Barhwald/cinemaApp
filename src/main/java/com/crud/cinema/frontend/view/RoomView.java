package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.DbService;
import com.crud.cinema.frontend.form.RoomForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route("/rooms")
public class RoomView extends VerticalLayout {

    private final DbService dbService;
    private final Grid<Room> roomGrid = new Grid<>(Room.class);
    private final Button goToDashboard = new Button("Dashboard");
    private final TextField filter1 = new TextField();
    private final TextField filter2 = new TextField();
    private final TextField filter3 = new TextField();
    private final Button addNewRoom = new Button("Add new room");

    @Autowired
    public RoomView(DbService dbService) {
        this.dbService = dbService;
        RoomForm form = new RoomForm(this, dbService);

        filter1.setPlaceholder("Filter by id");
        filter1.setClearButtonVisible(true);
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> updateId());
        filter2.setPlaceholder("Filter by seats");
        filter2.setClearButtonVisible(true);
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(e -> updateSeats());
        filter3.setPlaceholder("Filter by employees");
        filter3.setClearButtonVisible(true);
        filter3.setValueChangeMode(ValueChangeMode.EAGER);
        filter3.addValueChangeListener(e -> updateEmployees());

        roomGrid.setColumns("id", "seats");

        roomGrid.addColumn(room -> formatEmployees(room.getEmployees()))
                .setHeader("Employees");

        roomGrid.addColumn(new ComponentRenderer<>(room -> {
            List<Performance> performances = room.getPerformances();
            String formattedPerformances = performances.stream()
                    .map(perf -> perf.getId().toString())
                    .collect(Collectors.joining(", "));
            return new Span(formattedPerformances);
        })).setHeader("Performances");

        Grid.Column<Room> deleteColumn = roomGrid.addColumn(
                        new ComponentRenderer<>(this::createDeleteButton))
                .setHeader("Actions");
        deleteColumn.setWidth("100px");

        goToDashboard.addClickListener(e ->
                goToDashboard.getUI().ifPresent(ui ->
                        ui.navigate("")));

        addNewRoom.addClickListener(e -> {
            roomGrid.asSingleSelect().clear();
            form.setRoom(new Room());
        });

        H1 heading = new H1("Rooms");
        heading.getStyle().set("margin", "auto");

        HorizontalLayout filters = new HorizontalLayout(filter1, filter2, filter3, goToDashboard, addNewRoom);
        VerticalLayout mainContent = new VerticalLayout(roomGrid, form);

        mainContent.setSizeFull();
        roomGrid.setSizeFull();

        add(heading, filters, mainContent);
        form.setRoom(null);
        setSizeFull();
        refresh();

        roomGrid.asSingleSelect().addValueChangeListener(event -> {
            Room selectedRoom = event.getValue();
            form.setRoom(selectedRoom);
        });
    }

    public void refresh() {
        roomGrid.setItems(dbService.getAllRooms());
    }

    public void updateId() {
        roomGrid.setItems(dbService.getRoomsWithId(filter1.getValue()));
    }

    public void updateSeats() {
        roomGrid.setItems(dbService.getRoomsWithSeats(filter2.getValue()));
    }

    public void updateEmployees() {
        roomGrid.setItems(dbService.getRoomsWithEmployees(filter3.getValue()));
    }

    private Component createDeleteButton(Room room) {
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(event -> deleteRoom(room));
        return deleteButton;
    }

    private void deleteRoom(Room room) {
        boolean deleted = dbService.deleteRoomById(room.getId());

        if (deleted) {
            refresh();
        } else {
            Notification.show("Failed to delete the room.");
        }
        refresh();
    }

    private String formatEmployees(Set<Employee> employees) {
        if (employees == null || employees.isEmpty()) {
            return "";
        }

        StringBuilder formattedEmployees = new StringBuilder();
        for (Employee employee : employees) {
            formattedEmployees.append(employee.getFirstName())
                    .append(" ")
                    .append(employee.getLastName())
                    .append(", ");
        }
        formattedEmployees.setLength(formattedEmployees.length() - 2);
        return formattedEmployees.toString();
    }
}
