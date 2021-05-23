package beanbeanjuice.beanpunishments.utilities.usages.object;

import beanbeanjuice.beanpunishments.BeanPunishments;

/**
 * The {@link ArgumentAmount} enum.
 * Used for displaying what is wrong with that argument.
 */
public enum ArgumentAmount {
    TOO_MANY(BeanPunishments.getHelper().getConfigString("too-many-arguments")),
    NOT_ENOUGH(BeanPunishments.getHelper().getConfigString("not-enough-arguments")),
    CORRECT_AMOUNT("Correct Amount");

    private final String message;

    /**
     * Create a new {@link ArgumentAmount} enum static class.
     *
     * @param message The message to be used.
     */
    ArgumentAmount(String message) {
        this.message = message;
    }

    /**
     * @return The message associated with the particular {@link ArgumentAmount}.
     */
    public String getMessage() {
        return message;
    }
}
