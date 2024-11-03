package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CLIParser {
    private final Logger logger = LogManager.getLogger(CLIParser.class);
    private final Map<String, String> configMap;
    private final LocalDate localDate;

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    public CLIParser(String[] args) {
        this.configMap = parseConfig(args[0]);
        this.localDate = parseDateFromConfigMap(configMap);

    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    private LocalDate parseDateFromConfigMap(Map<String, String> configMap) {
        String date = configMap.get("date");

        if (date == null || date.isEmpty()) {
            return null; // Return empty if date is not present
        }
        configMap.remove("date");
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            // Log the exception if necessary
            logger.warn("The date could not be parsed correctly.");
            return null; // Return empty if parsing fails
        }
    }

    private Map<String, String> parseConfig(String arg) {
        Map<String, String> result = new HashMap<>();
        String[] splits = arg.split(";");
        for (String split : splits) {
            String[] subSplit = split.split("=");
            result.put(subSplit[0], subSplit[1]);
        }
        return result;
    }

    public static void main(String[] args) {
        String[] strings = new String[1];
        strings[0] = "jakarta.persistence.jdbc.user=opso;date=2024-10-45";
        CLIParser cliParser = new CLIParser(strings);
        System.out.println("Config map:");
        System.out.println(cliParser.getConfigMap().get("jakarta.persistence.jdbc.user"));
        System.out.println("date:");
        System.out.println(cliParser.getLocalDate());

    }
}
