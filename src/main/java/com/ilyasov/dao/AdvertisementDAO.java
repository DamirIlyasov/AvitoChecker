package com.ilyasov.dao;

import com.ilyasov.entity.Advertisement;

import java.sql.*;
import java.util.List;

public class AdvertisementDAO {

    private Connection conn;
    private Statement statmt;
    private ResultSet resSet;

    public AdvertisementDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:/home/damir/IdeaProjects/AvitoChecker/src/main/webapp/resources/database/avito  ");
            statmt = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized int countRows() {
        try {
            resSet = statmt.executeQuery("SELECT COUNT(*) AS rowcount FROM advertisements");
            resSet.next();
            int count = resSet.getInt("rowcount");
            resSet.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public synchronized void putAllAdvertisements(List<Advertisement> advertisements) {
        for (Advertisement adv : advertisements) {
            try {
                String sqlInsert = ("INSERT OR REPLACE INTO  advertisements (year, price,city,description,created_at) VALUES (?,?,?,?,?); ");
                PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
                preparedStatement.setInt(1, adv.getYear());
                preparedStatement.setInt(2, adv.getPrice());
                preparedStatement.setString(3, adv.getCity());
                preparedStatement.setString(4, adv.getDescription());
                preparedStatement.setString(5, adv.getCreatedAtFormatted());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
