package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;

public class DisconnectSimCards {
    private static List<SimCard> simcards = new ArrayList<SimCard>();

    public static void add(SimCard simCard) {
        simcards.add(simCard);
    }

    public static List<SimCard> getSimcards() {
        return simcards;
    }

    public static void setSimcards(List<SimCard> sims) {
        simcards = sims;
    }

}

