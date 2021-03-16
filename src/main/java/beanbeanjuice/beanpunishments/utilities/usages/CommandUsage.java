package beanbeanjuice.beanpunishments.utilities.usages;

import beanbeanjuice.beanpunishments.BeanPunishments;
import org.bukkit.Bukkit;

import java.util.ArrayList;

/**
 * A class used to parse {@link beanbeanjuice.beanpunishments.utilities.CommandInterface commands}.
 */
public class CommandUsage {

    /**
     * Each argument for the command will have its own {@link Usage}.
     */
    private ArrayList<Usage> usages;

    /**
     * Create a new {@link CommandUsage} object.
     * This {@link CommandUsage} object will contain
     * an {@link ArrayList<Usage>} which contains all
     * of the {@link Usage usages}.
     */
    public CommandUsage() {
        usages = new ArrayList<>();
    }

    /**
     * Add a {@link Usage}.
     * @param argumentName The name of the argument to be used. It is arbitrary.
     * @param usageType The {@link USAGE_TYPE} to be used.
     * @param required Whether or not this particular {@link Usage} is required.
     */
    public void addUsage(String argumentName, USAGE_TYPE usageType, boolean required) {
        usages.add(new Usage(argumentName, usageType, required));
    }

    /**
     * @return Gets the {@link ArrayList<Usage>} for this particular {@link beanbeanjuice.beanpunishments.utilities.CommandInterface command}.
     */
    public ArrayList<Usage> getUsages() {
        return usages;
    }

    /**
     * Gets the total amount of required arguments.
     * @param arguments The arguments found in the {@link beanbeanjuice.beanpunishments.utilities.CommandInterface command}.
     * @return The {@link ARGUMENT_AMOUNT} enum.
     */
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

    /**
     * Checks the {@link Usage} for that particular argument.
     * @param argument The argument that the {@link org.bukkit.entity.Player} has entered.
     * @param index The index of that particular {@link beanbeanjuice.beanpunishments.utilities.CommandInterface command} argument.
     * @return Whether or not the {@link Usage} for that particular argument checks out.
     */
    public boolean checkUsage(String argument, int index) {

        Usage usage = usages.get(index);
        switch (usage.getUsageType().getName()) {
            case ("text"):

            case ("other"): {
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

            default: {
                return false;
            }

        }
    }

    /**
     * The {@link ARGUMENT_AMOUNT} enum.
     * Used for displaying what is wrong with that argument.
     */
    public enum ARGUMENT_AMOUNT {
        TOO_MANY(BeanPunishments.getHelper().getConfigString("too-many-arguments")),
        NOT_ENOUGH(BeanPunishments.getHelper().getConfigString("not-enough-arguments")),
        CORRECT_AMOUNT("Correct Amount");

        private final String message;

        /**
         * Create a new {@link ARGUMENT_AMOUNT} enum static class.
         * @param message The message to be used.
         */
        ARGUMENT_AMOUNT(String message) {
            this.message = message;
        }

        /**
         * @return The message associated with the particular {@link ARGUMENT_AMOUNT}.
         */
        public String getMessage() {
            return message;
        }
    }

    /**
     * The {@link USAGE_TYPE} enum.
     * Used for displaying what type of argument it is.
     */
    public enum USAGE_TYPE {
        TEXT("text", BeanPunishments.getHelper().getConfigString("incorrect-syntax-text")),
        NUMBER("number", BeanPunishments.getHelper().getConfigString("incorrect-syntax-number")),
        PLAYER("player", BeanPunishments.getHelper().getConfigString("incorrect-syntax-player")),
        WORLD("world", BeanPunishments.getHelper().getConfigString("incorrect-syntax-world")),
        OTHER("other", BeanPunishments.getHelper().getConfigString("incorrect-syntax-other"));

        private final String name;
        private final String message;

        /**
         * Create a new {@link USAGE_TYPE} enum static class.
         * @param name The name of the type of argument.
         * @param message The message to be used.
         */
        USAGE_TYPE(String name, String message) {
            this.name = name;
            this.message = message;
        }

        /**
         * @return The message associated with the particular {@link USAGE_TYPE}.
         */
        public String getName() {
            return name;
        }

        /**
         * @return The message associated with the particular {@link USAGE_TYPE}.
         */
        public String getMessage() {
            return message;
        }
    }

    /**
     * Used for checking if the argument name is a {@link org.bukkit.World World}.
     * @param argument The {@link org.bukkit.World World} name.
     * @return Whether or not the argument is a {@link org.bukkit.World World}.
     */
    private boolean parseWorld(String argument) {
        return Bukkit.getWorld(argument) != null;
    }

    /**
     * Used for checking if the argument name is a {@link org.bukkit.entity.Player Player}.
     * @param argument The {@link org.bukkit.entity.Player Player} name.
     * @return Whether or not the argument is a {@link org.bukkit.entity.Player Player}.
     */
    private boolean parsePlayer(String argument) {
        return Bukkit.getPlayer(argument) != null;
    }

    /**
     * Used for checking if the argument name is a {@link Integer}.
     * @param argument The {@link Integer} number used.
     * @return Whether or not the argument is a {@link Integer}.
     */
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
