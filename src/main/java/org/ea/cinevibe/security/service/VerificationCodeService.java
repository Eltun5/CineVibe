package org.ea.cinevibe.security.service;

import org.ea.cinevibe.security.model.VerificationCode;
import org.ea.cinevibe.security.repository.VerificationCodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public class VerificationCodeService {

    private final VerificationCodeRepository repository;

    public VerificationCodeService(VerificationCodeRepository repository) {
        this.repository = repository;
    }

    public void save(VerificationCode code) {
        repository.save(code);
    }

    public VerificationCode findByEmailAndCode(String email, String code) throws TimeoutException {
        return repository.findByEmailAndCode(email, code).orElseThrow();
    }
}
