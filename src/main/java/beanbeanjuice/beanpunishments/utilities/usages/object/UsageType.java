package beanbeanjuice.beanpunishments.utilities.usages.object;

import beanbeanjuice.beanpunishments.BeanPunishments;

/**
 * The {@link UsageType} enum.
 * Used for displaying what type of argument it is.
 */
public enum UsageType {
    TEXT("text", BeanPunishments.getHelper().getConfigString("incorrect-syntax-text")),
    NUMBER("number", BeanPunishments.getHelper().getConfigString("incorrect-syntax-number")),
    PLAYER("player", BeanPunishments.getHelper().getConfigString("incorrect-syntax-player")),
    WORLD("world", BeanPunishments.getHelper().getConfigString("incorrect-syntax-world")),
    OTHER("other", BeanPunishments.getHelper().getConfigString("incorrect-syntax-other"));

    private final String name;
    private final String message;

    /**
     * Create a new {@link UsageType} enum static class.
     *
     * @param name    The name of the type of argument.
     * @param message The message to be used.
     */
    UsageType(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * @return The message associated with the particular {@link UsageType}.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The message associated with the particular {@link UsageType}.
     */
    public String getMessage() {
        return message;
    }
}
