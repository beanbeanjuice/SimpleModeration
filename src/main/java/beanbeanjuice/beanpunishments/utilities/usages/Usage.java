package beanbeanjuice.beanpunishments.utilities.usages;

import beanbeanjuice.beanpunishments.utilities.usages.object.UsageType;

/**
 * A {@link Usage} to be used in conjunction with the {@link CommandUsage} object.
 */
public class Usage {

    private final String argumentName;
    private final UsageType usageType;
    private final boolean required;

    /**
     * Create a new {@link Usage} object.
     * @param argumentName The name of the argument.
     * @param usageType The {@link UsageType Usage Type} of the argument.
     * @param required Whether or not the argument is required or not.
     */
    public Usage(String argumentName, UsageType usageType, boolean required) {
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
     * @return The {@link UsageType Usage Type} of the argument.
     */
    public UsageType getUsageType() {
        return usageType;
    }

    /**
     * @return Whether or not the {@link Usage} argument is required.
     */
    public boolean isRequired() {
        return required;
    }

}
