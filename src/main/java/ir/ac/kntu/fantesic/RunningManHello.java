package ir.ac.kntu.fantesic;

import ir.ac.kntu.Colors;

public class RunningManHello {
    public static void main(String[] args) {
        String[] runningMan = { "    O          wellcome to feriNeoBank", "   /|\\", "   / \\" };
        for (int i = 0; i < 10; i++) {
            for (String line : runningMan) {
                for (int j = 0; j < i; j++) {
                    System.out.print(Colors.colorString() + " ");
                }
                System.out.println(line);
            }
            try {
                Thread.sleep(200); // Adjust the sleep time to change the speed of running
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("\033[H\033[2J"); // Clear the console
        }
    }
}
