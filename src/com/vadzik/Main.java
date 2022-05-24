package com.vadzik;

import java.util.Scanner;

public class Main {

    static String[] menu = {"Добавить задачу\n", "Удалить задачу\n", "Приостановить задачу\n", "Состояние памяти\n", "Выход\n"};
    static boolean isExit = false;
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        OperationMemory op = new OperationMemory();
        while (!isExit) {
            printMenu();
            System.out.print("Введите пункт меню: ");
            int option = in.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.print("Введите размер нового процесса: ");
                    int num = in.nextInt();
                    op.addProcces(new Procces(num, OperationMemory.procceses.size()));
                }
                case 2 -> {
                }
                case 3 -> {
                    System.out.print("Введите id процесса для приостановки: ");
                    int num = in.nextInt();
                    op.pauseProcces(num);
                }
                case 4 -> op.showStatusMemory();
                case 5 -> {
                    isExit = true;
                }
                default -> System.out.print("Введен неверный вариант!!!\n\n");
            }
        }
    }

    static void printMenu() {
        for (int i = 0; i < menu.length; i++) {
            System.out.printf("%d. %s", i + 1, menu[i]);
        }
    }
}
