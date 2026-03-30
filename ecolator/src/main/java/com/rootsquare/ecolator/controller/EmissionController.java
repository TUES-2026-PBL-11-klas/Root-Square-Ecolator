package com.rootsquare.ecolator.controller;

import com.rootsquare.ecolator.dto.EmissionRequest;
import com.rootsquare.ecolator.dto.EmissionResponse;
import com.rootsquare.ecolator.service.EmissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emissions")
@CrossOrigin(origins = "*")
public class EmissionController {

    private final EmissionService emissionService;

    public EmissionController(EmissionService emissionService) {
        this.emissionService = emissionService;
    }

    @PostMapping("/calculate")
    public EmissionResponse calculate(@RequestBody EmissionRequest request) {
        return emissionService.calculate(request);
    }
}