/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.miniproject.railwaysystem;

import com.miniproject.railwaysystem.Widgets.GlassPanePopup.GlassPanePopup;
import com.miniproject.railwaysystem.Widgets.TableActionCellEditor;
import com.miniproject.railwaysystem.Widgets.TableActionEvent;
import com.miniproject.railwaysystem.Widgets.TableActionCellRenderer;
import java.awt.*;
import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Subash
 * Background Image DisplayBg.jpg
 */
public class DisplayTrain extends javax.swing.JFrame {
        Connection conn;
        java.sql.Date sdate;
        
        public static String extractTime(String dateTimeString) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            try {
                java.util.Date date = dateFormat.parse(dateTimeString);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                return timeFormat.format(date);
            } catch (ParseException ex) {
                System.out.println("Cannot Parse Date :"+dateTimeString);
            }
            String time = dateTimeString.split(" ")[1];
            return time.substring(0,time.length()-1);
        }
        
	/**
	 * Creates new form displaytrain
     * @param conn
     * @param src
     * @param dest
     * @param tids
     * @param d
	 */
	public DisplayTrain(Connection conn, String src, String dest,ArrayList<String> tids,Date d) {
            this.conn = conn;
            this.sdate = d;
            initComponents();
            this.setSize(800,480);
            this.setResizable(false);
            GlassPanePopup.install(this);
            TableActionEvent event;
            event = new TableActionEvent(){
                @Override
                public void onBook(int row) {
                    if(table.isEditing())
                    {
                        table.getCellEditor().stopCellEditing();
                    }
                    String tid = table.getModel().getValueAt(row, 0).toString();
                    BookTicket nf = new BookTicket(conn,tid,src,dest,sdate);
                    DisplayTrain.this.setVisible(false);
                    nf.setVisible(true);
                    DisplayTrain.this.dispose();
                }

                @Override
                public void onView(int row) {
                    if(table.isEditing())
                    {
                        table.getCellEditor().stopCellEditing();
                    }
                    GlassPanePopup.showPopup(getSeatInfo(tids.get(row),table.getValueAt(row, 1).toString(),sdate));
                }

                @Override
                public void onPrice(int row) {
                    if(table.isEditing())
                    {
                        table.getCellEditor().stopCellEditing();
                    }
                    Component comp = getPriceInfo(tids.get(row),table.getValueAt(row, 1).toString(),sdate);
                    GlassPanePopup.showPopup(comp);
                }
                @Override
                public void onSroute(int row) {
                    if(table.isEditing())
                    {
                        table.getCellEditor().stopCellEditing();
                    }
                    Component comp = getRouteInfo(tids.get(row),table.getValueAt(row, 1).toString());
                    GlassPanePopup.showPopup(comp);
                }

                private Component getSeatInfo(String tid,String name,Date date) {
                    Component info = new JOptionPane("Couldnt Get Info SQL ERROR");
                    try {
                        ArrayList<Map<Integer,Object>> result = new ArrayList();
                        String sql = "SELECT classname,capacity FROM trainclassinfo WHERE trainid = ? AND tdate = ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tid));
                        stmt.setDate(2, date);
                        stmt.execute();
                        ResultSet rs = stmt.getResultSet();
                        while(rs.next())
                        {
                            Map<Integer,Object> m = new HashMap();
                            for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                            {
                                m.put(i, rs.getObject(i+1));
                            }
                            result.add(m);
                        }
                        info = new SeatViewer(result,name);
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayTrain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return info;
                }
                
                
                private Component getPriceInfo(String tid, String name, Date sd) {
                    Component info = new JOptionPane("Couldnt Get Info SQL ERROR");
                    try {
                        ArrayList<Map<Integer,Object>> result = new ArrayList();
                        String sql = "SELECT classname,price FROM trainclassinfo WHERE trainid = ? AND tdate = ?";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tid));
                        stmt.setDate(2, sd);
                        stmt.execute();
                        ResultSet rs = stmt.getResultSet();
                        while(rs.next())
                        {
                            Map<Integer,Object> m = new HashMap();
                            for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                            {
                                m.put(i, rs.getObject(i+1));
                            }
                            result.add(m);
                        }
                        info = new PriceViewer(result,name);
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayTrain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return info;
                }

                private Component getRouteInfo(String tid, String name) {
                    Component info = new JOptionPane("Couldnt Get Info SQL ERROR");
                    try {
                        ArrayList<Map<Integer,Object>> result = new ArrayList();
                        String sql = "SELECT  st.stationname,ts.arrival,ts.departure FROM trainStations ts JOIN stations st ON ts.stationid=st.stationid  WHERE trainid = ? order by ts.arrival";
                        PreparedStatement stmt = conn.prepareStatement(sql);
                        stmt.setInt(1, Integer.parseInt(tid));
                        stmt.execute();
                        ResultSet rs = stmt.getResultSet();
                        while(rs.next())
                        {
                            Map<Integer,Object> m = new HashMap();
                            for(int i=0;i<rs.getMetaData().getColumnCount();i++)
                            {
                                m.put(i, rs.getObject(i+1));
                            }
                            result.add(m);
                        }
                        info = new RouteViewer(result,name);
                    } catch (SQLException ex) {
                        Logger.getLogger(DisplayTrain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return info;
                }


            };
            initTable(tids,src,dest);
            table.getColumnModel().getColumn(4).setCellRenderer(new TableActionCellRenderer());
            table.getColumnModel().getColumn(4).setCellEditor(new TableActionCellEditor(event));
            
	}

        private void initTable(ArrayList<String> tids,String src,String dest)
        {
            String columns[] = {
                "Train no", "Train name", "BoardingTime", "DestinationTime", "Actions"
            };
            DefaultTableModel model = new DefaultTableModel(columns,0){
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, true
                };
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
            Statement stmt;
            String sql = "SELECT t1.trainid TID,t3.trainname TNAME,t1.departure BTIME,t2.arrival DTIME "+
                    "FROM trainstations t1 join trainstations t2 ON t1.trainid =t2.trainid "+
                    "JOIN trains t3 ON t1.trainid = t3.trainid WHERE t1.stationid = '%s' "+
                    "and t2.stationid = '%s' and t1.trainid = %s";
            ResultSet rs;
            for(String tid:tids)
            {
                try {
                    stmt = this.conn.createStatement();
                    stmt.execute(String.format(sql,src,dest,tid));
                    rs = stmt.getResultSet();
                    if(rs.next())
                    {
                        Object arr[] = {rs.getString("TID"),rs.getString("TNAME"),extractTime(rs.getString("BTIME")),extractTime(rs.getString("DTIME")),null};
                        model.addRow(arr);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DisplayTrain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            table.setModel(model);
        }

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        kButton1 = new com.miniproject.railwaysystem.Widgets.KButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        Bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Railway Resevation System");
        setIconImage(new javax.swing.ImageIcon("D:\\dbms\\RailwaySystem\\src\\main\\java\\com\\miniproject\\railwaysystem\\Assets\\TrainIcon.jpg").getImage());
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setPreferredSize(new java.awt.Dimension(700, 480));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        kButton1.setText("BACK");
        kButton1.setkBackGroundColor(new java.awt.Color(204, 204, 204));
        kButton1.setkEndColor(new java.awt.Color(153, 153, 153));
        kButton1.setkHoverEndColor(new java.awt.Color(204, 255, 255));
        kButton1.setkHoverForeGround(new java.awt.Color(153, 153, 153));
        kButton1.setkHoverStartColor(new java.awt.Color(0, 255, 255));
        kButton1.setkStartColor(new java.awt.Color(102, 102, 102));
        kButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(kButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 60, 20));

        jPanel1.setBackground(new java.awt.Color(153, 255, 0));

        jLabel1.setFont(new java.awt.Font("Perpetua Titling MT", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Trains Found");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 140, 40));

        table.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Train no", "Train name", "ArrivalTime", "DestinationTime", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(40);
        table.setSelectionBackground(new java.awt.Color(51, 51, 255));
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(1).setPreferredWidth(150);
            table.getColumnModel().getColumn(2).setResizable(false);
            table.getColumnModel().getColumn(2).setPreferredWidth(50);
            table.getColumnModel().getColumn(3).setResizable(false);
            table.getColumnModel().getColumn(3).setPreferredWidth(50);
            table.getColumnModel().getColumn(4).setResizable(false);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 650, 350));

        Bg.setIcon(new javax.swing.ImageIcon("D:\\dbms\\RailwaySystem\\src\\main\\java\\com\\miniproject\\railwaysystem\\Assets\\DisplayBg.jpg")); // NOI18N
        jPanel2.add(Bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void kButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Search nf = new Search(conn);
        nf.setVisible(true);
    }//GEN-LAST:event_kButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.miniproject.railwaysystem.Widgets.KButton kButton1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
