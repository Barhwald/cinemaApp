package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.EmployeeDbService;
import com.crud.cinema.backend.service.RoomDbService;
import com.crud.cinema.frontend.form.EmployeeForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
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

import java.util.Set;

@Route("/employees")
public class EmployeeView extends VerticalLayout {

    private final EmployeeDbService employeeDbService;
    private final RoomDbService roomDbService;
    private final Grid<Employee> employeeGrid = new Grid<>(Employee.class);
    private final Button goToDashboard = new Button("Dashboard");
    private final TextField filter1 = new TextField();
    private final TextField filter2 = new TextField();
    private final TextField filter3 = new TextField();
    private final Button addNewEmployee = new Button("Add new employee");

    @Autowired
    public EmployeeView(EmployeeDbService employeeDbService, RoomDbService roomDbService) {
        this.employeeDbService = employeeDbService;
        this.roomDbService = roomDbService;
        EmployeeForm form = new EmployeeForm(this, employeeDbService, roomDbService);

        setFilters();
        setEmployeeGrid();
        setGoToDashboard();

        addNewEmployee.addClickListener(e -> {
            employeeGrid.asSingleSelect().clear();
            form.setEmployee(new Employee());
        });

        H1 heading = new H1("Employees");
        heading.getStyle().set("margin", "auto");

        HorizontalLayout filters = new HorizontalLayout(filter1, filter2, filter3, goToDashboard, addNewEmployee);
        VerticalLayout mainContent = new VerticalLayout(employeeGrid, form);

        mainContent.setSizeFull();
        employeeGrid.setSizeFull();

        add(heading, filters, mainContent);
        form.setEmployee(null);
        setSizeFull();
        refresh();

        employeeGrid.asSingleSelect().addValueChangeListener(event -> {
            form.setEmployee(employeeGrid.asSingleSelect().getValue());
        });
    }

    public void refresh() {
        employeeGrid.setItems(employeeDbService.getAllEmployees());
    }

    public void updateId() {
        employeeGrid.setItems(employeeDbService.getEmployeesWithId(filter1.getValue()));
    }

    public void updateFirstName() {
        employeeGrid.setItems(employeeDbService.getEmployeesWithFirstName(filter2.getValue()));
    }

    public void updateLastName() {
        employeeGrid.setItems(employeeDbService.getEmployeesWithLastName(filter3.getValue()));
    }

    private Component createDeleteButton(Employee employee) {
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(event -> deleteEmployee(employee));
        return deleteButton;
    }

    private void deleteEmployee(Employee employee) {
        employee.removeRoomAssociations();
        boolean deleted = employeeDbService.deleteEmployeeById(employee.getId());

        if (deleted) {
            refresh();
        } else {
            Notification.show("Failed to delete employee.");
        }
        refresh();
    }

    private String formatRooms(Set<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return "";
        }

        StringBuilder formattedRooms = new StringBuilder();
        for (Room room : rooms) {
            formattedRooms.append(room.getName())
                    .append(", ");
        }
        formattedRooms.setLength(formattedRooms.length() - 2);
        return formattedRooms.toString();
    }

    public Grid<Employee> getEmployeeGrid() {
        return employeeGrid;
    }

    public TextField getFilter1() {
        return filter1;
    }

    public TextField getFilter2() {
        return filter2;
    }

    public TextField getFilter3() {
        return filter3;
    }

    public void setFilter1() {
        filter1.setPlaceholder("Filter by id");
        filter1.setClearButtonVisible(true);
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> updateId());
    }

    public void setFilter2() {
        filter2.setPlaceholder("Filter by first name");
        filter2.setClearButtonVisible(true);
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(e -> updateFirstName());
    }

    public void setFilter3() {
        filter3.setPlaceholder("Filter by last name");
        filter3.setClearButtonVisible(true);
        filter3.setValueChangeMode(ValueChangeMode.EAGER);
        filter3.addValueChangeListener(e -> updateLastName());
    }

    public void setFilters() {
        setFilter1();
        setFilter2();
        setFilter3();
    }

    public void setEmployeeGrid() {
        employeeGrid.setColumns("id", "firstName", "lastName");

        employeeGrid.addColumn(emp -> formatRooms(emp.getRooms()))
                .setHeader("Rooms");

        Grid.Column<Employee> deleteColumn = employeeGrid.addColumn(
                        new ComponentRenderer<>(this::createDeleteButton))
                .setHeader("Actions");
        deleteColumn.setWidth("100px");
    }

    public void setGoToDashboard() {
        goToDashboard.addClickListener(e ->
                goToDashboard.getUI().ifPresent(ui ->
                        ui.navigate("")));
    }

}
