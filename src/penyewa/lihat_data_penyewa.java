/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package penyewa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import booking.lihat_booking;
import lapangan.lapangan;


/**
 *
 * @author Berantakan
 * 
 */
public class lihat_data_penyewa extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(lihat_data_penyewa.class.getName());
    private DefaultTableModel tableModel;

    /**
     * Creates new form tambah_penyewa
     */
    public lihat_data_penyewa() {
        initComponents();
        setTitle("Data Penyewa Lapangan");
        setLocationRelativeTo(null);
        String[] columns = {"ID", "NM tim", "Nama Kontak", "No HP"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(tableModel);
        jLabel3.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        jButton1.setBackground(new java.awt.Color(40, 167, 69));
        jButton1.setForeground(java.awt.Color.WHITE);
        jButton2.setBackground(java.awt.Color.WHITE);
        jButton2.setForeground(java.awt.Color.BLACK);
        jButton3.setBackground(new java.awt.Color(220, 53, 69));
        jButton3.setForeground(java.awt.Color.WHITE);
        jTable1.setModel(tableModel);
        jTable1.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected, 
                    boolean hasFocus, int row, int column) {
                
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(java.awt.Color.WHITE);
                    } else {
                        c.setBackground(new java.awt.Color(240, 240, 240)); 
                    }
                    c.setForeground(java.awt.Color.BLACK); 
                }
                return c;
                
            }
        });
        
        jButtonDashboard4.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new dashboard.dashboard().setVisible(true);
                dispose();
            }
        });
        loadData();
    }
    
    public void loadData() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        tableModel.setRowCount(0);
        
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
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); if (pst != null) pst.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    
    }
    private void tampilkanFormPenyewa(String id, String oldTim, String oldKontak, String oldHp, boolean isEdit) {
        javax.swing.JTextField txtNamaTim = new javax.swing.JTextField(oldTim);
        javax.swing.JTextField txtNamaKontak = new javax.swing.JTextField(oldKontak);
        javax.swing.JTextField txtNoHp = new javax.swing.JTextField(oldHp);
        
        javax.swing.JPanel formPanel = new javax.swing.JPanel();
        formPanel.setLayout(new javax.swing.BoxLayout(formPanel, javax.swing.BoxLayout.Y_AXIS));
        formPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        formPanel.add(new javax.swing.JLabel("Nama tim:"));
        formPanel.add(txtNamaTim);
        formPanel.add(javax.swing.Box.createVerticalStrut(8));
        formPanel.add(new javax.swing.JLabel("Nama Kontak:"));
        formPanel.add(txtNamaKontak);
        formPanel.add(javax.swing.Box.createVerticalStrut(8));
        formPanel.add(new javax.swing.JLabel("No Hp:"));
        formPanel.add(txtNoHp);
        
        int result = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                formPanel, 
                isEdit ? "Edit Data Penyewa" : "Tambah Data Penyewa",
                javax.swing.JOptionPane.OK_CANCEL_OPTION, 
                javax.swing.JOptionPane.PLAIN_MESSAGE
        );
                
        if (result == javax.swing.JOptionPane.OK_OPTION) {
            String tim = txtNamaTim.getText().trim();
            String kontak = txtNamaKontak.getText().trim();
            String hp = txtNoHp.getText().trim();

            if (tim.isEmpty() || kontak.isEmpty() || hp.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Semua input field wajib diisi!", "Validasi Error", javax.swing.JOptionPane.WARNING_MESSAGE);
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
                    javax.swing.JOptionPane.showMessageDialog(this, "Data penyewa berhasil disimpan!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                }
            } catch (SQLException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage(), "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } finally {
                try { if (pst != null) pst.close(); if (conn != null) conn.close(); } catch (Exception e) {}
            }
        }
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonDashboard4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "NM tim", "Nama Kontak", "No HP"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Tambah Data");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Edit Data");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setText("Hapus Data");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jLabel2.setText("Aksi :");

        jLabel3.setText("Data Penyewa");

        jButtonDashboard4.setText("Dashboard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonDashboard4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(126, 126, 126)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDashboard4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tampilkanFormPenyewa("", "", "", "", false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data penyewa yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = jTable1.getValueAt(row, 0).toString();
        String tim = jTable1.getValueAt(row, 1).toString();
        String kontak = jTable1.getValueAt(row, 2).toString();
        String hp = jTable1.getValueAt(row, 3).toString();
            
        // Memanggil fungsi panel edit internal asli bawaan Anda (5 parameter)
        tampilkanFormPenyewa(id, tim, kontak, hp, true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String id = jTable1.getValueAt(row, 0).toString();
        String tim = jTable1.getValueAt(row, 1).toString();
        
        // Validasi Relasi Database
        String sqlCheck = "SELECT COUNT(*) as total FROM booking WHERE id_penyewa = ?";
        String sqlDelete = "DELETE FROM penyewa WHERE id_penyewa = ?";
        
        try (Connection conn = koneksi.getConnection();
             PreparedStatement pstCheck = conn.prepareStatement(sqlCheck)) {
            
            pstCheck.setString(1, id);
            try (ResultSet rs = pstCheck.executeQuery()) {
                if (rs.next() && rs.getInt("total") > 0) {
                    JOptionPane.showMessageDialog(this, 
                            "Data tim '" + tim + "' tidak bisa dihapus karena masih terikat dengan jadwal di tabel booking!", 
                            "Hapus Gagal", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            int konfirmasi = JOptionPane.showConfirmDialog(this, "Hapus data penyewa tim '" + tim + "'?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                try (PreparedStatement pstDelete = conn.prepareStatement(sqlDelete)) {
                    pstDelete.setString(1, id);         
                    if (pstDelete.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                        loadData(); 
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new lihat_data_penyewa().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonDashboard4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

}
