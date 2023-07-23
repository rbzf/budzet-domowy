package ps.as;

import java.util.Scanner;

public class BudgetApp {

    Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            printMenu();
            String option = getOption();
            TransactionDao DAO = new TransactionDao();
            switch (option) {
                case "0":
                    DAO.close();
                    return;
                case "1":
                    System.out.println("Podaj dane transakcji, ktora chesz dodac:");
                    Transaction transaction = getTransaction(null);
                    DAO.addTransaction(transaction);
                    break;
                case "2":
                    System.out.println("Podaj Id transakcji, ktora chcesz zmodyfikowac:");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Podaj nowe dane transakcji:");
                    transaction = getTransaction(id);
                    DAO.modifyTransaction(transaction);
                    break;
                case "3":
                    System.out.println("Podaj Id transakcji, ktora chcesz usunac:");
                    id = scanner.nextInt();
                    DAO.deleteTransaction(id);
                    break;
                case "4":
                    DAO.printAllIncome();
                    break;
                case "5":
                    DAO.printAllExpenses();
                    break;
                default:
                    System.out.println("Wybrana opcja nie istnieje.");
                    break;
            }
        }
    }

    private Transaction getTransaction(Integer id) {
        System.out.println("jesli wydatek, to wpisz W, jesli przychod, wpisz P");
        Type type = Type.valueOf(scanner.nextLine());
        System.out.println("Podaj opis transakcji:");
        String description = scanner.nextLine();
        System.out.println("Podaj kwote transakcji:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj date transakcji:");
        String date = scanner.nextLine();
        Transaction transaction;
        if (id == null) {
            transaction = new Transaction(type, description, amount, date);
        } else
            transaction = new Transaction(id, type, description, amount, date);

        return transaction;
    }

    private String getOption() {
        String option = scanner.nextLine();
        return option;
    }

    private static void printMenu() {
        System.out.println("Witaj w programie budzet domowy!");
        System.out.println("Dostepne operacje:");
        System.out.println("0 - wyjscie z programu");
        System.out.println("1 - dodawanie transakcji");
        System.out.println("2 - modyfikacja transakcji");
        System.out.println("3 - usuwanie transakcji");
        System.out.println("4 - wyświetlanie wszystkich przychodów");
        System.out.println("5 - wyświetlanie wszystkich wydatków");
        System.out.println("Ktora operacje chcesz wykonac?");
    }
}
