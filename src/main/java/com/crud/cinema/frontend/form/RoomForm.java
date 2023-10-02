package com.crud.cinema.frontend.form;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.EmployeeDbService;
import com.crud.cinema.backend.service.RoomDbService;
import com.crud.cinema.frontend.view.RoomView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.util.HashSet;
import java.util.Set;

public class RoomForm extends FormLayout {
    private final RoomView roomView;
    private final TextField seats = new TextField("Seats");
    private final TextField name = new TextField("Name");
    private final Button save = new Button("Save");
    private final Binder<Room> roomBinder = new Binder<>(Room.class);
    private final EmployeeDbService employeeDbService;
    private final RoomDbService roomDbService;
    private Set<Employee> selectedEmployees = new HashSet<>();

    public RoomForm(RoomView roomView, EmployeeDbService employeeDbService, RoomDbService roomDbService) {
        this.roomView = roomView;
        this.employeeDbService = employeeDbService;
        this.roomDbService = roomDbService;
        HorizontalLayout textFields = new HorizontalLayout(name, seats, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        MultiSelectComboBox<Employee> employeeComboBox = createEmployeeComboBox();

        add(textFields, employeeComboBox);
        seats.setWidth("200px");
        name.setWidth("200px");

        roomBinder.bindInstanceFields(this);
        save.getStyle().set("margin-top", "auto");
        save.addClickListener(e -> save());
    }

    private void save() {
        Room room = roomBinder.getBean();
        room.setEmployees(new HashSet<>());
        for (Employee employee : employeeDbService.getEmployeesWithId(selectedEmployees.stream().map(Employee::getId).toList())) {
            room.addEmployee(employee);
        }

        roomDbService.saveRoom(room);
        roomView.refresh();
        setRoom(null);
    }

    public void setRoom(Room room) {
        roomBinder.setBean(room);

        if (room == null) {
            setVisible(false);
        } else {
            setVisible(true);
            name.focus();
        }
    }

    private MultiSelectComboBox<Employee> createEmployeeComboBox() {
        MultiSelectComboBox<Employee> employeeComboBox = new MultiSelectComboBox<>();
        employeeComboBox.setItems(employeeDbService.getAllEmployees());
        employeeComboBox.setAllowCustomValue(true);
        employeeComboBox.setLabel("Employees");
        employeeComboBox.setItemLabelGenerator(
                person -> person.getFirstName() + " " + person.getLastName());
        employeeComboBox.addValueChangeListener(event -> {
            selectedEmployees.clear();
            selectedEmployees.addAll(event.getValue());
        });
        return employeeComboBox;
    }

}
