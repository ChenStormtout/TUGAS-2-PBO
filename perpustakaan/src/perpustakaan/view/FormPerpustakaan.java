package perpustakaan.view;

import perpustakaan.controller.BukuController;
import perpustakaan.model.Buku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FormPerpustakaan extends JFrame {
    private JTextField tfId, tfJudul, tfGenre, tfPenulis, tfPenerbit, tfLokasi, tfStok, tfSearch;
    private JComboBox<String> cbKategori;
    private JTable table;
    private DefaultTableModel model;
    private final BukuController controller = new BukuController();
    private int selectedId = -1;

    public FormPerpustakaan() {
        setTitle("📚 Sistem Perpustakaan");
        setSize(850, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 🟦 Panel Form Input
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Buku"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        tfId = new JTextField(5); tfId.setEditable(false); tfId.setBackground(Color.LIGHT_GRAY);
        tfJudul = new JTextField(15); tfGenre = new JTextField(15);
        tfPenulis = new JTextField(15); tfPenerbit = new JTextField(15);
        tfLokasi = new JTextField(15); tfStok = new JTextField(5);

        int y = 0;
        formPanelAdd(formPanel, gbc, y++, "ID", tfId);
        formPanelAdd(formPanel, gbc, y++, "Judul", tfJudul);
        formPanelAdd(formPanel, gbc, y++, "Genre", tfGenre);
        formPanelAdd(formPanel, gbc, y++, "Penulis", tfPenulis);
        formPanelAdd(formPanel, gbc, y++, "Penerbit", tfPenerbit);
        formPanelAdd(formPanel, gbc, y++, "Lokasi", tfLokasi);
        formPanelAdd(formPanel, gbc, y++, "Stok", tfStok);

        // 🟦 Tombol Aksi
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JButton btnTambah = new JButton("Tambah");
        JButton btnUbah = new JButton("Ubah");
        JButton btnHapus = new JButton("Hapus");
        JButton btnReset = new JButton("Reset");
        buttonPanel.add(btnTambah); buttonPanel.add(btnUbah);
        buttonPanel.add(btnHapus); buttonPanel.add(btnReset);

        // 🟦 Panel Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cbKategori = new JComboBox<>(new String[]{"judul", "genre", "penulis", "penerbit"});
        tfSearch = new JTextField(20);
        JButton btnCari = new JButton("Cari");
        searchPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));
        searchPanel.add(new JLabel("Berdasarkan:"));
        searchPanel.add(cbKategori);
        searchPanel.add(tfSearch);
        searchPanel.add(btnCari);

        // 🟦 Tabel
        model = new DefaultTableModel(new String[]{"ID", "Judul", "Genre", "Penulis", "Penerbit", "Lokasi", "Stok"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ⬇️ Tambahkan ke Frame
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        leftPanel.setPreferredSize(new Dimension(330, getHeight()));

        add(searchPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        // 🧠 Aksi Tombol
        btnTambah.addActionListener(e -> tambahData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> resetForm());
        btnCari.addActionListener(e -> cariData());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                tfId.setText(String.valueOf(selectedId));
                tfJudul.setText(model.getValueAt(row, 1).toString());
                tfGenre.setText(model.getValueAt(row, 2).toString());
                tfPenulis.setText(model.getValueAt(row, 3).toString());
                tfPenerbit.setText(model.getValueAt(row, 4).toString());
                tfLokasi.setText(model.getValueAt(row, 5).toString());
                tfStok.setText(model.getValueAt(row, 6).toString());
            }
        });

        tampilkanData();
    }

    private void formPanelAdd(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField field) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label + ":"), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void tampilkanData() {
        model.setRowCount(0);
        for (Buku b : controller.getAll()) {
            model.addRow(new Object[]{
                b.getId(), b.getJudul(), b.getGenre(), b.getPenulis(),
                b.getPenerbit(), b.getLokasi(), b.getStok()
            });
        }
    }

    private void tambahData() {
        try {
            Buku b = new Buku(0,
                tfJudul.getText(), tfGenre.getText(), tfPenulis.getText(),
                tfPenerbit.getText(), tfLokasi.getText(), Integer.parseInt(tfStok.getText()));
            controller.tambah(b);
            tampilkanData();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Isi data dengan benar!");
        }
    }

    private void ubahData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel!");
            return;
        }
        try {
            Buku b = new Buku(selectedId,
                tfJudul.getText(), tfGenre.getText(), tfPenulis.getText(),
                tfPenerbit.getText(), tfLokasi.getText(), Integer.parseInt(tfStok.getText()));
            controller.update(b);
            tampilkanData();
            resetForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data tidak valid!");
        }
    }

    private void hapusData() {
        if (selectedId != -1) {
            controller.hapus(selectedId);
            tampilkanData();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
        }
    }

    private void resetForm() {
        tfId.setText(""); tfJudul.setText(""); tfGenre.setText(""); tfPenulis.setText("");
        tfPenerbit.setText(""); tfLokasi.setText(""); tfStok.setText(""); tfSearch.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    private void cariData() {
        String kategori = cbKategori.getSelectedItem().toString();
        String keyword = tfSearch.getText().trim();
        if (keyword.isEmpty()) {
            tampilkanData();
            return;
        }

        model.setRowCount(0);
        for (Buku b : controller.searchBy(kategori, keyword)) {
            model.addRow(new Object[]{
                b.getId(), b.getJudul(), b.getGenre(), b.getPenulis(),
                b.getPenerbit(), b.getLokasi(), b.getStok()
            });
        }
    }
}
