/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kasir;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Sadindiiw!
 */
public class kasir extends javax.swing.JFrame {

    Double totalAmount=0.0;
    Double cash=0.0;
    Double balance=0.0;
    Double bHeight=1.50;

    ArrayList<String> menuAry = new ArrayList<>();
    ArrayList<String> qtyAry = new ArrayList<>();
    ArrayList<String> hargaAry = new ArrayList<>();
    ArrayList<String> jumlahAry = new ArrayList<>();
    
    public Statement st;
    public PreparedStatement ps;
    public ResultSet rs;
    Connection conn = koneksi.koneksi.getCon();
    NumberFormat angkakoma = NumberFormat.getIntegerInstance();
    public kasir() {
        initComponents();
        showDataItem();
        idOrder();
        tanggalOrder();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void showDataItem(){
        DefaultTableModel model=(DefaultTableModel)tbl_orderMenu.getModel();
        model.setRowCount(0);
        String cariMenu = txt_cari.getText();
        try{
            String sql = "SELECT * FROM menu WHERE INSTR(barang, '"+ cariMenu +"')>0";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("id");
                String barang = rs.getString("barang");
                int stok = rs.getInt("stok");
                int harga = rs.getInt("harga");
                
                
                Object[] RowData = {stok,barang,angkakoma.format(harga)};
                model.addRow(RowData);
            }
        }catch(Exception e){
            
        }
    }
    
    private void hitungTotal(){
        int tot = 0;
        
        for(int i=0; i<tbl_checkout.getRowCount();i++){
            tot = tot + Integer.parseInt(tbl_checkout.getValueAt(i, 5).toString()
            .replaceAll(",", ""));
        }
        
        txt_totalBayar.setText(String.valueOf(angkakoma.format(tot)));
    }
    
    private void formatDesimal(){
        if(!txt_bayar.getText().isEmpty()){
            String replaceBayar = txt_bayar.getText().replaceAll("[^\\d]", "");
            double FormatDec = Double.parseDouble(replaceBayar);
            DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
            txt_bayar.setText(decimalFormat.format(FormatDec));
            return;
        }
    }
    
    private void idOrder(){
        String sql = "SELECT MAX(no_faktur)FROM penjualan";
        try{
            st = conn.createStatement();
            rs=st.executeQuery(sql);
            rs.next();
            rs.getString("MAX(no_faktur)");
            if(rs.getString("MAX(no_faktur)")== null){
                lb_faktur.setText("CCH00001");
            }else{
                long InvAngka = Long.parseLong(rs.getString("MAX(no_faktur)")
                        .substring(3,rs.getString("MAX(no_faktur)").length()));
                InvAngka++;
                lb_faktur.setText("CCH"+String.format("%05d", InvAngka ));
            }
        }catch(Exception e){
            
        }
    }
    
    private void tanggalOrder(){
        java.util.Date tgl = new java.util.Date();
        SimpleDateFormat tanggal = new SimpleDateFormat("dd/MM/yyyy",
        Locale.getDefault());
        String cDate = tanggal.format(tgl);
        
        lb_tanggal.setText(cDate);
    }
    
    private void reset(){
        DefaultTableModel model = (DefaultTableModel)tbl_checkout.getModel();
        model.setRowCount(0);
        txt_menu.setText("");
        txt_harga.setText("");
        txt_qyt.setText("");
        txt_totalBayar.setText("");
        txt_bayar.setText("");
        txt_kembalian.setText("");
    }
    
    public PageFormat getPageFormat(PrinterJob pj){
    
    PageFormat pf = pj.defaultPage();
    Paper paper = pf.getPaper();    

    double bodyHeight = bHeight;  
    double headerHeight = 5.0;                  
    double footerHeight = 5.0;        
    double width = cm_to_pp(8); 
    double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
    paper.setSize(width, height);
    paper.setImageableArea(0,10,width,height - cm_to_pp(1));  
            
    pf.setOrientation(PageFormat.PORTRAIT);  
    pf.setPaper(paper);    

    return pf;
    }
    
    protected static double cm_to_pp(double cm)
    {            
	        return toPPI(cm * 0.393600787);            
    }
 
    protected static double toPPI(double inch)
        {            
                    return inch * 72d;            
        }



    public class BillPrintable implements Printable {




      public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
      throws PrinterException 
      {    

          int r= menuAry.size();
          ImageIcon icon=new ImageIcon("C:logo.jpg"); 
          int result = NO_SUCH_PAGE;    
            if (pageIndex == 0) {                    

                Graphics2D g2d = (Graphics2D) graphics;                    
                double width = pageFormat.getImageableWidth();                               
                g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 



                FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));

