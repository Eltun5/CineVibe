package org.ea.cinevibe.service;

import lombok.extern.slf4j.Slf4j;
import org.ea.cinevibe.excepions.NoDataFoundException;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUserByUsername(String username) {
        return repository.getUserByUsername(username).
                orElseThrow(() -> new NoDataFoundException("User"));
    }

    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email).
                orElseThrow(() -> new NoDataFoundException("User"));
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(User user) {
        log.info("Some user delete user.");
        repository.delete(user);
    }
}
