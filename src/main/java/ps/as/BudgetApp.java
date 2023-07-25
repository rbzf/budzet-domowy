package ps.as;

import java.util.List;
import java.util.Scanner;

public class BudgetApp {

    private Scanner scanner = new Scanner(System.in);
    private final static String EXIT = "0";
    private final static String ADD_TRANSACTION = "1";
    private final static String MODIFY_TRANSACTION = "2";
    private final static String DELETE_TRANSACTION = "3";
    private final static String DISPLAY_ALL_INCOME = "4";
    private final static String DISPLAY_ALL_EXPENSES = "5";


    public void run() {
        TransactionDao dao = new TransactionDao();
        while (true) {
            printMenu();
            String option = getOption();

            switch (option) {
                case EXIT:
                    dao.close();
                    return;
                case ADD_TRANSACTION:
                    addTransaction(dao);
                    break;
                case MODIFY_TRANSACTION:
                    modifyTransaction(dao);
                    break;
                case DELETE_TRANSACTION:
                    deleteTransaction(dao);
                    break;
                case DISPLAY_ALL_INCOME:
                    printAllIncome(dao);
                    break;
                case DISPLAY_ALL_EXPENSES:
                    printAllExpenses(dao);
                    break;
                default:
                    System.out.println("Wybrana opcja nie istnieje.");
                    break;
            }
        }
    }

    private static void printAllExpenses(TransactionDao dao) {
        System.out.println("Wydatki:");
        List<Transaction> listOfTransactions = dao.findAllExpenses();
        printSelectedTransactions(listOfTransactions);
    }

    private static void printAllIncome(TransactionDao dao) {
        System.out.println("Przychody:");
        List<Transaction> listOfTransactions = dao.findAllIncome();
        printSelectedTransactions(listOfTransactions);
    }

    private void deleteTransaction(TransactionDao dao) {
        System.out.println("Podaj Id transakcji, ktora chcesz usunac:");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (dao.deleteTransaction(id)) {
            System.out.println("Transakcja usunieta z bazy danych.");
        } else System.out.println("Usuwanie transakcji nie powiodlo sie.");
    }

    private void modifyTransaction(TransactionDao dao) {
        System.out.println("Podaj Id transakcji, ktora chcesz zmodyfikowac:");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Podaj nowe dane transakcji:");
        Transaction transaction = getTransaction();
        transaction.setId(id);
        if (dao.modifyTransaction(transaction)) {
            System.out.println("Transakcja zostala zmodyfikowana.");
        } else System.out.println("Nie udalo sie zmodyfikowac transakcji");
    }

    private void addTransaction(TransactionDao dao) {
        System.out.println("Podaj dane transakcji, ktora chesz dodac:");
        Transaction transaction = getTransaction();
        dao.addTransaction(transaction);
        System.out.println("Transakcja dodana do bazy danych.");
    }

    private static void printSelectedTransactions(List<Transaction> listOfTransactions) {
        for (Transaction transaction : listOfTransactions) {
            System.out.println(transaction);
        }
    }

    private Transaction getTransaction() {
        System.out.println("jesli wydatek, to wpisz W, jesli przychod, wpisz P");
        Type type = Type.valueOf(scanner.nextLine());
        System.out.println("Podaj opis transakcji:");
        String description = scanner.nextLine();
        System.out.println("Podaj kwote transakcji:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Podaj date transakcji:");
        String date = scanner.nextLine();
        Transaction transaction = new Transaction(type, description, amount, date);
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
