package com.miniproject.railwaysystem.Widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class IconTextField extends JTextField {

    public String getHelperText() {
        return helperText;
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
        repaint();
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }
    
    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
    }

    private Icon prefixIcon;
    private Icon suffixIcon;
    private final Animator animator;
    private boolean animateHinText = true;
    private float location;
    private boolean show;
    private boolean mouseOver = false;
    private boolean focusGain = false;
    private String labelText = "Label";
    private String helperText = "";
    private final int spaceHelperText = 15;
    private Color lineColor = new Color(3, 155, 216);
    private Color labelColor = new Color(0,0,0);

    public IconTextField(){
        setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 5, 7, 5));
        setSelectionColor(new Color(76, 204, 255));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                mouseOver = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent me) {
                mouseOver = false;
                repaint();
            }
        });
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent fe) {
                focusGain = true;
                showing(false);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                focusGain = false;
                showing(true);
            }
        });
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                animateHinText = getText().equals("");
            }

            @Override
            public void  timingEvent(float fraction) {
                location = fraction;
                repaint();
            }

        };
        animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    private void showing(boolean action) {
        if (animator.isRunning()) {
            animator.stop();
        } else {
            location = 1;
        }
        animator.setStartFraction(1f - location);
        show = action;
        location = 1f - location;
        animator.start();
    }
    

    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            g2.drawImage(prefix, 5, y-1, this);
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 3, y, this);
        }
    }

    private void initBorder() {
        int left = 5;
        int right = 5;
        //  5 is default
        if (prefixIcon != null) {
            //  prefix is left
            left = prefixIcon.getIconWidth() + 7;
        }
        if (suffixIcon != null) {
            //  suffix is right
            right = suffixIcon.getIconWidth() + 7;
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(7, left, 7, right));
    }
    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        paintIcon(g2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        int width = getWidth();
        int height = getHeight();
        if (mouseOver) {
            g2.setColor(lineColor);
        } else {
            g2.setColor(new Color(150, 150, 150));
        }
        int wsize = (prefixIcon!=null)?prefixIcon.getIconWidth():0;
        g2.fillRect(2 +wsize, height - spaceHelperText - 1, width - 4, 1);
        createHintText(g2);
        createLineStyle(g2);
        createHelperText(g2);
        g2.dispose();
    }

    private void createHintText(Graphics2D g2) {
        Insets in = getInsets();
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(labelText, g2);
        double height = getHeight() - in.top - in.bottom;
        double textY = (height - r2.getHeight()) / 2;
        double rsize = 2;
        double size;
        if (animateHinText) {
            if (show) {
                size = 17 * (1 - location);
                rsize = 2 * (1-location);
            } else {
                size = 17 * location;
                rsize = 2*location;
            }
        } else {
            size = 18;
            rsize = 2;
        }
        int wsize = (prefixIcon!=null)?prefixIcon.getIconWidth():0;
        if(focusGain){
            drawLabelBox(g2);
            Font f = this.getFont();
            g2.setFont(f.deriveFont(f.getSize()-2));
            g2.setColor(labelColor);
            g2.drawString(labelText,(int) (in.right+rsize) , (int) (in.top - size));
            g2.setFont(this.getFont());
        }
        else
            g2.drawString(labelText, in.right+wsize , (int) (in.top + textY + ft.getAscent() - size));
    }
    
private void drawLabelBox(Graphics2D g2)
{
    Insets in = getInsets();
    int width = getWidth() -1;
    int height = getHeight() -in.bottom;
    FontMetrics ft = g2.getFontMetrics();
    Rectangle2D r2 = ft.getStringBounds(labelText, g2);
    r2.setFrame(5, 2, r2.getWidth()+4,r2.getHeight());
    RoundRectangle2D area = new RoundRectangle2D.Double(0,10,width,height-10,10,10);
    
    if(focusGain){
            g2.setColor(getSelectionColor());
            g2.draw(area);
            g2.setColor(Color.WHITE);
            g2.fill(r2);
    }
}
    private void createLineStyle(Graphics2D g2) {
        if (isFocusOwner()) {
            double width = getWidth() - 4;
            int height = getHeight() - spaceHelperText;
            g2.setColor(lineColor);
            double size;
            if (show) {
                size = width * (1 - location);
            } else {
                size = width * location;
            }
            double x = (width - size) / 2;
            int wsize = (prefixIcon!=null)?prefixIcon.getIconWidth():0;
            g2.fillRect((int) (x + 2 +wsize), height - 2, (int) size, 2);
        }
    }

    private void createHelperText(Graphics2D g2) {
        if (helperText != null && !helperText.equals("")) {
            Insets in = getInsets();
            int height = getHeight() - 15;
            g2.setColor(new Color(255, 76, 76));
            Font font = getFont();
            g2.setFont(font.deriveFont(0, font.getSize() - 1));
            FontMetrics ft = g2.getFontMetrics();
            Rectangle2D r2 = ft.getStringBounds(labelText, g2);
            double textY = (15 - r2.getHeight()) / 2f;
            g2.drawString(helperText, in.right, (int) (height + ft.getAscent() - textY));
        }
    }

    @Override
    public void setText(String string) {
        if (!getText().equals(string)) {
            showing(string.equals(""));
        }
        super.setText(string);
    }
}
