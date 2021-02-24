package beanbeanjuice.beanpunishments.managers.configs;

import java.util.ArrayList;

public class ConfigHandler {

    ArrayList<String> configs;
    private String config;
    private String messages;

    public ConfigHandler() {
        config = ("config.yml");
        messages = ("messages.yml");

        configs = new ArrayList<>();
        configs.add(config);
        configs.add(messages);
    }

    public void checkConfig(String configName) {

        for (String string : configs) {
            if (string.equals(configName)) {
                // TODO: Check Config
                // Check version numbers
                // if different, fix.
            }
        }

    }

}
