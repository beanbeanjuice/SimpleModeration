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

    public ARGUMENT_AMOUNT checkTotal(String[] arguments) {
        int totalRequired = 0;

        for (Usage value : usages) {
            if (value.isRequired()) {
                totalRequired++;
            }
        }

        if (arguments.length > usages.size()) {
            return ARGUMENT_AMOUNT.TOO_MANY;
        }

        if (arguments.length < totalRequired) {
            return ARGUMENT_AMOUNT.NOT_ENOUGH;
        }

        return ARGUMENT_AMOUNT.CORRECT_AMOUNT;
    }

    public boolean checkUsage(String argument, int index) {


        Usage usage = usages.get(index);
        switch (usage.getUsageType().getName()) {
            case ("text"): {
                return true;
            }

            case ("number"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parseNumber(argument);
            }

            case ("player"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parsePlayer(argument);
            }

            case ("world"): {

                if (!usage.isRequired() && argument.isEmpty()) {
                    return true;
                }

                return parseWorld(argument);
            }

            case ("other"): {
                return true;
            }

            default: {
                return false;
            }

        }
    }

    public enum ARGUMENT_AMOUNT {
        TOO_MANY(BeanPunishments.getHelper().getConfigString("too-many-arguments")),
        NOT_ENOUGH(BeanPunishments.getHelper().getConfigString("not-enough-arguments")),
        CORRECT_AMOUNT("Correct Amount");

        private final String message;

        ARGUMENT_AMOUNT(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public enum USAGE_TYPE {
        TEXT("text", BeanPunishments.getHelper().getConfigString("incorrect-syntax-text")),
        NUMBER("number", BeanPunishments.getHelper().getConfigString("incorrect-syntax-number")),
        PLAYER("player", BeanPunishments.getHelper().getConfigString("incorrect-syntax-player")),
        WORLD("world", BeanPunishments.getHelper().getConfigString("incorrect-syntax-world")),
        OTHER("other", BeanPunishments.getHelper().getConfigString("incorrect-syntax-other"));

        private final String name;
        private final String message;

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
