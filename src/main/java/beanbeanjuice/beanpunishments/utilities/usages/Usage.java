package beanbeanjuice.beanpunishments.utilities.usages;

public class Usage {

    private final String argumentName;
    private final CommandUsage.USAGE_TYPE usageType;
    private final boolean required;

    public Usage(String argumentName, CommandUsage.USAGE_TYPE usageType, boolean required) {
        this.argumentName = argumentName;
        this.usageType = usageType;
        this.required = required;
    }

    public String getHelp() {
        return argumentName;
    }

    public CommandUsage.USAGE_TYPE getUsageType() {
        return usageType;
    }

    public boolean isRequired() {
        return required;
    }

}
