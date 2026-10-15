/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;
/**
 *
 * @author MSI THIN 15
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDataBase {
    private static final String URL = "jdbc:mysql://localhost:3306/db_keuangan_pribadi";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private KoneksiDataBase() { }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
