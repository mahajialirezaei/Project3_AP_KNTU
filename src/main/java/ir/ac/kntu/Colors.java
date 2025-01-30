package ir.ac.kntu;

import java.util.Random;

public class Colors {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_WHITE = "\u001B[37m";
    // public static final String RED_YELLOW = "\u001b[37;46m";
    public static final String PINK = "\u001b[38;5;201m";
    public static final String LAVENDER = "\u001b[38;5;147m";
    public static final String AQUA = "\u001b[38;2;145;231;255m";

    public static String colorString() {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        if (randomDouble < 0.1) {
            return ANSI_WHITE;
        } else if (randomDouble < 0.2) {
            return ANSI_GREEN;
        } else if (randomDouble < 0.4) {
            return ANSI_RED;
        } else if (randomDouble < 0.5) {
            return ANSI_BLUE;
        } else if (randomDouble < 0.6) {
            return AQUA;
        }
        return LAVENDER;

    }
}
