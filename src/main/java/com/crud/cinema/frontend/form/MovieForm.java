package com.crud.cinema.frontend.form;

import com.crud.cinema.backend.domain.Movie;
import com.crud.cinema.backend.domain.MovieDto;
import com.crud.cinema.backend.omdb.domain.OmdbMovieDto;
import com.crud.cinema.backend.omdb.mapper.OmdbMapper;
import com.crud.cinema.backend.service.MovieDbService;
import com.crud.cinema.backend.omdb.service.OmdbService;
import com.crud.cinema.frontend.view.MovieView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MovieForm extends FormLayout {
    private final MovieView movieView;
    private final TextField title = new TextField("Title");
    private final TextField description = new TextField("Description");
    private final TextField year = new TextField("Year");
    private final Button save = new Button("Save");
    private final TextField searchTitle = new TextField("Title");
    private final Button search = new Button("Search OMDB");
    private final Binder<Movie> movieBinder = new Binder<>(Movie.class);
    private MovieDbService movieDbService;
    private OmdbService omdbService;
    private final OmdbMapper omdbMapper = new OmdbMapper();

    public MovieForm(MovieView movieView, MovieDbService movieDbService, OmdbService omdbService) {
        this.movieView = movieView;
        this.movieDbService = movieDbService;
        this.omdbService = omdbService;
        HorizontalLayout textFields = new HorizontalLayout(title, description, year, save);
        HorizontalLayout searchSection = new HorizontalLayout(searchTitle, search);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(searchSection, textFields);
        title.setWidth("200px");
        description.setWidth("200px");
        year.setWidth("200px");
        movieBinder.bindInstanceFields(this);
        save.getStyle().set("margin-top", "auto");
        search.getStyle().set("margin-top", "auto");
        save.addClickListener(e -> save());
        search.addClickListener(e -> search());
    }

    private void save() {
        Movie movie = movieBinder.getBean();
        movieDbService.saveMovie(movie);
        movieView.refresh();
        setMovie(null);
    }

    private void search() {
        try {
            OmdbMovieDto omdbMovieDto = omdbService.getOmdbMovieByTitle(searchTitle.getValue());
            MovieDto movieDto = omdbMapper.maptoMovieDto(omdbMovieDto);
            title.setValue(movieDto.getTitle());
            description.setValue(movieDto.getDescription());
            year.setValue(movieDto.getYear());
        } catch (NullPointerException e) {
            Notification errorNotification = new Notification("No movie with this title found", 3000);
            errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            errorNotification.open();
        }
    }

    public void setMovie(Movie movie) {
        movieBinder.setBean(movie);

        if (movie == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }

}
