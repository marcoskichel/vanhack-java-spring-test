package com.hackerrank.github.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GithubApiRestController {

    @DeleteMapping("/erase")
    public void erase() {

    }
}
