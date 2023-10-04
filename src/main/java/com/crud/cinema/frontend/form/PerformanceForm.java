package com.crud.cinema.frontend.form;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.domain.Room;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.service.PerformanceDbService;
import com.crud.cinema.backend.service.RoomDbService;
import com.crud.cinema.frontend.view.PerformanceView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PerformanceForm extends FormLayout {
    private final PerformanceView performanceView;
    private final TextField date = new TextField("Date");
    private final TextField time = new TextField("Time");
    private final Button save = new Button("Save");
    private final Binder<Performance> performanceBinder = new Binder<>(Performance.class);
    private final PerformanceDbService performanceDbService;
    private final RoomDbService roomDbService;
    private final MovieDbService movieDbService;
    private Room selectedRoom = new Room();
    private Movie selectedMovie = new Movie();

    public PerformanceForm(PerformanceView performanceView,
                           PerformanceDbService performanceDbService,
                           RoomDbService roomDbService,
                           MovieDbService movieDbService) {
        this.performanceView = performanceView;
        this.performanceDbService = performanceDbService;
        this.roomDbService = roomDbService;
        this.movieDbService = movieDbService;

        ComboBox<Movie> movieComboBox = createMovieComboBox();
        ComboBox<Room> roomComboBox = createRoomComboBox();

        HorizontalLayout textFields = new HorizontalLayout(date, time, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(movieComboBox, roomComboBox, textFields);
        date.setWidth("200px");
        time.setWidth("200px");
        movieComboBox.setWidth("200px");
        roomComboBox.setWidth("200px");

        performanceBinder.bindInstanceFields(this);
        save.getStyle().set("margin-top", "auto");
        save.addClickListener(e -> save());

    }

    private void save() {
        Performance performance = performanceBinder.getBean();
        performance.setMovie(selectedMovie);

        selectedMovie.addPerformance(performance);

        performance.setRoom(selectedRoom);
        selectedRoom.addPerformance(performance);


        performanceDbService.savePerformance(performance);
        performanceView.refresh();
        setPerformance(null);
    }

    private ComboBox<Movie> createMovieComboBox() {
        ComboBox<Movie> movieComboBox = new ComboBox<>();
        movieComboBox.setItems(movieDbService.getAllMovies());
        movieComboBox.setLabel("Movies");
        movieComboBox.setAllowCustomValue(true);
        movieComboBox.setItemLabelGenerator(movie -> {
            String label = movie.getTitle();
            if (label != null) {
                return label;
            } else {
                Notification.show("One of the movies has no name. Please check movies.");
                return "Unknown";
            }
                }
        );
        movieComboBox.addValueChangeListener(event -> {
            selectedMovie = event.getValue();
        });
        return movieComboBox;
    }

    private ComboBox<Room> createRoomComboBox() {
        ComboBox<Room> roomComboBox = new ComboBox<>();
        roomComboBox.setItems(roomDbService.getAllRooms());
        roomComboBox.setLabel("Rooms");
        roomComboBox.setAllowCustomValue(true);
        roomComboBox.setItemLabelGenerator(
                room -> {
                    String label = room.getName();
                    if (label != null) {
                        return label;
                    } else {
                        Notification.show("One of the rooms has no name. Please check rooms.");
                        return "Unknown";
                    }
                }
        );
        roomComboBox.addValueChangeListener(event -> {
            selectedRoom = event.getValue();
        });
        return roomComboBox;
    }

    public void setPerformance(Performance performance) {
        performanceBinder.setBean(performance);

        if (performance == null) {
            setVisible(false);
        } else {
            setVisible(true);
            date.focus();
        }
    }

}
