package com.crud.cinema.frontend.view;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.Performance;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.service.OmdbService;
import com.crud.cinema.frontend.form.MovieForm;
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

import java.util.ArrayList;
import java.util.List;

@Route("/movies")
public class MovieView extends VerticalLayout {

    private final MovieDbService movieDbService;
    private final OmdbService omdbService;
    private final Grid<Movie> movieGrid = new Grid<>(Movie.class);
    private final Button goToDashboard = new Button("Dashboard");
    private final TextField filter1 = new TextField();
    private final TextField filter2 = new TextField();
    private final TextField filter3 = new TextField();
    private final TextField filter4 = new TextField();
    private final Button addNewMovie = new Button("Add new movie");

    @Autowired
    public MovieView(MovieDbService movieDbService, OmdbService omdbService) {
        this.movieDbService = movieDbService;
        this.omdbService = omdbService;
        MovieForm form = new MovieForm(this, movieDbService, omdbService);

        filter1.setPlaceholder("Filter by id");
        filter1.setClearButtonVisible(true);
        filter1.setValueChangeMode(ValueChangeMode.EAGER);
        filter1.addValueChangeListener(e -> updateId());
        filter2.setPlaceholder("Filter by title");
        filter2.setClearButtonVisible(true);
        filter2.setValueChangeMode(ValueChangeMode.EAGER);
        filter2.addValueChangeListener(e -> updateTitle());
        filter3.setPlaceholder("Filter by description");
        filter3.setClearButtonVisible(true);
        filter3.setValueChangeMode(ValueChangeMode.EAGER);
        filter3.addValueChangeListener(e -> updateDescription());
        filter4.setPlaceholder("Filter by year");
        filter4.setClearButtonVisible(true);
        filter4.setValueChangeMode(ValueChangeMode.EAGER);
        filter4.addValueChangeListener(e -> updateYear());
        movieGrid.setColumns("id", "title", "description", "year");

        Grid.Column<Movie> deleteColumn = movieGrid.addColumn(
                        new ComponentRenderer<>(this::createDeleteButton))
                .setHeader("Delete");
        deleteColumn.setWidth("100px");

        goToDashboard.addClickListener(e ->
                goToDashboard.getUI().ifPresent(ui ->
                        ui.navigate("")));

        addNewMovie.addClickListener(e -> {
            movieGrid.asSingleSelect().clear();
            form.setMovie(new Movie());
        });

        H1 heading = new H1("Movies");
        heading.getStyle().set("margin", "auto");

        HorizontalLayout filters = new HorizontalLayout(filter1, filter2, filter3, filter4, goToDashboard, addNewMovie);
        VerticalLayout mainContent = new VerticalLayout(movieGrid, form);

        mainContent.setSizeFull();
        movieGrid.setSizeFull();

        add(heading, filters, mainContent);
        form.setMovie(null);
        setSizeFull();
        refresh();

        movieGrid.asSingleSelect().addValueChangeListener(event -> form.setMovie(movieGrid.asSingleSelect().getValue()));

    }

    public void refresh() {
        movieGrid.setItems(movieDbService.getAllMovies());
    }

    public void updateId() {
        movieGrid.setItems(movieDbService.getMoviesWithId(filter1.getValue()));
    }
    public void updateTitle() {
        movieGrid.setItems(movieDbService.getMoviesWithTitle(filter2.getValue()));
    }
    public void updateDescription() {
        movieGrid.setItems(movieDbService.getMoviesWithDescription(filter3.getValue()));
    }
    public void updateYear() {
        movieGrid.setItems(movieDbService.getMoviesWithYear(filter4.getValue()));
    }
    private Component createDeleteButton(Movie movie) {
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClickListener(event -> deleteMovie(movie));
        return deleteButton;
    }
    private void deleteMovie(Movie movie) {
        movie.removePerformanceAssociations();
        List<Performance> iterationList = new ArrayList<>(movie.getPerformances());

        for (Performance performance : iterationList) {
            movie.removePerformance(performance);
        }
        boolean deleted = movieDbService.deleteMovieById(movie.getId());

        if (deleted) {
            refresh();
        } else {
            Notification.show("Failed to delete the movie.");
        }
        refresh();
    }
}
