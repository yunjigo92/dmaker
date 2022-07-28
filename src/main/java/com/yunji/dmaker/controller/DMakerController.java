package com.yunji.dmaker.controller;

import com.yunji.dmaker.dto.*;
import com.yunji.dmaker.exception.DMakerException;
import com.yunji.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    public List<DeveloperDto> getAllDevelopers() {
        //GET
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/developers/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(@PathVariable String memberId){
        return dMakerService.getDeveloperDetail(memberId);
    }


    @PutMapping("/developers/{memberId}")
    public DeveloperDetailDto editDeveloper(@PathVariable String memberId,
                                            @Valid @RequestBody EditDeveloper.Request request
    ){
        return dMakerService.editDeveloper(memberId, request);
    }

    @PostMapping("/developers")
    public CreateDeveloper.Response createDeveloper(@Valid @RequestBody CreateDeveloper.Request request){
        //POST
        log.info("POST /developers HTTP/1.1");
        log.info("request : {}", request);

        return dMakerService.createDeveloper(request);
    }

    @DeleteMapping("/developers/{memberId}")
    public DeveloperDetailDto deleteDeverloper(@PathVariable String memberId){
        return dMakerService.deleteDeveloper(memberId);
    }



}
