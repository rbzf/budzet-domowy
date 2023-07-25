package ps.as;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modifyTransaction(Transaction transaction) {
        String sql = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(transaction.getType()));
            preparedStatement.setString(2, transaction.getDescription());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getDate());
            preparedStatement.setInt(5, transaction.getId());
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTransaction(int id) {
        String sql = "DELETE FROM transaction WHERE id = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findAllIncome() {
        String sql = "SELECT * FROM transaction WHERE type = 'P'";
        return getTransactions(sql);
    }

    private List<Transaction> getTransactions(String sql) {
        PreparedStatement preparedStatement;
        List<Transaction> listOfTransactions = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Type type = Type.valueOf(resultSet.getString("type"));
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String date = resultSet.getString("date");
                Transaction transaction = new Transaction(id, type, description, amount, date);
                listOfTransactions.add(transaction);
            }
            return listOfTransactions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> findAllExpenses() {
        String sql = "SELECT * FROM transaction WHERE type = 'W'";
        return getTransactions(sql);
    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
