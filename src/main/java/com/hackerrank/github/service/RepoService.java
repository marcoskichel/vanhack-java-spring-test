package com.hackerrank.github.service;

import com.hackerrank.github.repository.RepoRepository;
import org.springframework.stereotype.Service;

@Service
public class RepoService {

    private final RepoRepository repository;

    public RepoService(RepoRepository repository) {
        this.repository = repository;
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
