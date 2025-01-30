package ir.ac.kntu;

public enum AuthenticationType {
    ACCEPT, INPROGRESS, REJECT;

    @Override
    public String toString() {
        return switch (this) {
            case ACCEPT -> "ACCEPT";
            case REJECT -> "REJECT";
            case INPROGRESS -> "INPROGRESS";
        };
    }
}
