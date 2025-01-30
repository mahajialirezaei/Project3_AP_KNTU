package ir.ac.kntu;

public enum TransferType {
    CUSTOM, CONTACT, PAYA, POL, CARDTOCARD;

    @Override
    public String toString() {
        return switch (this) {
            case CUSTOM -> "CUSTOM";
            case CONTACT -> "CONTACT";
            case PAYA -> "CUSTOMPAYA";
            case POL -> "CUSTOMPOL";
            case CARDTOCARD -> "CARDTOCARD";
        };
    }

}
