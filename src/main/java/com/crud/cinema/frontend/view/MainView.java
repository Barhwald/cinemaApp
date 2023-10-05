package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.freecurrency.facade.FreecurrencyFacade;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final Button goToMovies = new Button("Movies");
    private final Button goToRooms = new Button("Rooms");
    private final Button goToPerformances = new Button("Performances");
    private final Button goToEmployees = new Button("Employees");
    private final TextField readonlyCurrencyField = new TextField();
    private final FreecurrencyFacade freecurrencyFacade;

    public MainView(FreecurrencyFacade freecurrencyFacade) {
        this.freecurrencyFacade = freecurrencyFacade;

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

        H3 currencyInfo = new H3("Current EUR to PLN rate: ");

        readonlyCurrencyField.setReadOnly(true);
        readonlyCurrencyField.getStyle().set("margin-top", "auto");
        readonlyCurrencyField.setValue(freecurrencyFacade.getEutToPlnRate().getDataPLN().getPln().substring(1,4));

        HorizontalLayout currencyWidget = new HorizontalLayout(currencyInfo, readonlyCurrencyField);
        currencyWidget.getStyle().set("margin-left", "auto");
        currencyWidget.getStyle().set("margin-right", "auto");
        currencyWidget.getStyle().set("position", "absolute");
        currencyWidget.getStyle().set("bottom", "0");

        add(header, toolbar, currencyWidget);
        setSizeFull();
    }

}