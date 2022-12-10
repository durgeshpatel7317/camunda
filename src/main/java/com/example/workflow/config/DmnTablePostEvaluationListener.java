package com.example.workflow.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionTableEvaluationEvent;
import org.camunda.bpm.dmn.engine.delegate.DmnDecisionTableEvaluationListener;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DmnTablePostEvaluationListener implements DmnDecisionTableEvaluationListener {
    @Override
    public void notify(DmnDecisionTableEvaluationEvent event) {
        DmnDecisionTableImpl decisionTableImpl = (DmnDecisionTableImpl) event.getDecision().getDecisionLogic();

        Map<String, Object> inputIdAndNameMap = decisionTableImpl.getInputs()
                .stream()
                .flatMap(input -> Collections
                        .singletonMap(input.getId(), input.getExpression().getExpression())
                        .entrySet()
                        .stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, Object> inputs = event.getInputs()
                .stream()
                .flatMap(input -> Collections
                        .singletonMap(inputIdAndNameMap.get(input.getId()).toString(), input.getValue().getValue())
                        .entrySet()
                        .stream()
                )
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Map<String, Object>> outputs = event.getMatchingRules()
                .stream()
                .flatMap(r -> r.getOutputEntries().values()
                        .stream()
                        .map(o -> Collections.singletonMap(o.getOutputName(), o.getValue().getValue()))
                )
                .collect(Collectors.toList());

        log.debug("Input variables are {} ", inputs);
        log.debug("Output variables are {} ", outputs);
    }
}
