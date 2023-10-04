package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.service.PerformanceDbService;
import com.crud.cinema.backend.service.RoomDbService;
import com.crud.cinema.frontend.form.PerformanceForm;
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

@Route("/performances")
public class PerformanceView extends VerticalLayout {

    private final PerformanceDbService performanceDbService;
    private final RoomDbService roomDbService;
    private final MovieDbService movieDbService;
    private final Grid<Performance> performanceGrid = new Grid<>(Performance.class);
    private final Button goToDashboard = new Button("Dashboard");
    private final TextField filter1 = new TextField();
    private final TextField filter2 = new TextField();
    private final TextField filter3 = new TextField();
    private final TextField filter4 = new TextField();
    private final TextField filter5 = new TextField();
    private final Button addNewPerformance = new Button("Add new performance");

    @Autowired
    public PerformanceView(PerformanceDbService performanceDbService,
                           RoomDbService roomDbService,
                           MovieDbService movieDbService) {
    this.performanceDbService = performanceDbService;
    this.roomDbService = roomDbService;
    this.movieDbService = movieDbService;

    PerformanceForm form = new PerformanceForm(this, performanceDbService,
            roomDbService,
            movieDbService);

        filter1.setPlaceholder("Filter by id");
        filter1.setClearButtonVisible(true);
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> updateId());

        filter2.setPlaceholder("Filter by movie");
        filter2.setClearButtonVisible(true);
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(e -> updateMovie());

        filter3.setPlaceholder("Filter by room");
        filter3.setClearButtonVisible(true);
        filter3.setValueChangeMode(ValueChangeMode.EAGER);
        filter3.addValueChangeListener(e -> updateRoom());

        filter4.setPlaceholder("Filter by date");
        filter4.setClearButtonVisible(true);
        filter4.setValueChangeMode(ValueChangeMode.EAGER);
        filter4.addValueChangeListener(e -> updateDate());

        filter5.setPlaceholder("Filter by time");
        filter5.setClearButtonVisible(true);
        filter5.setValueChangeMode(ValueChangeMode.EAGER);
        filter5.addValueChangeListener(e -> updateTime());

        performanceGrid.setColumns("id");

        performanceGrid.addColumn(performance -> formatMovie(performance.getMovie()))
                .setHeader("Movies");

        performanceGrid.addColumn(performance -> formatRoom(performance.getRoom()))
                .setHeader("Rooms");

        performanceGrid.addColumn("date").setHeader("Date");
        performanceGrid.addColumn("time").setHeader("Time");

        Grid.Column<Performance> deleteColumn = performanceGrid.addColumn(
                        new ComponentRenderer<>(this::createDeleteButton))
                .setHeader("Actions");
        deleteColumn.setWidth("100px");

        goToDashboard.addClickListener(e ->
                goToDashboard.getUI().ifPresent(ui ->
                        ui.navigate("")));

        addNewPerformance.addClickListener(e -> {
            performanceGrid.asSingleSelect().clear();
            form.setPerformance(new Performance());
        });

        H1 heading = new H1("Performances");
        heading.getStyle().set("margin", "auto");

        HorizontalLayout filters = new HorizontalLayout(filter1, filter2,
                filter3, filter4, filter5, goToDashboard, addNewPerformance);

        VerticalLayout mainContent = new VerticalLayout(performanceGrid, form);

        mainContent.setSizeFull();
        performanceGrid.setSizeFull();

        add(heading, filters, mainContent);
        form.setPerformance(null);
        setSizeFull();
        refresh();

        performanceGrid.asSingleSelect().addValueChangeListener(event ->
                form.setPerformance(performanceGrid.asSingleSelect().getValue()));
    }

    public void refresh() {
        performanceGrid.setItems(performanceDbService.getAllPerformances());
    }

    public void updateId() {
        performanceGrid.setItems(performanceDbService.getPerformancesWithId(filter1.getValue()));
    }

    public void updateMovie() {
        performanceGrid.setItems(performanceDbService.getPerformancesWithMovie(filter2.getValue()));
    }

    public void updateRoom() {
        performanceGrid.setItems(performanceDbService.getPerformancesWithRoom(filter3.getValue()));
    }

    public void updateDate() {
        performanceGrid.setItems(performanceDbService.getPerformancesWithDate(filter4.getValue()));
    }

    public void updateTime() {
        performanceGrid.setItems(performanceDbService.getPerformancesWithTime(filter5.getValue()));
    }


    private Component createDeleteButton(Performance performance) {
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(event -> deletePerformance(performance));
        return deleteButton;
    }

    private void deletePerformance(Performance performance) {
        performance.setRoom(null);
        performance.setMovie(null);
        performanceDbService.savePerformance(performance);
        boolean deleted = performanceDbService.deletePerformanceById(performance.getId());

        if (deleted) {
            refresh();
        } else {
            Notification.show("Failed to delete performance.");
        }
        refresh();
    }

    private String formatMovie(Movie movie) {
        return movie == null ? "" : movie.getTitle();
    }

    private String formatRoom(Room room) {
        return room == null ? "" : room.getName();
    }

    public Grid<Performance> getPerformanceGrid() {
        return performanceGrid;
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

    public TextField getFilter4() {
        return filter4;
    }

    public TextField getFilter5() {
        return filter5;
    }
}
