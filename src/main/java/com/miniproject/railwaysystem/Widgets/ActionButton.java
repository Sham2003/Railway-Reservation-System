/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miniproject.railwaysystem.Widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Sham
 */
public class ActionButton extends JButton {
    private boolean mousePress;
    public ActionButton()
    {
        this.setContentAreaFilled(false);
        this.setBorder(new EmptyBorder(3,3,3,3));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePress = true;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                mousePress = false;
            }
        });
    }
    @Override
    protected void paintComponent(Graphics grphcs)
    {
        Graphics2D g2 = (Graphics2D)grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width,height);
        int x=(width-size)/2;
        int y=(height-size)/2;
        if(mousePress){
            g2.setColor(new Color(159,159,159));
        }
        else
        {
            g2.setColor(new Color(199,199,199));
        }
        g2.fill(new Ellipse2D.Double(x,y,size,size));
        g2.dispose();
        super.paintComponent(grphcs);
    }
}
