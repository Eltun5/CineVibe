package org.ea.cinevibe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.dto.MovieFilterRequestDTO;
import org.ea.cinevibe.dto.MovieRequestDTO;
import org.ea.cinevibe.dto.MovieResponseDTO;
import org.ea.cinevibe.mapper.MovieMapper;
import org.ea.cinevibe.model.Genre;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.repository.MovieRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository repository;

    private final GenreService genreService;

    private final CloudinaryService cloudinaryService;

    public Movie save(MovieRequestDTO request, MultipartFile file) throws IOException {
        log.info("Someone try to create movie.");

        Movie movie = MovieMapper.toEntity(request);
        movie.setPosterImageUrl(cloudinaryService.uploadFile(file));

        return repository.save(movie);
    }

    public Movie getById(Long id) {
        log.info("Someone try to get movie. Id : " + id);
        return repository.findById(id).orElseThrow(()->{
            log.warn("Cannot find movie with id : " + id);
            return new NoSuchElementException("Cannot find movie with id : " + id);
        });
    }

    public MovieResponseDTO getComplexFilter(MovieFilterRequestDTO requestDTO) {
        log.info("Someone try to get movie with complex filter.");

        List<Long> genres = requestDTO.genres();
        if (genres == null || genres.isEmpty()) {
            genres = genreService.findAll().stream().map(Genre::getId).toList();
        }

        Pageable pageable = PageRequest.of(requestDTO.pageNum() - 1, 10);

        Integer releaseYear = requestDTO.releaseYear();
        List<Movie> movies;
        if (releaseYear == null) movies = repository.
                getMoviesByGenres(genres, pageable);
        else movies = repository.getMoviesByGenresAndReleaseYear(releaseYear, genres, pageable);

        if (requestDTO.sortByTitle()) movies.sort(Comparator.comparing(Movie::getTitle));
        if (requestDTO.sortByReleaseYear()) movies.sort(Comparator.comparing(Movie::getReleaseYear));

        return new MovieResponseDTO(movies, genreService.findAll(), requestDTO);
    }

    public MovieResponseDTO search(String title, Integer pageNum){
        log.info("Someone try to search movies. Title : " + title);


        Pageable pageable = PageRequest.of(pageNum - 1, 10);
        return new MovieResponseDTO(repository.searchByTitle(title, pageable),
                genreService.findAll(),
                new MovieFilterRequestDTO(
                        null,
                        null,
                        1,
                        false,
                        false));
    }

    public Movie update(Long id, MovieRequestDTO requestDTO, MultipartFile file) throws IOException {
        log.info("Someone try to update movie.");
        Movie oldMovie = repository.getReferenceById(id);
        Movie movie = MovieMapper.updateEntity(requestDTO, oldMovie);

        cloudinaryService.deleteImage(movie.getPosterImageUrl());
        movie.setPosterImageUrl(cloudinaryService.uploadFile(file));

        return repository.save(movie);
    }

    public void changeForAddReviewAction(Review review) {
        log.info("Someone try to add review movie.");

        Movie movie = repository.getReferenceById(review.getMovie().getId());
        double newAvgRating = movie.getAverageRating() * movie.getReviewCount();
        movie.setReviewCount(movie.getReviewCount() + 1);
        newAvgRating = (newAvgRating + review.getRating()) / movie.getReviewCount();
        movie.setAverageRating(newAvgRating);

        repository.save(movie);
    }

    public void delete(Long id) {
        log.warn("Someone try to delete movie. Id : " + id);
        repository.delete(repository.getReferenceById(id));
    }
}