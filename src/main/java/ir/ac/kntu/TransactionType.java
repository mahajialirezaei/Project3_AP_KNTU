package ir.ac.kntu;

public enum TransactionType {
    TRANSFERCUSTOM, TRANSFERCONTACT, CHARGESIMCARD, CHARGE, WITHDRAW;

    @Override
    public String toString() {
        return switch (this) {
            case TRANSFERCUSTOM -> "TRANSFERCUSTOM";
            case TRANSFERCONTACT -> "TRANSFERCONTACT";
            case CHARGE -> "CHARGE";
            case WITHDRAW -> "WITHDRAW";
            case CHARGESIMCARD -> "CHARGESIMCARD";
        };
    }
}
