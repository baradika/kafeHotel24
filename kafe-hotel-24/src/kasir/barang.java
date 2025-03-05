package kasir;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Sadindiiw!
 */
public class barang extends javax.swing.JFrame {

    public Statement st;
    public PreparedStatement ps;
    public ResultSet rs;
    Connection conn = koneksi.koneksi.getCon();
    public barang() {
        initComponents();
        showData();
        reset();
//        formatRupiah();
    }

    private void showData(){
        DefaultTableModel model = (DefaultTableModel)tbl_menu.getModel();
        model.setRowCount(0);
        try{
            String sql = "SELECT * FROM menu";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()){
                int id = rs.getInt("id");
                String nama = rs.getString("barang");
                int stok = rs.getInt("stok");
                int harga = rs.getInt("harga");
                NumberFormat kursIndo = NumberFormat.getIntegerInstance(new Locale("id",
                "ID"));
                
                Object[] Rowdata = {id,nama,stok,kursIndo.format(harga)};
                model.addRow(Rowdata);
            }
        }catch (Exception e){
            
        }
    }
    
    private void reset(){
        txt_nama.setText("");
        txt_stok.setText("");
        txt_harga.setText("");
        btn_simpan.setText("Simpan");

    }
    
    private void formatRupiah(){
        if(!txt_harga.getText().equals("")){
            String repl = txt_harga.getText().replaceAll("[^\\d]", "");
            double formatRP = Double.parseDouble(repl);
            DecimalFormat dcf = new DecimalFormat("#,###,###");
            txt_harga.setText(dcf.format(formatRP));
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txt_nama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_stok = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_menu = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(99, 68, 18));

        jPanel2.setBackground(new java.awt.Color(163, 133, 99));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Menu");

        txt_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Stok");

        txt_harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_hargaKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Harga");

        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        btn_simpan.setText("Simpan");
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_hapus)
                        .addGap(18, 18, 18)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_simpan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txt_stok)
                    .addComponent(txt_nama)
                    .addComponent(txt_harga))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_hapus)
                    .addComponent(btn_reset)
                    .addComponent(btn_simpan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cafe Menu's - Kitchen Page");

        tbl_menu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Menu", "Nama Menu", "Stok", "Harga"
            }
        ));
        tbl_menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_menuMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_menu);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if(txt_nama.getText().equals("")||txt_harga.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Data yang dimasukkan belum lengkap", "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            st = conn.createStatement();
            if(btn_simpan.getText()== "Simpan"){
                String find = "SELECT * FROM menu WHERE barang='" + txt_nama.getText() +
                        "' ";
                rs = st.executeQuery(find);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Menu sudah ada, masukkan menu lain", "Validasi",
                    JOptionPane.WARNING_MESSAGE);
                }else{
                    String nama = txt_nama.getText();
                    String stok = txt_stok.getText();
                    String harga = txt_harga.getText();
                    String ReplaceHarga = harga.replaceAll(",", "");
                    
                    String sql = "INSERT INTO menu (barang, stok, harga)VALUES (?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, nama);
                    ps.setString(2, stok);
                    ps.setString(3, ReplaceHarga);
                    
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Menu berhasil update!", "Input Menu",
                    JOptionPane.INFORMATION_MESSAGE);
                    showData();
                    reset();
                }
            } else {
                int selectedRow = tbl_menu.getSelectedRow();
                String id = tbl_menu.getValueAt(selectedRow, 0).toString();
                String menu = txt_nama.getText();
                String stok = txt_stok.getText();
                String harga = txt_harga.getText();
                String ReplaceHarga = harga.replaceAll(",", "");
                
                String sql = "UPDATE menu SET barang=?, stok=?, harga=? WHERE id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, menu);
                ps.setString(2, stok);
                ps.setString(3, ReplaceHarga);
                ps.setString(4, id);
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di Update", "input data",
                        JOptionPane.INFORMATION_MESSAGE);
                showData();
                reset();
            }
        } catch (Exception e){
            
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void tbl_menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_menuMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tbl_menu.getModel();
        int selectedRow = tbl_menu.getSelectedRow();
        
        txt_nama.setText(model.getValueAt(selectedRow, 1).toString());
        txt_stok.setText(model.getValueAt(selectedRow, 2).toString());
        txt_harga.setText(model.getValueAt(selectedRow, 3).toString());
        btn_simpan.setText("update");
        formatRupiah();
    }//GEN-LAST:event_tbl_menuMouseClicked

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        reset();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
       try{
           if(txt_nama.getText().equals("")|| txt_nama.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Silahkan pilih data yang tersedia"
               , "Validasi", JOptionPane.INFORMATION_MESSAGE);
           }else{
               int konfir = JOptionPane.showConfirmDialog(null, "Anda yakin akan menghapus data?",
                       "Hapus Menu", JOptionPane.YES_NO_OPTION);
               if(konfir==0){
                   int selectedRow = tbl_menu.getSelectedRow();
                   String id = tbl_menu.getValueAt(selectedRow, 0).toString();
                   String sql = "DELETE FROM menu WHERE id=?";
                   ps = conn.prepareStatement(sql);
                   ps.setString(1, id);
                   ps.executeUpdate();
                   showData();
                   reset();
               }else{
                   reset();
               }
           }    
       }catch (Exception e){
           
       }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_hargaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_hargaKeyReleased
        formatRupiah();
    }//GEN-LAST:event_txt_hargaKeyReleased

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_menu;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_stok;
    // End of variables declaration//GEN-END:variables
}
