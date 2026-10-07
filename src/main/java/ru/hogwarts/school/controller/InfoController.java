package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/sum")
    public Long getSum() {
        return LongStream.rangeClosed(1, 1_000_000)
                .parallel()
                .sum();
    }

    @GetMapping("/port")
    public int getPort() {
        return serverPort;
    }
}
