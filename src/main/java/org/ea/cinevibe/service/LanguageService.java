package org.ea.cinevibe.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.model.Language;
import org.ea.cinevibe.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LanguageService {
    private final LanguageRepository repository;

    public Language save(String name) {
        log.info("Someone try to create language. Name : " + name);
        return repository.save(Language.builder().name(name).build());
    }

    public List<Language> getAll() {
        log.info("Someone try to get all language.");
        return repository.findAll();
    }

    public Language getById(Long id) {
        log.info("Someone try to get language. Id : " + id);
        return repository.findById(id).orElseThrow(() -> {
            log.warn("Cannot find language with id : " + id);
            return new NoSuchElementException("Cannot find language with id : " + id);
        });
    }

    public Language update(Long id, String name) {
        log.info("Someone try to update language.");
        Language language = getById(id);
        language.setName(name);
        return repository.save(language);
    }

    public void delete(Long id) {
        log.warn("Someone try to delete language. Id : " + id);
        repository.deleteById(id);
    }
}
