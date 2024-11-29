package menu;

import java.util.Scanner;

public class Input {
    static Scanner scanner;

    private Input() {
    }

    private static void init() {
        scanner = new Scanner(System.in);
    }

    public static String readString(String message) {
        init();
        System.out.print(message + " ");
        return scanner.nextLine();
    }

    public static int readInt(String message) {
        init();
        System.out.print(message + " ");
        int v = scanner.nextInt();
        scanner.nextLine();
        return v;
    }
}
