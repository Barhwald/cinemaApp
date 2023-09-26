package com.crud.cinema.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("/performances")
public class PerformanceView extends VerticalLayout {

    public PerformanceView() {
        add(new Button("Click me performances", e -> Notification.show("Hello World")));

    }

}
