package com.crud.cinema.frontend.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final Button goToMovies = new Button("Movies");
    private final Button goToRooms = new Button("Rooms");
    private final Button goToPerformances = new Button("Performances");
    private final Button goToEmployees = new Button("Employees");

    public MainView() {

        goToMovies.addClickListener(e -> goToMovies.getUI().ifPresent(ui ->
                ui.navigate("movies")));

        goToRooms.addClickListener(e ->
                goToRooms.getUI().ifPresent(ui ->
                        ui.navigate("rooms")));


        goToPerformances.addClickListener(e ->
                goToPerformances.getUI().ifPresent(ui ->
                        ui.navigate("performances")));

        goToEmployees.addClickListener(e ->
                goToEmployees.getUI().ifPresent(ui ->
                        ui.navigate("employees")));

        H1 heading = new H1("Cinema Management App"); //Header
        heading.getStyle().set("margin", "auto");
        VerticalLayout header = new VerticalLayout(heading);

        HorizontalLayout toolbar = new HorizontalLayout(
                goToMovies, goToRooms, goToPerformances, goToEmployees); //Toolbar
        toolbar.getStyle().set("margin-left", "auto");
        toolbar.getStyle().set("margin-right", "auto");

        H3 weatherInfo = new H3("Weather for today: ");
        weatherInfo.getStyle().set("margin-left", "auto");
        weatherInfo.getStyle().set("margin-right", "auto");
        VerticalLayout weatherWidget = new VerticalLayout(weatherInfo); //Weather

        add(header, toolbar, weatherWidget);
        setSizeFull();
    }

}