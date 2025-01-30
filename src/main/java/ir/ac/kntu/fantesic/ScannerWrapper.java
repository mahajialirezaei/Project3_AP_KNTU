package ir.ac.kntu.fantesic;

import java.util.Scanner;

public class ScannerWrapper {
    private static Scanner scan = new Scanner(System.in);

    public static int nextInt() {
        return scan.nextInt();
    }

    public static double nextDouble() {
        return scan.nextDouble();
    }

    public static String nextLine() {
        return scan.nextLine();
    }

    public static boolean hasNextInt() {
        return scan.hasNextInt();
    }

    public static boolean hasNextDouble() {
        return scan.hasNextDouble();
    }

    public static boolean hasNextLine() {
        return scan.hasNextLine();
    }

    public static void next() {
        scan.next();
    }

    public static void useDelimiter(String delimiter) {
        scan.useDelimiter(delimiter);
    }

    public static void reset() {
        scan.reset();
    }

    public static void close() {
        scan.close();
    }

    public static Scanner getScanner() {
        return scan;
    }


    public static void setScanner(Scanner scan) {
        ScannerWrapper.scan = scan;
    }

    public static void closeScanner() {
        scan.close();
    }
}
