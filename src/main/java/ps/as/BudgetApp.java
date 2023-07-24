package ps.as;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BudgetApp {

    private Scanner scanner = new Scanner(System.in);
    private final static String WYJSCIE_Z_PROGRAMU = "0";
    private final static String DODAWANIE_TRANSAKCJI = "1";
    private final static String MODYFIKACJA_TRANSAKCJI = "2";
    private final static String USUWANIE_TRANSAKCJI = "3";
    private final static String WYSWIETLANIE_WSZYSTKICH_PRZYCHODOW = "4";
    private final static String WYSWIETLANIE_WSZYSTKICH_WYDATKOW = "5";


    public void run() {
        TransactionDao dao = new TransactionDao();
        while (true) {
            printMenu();
            String option = getOption();

            switch (option) {
                case WYJSCIE_Z_PROGRAMU:
                    dao.close();
                    return;
                case DODAWANIE_TRANSAKCJI:
                    addTransaction(dao);
                    break;
                case MODYFIKACJA_TRANSAKCJI:
                    modifyTransaction(dao);
                    break;
                case USUWANIE_TRANSAKCJI:
                    deleteTransaction(dao);
                    break;
                case WYSWIETLANIE_WSZYSTKICH_PRZYCHODOW:
                    printAllIncome(dao);
                    break;
                case WYSWIETLANIE_WSZYSTKICH_WYDATKOW:
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
        ResultSet resultSet = dao.findAllExpenses();
        printSelectedTransactions(resultSet);
    }

    private static void printAllIncome(TransactionDao dao) {
        System.out.println("Przychody:");
        ResultSet resultSet = dao.findAllIncome();
        printSelectedTransactions(resultSet);
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

    private static void printSelectedTransactions(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Type type = Type.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                Transaction transaction = new Transaction(id, type, description, amount, date);
                System.out.println(transaction);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
