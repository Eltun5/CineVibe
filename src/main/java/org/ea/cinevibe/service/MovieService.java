package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.dto.MovieResponseDTO;
import org.ea.cinevibe.mapper.MovieMapper;
import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.repository.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MovieService {
    private final MovieRepository repository;

    private final GenreService genreService;

    public MovieService(MovieRepository repository, GenreService genreService) {
        this.repository = repository;
        this.genreService = genreService;
    }

    public Movie save(Movie movie) {
        if (movie.getReleaseYear() < 1850) {
            log.error("Release year cannot be less than 1850!!!");
            throw new RuntimeException("Release year cannot be less than 1850!!!");
        }
        log.info("Some user create movie.");
        return repository.save(movie);
    }

    public Movie getMovieById(Long id) {
        return repository.getReferenceById(id);
    }

    public MovieResponseDTO getMoviesComplexFilter(String title,
                                                   Integer releaseYear,
                                                   List<String> genres,
                                                   int pageNum,
                                                   boolean sortByTitle,
                                                   boolean sortByReleaseYear) {
        if (title == null) title = "";

        List<Genre> genresByNames;
        if (genres == null || genres.isEmpty()) genresByNames = genreService.findAll();
        else genresByNames = genreService.getGenresByNames(genres);

        Pageable pageable = PageRequest.of(pageNum - 1, 10);

        List<Movie> movies;
        if (releaseYear == null) movies = repository.
                getMoviesByTitleAndGenres(title, genresByNames, pageable);
        else movies = repository.getMoviesByTitleAndGenresAndReleaseYear(
                title, releaseYear, genresByNames, pageable);

        if (sortByTitle) movies.sort(Comparator.comparing(Movie::getTitle));
        if (sortByReleaseYear) movies.sort(Comparator.comparing(Movie::getReleaseYear));

        return new MovieResponseDTO(
                movies, genreService.findAll(),
                releaseYear, genresByNames,
                sortByTitle, sortByReleaseYear);
    }

    public Movie update(Long id, Movie movie) {
        log.info("Some user update movie.");
        Movie oldMovie = repository.getReferenceById(id);

        return repository.save(MovieMapper.mapMovie(oldMovie, movie));
    }

    public void delete(Long id) {
        log.warn("Some user delete movie.");
        repository.delete(repository.getReferenceById(id));
    }

    public void changeMovieForAddReviewAction(Review review) {
        Movie movie = repository.getReferenceById(review.getMovie().getId());
        double newAvgRating = movie.getAverageRating() * movie.getReviewCount();
        movie.setReviewCount(movie.getReviewCount() + 1);
        newAvgRating = (newAvgRating + review.getRating()) / movie.getReviewCount();
        movie.setAverageRating(newAvgRating);

        repository.save(movie);
    }
}
