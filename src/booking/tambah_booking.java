/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import koneksi.koneksi;
import penyewa.tambah_penyewa;

/**
 *
 * @author dell
 */
public class tambah_booking extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(tambah_booking.class.getName());
    private String id_booking_edit = "";
    /**
     * Creates new form tambah_booking
     */
    
    public tambah_booking() {
        initComponents();
        tampil_penyewa();
        tampil_lapangan();
        this.id_booking_edit = ""; 
        jButtonCancel1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new booking.lihat_booking().setVisible(true);
                dispose();
            }
        });
        reset_form();
    }
    
    public tambah_booking(String id_booking, String id_lapangan, String id_penyewa, String tanggal, String mulai, String selesai, String total, String status) {
        initComponents();
        tampil_penyewa();
        tampil_lapangan();
        reset_form();
        jButtonCancel1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new booking.lihat_booking().setVisible(true);
                dispose();
            }
        });
        
        // Kunci ID booking lama ke variabel global
        this.id_booking_edit = id_booking; 
        
        // Set data ke komponen inputan
        jTextFieldJamMulai.setText(mulai);
        jTextFieldJamSelesai.setText(selesai);
        jTextFieldTotalBiaya.setText(total);
        
        // Pilih ComboBox Lapangan otomatis berdasarkan ID Lama
        for (int i = 0; i < jComboBoxLapangan.getItemCount(); i++) {
            if (jComboBoxLapangan.getItemAt(i).startsWith(id_lapangan)) {
                jComboBoxLapangan.setSelectedIndex(i);
                break;
            }
        }
        
        // Pilih ComboBox Penyewa otomatis berdasarkan ID Lama
        for (int i = 0; i < jComboBoxPenyewa.getItemCount(); i++) {
            if (jComboBoxPenyewa.getItemAt(i).startsWith(id_penyewa)) {
                jComboBoxPenyewa.setSelectedIndex(i);
                break;
            }
        }
        
        // Pilih Radio Button Status Bayar otomatis
        if (status.equalsIgnoreCase("DP")) {
            rbDP.setSelected(true);
        } else {
            rbLunas.setSelected(true);
        }
        
        // Set Tanggal lama ke JDateChooser
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
            jDateChooserTanggal.setDate(date);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat tanggal lama", e);
        }
    }
    
    private void tampil_penyewa() {
    try {
        jComboBoxPenyewa.removeAllItems();
        Connection conn = koneksi.getConnection();
        
        // Query mengambil ID dan Nama Penyewa (Nama Tim) dari database
        String sql = "SELECT id_penyewa, nama_tim FROM penyewa";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            // Menggabungkan ID dan Nama Tim agar informatif saat dipilih kasir
            String item = rs.getString("id_penyewa") + " - " + rs.getString("nama_tim");
            jComboBoxPenyewa.addItem(item);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data penyewa: " + e.getMessage());
    }
}
    
    private void tampil_lapangan() {
    try {
        jComboBoxLapangan.removeAllItems();
        Connection conn = koneksi.getConnection();
        
        // Query mengambil ID dan Jenis Lapangan dari tabel lapangan
        String sql = "SELECT id_lapangan, jenis_lantai FROM lapangan"; 
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            // Gabungkan ID dan Jenis Lapangan untuk ditampilkan di ComboBox
            String item = rs.getString("id_lapangan") + " - " + rs.getString("jenis_lantai");
            jComboBoxLapangan.addItem(item);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Gagal memuat data lapangan: " + e.getMessage());
    }
    }
    
    private void reset_form() {
        jComboBoxLapangan.setSelectedIndex(-1);
        jComboBoxPenyewa.setSelectedIndex(-1);
        jDateChooserTanggal.setDate(null); 
        jTextFieldJamMulai.setText("19:00");     
        jTextFieldJamSelesai.setText("21:00");   
        jTextFieldTotalBiaya.setText("");
        rbDP.setSelected(true); // Default status awal
    }
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxPenyewa = new javax.swing.JComboBox<>();
        jComboBoxLapangan = new javax.swing.JComboBox<>();
        jDateChooserTanggal = new com.toedter.calendar.JDateChooser();
        jTextFieldJamMulai = new javax.swing.JTextField();
        jTextFieldJamSelesai = new javax.swing.JTextField();
        rbDP = new javax.swing.JRadioButton();
        rbLunas = new javax.swing.JRadioButton();
        jTextFieldTotalBiaya = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnTambahMember = new javax.swing.JButton();
        jButtonCancel1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("BOOKING LAPANGAN");

        jLabel2.setText("Nama Tim:");

        jLabel3.setText("Lapangan:");

        jLabel4.setText("Tanggal:");

        jLabel5.setText("Jam Mulai:");

        jLabel6.setText("Jam Selesai:");

        jLabel7.setText("Status Bayar:");

        jLabel8.setText("Total Biaya:");

        buttonGroup1.add(rbDP);
        rbDP.setText("DP");
        rbDP.addActionListener(this::rbDPActionPerformed);

        buttonGroup1.add(rbLunas);
        rbLunas.setText("Lunas");
        rbLunas.addActionListener(this::rbLunasActionPerformed);

        jTextFieldTotalBiaya.addActionListener(this::jTextFieldTotalBiayaActionPerformed);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(this::btnSimpanActionPerformed);

        btnTambahMember.setText("Data Baru");
        btnTambahMember.addActionListener(this::btnTambahMemberActionPerformed);

        jButtonCancel1.setText("Cancel");
        jButtonCancel1.addActionListener(this::jButtonCancel1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxPenyewa, 0, 190, Short.MAX_VALUE)
                                    .addComponent(jComboBoxLapangan, 0, 190, Short.MAX_VALUE)
                                    .addComponent(jDateChooserTanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jTextFieldJamSelesai, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rbDP, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbLunas, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTextFieldTotalBiaya, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jTextFieldJamMulai))
                                .addGap(18, 18, 18)
                                .addComponent(btnTambahMember)))))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(3, 3, 3)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxPenyewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambahMember))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxLapangan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jDateChooserTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldJamMulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldJamSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbDP)
                        .addComponent(rbLunas)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldTotalBiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(jButtonCancel1))
                .addContainerGap(274, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbDPActionPerformed

    private void rbLunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbLunasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbLunasActionPerformed

    private void jTextFieldTotalBiayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalBiayaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalBiayaActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (jDateChooserTanggal.getDate() == null || 
            jComboBoxLapangan.getSelectedItem() == null || 
            jComboBoxPenyewa.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(null, "Tanggal, Lapangan, dan Nama Tim wajib dipilih!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String jamMulai = jTextFieldJamMulai.getText().trim();
        String jamSelesai = jTextFieldJamSelesai.getText().trim();
        String totalBiaya = jTextFieldTotalBiaya.getText().trim();

        if (jamMulai.equals("") || jamSelesai.equals("") || totalBiaya.equals("")) {
            JOptionPane.showMessageDialog(null, "Jam Mulai, Jam Selesai, dan Total Biaya tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Potong String ComboBox (Ambil ID-nya saja)
        String idLapangan = jComboBoxLapangan.getSelectedItem().toString().split(" - ")[0];
        String idPenyewa = jComboBoxPenyewa.getSelectedItem().toString().split(" - ")[0];

        // 3. Format Tanggal dari JDateChooser
        SimpleDateFormat formatTanggal = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalFormat = formatTanggal.format(jDateChooserTanggal.getDate());

        // 4. Ambil Status Bayar dari Radio Button
        String status_bayar = rbDP.isSelected() ? "DP" : "Lunas";

        try {
            Connection conn = koneksi.getConnection();
            String sql;
            PreparedStatement pst;

            // KONDISI A: MODE CREATE (INSERT)
            if (id_booking_edit.equals("")) {
                sql = "INSERT INTO booking (id_lapangan, id_penyewa, tanggal_main, jam_mulai, jam_selesai, total_biaya, status_bayar) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, idLapangan);
                pst.setString(2, idPenyewa);
                pst.setString(3, tanggalFormat);
                pst.setString(4, jTextFieldJamMulai.getText());
                pst.setString(5, jTextFieldJamSelesai.getText());
                pst.setString(6, jTextFieldTotalBiaya.getText());
                pst.setString(7, status_bayar);
            } 
            // KONDISI B: MODE EDIT (UPDATE)
            else {
                sql = "UPDATE booking SET id_lapangan=?, id_penyewa=?, tanggal_main=?, jam_mulai=?, jam_selesai=?, total_biaya=?, status_bayar=? WHERE id_booking=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, idLapangan);
                pst.setString(2, idPenyewa);
                pst.setString(3, tanggalFormat);
                pst.setString(4, jTextFieldJamMulai.getText());
                pst.setString(5, jTextFieldJamSelesai.getText());
                pst.setString(6, jTextFieldTotalBiaya.getText());
                pst.setString(7, status_bayar);
                pst.setString(8, id_booking_edit); // Target klausa WHERE
            }

            int hasil = pst.executeUpdate();
            if (hasil > 0) {
                if (id_booking_edit.equals("")) {
                    JOptionPane.showMessageDialog(null, "Data Booking Baru Berhasil Disimpan!");
                } else {
                    JOptionPane.showMessageDialog(null, "Data Booking Berhasil Diperbarui!");
                    new lihat_booking().setVisible(true); // Buka kembali dashboard utama
                    this.dispose();                      // Tutup form edit ini
                }
                reset_form(); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memproses data ke database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    public void refresh_data() {
        tampil_penyewa();
        tampil_lapangan();
    }
    private void btnTambahMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahMemberActionPerformed
        // TODO add your handling code here:
        // new tambah_penyewa().setVisible(true);
        tambah_penyewa formPenyewa = new tambah_penyewa();
        formPenyewa.setVisible(true);
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
                refresh_data();
            }
        });
    }//GEN-LAST:event_btnTambahMemberActionPerformed

    private void jButtonCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancel1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonCancel1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new tambah_booking().setVisible(true));
    }
    private com.toedter.calendar.JDateChooser jDateChooser1;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambahMember;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonCancel1;
    private javax.swing.JComboBox<String> jComboBoxLapangan;
    private javax.swing.JComboBox<String> jComboBoxPenyewa;
    private com.toedter.calendar.JDateChooser jDateChooserTanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldJamMulai;
    private javax.swing.JTextField jTextFieldJamSelesai;
    private javax.swing.JTextField jTextFieldTotalBiaya;
    private javax.swing.JRadioButton rbDP;
    private javax.swing.JRadioButton rbLunas;
    // End of variables declaration//GEN-END:variables
}
