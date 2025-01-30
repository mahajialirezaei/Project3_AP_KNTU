package ir.ac.kntu;

public class SimilarityAlgorithm {
    public static boolean isSimilarity(String mainString, String subString) {
        return similarity(mainString, subString) >= 0.5;
    }

    public static double similarity(String mainString, String subString) {
        String longer = mainString, shorter = subString;
        if (mainString.length() < subString.length()) { // longer should always have greater length
            longer = subString;
            shorter = mainString;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String string1, String string2) {
        string1 = string1.toLowerCase();
        string2 = string2.toLowerCase();

        int[] costs = new int[string2.length() + 1];
        for (int i = 0; i <= string1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= string2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (string1.charAt(i - 1) != string2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[string2.length()] = lastValue;
            }
        }
        return costs[string2.length()];
    }
}