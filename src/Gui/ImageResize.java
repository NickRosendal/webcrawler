package Gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class ImageResize extends JPanel {

    BufferedImage image;
    
    
    public ImageResize(BufferedImage image) {
        this.image = image;
        
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        int w = getWidth();
        int h = getHeight();
        int iw = image.getWidth();
        int ih = image.getHeight();
        double xScale = (double)w/iw;
        double yScale = (double)h/ih;
        double scale = Math.min(xScale, yScale);    // scale to fill
                       //Math.max(xScale, yScale);  // scale to fit
        int width = (int)(scale*iw);
        int height = (int)(scale*ih);
        int x = (w - width)/2;
        int y = (h - height)/2;
        g2.drawImage(image, x, y, width, height, this);
    }

}