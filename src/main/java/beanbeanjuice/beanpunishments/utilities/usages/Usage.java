package beanbeanjuice.beanpunishments.utilities.usages;

/**
 * A {@link Usage} to be used in conjunction with the {@link CommandUsage} object.
 */
public class Usage {

    private final String argumentName;
    private final CommandUsage.USAGE_TYPE usageType;
    private final boolean required;

    /**
     * Create a new {@link Usage} object.
     * @param argumentName The name of the argument.
     * @param usageType The {@link beanbeanjuice.beanpunishments.utilities.usages.CommandUsage.USAGE_TYPE Usage Type} of the argument.
     * @param required Whether or not the argument is required or not.
     */
    public Usage(String argumentName, CommandUsage.USAGE_TYPE usageType, boolean required) {
        this.argumentName = argumentName;
        this.usageType = usageType;
        this.required = required;
    }

    /**
     * @return The name of the particular {@link Usage}.
     */
    public String getHelp() {
        return argumentName;
    }

    /**
     * @return The {@link beanbeanjuice.beanpunishments.utilities.usages.CommandUsage.USAGE_TYPE Usage Type} of the argument.
     */
    public CommandUsage.USAGE_TYPE getUsageType() {
        return usageType;
    }

    /**
     * @return Whether or not the {@link Usage} argument is required.
     */
    public boolean isRequired() {
        return required;
    }

}
