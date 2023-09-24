package com.crud.cinema.frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/employees")
public class EmployeeView extends VerticalLayout {

    public EmployeeView() {
        add(new Button("Click me employees", e -> Notification.show("Hello World")));

    }

}
