package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;

@RestController
@RequestMapping("/info")
public class InfoController {

    @GetMapping("/sum")
    public Long getSum() {
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }
}