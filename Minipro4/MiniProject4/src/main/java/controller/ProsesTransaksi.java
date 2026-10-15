package controller;

import database.TransaksiDAO;
import database.TransaksiDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Pemasukan;
import model.Pengeluaran;
import model.Transaksi;
/**
 *
 * @author MSI THIN 15
 */
public class ProsesTransaksi implements TransaksiService {
    private final TransaksiDAO transaksiDAO;

    public ProsesTransaksi() {
        this.transaksiDAO = new TransaksiDAOImpl();
    }

    @Override
    public int getNextId() {
        return 0;
    }

    @Override
    public void tambahTransaksi(Pemasukan p) {
        simpan(p);
    }

    @Override
    public void tambahTransaksi(Pengeluaran p) {
        simpan(p);
    }

    private void simpan(Transaksi transaksi) {
        try {
            if (!transaksiDAO.tambah(transaksi)) {
                throw new IllegalStateException("Transaksi gagal disimpan.");
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Gagal menyimpan transaksi ke database: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Transaksi> getDaftarTransaksi() {
        try {
            return transaksiDAO.getAll();
        } catch (SQLException e) {
            throw new IllegalStateException("Gagal membaca transaksi dari database: " + e.getMessage(), e);
        }
    }

    @Override
    public ArrayList<Transaksi> getDaftarTransaksi(String jenis) {
        ArrayList<Transaksi> hasil = new ArrayList<>();
        for (Transaksi t : getDaftarTransaksi()) {
            if (t.getJenis().equalsIgnoreCase(jenis)) hasil.add(t);
        }
        return hasil;
    }

    @Override
    public Transaksi cariTransaksi(int id) {
        for (Transaksi t : getDaftarTransaksi()) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    @Override
    public boolean updateTransaksi(int id, String keterangan, double jumlah) {
        try {
            return transaksiDAO.update(id, keterangan, jumlah);
        } catch (SQLException e) {
            throw new IllegalStateException("Gagal mengubah transaksi: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean hapusTransaksi(int id) {
        try {
            return transaksiDAO.hapus(id);
        } catch (SQLException e) {
            throw new IllegalStateException("Gagal menghapus transaksi: " + e.getMessage(), e);
        }
    }

    @Override
    public double hitungSaldo() {
        double saldo = 0;
        for (Transaksi t : getDaftarTransaksi()) saldo += t.hitungPengaruhSaldo();
        return saldo;
    }

    @Override
    public double hitungTotalPemasukan() {
        double total = 0;
        for (Transaksi t : getDaftarTransaksi()) {
            if (t instanceof Pemasukan) total += t.getJumlah();
        }
        return total;
    }

    @Override
    public double hitungTotalPengeluaran() {
        double total = 0;
        for (Transaksi t : getDaftarTransaksi()) {
            if (t instanceof Pengeluaran) total += t.getJumlah();
        }
        return total;
    }
}
