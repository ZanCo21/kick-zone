/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lapangan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

/**
 *  @author Berantakan
 */
public class tambah_penyewa extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tambah_penyewa.class.getName());
    private DefaultTableModel tableModel;
    public static String idTerpilih = "";
    public static String namaTimTerpilih = "";
    private boolean dipanggilDariBooking = false;
    
    // Deklarasi Komponen GUI secara manual
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JTable jTable1;
    private JScrollPane jScrollPane2;
    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnPilih;

    public tambah_penyewa() {
        this.dipanggilDariBooking = false;
        initSistem();
    }
    
    public tambah_penyewa(boolean modePilih) {
        this.dipanggilDariBooking = modePilih;
        initSistem();
        if (modePilih) {
            btnPilih.setVisible(true);
            jLabel2.setText("Mode Pilih Penyewa untuk Transaksi Booking");
        }
    }
    
    private void initSistem() {
        // 1. Instansiasi objek komponen GUI
        jLabel1 = new JLabel("Data penyewa");
        jLabel2 = new JLabel("Kelola data tim dan kontak pelanggan");
        jTable1 = new JTable();
        jScrollPane2 = new JScrollPane();
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        btnPilih = new JButton("Pilih & Masukkan Ke Booking");

        // 2. Konfigurasi Properti & Style
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manajemen Penyewa");
        getContentPane().setBackground(new Color(245, 247, 250));
        
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 24));
        jLabel1.setForeground(new Color(15, 23, 42));
        
        jLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        jLabel2.setForeground(new Color(100, 116, 139));
        
        btnTambah.setBackground(new Color(22, 163, 74));
        btnTambah.setForeground(Color.WHITE);  
        
        btnEdit.setBackground(new Color(37, 99, 235));
        btnEdit.setForeground(Color.WHITE);
        
        btnHapus.setBackground(new Color(220, 38, 38));
        btnHapus.setForeground(Color.WHITE);
        
        btnPilih.setBackground(new Color(234, 179, 8));
        btnPilih.setForeground(Color.BLACK);
        btnPilih.setVisible(false);
        
        // 3. Konfigurasi Tabel
        String[] columns = {"ID", "NM tim", "Nama Kontak", "No hp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        jScrollPane2.setViewportView(jTable1);

        // 4. Pasang Event Listener (Aksi Tombol)
        btnTambah.addActionListener(evt -> btnTambahActionPerformed(evt));
        btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        btnHapus.addActionListener(evt -> btnHapusActionPerformed(evt));
        btnPilih.addActionListener(evt -> btnPilihActionPerformed(evt));

        // 5. Penyusunan Layouting (BorderLayout + Panel Khusus)
        getContentPane().setLayout(new BorderLayout(20, 20));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // Panel Atas (Judul & Keterangan)
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.setOpaque(false);
        JPanel panelTeks = new JPanel(new GridLayout(2, 1, 0, 5));
        panelTeks.setOpaque(false);
        panelTeks.add(jLabel1);
        panelTeks.add(jLabel2);
        panelAtas.add(panelTeks, BorderLayout.WEST);
        panelAtas.add(btnTambah, BorderLayout.EAST);
        
        // Panel Bawah (Tombol Aksi)
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setOpaque(false);
        JPanel panelAksiKiri = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelAksiKiri.setOpaque(false);
        panelAksiKiri.add(btnEdit);
        panelAksiKiri.add(btnHapus);
        panelBawah.add(panelAksiKiri, BorderLayout.WEST);
        panelBawah.add(btnPilih, BorderLayout.EAST);
        
        // Masukkan semua panel utama ke Frame
        getContentPane().add(panelAtas, BorderLayout.NORTH);
        getContentPane().add(jScrollPane2, BorderLayout.CENTER);
        getContentPane().add(panelBawah, BorderLayout.SOUTH);

        pack();
        setSize(800, 550);
        setLocationRelativeTo(null);

        // Ambil data dari database
        loadData();
    }
    
    private void loadData() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getConnection();
            tableModel.setRowCount(0);
            
            String sql = "SELECT id_penyewa, nama_tim, nama_kontak, no_hp FROM penyewa ORDER BY id_penyewa ASC";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id_penyewa"),
                    rs.getString("nama_tim"),
                    rs.getString("nama_kontak"),
                    rs.getString("no_hp")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (pst != null) pst.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    private void tampilkanFormPenyewa(String id, String oldTim, String oldKontak, String oldHp, boolean isEdit) {
        JTextField txtNamaTim = new JTextField(oldTim);
        JTextField txtNamaKontak = new JTextField(oldKontak);
        JTextField txtNoHp = new JTextField(oldHp);
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Nama tim:"));
        formPanel.add(txtNamaTim);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(new JLabel("Nama Kontak:"));
        formPanel.add(txtNamaKontak);
        formPanel.add(Box.createVerticalStrut(8));
        formPanel.add(new JLabel("No Hp:"));
        formPanel.add(txtNoHp);
        
        int result = JOptionPane.showConfirmDialog(this, formPanel, isEdit ? "Edit Data Penyewa" : "Create Data Penyewa",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String tim = txtNamaTim.getText().trim();
            String kontak = txtNamaKontak.getText().trim();
            String hp = txtNoHp.getText().trim();

            if (tim.isEmpty() || kontak.isEmpty() || hp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua input field wajib diisi!", "Validasi Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Connection conn = null;
            PreparedStatement pst = null;
            try {
                conn = koneksi.getConnection();
                String sql;
                if (isEdit) {
                    sql = "UPDATE penyewa SET nama_tim=?, nama_kontak=?, no_hp=? WHERE id_penyewa=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, tim);
                    pst.setString(2, kontak);
                    pst.setString(3, hp);
                    pst.setString(4, id);
                } else {
                    sql = "INSERT INTO penyewa (nama_tim, nama_kontak, no_hp) VALUES (?, ?, ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, tim);
                    pst.setString(2, kontak);
                    pst.setString(3, hp);
                }

                if (pst.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Data penyewa berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage());
            } finally {
                try { if (pst != null) pst.close(); if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
    }
    
    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {
        tampilkanFormPenyewa("", "", "", "", false);
    }
    
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data penyewa pada tabel terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = jTable1.getValueAt(row, 0).toString();
        String tim = jTable1.getValueAt(row, 1).toString();
        String kontak = jTable1.getValueAt(row, 2).toString();
        String hp = jTable1.getValueAt(row, 3).toString();
        tampilkanFormPenyewa(id, tim, kontak, hp, true);
    }
    
    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = jTable1.getValueAt(row, 0).toString();
        String tim = jTable1.getValueAt(row, 1).toString();
        Connection conn = null;
        PreparedStatement pstCheck = null;
        PreparedStatement pstDelete = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getConnection();
            String sqlCheck = "SELECT COUNT(*) as total FROM booking WHERE id_penyewa = ?";
            pstCheck = conn.prepareStatement(sqlCheck);
            pstCheck.setString(1, id);
            rs = pstCheck.executeQuery();
            if (rs.next() && rs.getInt("total") > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Data tim '" + tim + "' tidak bisa dihapus karena id_penyewa tercatat di tabel booking!", 
                        "Hapus Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data penyewa tim '" + tim + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                String sqlDelete = "DELETE FROM penyewa WHERE id_penyewa = ?";
                pstDelete = conn.prepareStatement(sqlDelete);
                pstDelete.setString(1, id);         
                if (pstDelete.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                    loadData();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage());
        } finally {
            try { if (rs != null) rs.close(); if (pstCheck != null) pstCheck.close(); if (pstDelete != null) pstDelete.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
    
    private void btnPilihActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data tim pada tabel!");
            return;
        }
        idTerpilih = jTable1.getValueAt(row, 0).toString();
        namaTimTerpilih = jTable1.getValueAt(row, 1).toString();
        dispose();
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new tambah_penyewa().setVisible(true));
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}