package ir.ac.kntu;

public enum RequestSection {
    AUTHENTICATION, REPORT, FUNDS, CONTACT, TRANSFER, CHARGE, CARD, SETTING;

    @Override
    public String toString() {
        return switch (this) {
            case CARD -> "CARD";
            case FUNDS -> "FUNDS";
            case CHARGE -> "CHARGE";
            case REPORT -> "REPORT";
            case SETTING -> "SETTING";
            case CONTACT -> "CONTACT";
            case TRANSFER -> "TRANSFER";
            case AUTHENTICATION -> "AUTHENTICATION";
        };
    }
}
