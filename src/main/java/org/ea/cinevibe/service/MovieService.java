package org.ea.cinevibe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.dto.MovieFilterRequestDTO;
import org.ea.cinevibe.dto.MovieRequestDTO;
import org.ea.cinevibe.dto.MovieResponseDTO;
import org.ea.cinevibe.mapper.MovieMapper;
import org.ea.cinevibe.model.Movie;
import org.ea.cinevibe.model.Review;
import org.ea.cinevibe.model.UserViewHistory;
import org.ea.cinevibe.model.enums.MovieStatus;
import org.ea.cinevibe.model.enums.ViewType;
import org.ea.cinevibe.repository.MovieRepository;
import org.ea.cinevibe.repository.UserViewHistoryRepository;
import org.ea.cinevibe.security.model.SecurityUserDetails;
import org.ea.cinevibe.specification.MovieSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    private final UserViewHistoryRepository historyRepository;

    private final UserService userService;

    public Movie save(MovieRequestDTO request, MultipartFile file) throws IOException {
        log.info("Someone try to create movie.");

        Movie movie = MovieMapper.toEntity(request);
        movie.setPosterImageUrl(cloudinaryService.uploadFile(file));

        return repository.save(movie);
    }

    public Movie getById(Long id) {
        log.info("Someone try to get movie. Id : " + id);

        Movie movie = repository.findById(id).orElseThrow(() -> {
            log.warn("Cannot find movie with id : " + id);
            return new NoSuchElementException("Cannot find movie with id : " + id);
        });

        var userDetails = (SecurityUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails != null) {
            log.info(userDetails.getUsername() + " view movie.");
            Long userId = userService.getUserByUsername(userDetails.getUsername()).getId();
            historyRepository.save(UserViewHistory.builder()
                    .userId(userId).type(ViewType.MOVIE).viewId(id).build());
        }

        return movie;
    }

    public MovieResponseDTO getComplexFilter(MovieFilterRequestDTO requestDTO) {
        log.info("Someone try to get movie with complex filter.");
        Specification<Movie> spec = Specification.where(null);

        List<Long> genreIds = requestDTO.genreIds();
        if (genreIds != null && !genreIds.isEmpty()) {
            spec = spec.and(MovieSpecification.hasGenre(genreIds));
        }

        if (requestDTO.IMDB() != null) {
            spec = spec.and(MovieSpecification.imdbGraterThan(requestDTO.IMDB()));
        }

        List<Long> languageIds = requestDTO.languageIds();
        if (languageIds == null || languageIds.isEmpty()) {
            spec = spec.and(MovieSpecification.hasLanguage(languageIds));
        }

        List<MovieStatus> status = requestDTO.status();
        if (status == null || status.isEmpty()) {
            spec = spec.and(MovieSpecification.hasStatus(status));
        }

        if (requestDTO.releaseYear() != null) {
            spec = spec.and(MovieSpecification.equalReleaseYear(requestDTO.releaseYear()));
        }

        String sortBy = selectSortBy(requestDTO);

        Pageable pageable = PageRequest.of(requestDTO.pageNum() - 1, 10,
                requestDTO.isSortingDesc() ?
                        Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        Page<Movie> movies = repository.findAll(spec, pageable);

        List<Movie> movieList = new ArrayList<>(movies.stream().toList());

        if (requestDTO.sortByTitle()) movieList.sort(Comparator.comparing(Movie::getTitle));
        if (requestDTO.sortByReleaseYear()) movieList.sort(Comparator.comparing(Movie::getReleaseYear));

        return new MovieResponseDTO(movieList, genreService.findAll(), requestDTO);
    }

    private String selectSortBy(MovieFilterRequestDTO requestDTO) {
        return requestDTO.sortByTitle() ? "title" :
                requestDTO.sortByReleaseYear() ? "releaseYear" :
                        requestDTO.sortByReviewCount() ? "reviewCount" :
                                requestDTO.sortByImdb() ? "IMDB" :
                                        requestDTO.sortByBudget() ? "budget" :
                                                requestDTO.sortByIncome() ? "income" :
                                                        "createAt";
    }

    public MovieResponseDTO search(String title, Integer pageNum) {
        log.info("Someone try to search movies. Title : " + title);

        Pageable pageable = PageRequest.of(pageNum - 1, 10);
        return new MovieResponseDTO(
                repository.findAll(Specification.
                        where(MovieSpecification.searchTitle(title)),pageable).
                        stream().toList(),
                genreService.findAll(),
                new MovieFilterRequestDTO(null, null,
                        null, pageNum, null,
                        null, false, false,
                        false,false,false,
                        false,false));
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