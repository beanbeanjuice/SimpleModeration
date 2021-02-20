package beanbeanjuice.beanpunishments.utilities.usages;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public class CommandUsage {

    private ArrayList<Usage> usages;

    public CommandUsage() {
        usages = new ArrayList<>();
    }

    public void addUsage(String argumentName, USAGE_TYPE usageType, boolean required) {
        usages.add(new Usage(argumentName, usageType, required));
    }

    public ArrayList<Usage> getUsages() {
        return usages;
    }

    public boolean checkUsage(String argument, int index) {
        switch (usages.get(index).getUsageType().getName()) {
            case ("text"): {
                return true;
            }

            case ("number"): {
                return parseNumber(argument);
            }

            case ("player"): {
                return parsePlayer(argument);
            }

            case ("world"): {
                return parseWorld(argument);
            }

            default: {
                return false;
            }

        }
    }

    public enum USAGE_TYPE {
        TEXT("text", BeanPunishments.getHelper().getConfigString("incorrect-syntax-text")),
        NUMBER("number", BeanPunishments.getHelper().getConfigString("incorrect-syntax-number")),
        PLAYER("player", BeanPunishments.getHelper().getConfigString("incorrect-syntax-player")),
        WORLD("world", BeanPunishments.getHelper().getConfigString("incorrect-syntax-world"));

        private String name;
        private String message;

        USAGE_TYPE(String name, String message) {
            this.name = name;
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public String getMessage() {
            return message;
        }
    }

    private boolean parseWorld(String argument) {
        return Bukkit.getWorld(argument) != null;
    }

    private boolean parsePlayer(String argument) {
        return Bukkit.getPlayer(argument) != null;
    }

    private boolean parseNumber(String argument) {
        if (argument == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(argument);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
