
package perpustakaan.controller;

import perpustakaan.config.DBConnection;
import perpustakaan.model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuController {

    // Tambah data buku
    public void tambah(Buku b) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO buku (judul, genre, penulis, penerbit, lokasi, stok) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getJudul());
            ps.setString(2, b.getGenre());
            ps.setString(3, b.getPenulis());
            ps.setString(4, b.getPenerbit());
            ps.setString(5, b.getLokasi());
            ps.setInt(6, b.getStok());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Ubah data buku
    public void update(Buku b) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE buku SET judul=?, genre=?, penulis=?, penerbit=?, lokasi=?, stok=? WHERE id_buku=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, b.getJudul());
            ps.setString(2, b.getGenre());
            ps.setString(3, b.getPenulis());
            ps.setString(4, b.getPenerbit());
            ps.setString(5, b.getLokasi());
            ps.setInt(6, b.getStok());
            ps.setInt(7, b.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hapus data buku
    public void hapus(int id) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM buku WHERE id_buku=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Tampilkan semua data
    public List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM buku")) {

            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id_buku"),
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getString("penulis"),
                    rs.getString("penerbit"),
                    rs.getString("lokasi"),
                    rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cari data berdasarkan kategori dan keyword
    public List<Buku> searchBy(String field, String keyword) {
        List<Buku> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM buku WHERE " + field + " LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Buku(
                    rs.getInt("id_buku"),
                    rs.getString("judul"),
                    rs.getString("genre"),
                    rs.getString("penulis"),
                    rs.getString("penerbit"),
                    rs.getString("lokasi"),
                    rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

