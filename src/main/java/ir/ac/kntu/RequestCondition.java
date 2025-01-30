package ir.ac.kntu;

public enum RequestCondition {
    REGISTERED, INPROGRESS, CLOSED;

    @Override
    public String toString() {
        return switch (this) {
            case INPROGRESS -> "INPROGRESS";
            case CLOSED -> "CLOSED";
            case REGISTERED -> "REGISTERED";
        };
    }
}
