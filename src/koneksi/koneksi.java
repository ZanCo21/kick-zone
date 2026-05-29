/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {
    public static Connection getConnection() {
    try {
        String url = "jdbc:mysql://localhost:3306/db_kick_zone";
        String user = "root";
        String pass = "";
        
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    } catch (Exception e) {
    System.out.println("Koneksi ke database gagal: " + e.getMessage());
    return null;
    }
    }
}
