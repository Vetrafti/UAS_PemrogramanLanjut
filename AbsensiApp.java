/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplikasiabsensi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class AbsensiApp extends JFrame {
    private AbsensiManager manager = new AbsensiManager();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nimField, namaField, ketField, tanggalField, searchField;
    private JComboBox<String> searchCombo, statusCombo;

    public AbsensiApp() {
        setTitle("Aplikasi Absensi Kelas");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        // 🔴 Warna background maroon
        Color maroon = new Color(128, 0, 0);
        getContentPane().setBackground(maroon);

        // Panel Search
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.setBackground(maroon);
        panelSearch.add(new JLabel("Cari Berdasarkan:")).setForeground(Color.WHITE);

        searchCombo = new JComboBox<>(new String[]{"Nama", "NIM", "Keterangan"});
        panelSearch.add(searchCombo);

        searchField = new JTextField(20);
        panelSearch.add(searchField);

        JButton btnCari = new JButton("Search");
        panelSearch.add(btnCari);

        btnCari.addActionListener(e -> cariData(searchField.getText()));

        // Panel Input
        JPanel panelInput = new JPanel(new GridLayout(6, 2, 10, 10));
        panelInput.setBackground(maroon);

        panelInput.add(labelWithColor("NIM:"));
        nimField = new JTextField(); panelInput.add(nimField);

        panelInput.add(labelWithColor("Nama:"));
        namaField = new JTextField(); panelInput.add(namaField);

        panelInput.add(labelWithColor("Keterangan:"));
        ketField = new JTextField(); panelInput.add(ketField);

        panelInput.add(labelWithColor("Tanggal (dd-MM-yyyy):"));
        tanggalField = new JTextField(); panelInput.add(tanggalField);

        panelInput.add(labelWithColor("Status:"));
        statusCombo = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif"});
        panelInput.add(statusCombo);

        // Panel Tombol
        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnHapus = new JButton("Hapus");

        JPanel panelButton = new JPanel();
        panelButton.setBackground(maroon);
        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"NIM", "Nama", "Keterangan", "Tanggal", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout
        setLayout(new BorderLayout());
        add(panelSearch, BorderLayout.NORTH);
        add(panelInput, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // Aksi Tombol
        btnTambah.addActionListener(e -> tambahData());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());

        // Klik Tabel
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    nimField.setText(tableModel.getValueAt(row, 0).toString());
                    namaField.setText(tableModel.getValueAt(row, 1).toString());
                    ketField.setText(tableModel.getValueAt(row, 2).toString());
                    tanggalField.setText(tableModel.getValueAt(row, 3).toString());
                    statusCombo.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private JLabel labelWithColor(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void tambahData() {
        if (isFormKosong()) return;

        Mahasiswa mhs = new Mahasiswa(
            nimField.getText(), namaField.getText(), ketField.getText(),
            tanggalField.getText(), statusCombo.getSelectedItem().toString()
        );
        manager.tambah(mhs);
        tableModel.addRow(new Object[]{
            mhs.getNim(), mhs.getNama(), mhs.getKeterangan(), mhs.getTanggal(), mhs.getStatus()
        });
        clearFields();
    }

    private void updateData() {
        int selected = table.getSelectedRow();
        if (selected >= 0 && !isFormKosong()) {
            Mahasiswa mhs = new Mahasiswa(
                nimField.getText(), namaField.getText(), ketField.getText(),
                tanggalField.getText(), statusCombo.getSelectedItem().toString()
            );
            manager.update(selected, mhs);
            tableModel.setValueAt(mhs.getNim(), selected, 0);
            tableModel.setValueAt(mhs.getNama(), selected, 1);
            tableModel.setValueAt(mhs.getKeterangan(), selected, 2);
            tableModel.setValueAt(mhs.getTanggal(), selected, 3);
            tableModel.setValueAt(mhs.getStatus(), selected, 4);
            clearFields();
        }
    }

    private void hapusData() {
        int selected = table.getSelectedRow();
        if (selected >= 0) {
            manager.hapus(selected);
            tableModel.removeRow(selected);
            clearFields();
        }
    }

    private void cariData(String keyword) {
        String kategori = searchCombo.getSelectedItem().toString().toLowerCase();
        tableModel.setRowCount(0);

        for (Mahasiswa mhs : manager.getSemuaMahasiswa()) {
            boolean cocok = switch (kategori) {
                case "nama" -> mhs.getNama().toLowerCase().contains(keyword.toLowerCase());
                case "nim" -> mhs.getNim().toLowerCase().contains(keyword.toLowerCase());
                case "keterangan" -> mhs.getKeterangan().toLowerCase().contains(keyword.toLowerCase());
                default -> false;
            };
            if (cocok) {
                tableModel.addRow(new Object[]{
                    mhs.getNim(), mhs.getNama(), mhs.getKeterangan(), mhs.getTanggal(), mhs.getStatus()
                });
            }
        }
    }

    private boolean isFormKosong() {
        if (nimField.getText().isEmpty() || namaField.getText().isEmpty() ||
            ketField.getText().isEmpty() || tanggalField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    private void clearFields() {
        nimField.setText("");
        namaField.setText("");
        ketField.setText("");
        tanggalField.setText("");
        statusCombo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AbsensiApp().setVisible(true));
    }
}
