package ir.ac.kntu;

public class Amounts {
    private static double bonus = 0.2;
    private static double cToCardIncome = 300;

    private static double polIncome = 0.02;

    private static double payaIncome = 2000;

    private static double inChargePhoneNum = 0.1;

    public static double getInChargePhoneNum() {
        return inChargePhoneNum;
    }

    public static void setInChargePhoneNum(double inChargePhoneNum) {
        Amounts.inChargePhoneNum = inChargePhoneNum;
    }

    public static double getBonus() {
        return bonus;
    }

    public static void setBonus(double bounds) {
        Amounts.bonus = bounds;
    }

    public static double getcToCardIncome() {
        return cToCardIncome;
    }

    public static void setcToCardIncome(double cToCardIncome) {
        Amounts.cToCardIncome = cToCardIncome;
    }

    public static double getPolIncome() {
        return polIncome;
    }

    public static void setPolIncome(double polIncome) {
        Amounts.polIncome = polIncome;
    }

    public static double getPayaIncome() {
        return payaIncome;
    }

    public static void setPayaIncome(double payaIncome) {
        Amounts.payaIncome = payaIncome;
    }


}
