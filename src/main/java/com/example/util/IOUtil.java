package com.example.util;

import java.util.List;
import java.util.Scanner;

public class IOUtil {
    public static String inputString(String msg, int maxLength) {
        boolean isCorrectInput;
        String str;

        do {
            isCorrectInput = true;

            Scanner scanner = new Scanner(System.in);
            System.out.print(msg);

            str = scanner.nextLine();

            if (str.length() == 0 || str.length() > maxLength) {
                isCorrectInput = false;
                System.out.println(String.format("Ошибка. Длина строки должна быть от 1 до %d символов", maxLength));
            }

        } while (!isCorrectInput);

        return str;
    }

    public static int inputInt(String msg, int min, int max) {
        boolean isCorrectInput;
        int num = 0;

        do {
            isCorrectInput = true;

            Scanner scanner = new Scanner(System.in);
            System.out.print(msg);

            try {
                num = scanner.nextInt();
                if (num < min || num > max) {
                    isCorrectInput = false;
                    System.out.println(String.format("Ошибка. Число должно быть от %d до %d", min, max));
                }
            } catch (Exception e) {
                isCorrectInput = false;
                System.out.println("Ошибка. Введено не число");
            }

        } while (!isCorrectInput);

        return num;
    }

    public static long inputLong(String msg, List<Long> numbers) {
        boolean isCorrectInput;
        long num = 0;

        do {
            isCorrectInput = true;

            Scanner scanner = new Scanner(System.in);
            System.out.print(msg);

            try {
                num = scanner.nextLong();
                if (!numbers.contains(num)) {
                    isCorrectInput = false;
                    System.out.println(String.format("Ошибка. Число должно быть в массиве чисел"));
                }
            } catch (Exception e) {
                isCorrectInput = false;
                System.out.println("Ошибка. Введено не число");
            }

        } while (!isCorrectInput);

        return num;
    }

    public static void println(String text) {
        System.out.println(text);
    }
}
