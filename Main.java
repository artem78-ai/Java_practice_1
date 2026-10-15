import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private static ComplexMatrix current;

    public static void main(String[] args) {

        current = readMatrix("A");
        System.out.println("\nТекущая матрица:");
        System.out.println(current);

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Выбор: ");

            try {
                switch (choice) {
                    case 1 -> {
                        ComplexMatrix b = readMatrix("B");
                        current = current.add(b);
                        printResult("Результат сложения:");
                    }
                    case 2 -> {
                        ComplexMatrix b = readMatrix("B");
                        current = current.multiply(b);
                        printResult("Результат умножения:");
                    }
                    case 3 -> {
                        ComplexMatrix b = readMatrix("B");
                        current = current.divide(b);
                        printResult("Результат деления (A * B^-1):");
                    }
                    case 4 -> {
                        current = current.transpose();
                        printResult("Транспонированная матрица:");
                    }
                    case 5 -> System.out.println("Определитель: " + current.determinant());
                    case 0 -> {
                        running = false;
                    }
                    default -> System.out.println("Неверный выбор");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n       Меню операций       ");
        System.out.println("Текущая матрица:");
        System.out.println(current);
        System.out.println();
        System.out.println("1 - Сложить с матрицей B");
        System.out.println("2 - Умножить на матрицу B");
        System.out.println("3 - Разделить на матрицу B");
        System.out.println("4 - Транспонировать");
        System.out.println("5 - Вычислить определитель");
        System.out.println("0 - Выход");
    }

    private static void printResult(String title) {
        System.out.println(title + "\n" + current);
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    private static ComplexMatrix readMatrix(String name) {
        System.out.println("\nВведите размеры матрицы " + name + ":");
        int rows = readInt("Строк: ");
        int cols = readInt("Столбцов: ");

        ComplexMatrix matrix = new ComplexMatrix(rows, cols);

        System.out.println("Введите " + (rows * cols) + " комплексных чисел.");
        System.out.println("Каждое число — два значения через пробел: действительная мнимая");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("Элемент [%d][%d]: ", i, j);
                double real = scanner.nextDouble();
                double imag = scanner.nextDouble();
                matrix.set(i, j, new ComplexNumber(real, imag));
            }
        }
        return matrix;
    }
}
