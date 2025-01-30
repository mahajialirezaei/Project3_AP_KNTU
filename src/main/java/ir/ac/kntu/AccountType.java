package ir.ac.kntu;

public enum AccountType {
    FERRI, OTHER;

    @Override
    public String toString() {
        return switch (this) {
            case FERRI -> "FERRI";
            case OTHER -> "OTHER";
        };
    }
}