            try{
                int y=20;
                int yShift = 10;
                int headerRectHeight=15;
                int headerRectHeighta=40;


                g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
                g2d.drawImage(icon.getImage(), 50, 20, 90, 30, rootPane);y+=yShift+30;
                g2d.drawString("-------------------------------------",12,y);y+=yShift;
                g2d.drawString("      Cafe Hotel 24          ",12,y);y+=yShift;
                g2d.drawString("   No 00000 Alamat Baris 1   ",12,y);y+=yShift;
                g2d.drawString("   Alamat Baris 02 Jakarta   ",12,y);y+=yShift;
                g2d.drawString("   www.Instagra.com / www.   ",12,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;
                g2d.drawString(" No : "+lb_faktur.getText()+"  ",12,y);y+=yShift;
                g2d.drawString(" Tgl:  "+lb_tanggal.getText()+"  ",12,y);y+=yShift;
                g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;

                g2d.drawString(" Nama Menu                 Price   ",10,y);y+=yShift;
                g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;

                for(int s=0; s<r; s++)
                {
                g2d.drawString(" "+menuAry.get(s)+"                            ",10,y);y+=yShift;
                g2d.drawString("      "+qtyAry.get(s)+" * "+hargaAry.get(s),10,y); g2d.drawString(jumlahAry.get(s),160,y);y+=yShift;

                }

                g2d.drawString("-------------------------------------",10,y);y+=yShift;
                g2d.drawString(" Total belanja:               "+txt_totalBayar.getText()+"   ",10,y);y+=yShift;
                g2d.drawString("-------------------------------------",10,y);y+=yShift;
                g2d.drawString(" Tunai      :                 "+txt_bayar.getText()+"   ",10,y);y+=yShift;
                g2d.drawString("-------------------------------------",10,y);y+=yShift;
                g2d.drawString(" Kembali    :                 "+txt_kembalian.getText()+"   ",10,y);y+=yShift;

                g2d.drawString("*************************************",10,y);y+=yShift;
                g2d.drawString("   TERIMA KASIH ATAS KUNJUNGAN ANDA  ",10,y);y+=yShift;
                g2d.drawString("*************************************",10,y);y+=yShift;
                g2d.drawString("   Mohon, Maaf Barang yang dibeli    ",10,y);y+=yShift;
                g2d.drawString("    Tidak bisa dikembalikan lagi     ",10,y);y+=yShift;       


        }
        catch(Exception e){
        e.printStackTrace();
        }

                  result = PAGE_EXISTS;    
              }    
              return result;    
          }
    }
    
    private void removeAllArray(){
        menuAry.removeAll(menuAry);
        qtyAry.removeAll(qtyAry);
        hargaAry.removeAll(hargaAry);
        jumlahAry.removeAll(jumlahAry);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        item = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_cari = new javax.swing.JTextField();
        tbl_menu = new javax.swing.JScrollPane();
        tbl_orderMenu = new javax.swing.JTable();
        transaksi = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txt_menu = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        txt_qyt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_checkout = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txt_totalBayar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        btn_bayar = new javax.swing.JButton();
        btn_tbhItem = new javax.swing.JButton();
        btn_deposit = new javax.swing.JButton();
        btn_pengeluaran = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lb_faktur = new javax.swing.JLabel();
        lb_tanggal = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        background.setBackground(new java.awt.Color(100, 70, 19));

        header.setBackground(new java.awt.Color(164, 134, 99));

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 3, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Cafee Hotel 24 - Cashier Page");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(558, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(555, 555, 555))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap())
        );

        item.setBackground(new java.awt.Color(164, 134, 99));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cari Menu");

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });

        tbl_orderMenu.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        tbl_orderMenu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Stok", "Menu", "Harga"
            }
        ));
        tbl_orderMenu.setRowHeight(30);
        tbl_orderMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_orderMenuMouseClicked(evt);
            }
        });
        tbl_menu.setViewportView(tbl_orderMenu);

        javax.swing.GroupLayout itemLayout = new javax.swing.GroupLayout(item);
        item.setLayout(itemLayout);
        itemLayout.setHorizontalGroup(
            itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tbl_menu, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(itemLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_cari)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        itemLayout.setVerticalGroup(
            itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(itemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(tbl_menu)
                .addContainerGap())
        );

        transaksi.setBackground(new java.awt.Color(164, 134, 99));

        txt_menu.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        txt_menu.setForeground(new java.awt.Color(0, 153, 0));
        txt_menu.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txt_harga.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        txt_harga.setForeground(new java.awt.Color(0, 153, 0));
        txt_harga.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_add.setBackground(new java.awt.Color(0, 255, 0));
        btn_add.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        btn_reset.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        txt_qyt.setFont(new java.awt.Font("Segoe UI Semibold", 1, 16)); // NOI18N
        txt_qyt.setForeground(new java.awt.Color(0, 153, 0));
        txt_qyt.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_menu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_qyt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_reset)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_qyt)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_reset)
                        .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txt_menu)
                    .addComponent(txt_harga))
                .addContainerGap())
        );

        tbl_checkout.setFont(new java.awt.Font("Segoe UI Semibold", 1, 12)); // NOI18N
        tbl_checkout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Faktur", "Tanggal", "Menu", "Qyt", "Harga", "Jumlah"
            }
        ));
        tbl_checkout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_checkoutMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_checkout);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel2.setText("Total Bayar");

        txt_totalBayar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_totalBayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel3.setText("Bayar");

        txt_bayar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_bayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_bayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_bayarKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        jLabel4.setText("Kembalian");

        txt_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_kembalian.setForeground(new java.awt.Color(255, 204, 51));
        txt_kembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btn_bayar.setBackground(new java.awt.Color(255, 153, 51));
        btn_bayar.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btn_bayar.setText("Bayar");
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_totalBayar)
                    .addComponent(txt_bayar)
                    .addComponent(txt_kembalian)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_totalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );

        btn_tbhItem.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btn_tbhItem.setText("Tambah Item");

        btn_deposit.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btn_deposit.setText("Deposit");

        btn_pengeluaran.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btn_pengeluaran.setText("Pengeluaran");

        btn_laporan.setFont(new java.awt.Font("Segoe UI Semibold", 1, 14)); // NOI18N
        btn_laporan.setText("Laporan");
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout transaksiLayout = new javax.swing.GroupLayout(transaksi);
        transaksi.setLayout(transaksiLayout);
        transaksiLayout.setHorizontalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(transaksiLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(transaksiLayout.createSequentialGroup()
                        .addComponent(btn_tbhItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_deposit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_pengeluaran)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_laporan)))
                .addContainerGap())
        );
        transaksiLayout.setVerticalGroup(
            transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transaksiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(transaksiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tbhItem)
                    .addComponent(btn_deposit)
                    .addComponent(btn_pengeluaran)
                    .addComponent(btn_laporan))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("No. Faktur");

        lb_faktur.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        lb_faktur.setForeground(new java.awt.Color(255, 153, 0));
        lb_faktur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_faktur.setText("Faktur");
        lb_faktur.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        lb_tanggal.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        lb_tanggal.setForeground(new java.awt.Color(255, 153, 0));
        lb_tanggal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_tanggal.setText("Tanggal");
        lb_tanggal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel9.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Tanggal");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(transaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_faktur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_tanggal, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(64, 64, 64)))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(lb_faktur))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(lb_tanggal)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(item, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        if(txt_kembalian.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Anda belum memasukkan pembayaran!",
                    "validasi", JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                DefaultTableModel model =(DefaultTableModel)tbl_checkout.getModel();
                for(int i = 0; i<tbl_checkout.getRowCount() ; i++ ){
                    String noFaktur = model.getValueAt(i, 0).toString();
                    String tanggal = model.getValueAt(i, 1).toString();
                    String item = model.getValueAt(i, 2).toString();
                    String qty = model.getValueAt(i, 3).toString();
                    String harga = model.getValueAt(i, 4).toString();
                    String jumlah = model.getValueAt(i, 5).toString();
                    
                    String sql = "INSERT INTO penjualan (no_faktur, tgl, item,"
                            + "qty, harga, jumlah) VALUES (?,?,?,?,?,?)";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, noFaktur);
                    ps.setString(2, tanggal);
                    ps.setString(3, item);
                    ps.setString(4, qty);
                    ps.setString(5, harga);
                    ps.setString(6, jumlah);
                    ps.executeUpdate();
                    
                    menuAry.add(item);
                    qtyAry.add(qty);
                    hargaAry.add(harga);
                    jumlahAry.add(jumlah);
                }
                JOptionPane.showMessageDialog(this, "Pembayaran berhasil!","penjualan",
                        JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
            idOrder();
            
            int konfirm = JOptionPane.showConfirmDialog(null, "Cetak Struk?",
                    "Print", JOptionPane.YES_NO_OPTION);
            if(konfirm==0){
                PrinterJob pj = PrinterJob.getPrinterJob();        
                pj.setPrintable(new BillPrintable(),getPageFormat(pj));
                try {
                        pj.print();
                        removeAllArray();
          
                    } catch (PrinterException ex) {
                    }

            }else{
                removeAllArray();
            }
        }
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void tbl_orderMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_orderMenuMouseClicked
       DefaultTableModel model = (DefaultTableModel)tbl_orderMenu.getModel();
        int SelectedRow = tbl_orderMenu.getSelectedRow();
        String menu = model.getValueAt(SelectedRow, 1).toString();
        String harga = model.getValueAt(SelectedRow, 2).toString();
        int qyt = 1;

        int confirmDialog = JOptionPane.showConfirmDialog(null, "<html><b style=\" color:green;"
                + " font-size:15px;\">"+String.valueOf(menu)+"</b></html>",
                "Pilih Menu",JOptionPane.YES_NO_OPTION);
        if(confirmDialog==0){
            txt_menu.setText(menu);
            txt_harga.setText(String.valueOf(harga));
            txt_qyt.setText(String.valueOf(qyt));
            txt_qyt.requestFocus();
            txt_cari.setText("");
            showDataItem();
        }else{
            txt_cari.setText("");
            showDataItem();
        }
    }//GEN-LAST:event_tbl_orderMenuMouseClicked

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        DefaultTableModel model = (DefaultTableModel)tbl_checkout.getModel();
        
        
        if(txt_menu.getText().equals("")||txt_harga.getText().equals("")||txt_qyt.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Gagal menambahkan menu!","Peringatan",
                    JOptionPane.WARNING_MESSAGE);
        }else{
            String noFaktur = lb_faktur.getText();
            String tanggal = lb_tanggal.getText();
            String menu = txt_menu.getText();
            int qyt = Integer.parseInt(txt_qyt.getText());
            String harga = txt_harga.getText();
            String replaceHarga = harga.replaceAll(",", "");
            
            int jumlah = qyt * Integer.parseInt(replaceHarga);
            Object[] RowData = {noFaktur, tanggal, menu, qyt, harga, angkakoma.format(jumlah)};
            model.addRow(RowData);
            hitungTotal();
            txt_menu.setText("");
            txt_harga.setText("");
            txt_qyt.setText("");
            txt_cari.setText("");
            txt_cari.requestFocus();
            
        }
    }//GEN-LAST:event_btn_addActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
         showDataItem();
    }//GEN-LAST:event_txt_cariKeyReleased

    private void txt_bayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyTyped
        char validasi = evt.getKeyChar();
        if(!Character.isDigit(validasi)){
            evt.consume();
        }
    }//GEN-LAST:event_txt_bayarKeyTyped

    private void txt_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyReleased
        formatDesimal();
        if(txt_bayar.getText().isEmpty()){
            txt_kembalian.setText("");
        }else{
            int bayar = Integer.parseInt(txt_bayar.getText().replaceAll(",", ""));
            int totalbayar = Integer.parseInt(txt_totalBayar.getText().replaceAll(",",""));
            int hasil = bayar - totalbayar;
            
            if(bayar >= totalbayar){
                String rp = angkakoma.format(hasil);
                txt_kembalian.setText(String.valueOf(rp));
            }else{
                txt_kembalian.setText("");
            }
        }
    }//GEN-LAST:event_txt_bayarKeyReleased

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
        reset();
    }//GEN-LAST:event_btn_resetActionPerformed

    private void tbl_checkoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_checkoutMouseClicked
       DefaultTableModel model = (DefaultTableModel)tbl_checkout.getModel();
       int SelectedRow = tbl_checkout.getSelectedRow();
       if(SelectedRow!=-1){
           int rowIndexModel = tbl_checkout.convertRowIndexToModel(SelectedRow);
           model.removeRow(rowIndexModel);
       }
       hitungTotal();
    }//GEN-LAST:event_tbl_checkoutMouseClicked

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
       
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        home.cashier formCashier = new home.cashier();
        formCashier.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(kasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new kasir().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_deposit;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_pengeluaran;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tbhItem;
    private javax.swing.JPanel header;
    private javax.swing.JPanel item;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_faktur;
    private javax.swing.JLabel lb_tanggal;
    private javax.swing.JTable tbl_checkout;
    private javax.swing.JScrollPane tbl_menu;
    private javax.swing.JTable tbl_orderMenu;
    private javax.swing.JPanel transaksi;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_menu;
    private javax.swing.JTextField txt_qyt;
    private javax.swing.JTextField txt_totalBayar;
    // End of variables declaration//GEN-END:variables
}
