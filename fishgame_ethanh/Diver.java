/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishgame_ethanh;

import static fishgame_ethanh.frame1.Diver1;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author Ethan
 */
public class Diver extends JComponent{
    
        
    //postion of the diver
    int xPos = 200;
    int yPos = -50;
    
    int Health = 30;
    
    //Timer to move the diver
    Timer xMove;
    
    //Icon for the diver
    ImageIcon Icon;
    
    //Size of the panel
    int PanWidth = 1020;
    
    //Constructor
    public Diver()
    {
        xPos = 200;
        yPos = -50;
        
    }
    
    public ImageIcon DiverImage()
    {
        ImageIcon Icon = new ImageIcon(new ImageIcon ("src\\fishgame_ethanh\\Images\\DiverRight.png").getImage().getScaledInstance(100, 70, Image.SCALE_DEFAULT));
        return Icon;
    }
    
    public ImageIcon DiverImageLeft()
    {
        ImageIcon Icon = new ImageIcon(new ImageIcon ("src\\fishgame_ethanh\\Images\\DiverLeft.png").getImage().getScaledInstance(100, 70, Image.SCALE_DEFAULT));
        return Icon;
    }
    
    //need to update the position
    public Rectangle getBoundary(int cxPos, int cyPos)
    {          
        Rectangle coli = new Rectangle(cxPos, cyPos, 100, 70);
         return coli;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public ImageIcon getIcon() {
        return Icon;
    }

    public void setIcon(ImageIcon Icon) {
        this.Icon = Icon;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int Health) {
        this.Health = Health;
    }

    
    
    
}
