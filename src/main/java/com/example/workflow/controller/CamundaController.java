package com.example.workflow.controller;

import com.example.workflow.service.CamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CamundaController {

    private final CamundaService camundaService;

    @Autowired
    public CamundaController(CamundaService camundaService) {
        this.camundaService = camundaService;
    }

    @PostMapping("/evaluate-decision")
    public ResponseEntity<Object> evaluateDecision(@RequestBody Map<String, Object> variables) {
        String response = camundaService.evaluateDecision(variables);
        return ResponseEntity.ok(Map.of("result", response));
    }
}
