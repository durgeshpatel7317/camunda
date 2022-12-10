package com.example.workflow.service;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CamundaService {
    private final ProcessEngine processEngine;

    @Autowired
    public CamundaService(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }

    public String evaluateDecision(Map<String, Object> variables) {
        DecisionService decisionService = processEngine.getDecisionService();

        DmnDecisionTableResult results = decisionService.evaluateDecisionTableByKey("dish", Variables.fromMap(variables));
        String response = results.getSingleEntry();

        log.debug("Response for decision table is {} ", response);

        return response;
    }
}
