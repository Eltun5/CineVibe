package org.ea.cinevibe.service;

import org.ea.cinevibe.excepions.NoDataFoundException;
import org.ea.cinevibe.model.User;
import org.ea.cinevibe.repository.UserRepository;
import org.springframework.stereotype.Service;

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
}
