package org.example;
import com.fasterxml.jackson.databind.JsonNode;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureFileGeneratorWithFreeMarker {

    public static void featureGenerator(JsonNode jsonNode) {
        try {
            System.out.println(jsonNode.toString());

            // Initialize FreeMarker configuration
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            cfg.setClassLoaderForTemplateLoading(FeatureFileGeneratorWithFreeMarker.class.getClassLoader(), "/templates");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

            // Load the FreeMarker template
            Template template = cfg.getTemplate("template.ftl");

            String featureName = jsonNode.findValue("name").asText();
            JsonNode items = jsonNode.get("item");
            // Prepare data model
            DataModel dataModel = new DataModel(featureName, createScenarios(items));

            // Process the template
            StringWriter resultWriter = new StringWriter();
            template.process(dataModel, resultWriter);

            // Print or save the generated content
            System.out.println(resultWriter.toString());

            // Save the content to a file (optional)
            try (FileWriter fileWriter = new FileWriter("src/main/resources/API.feature")) {
                fileWriter.write(resultWriter.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<ScenarioData> createScenarios(JsonNode items) {
        List<ScenarioData> scenarios = new ArrayList<>();
        if (items.isArray()) {
            for (JsonNode item : items) {
                String itemName = item.get("name").asText();
                String raw = item.get("request").get("url").get("raw").asText();
                String method = item.get("request").get("method").asText();

                JsonNode events = item.get("event");
                String exec = "";

                for (JsonNode event : events) {
                    JsonNode script = event.get("script");
                    if (script != null) {
                        JsonNode execNode = script.get("exec");
                        if (execNode != null && execNode.isArray()) {
                            for (JsonNode exe : execNode) {
                                if (exe.toString().contains("pm.response.text())")) {
                                    exec = exe.asText().trim();
                                    break; // exit loop once found
                                }
                            }
                        }
                    }
                }

                String extractedString = "";
                try {
                    String pattern = "pm\\.expect\\(pm\\.response\\.text\\(\\)\\)\\.to\\.include\\(\"(.*?)\"\\)";
                    Pattern regex = Pattern.compile(pattern);
                    Matcher matcher = regex.matcher(exec);
                    if (matcher.find()) {
                        extractedString = matcher.group(1);
                        System.out.println("Extracted String: " + extractedString);
                        scenarios.add(new ScenarioData(itemName, raw, method, extractedString));
                    } else {
                        scenarios.add(new ScenarioData(itemName, raw, method, null));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    scenarios.add(new ScenarioData(itemName, raw, method, null));
                }
            }
        }
        return scenarios;
    }
}

