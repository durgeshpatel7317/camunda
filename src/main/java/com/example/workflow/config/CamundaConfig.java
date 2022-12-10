package com.example.workflow.config;

import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class CamundaConfig {
    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    @Primary
    public ProcessEngine customProcessEngine(DataSource dataSource) {
        ProcessEngineConfigurationImpl processEngineConfiguration = (ProcessEngineConfigurationImpl) ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration()
                .setDataSource(dataSource)
                .setJobExecutorActivate(true);
        processEngineConfiguration.setDmnEngineConfiguration(customDmnEngineConfig());

        return processEngineConfiguration.buildProcessEngine();
    }

    private DefaultDmnEngineConfiguration customDmnEngineConfig() {
        DefaultDmnEngineConfiguration dmnEngineConfiguration = (DefaultDmnEngineConfiguration)
                DmnEngineConfiguration.createDefaultDmnEngineConfiguration();

        dmnEngineConfiguration.getCustomPostDecisionTableEvaluationListeners()
                .add(new DmnTablePostEvaluationListener());

        return dmnEngineConfiguration;
    }
}
