package org.example;

import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileWriter;
import java.sql.*;

public class JDBC_Logs {
    private final static Logger log = LoggerFactory.getLogger(JDBC_Logs.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://10.1.27.41:5432/dummydb";
        String user = "dummyuser";
        String password = "password1";
        String query = "select * from adit";

        log.info("Starting Database Connection");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             CSVWriter writer = new CSVWriter(new FileWriter("account.csv"))){

            log.info("Connected to database and execting the query");

            try (ResultSet rs = stmt.executeQuery(query)) {
                // Exporting data to CSV
                writer.writeAll(rs, false);
                // Log successful export
                log.info("Data exported to column_header-false.csv successfully.");
            } catch (SQLException e) {
                log.error("Error executing query: {}. SQLState: {}. Message: {}", query, e.getSQLState(), e.getMessage());
            }
        } catch (SQLException e) {
            if (e.getSQLState() != null && e.getSQLState().startsWith("28")) { // SQLSTATE code for invalid username or password
                log.error("Invalid username or password: {}", e.getMessage());
            } else if (e.getSQLState() != null && e.getSQLState().startsWith("08")) { // SQLSTATE code for connection issues
                log.error("Database connection failed: {}", e.getMessage());
            } else {
                log.error("SQL error occurred: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            // Log general exceptions
            log.error("An error occurred while exporting data: {}", e.getMessage(), e);
        }
    }
}
