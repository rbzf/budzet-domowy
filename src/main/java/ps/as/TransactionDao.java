package ps.as;

import java.sql.*;

public class TransactionDao {

    private Connection connection;

    public TransactionDao() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/budzet_domowy", "root", "aplauz");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (type, description, amount, date) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(transaction.getType()));
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getDate());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
               transaction.setId(generatedKeys.getInt(1));
           }
            System.out.println("Transakcja dodana do bazy danych.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void modifyTransaction(Transaction transaction) {
        String sql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(transaction.getType()));
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getDate());
            preparedStatement.setInt(5, transaction.getId());
            preparedStatement.executeUpdate();
            System.out.println("Transakcja zostala zmodyfikowana.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTransaction(int id) {
        String sql = "DELETE FROM transaction WHERE id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Transakcja usunieta z bazy danych.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void printAllIncome() {
        String sql = "SELECT * FROM transaction WHERE type = 'P'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Przychody:");
            printSelectedTransactions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void printAllExpenses() {
        String sql = "SELECT * FROM transaction WHERE type = 'W'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Wydatki:");
            printSelectedTransactions(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printSelectedTransactions(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            Type type = Type.valueOf(resultSet.getString("type"));
            String description = resultSet.getString("description");
            double amount = resultSet.getDouble("amount");
            String date = resultSet.getString("date");
            Transaction transaction = new Transaction(id, type, description, amount, date);
            System.out.println(transaction);
        }
    }


    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
