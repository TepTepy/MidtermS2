import java.sql.*;

public class ProductList {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
        ) {
            // Create table if not exists
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Product ("
                                   + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                   + "name VARCHAR(100),"
                                   + "price_per_unit DOUBLE,"
                                   + "active_for_sell TINYINT(1)"
                                   + ")");
            
            // Insert initial data using batch
            String insertSQL = "INSERT INTO Product (name, price_per_unit, active_for_sell) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, "Coke");
                preparedStatement.setDouble(2, 2.00);
                preparedStatement.setBoolean(3, true);
                preparedStatement.addBatch(); // Add to batch
                preparedStatement.setString(1, "Pepsi");
                preparedStatement.setDouble(2, 2.00);
                preparedStatement.setBoolean(3, true);
                preparedStatement.addBatch(); // Add to batch
                preparedStatement.setString(1, "Kizz");
                preparedStatement.setDouble(2, 1.50);
                preparedStatement.setBoolean(3, true);
                preparedStatement.addBatch(); // Add to batch
                preparedStatement.setString(1, "Redbull");
                preparedStatement.setDouble(2, 2.00);
                preparedStatement.setBoolean(3, false);
                preparedStatement.addBatch(); // Add to batch
                preparedStatement.executeBatch(); // Execute batch
            }
            
            // Retrieve and display data
            try (ResultSet resultSet = statement.executeQuery("SELECT id, name, price_per_unit, active_for_sell FROM Product")) {
                System.out.println("id\tname\t\tprice_per_unit\tactive_for_sell");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double pricePerUnit = resultSet.getDouble("price_per_unit");
                    boolean activeForSell = resultSet.getBoolean("active_for_sell");

                    System.out.printf("%d\t%s\t\t%.2f\t\t%s%n", id, name, pricePerUnit, activeForSell ? "Yes" : "No");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}