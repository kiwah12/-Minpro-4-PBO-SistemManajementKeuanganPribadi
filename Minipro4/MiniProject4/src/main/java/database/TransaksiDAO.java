/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;
import model.Transaksi;
/**
 *
 * @author MSI THIN 15
 */
public interface TransaksiDAO {
    ArrayList<Transaksi> getAll() throws SQLException;
    boolean tambah(Transaksi transaksi) throws SQLException;
    boolean update(int id, String keterangan, double jumlah) throws SQLException;
    boolean hapus(int id) throws SQLException;
}
