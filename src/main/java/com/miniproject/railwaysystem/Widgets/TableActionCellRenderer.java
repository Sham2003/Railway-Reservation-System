/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miniproject.railwaysystem.Widgets;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Sham
 */
public class TableActionCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable jtable,Object o,boolean bln,boolean bln1,int i,int i1)
    {
        Component com =  super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
        PanelAction action = new PanelAction();
        if(bln==false && i%2==0)
        {
            action.setBackground(Color.WHITE);
        }
        else
        {
            action.setBackground(com.getBackground());
        }
        
        return action;
    }
}
