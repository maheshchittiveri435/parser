package org.example;

import java.util.List;

public class DataModel {
    private String featureName;
    private List<ScenarioData> scenarios;

    public DataModel(String featureName, List<ScenarioData> scenarios) {
        this.featureName = featureName;
        this.scenarios = scenarios;
    }

    public String getFeatureName() {
        return featureName;
    }

    public List<ScenarioData> getScenarios() {
        return scenarios;
    }
}
