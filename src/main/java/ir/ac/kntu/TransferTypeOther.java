package ir.ac.kntu;

public enum TransferTypeOther {
    CARDTOCARD, POL, PAYA;

    @Override
    public String toString() {
        return switch (this){
            case CARDTOCARD -> "CARDTOCARD";
            case PAYA -> "PAYA";
            case POL -> "POL";
        };
    }
}
