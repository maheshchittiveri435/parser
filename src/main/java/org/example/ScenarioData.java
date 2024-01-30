package org.example;

public class ScenarioData {

    private final String url;
    private String name;
    private String method;
    private String text;

    public ScenarioData(String name, String url, String method, String text) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
    public String getMethod() {
        return method;
    }

    public String getText() {
        return text;
    }
}
