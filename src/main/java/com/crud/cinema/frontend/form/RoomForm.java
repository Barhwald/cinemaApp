package com.crud.cinema.frontend.form;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.DbService;
import com.crud.cinema.frontend.view.RoomView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class RoomForm extends FormLayout {
    private final RoomView roomView;
    private final TextField seats = new TextField("Seats");
    private final Button save = new Button("Save");
    private final Binder<Room> roomBinder = new Binder<>(Room.class);
    private DbService dbService;
    private Set<Employee> selectedEmployees = new HashSet<>();

    public RoomForm(RoomView roomView, DbService dbService) {
        this.roomView = roomView;
        this.dbService = dbService;
        HorizontalLayout textFields = new HorizontalLayout(seats, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        MultiSelectComboBox<Employee> employeeComboBox = createEmployeeComboBox();

        add(textFields, employeeComboBox);
        seats.setWidth("200px");

        roomBinder.bindInstanceFields(this);
        save.getStyle().set("margin-top", "auto");
        save.addClickListener(e -> save());
    }

    private void save() {
        Room room = roomBinder.getBean();
        room.setEmployees(new HashSet<>());
        selectedEmployees.forEach(room::addEmployee);
        dbService.saveRoom(room);
        roomView.refresh();
        setRoom(null);

        System.out.println("Room before saving: " + room);
        System.out.println("Employees before saving: " + room.getEmployees());
        Room savedRoom = dbService.getRoomWithId(room.getId());
        System.out.println("Room after saving: " + savedRoom);
        System.out.println("Employees after saving: " + savedRoom.getEmployees());
    }

    public void setRoom(Room room) {
        roomBinder.setBean(room);

        if (room == null) {
            setVisible(false);
        } else {
            setVisible(true);
            seats.focus();
        }
    }

    private MultiSelectComboBox<Employee> createEmployeeComboBox() {
        MultiSelectComboBox<Employee> employeeComboBox = new MultiSelectComboBox<>();
        employeeComboBox.setItems(dbService.getAllEmployees());
        employeeComboBox.setAllowCustomValue(true);
        employeeComboBox.setItemLabelGenerator(
                person -> person.getFirstName() + " " + person.getLastName());
        employeeComboBox.addValueChangeListener(event -> {
            selectedEmployees.clear();
            selectedEmployees.addAll(event.getValue());
        });
        return employeeComboBox;
    }

}
