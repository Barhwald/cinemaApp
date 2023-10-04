package com.crud.cinema.frontend.form;

import com.crud.cinema.backend.domain.Employee;
import com.crud.cinema.backend.service.EmployeeDbService;
import com.crud.cinema.backend.service.RoomDbService;
import com.crud.cinema.frontend.view.EmployeeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class EmployeeForm extends FormLayout {
    private final EmployeeView employeeView;
    private final TextField firstName = new TextField("First name");
    private final TextField lastName = new TextField("First name");
    private final Button save = new Button("Save");
    private final Binder<Employee> employeeBinder = new Binder<>(Employee.class);
    private final EmployeeDbService employeeDbService;
    private final RoomDbService roomDbService;

    public EmployeeForm(EmployeeView employeeView, EmployeeDbService employeeDbService, RoomDbService roomDbService) {
        this.employeeView = employeeView;
        this.employeeDbService = employeeDbService;
        this.roomDbService = roomDbService;
        HorizontalLayout textFields = new HorizontalLayout(firstName, lastName, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(textFields);
        firstName.setWidth("200px");
        lastName.setWidth("200px");

        employeeBinder.bindInstanceFields(this);
        save.getStyle().set("margin-top", "auto");
        save.addClickListener(e -> save());
    }

    private void save() {
        Employee employee = employeeBinder.getBean();
        employeeDbService.saveEmployee(employee);
        employeeView.refresh();
        setEmployee(null);
    }

    public void setEmployee(Employee employee) {
        employeeBinder.setBean(employee);

        if (employee == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

}
