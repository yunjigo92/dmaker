package com.yunji.dmaker.controller;

import com.yunji.dmaker.dto.CreateDeveloper;
import com.yunji.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * description
 * <p>
 * author         : yunji
 * date           : 22. 7. 18.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    // 현재 구조
    //    DMakerController(Bean) DMakerService(Bean)  DeveloperRepository(Bean)
    // =========================== spring context ==================================

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        //GET
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("yunji", "jimin");
    }

    @PostMapping("/developers")
    public String createDeveloper(@Valid @RequestBody CreateDeveloper.Request request){
        //POST
        log.info("POST /developers HTTP/1.1");
        log.info("request : {}", request);
        dMakerService.createDeveloper(request);
        return "tt";
    }
}
