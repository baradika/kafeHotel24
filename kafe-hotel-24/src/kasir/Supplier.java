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
public class Supplier extends javax.swing.JFrame {

    public Statement st;
    public PreparedStatement ps;
    public ResultSet rs;
    Connection conn = koneksi.koneksi.getCon();
    
    public Supplier() {
        initComponents();
        showData();
        reset();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void showData(){
        DefaultTableModel model = (DefaultTableModel)tbl_barang.getModel();
        model.setRowCount(0);
        try{
            String sql = "SELECT * FROM supplier";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()){
                int kode = rs.getInt("kode");
                String toko = rs.getString("toko");
                String kategori = rs.getString("kategori");
                String barang = rs.getString("barang");
                int harga = rs.getInt("harga");
                NumberFormat kursIndo = NumberFormat.getIntegerInstance(new Locale("id",
                "ID"));
                
                Object[] Rowdata = {kode,toko,kategori,barang,kursIndo.format(harga)};
                model.addRow(Rowdata);
            }
        }catch (Exception e){
            
        }
    }
    
    private void reset(){
        txt_toko.setText("");
        cmb_kategori.setSelectedItem(0);
        txt_barang.setText("");
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
        txt_toko = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_barang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_harga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        btn_simpan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmb_kategori = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_barang = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(99, 68, 18));

        jPanel2.setBackground(new java.awt.Color(163, 133, 99));

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Toko");

        txt_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_barangActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Barang");

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

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kategori");

        cmb_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "Makanan", "Minuman", "Packaging", " " }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_barang)
                    .addComponent(txt_toko)
                    .addComponent(txt_harga)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_hapus)
                        .addGap(18, 18, 18)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_simpan)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmb_kategori, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_toko, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmb_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_hapus)
                    .addComponent(btn_reset)
                    .addComponent(btn_simpan))
                .addGap(39, 39, 39))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cafe Stock - Supplier Page");

        tbl_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "IDsupplier", "Toko", "Kategori", "Barang", "Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_barang);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_barangActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if(txt_barang.getText().equals("")||txt_harga.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Data yang dimasukkan belum lengkap", "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            st = conn.createStatement();
            if(btn_simpan.getText()== "Simpan"){
                String find = "SELECT * FROM supplier WHERE barang='" + txt_barang.getText() +
                        "' ";
                rs = st.executeQuery(find);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "barang sudah ada, masukkan yang lain", "Validasi",
                    JOptionPane.WARNING_MESSAGE);
                }else{
                    String toko = txt_toko.getText();
                    String kategori = cmb_kategori.getItemAt(cmb_kategori.getSelectedIndex());
                    String barang = txt_barang.getText();
                    String harga = txt_harga.getText();
                    String ReplaceHarga = harga.replaceAll(",", "");
                    
                    String sql = "INSERT INTO supplier (toko, kategori, barang, harga)VALUES (?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, toko);
                    ps.setString(2, kategori);
                    ps.setString(3, barang);
                    ps.setString(4, ReplaceHarga);
                    
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Barang berhasil update!", "Input barang",
                    JOptionPane.INFORMATION_MESSAGE);
                    showData();
                    reset();
                }
            } else {
                int selectedRow = tbl_barang.getSelectedRow();
                String id = tbl_barang.getValueAt(selectedRow, 0).toString();
                String toko = txt_toko.getText();
                String kategori = cmb_kategori.getItemAt(cmb_kategori.getSelectedIndex());
                String barang = txt_barang.getText();
                String harga = txt_harga.getText();
                String ReplaceHarga = harga.replaceAll(",", "");
                
                String sql = "UPDATE supplier SET toko=?, kategori=?, barang=?,  harga=? WHERE kode=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, toko);
                ps.setString(2, kategori);
                ps.setString(3, barang);
                ps.setString(4, ReplaceHarga);
                ps.setString(5, id);
                
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di Update", "input data",
                        JOptionPane.INFORMATION_MESSAGE);
                showData();
                reset();
            }
        } catch (Exception e){
            
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void tbl_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_barangMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel)tbl_barang.getModel();
        int selectedRow = tbl_barang.getSelectedRow();
        
        txt_toko.setText(model.getValueAt(selectedRow, 1).toString());
        String colkat = model.getValueAt(selectedRow, 2).toString();
        for(int i=0;i<cmb_kategori.getItemCount();i++){
            if(cmb_kategori.getItemAt(i).toString().equalsIgnoreCase(colkat)){
                cmb_kategori.setSelectedIndex(i);
            }
        }
        txt_barang.setText(model.getValueAt(selectedRow, 3).toString());
        txt_harga.setText(model.getValueAt(selectedRow, 4).toString());
        btn_simpan.setText("update");
        formatRupiah();
    }//GEN-LAST:event_tbl_barangMouseClicked

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        reset();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
       try{
           if(txt_toko.getText().equals("")|| txt_toko.getText().equals("")){
               JOptionPane.showMessageDialog(null, "Silahkan pilih data yang tersedia"
               , "Validasi", JOptionPane.INFORMATION_MESSAGE);
           }else{
               int konfir = JOptionPane.showConfirmDialog(null, "Anda yakin akan menghapus data?",
                       "Hapus barang", JOptionPane.YES_NO_OPTION);
               if(konfir==0){
                   int selectedRow = tbl_barang.getSelectedRow();
                   String id = tbl_barang.getValueAt(selectedRow, 0).toString();
                   String sql = "DELETE FROM supplier WHERE kode=?";
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        home.kitchen ktc = new home.kitchen();
        ktc.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JComboBox<String> cmb_kategori;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_barang;
    private javax.swing.JTextField txt_barang;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_toko;
    // End of variables declaration//GEN-END:variables
}
