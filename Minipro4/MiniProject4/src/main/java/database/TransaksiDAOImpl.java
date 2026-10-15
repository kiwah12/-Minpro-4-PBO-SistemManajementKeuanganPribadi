/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Pemasukan;
import model.Pengeluaran;
import model.Transaksi;
/**
 *
 * @author MSI THIN 15
 */
public class TransaksiDAOImpl implements TransaksiDAO {

    @Override
    public ArrayList<Transaksi> getAll() throws SQLException {
        ArrayList<Transaksi> daftar = new ArrayList<>();
        String sql = "SELECT id, tanggal, jenis, kategori, keterangan, jumlah, "
                   + "sumber_dana, metode_pembayaran FROM transaksi ORDER BY id";

        try (Connection conn = KoneksiDataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String tanggal = rs.getString("tanggal");
                String jenis = rs.getString("jenis");
                String kategori = rs.getString("kategori");
                String keterangan = rs.getString("keterangan");
                double jumlah = rs.getDouble("jumlah");

                if ("Pemasukan".equalsIgnoreCase(jenis)) {
                    daftar.add(new Pemasukan(id, tanggal, kategori, keterangan,
                            jumlah, rs.getString("sumber_dana")));
                } else if ("Pengeluaran".equalsIgnoreCase(jenis)) {
                    daftar.add(new Pengeluaran(id, tanggal, kategori, keterangan,
                            jumlah, rs.getString("metode_pembayaran")));
                }
            }
        }
        return daftar;
    }

    @Override
    public boolean tambah(Transaksi transaksi) throws SQLException {
        String sql = "INSERT INTO transaksi "
                + "(tanggal, jenis, kategori, keterangan, jumlah, sumber_dana, metode_pembayaran) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = KoneksiDataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, transaksi.getTanggal());
            ps.setString(2, transaksi.getJenis());
            ps.setString(3, transaksi.getKategori());
            ps.setString(4, transaksi.getKeterangan());
            ps.setDouble(5, transaksi.getJumlah());

            if (transaksi instanceof Pemasukan) {
                ps.setString(6, ((Pemasukan) transaksi).getSumberDana());
                ps.setNull(7, java.sql.Types.VARCHAR);
            } else if (transaksi instanceof Pengeluaran) {
                ps.setNull(6, java.sql.Types.VARCHAR);
                ps.setString(7, ((Pengeluaran) transaksi).getMetodePembayaran());
            } else {
                ps.setNull(6, java.sql.Types.VARCHAR);
                ps.setNull(7, java.sql.Types.VARCHAR);
            }

            int affected = ps.executeUpdate();
            if (affected == 0) return false;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) transaksi.setId(keys.getInt(1));
            }
            return true;
        }
    }

    @Override
    public boolean update(int id, String keterangan, double jumlah) throws SQLException {
        String sql = "UPDATE transaksi SET keterangan = ?, jumlah = ? WHERE id = ?";
        try (Connection conn = KoneksiDataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, keterangan);
            ps.setDouble(2, jumlah);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean hapus(int id) throws SQLException {
        String sql = "DELETE FROM transaksi WHERE id = ?";
        try (Connection conn = KoneksiDataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
