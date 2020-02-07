/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishgame_ethanh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ethan
 */
public class Fish extends JComponent{
    
    

    //buffered fish image
    BufferedImage fishImage;
    
    //cash
    int cash;
    
    //Makes the fish change directions
    Random randomDir;
    Random RNG;
    
    //postion of the fish
    int xPos = 1;
    int yPos = 1;
    
    //Direction
    int xDir = 1;
    int yDir = 1;
    
    //Size of the panel
    int PanWidth = 1020;
    int PanHeight = 618;
    
    //angle / direction of the fish
    int run = 1;
    int rise = 1;
    
    //speed of the fish
    int speed = 25;
    
    //radius of the fish
    int radius;
    
    //Timers
    Timer xTimer;
    Timer cashTimer;
    Timer changeDirection;
    Timer xMove;
    Timer xGrow;
    Timer xFish;
    
    //color
    Color fishColor;
    
    //set fish type
    int fish;
    
    //has the fish been counted
    boolean hasbeencounted = false;
    
    //makes sure the fish stay in shape
    int heightCntr = 0;
    
    //Constructor
    public Fish()
    {
        xPos = 1;
        yPos = 1;
        
    }
    
    
    public Rectangle getBoundary()
    {
        return new Rectangle(xPos, yPos, radius, radius);
        
        
    }
    
    
    void whatFish()
    {

        //detects what fish is being produced
        if(fish == 1)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\Fish.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\Fishb.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
        
        //detects what fish is being produced
        if(fish == 2)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\fish-tropical.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\fish-tropical-Flip.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
        
        //detects what fish is being produced
        if(fish == 3)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\Starfish-Flip.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\Starfish.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
        
        //detects what fish is being produced
        if(fish == 4)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\BYFish.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\BYFishB.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
        
        //detects what fish is being produced
        if(fish == 5)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\green_fish.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\green_fishB.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
        
        //detects what fish is being produced
        if(fish == 6)
        {
            try{
                if(xDir >= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\whale.png"));
                }
                else if(xDir <= 0)
                {
                    fishImage = ImageIO.read(getClass().getResource("Images\\whaleB.png"));
                }
                

            }
            catch(IOException ioe)
            {
                System.out.println(ioe.getMessage());
            }
        }
    }
    
    //Paint the fish
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(fish == 6)
        {
            g.drawImage(fishImage, xPos, yPos, radius + 40, radius - heightCntr, this);
        }
        else{
            g.drawImage(fishImage, xPos, yPos, radius, radius, this);
            //g.setColor(fishColor);
            //g.fillOval(xPos, yPos, radius, radius);
        }
    }
    
    //Gain cash
    void gainCash()
    {
        RNG = new Random();
        int Random = RNG.nextInt(30) + 5;
        Random = Random * 1000;
        cashTimer = new Timer(Random, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                
                    cash++;
                    hasbeencounted = true;
                    
                    if(fish == 2)
                    {
                        cash++;
                    }
                    
                    if(fish == 3)
                    {
                        cash += 4;
                    }
                    if(fish == 4)
                    {
                        cash += 7;
                    }
                    if(fish == 5)
                    {
                        cash += 14;
                    }
                    if(fish == 6)
                    {
                        cash += 80;
                    }
                
            }
        });
        
        //start timer
        cashTimer.start();
        Random = RNG.nextInt(30) + 5;
        
        
    }
    
    
    //setters

    public void setBallColor(Color ballColor) {
        this.fishColor = fishColor;
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

    public int getPanWidth() {
        return PanWidth;
    }

    public void setPanWidth(int PanWidth) {
        this.PanWidth = PanWidth - radius; 
    }

    public int getPanHeight() {
        return PanHeight;
    }

    public void setPanHeight(int PanHeight) {
        this.PanHeight = PanHeight - radius;
    }

    public int getRun() {
        return run;
    }

    public void setRun(int run) {
        this.run = run;
    }

    public int getRise() {
        return rise;
    }

    public void setRise(int rise) {
        this.rise = rise;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getxDir() {
        return xDir;
    }

    public void setxDir(int xDir) {
        randomDir = new Random();
        int randomDirectionx = randomDir.nextInt(3 + 1 + 3) - 3;
        
        if(randomDirectionx == 0)
        {
            randomDirectionx++;
        }
        
        this.xDir = randomDirectionx;
    }

    public int getyDir() {
        return yDir;
    }

    public void setyDir(int yDir) {
        randomDir = new Random();
        int randomDirectiony = randomDir.nextInt(3 + 1 + 3) - 3;
        
        if(randomDirectiony == 0)
        {
            randomDirectiony++;
        }

        this.yDir = randomDirectiony;
    }
    
    public void setFishType(int fish)
    {
        this.fish = fish;
    }
    
    public int getFishType()
    {
        return fish;
    }
    
    public void setCash(int cash)
    {
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }
    
    public boolean setFalse()
    {
        this.hasbeencounted = false;
        return hasbeencounted;
    }
    
    public boolean setTrue()
    {
        this.hasbeencounted = true;
        return hasbeencounted;
    }
    
    public boolean getBeenCounted()
    {
        return hasbeencounted;
    }
    
    //change Direction after so long
    void changeDir()
    {
        RNG = new Random();
        int Random = RNG.nextInt(5) + 1;
        Random = Random * 1000;
        //after 5000 the direction will change
        changeDirection = new Timer(Random, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                randomDir = new Random();
                xDir = randomDir.nextInt(3 + 1 + 3) - 3;
                yDir = randomDir.nextInt(3 + 1 + 3) - 3;
                if(xDir == 0)
                {
                    xDir++;
                }
                if(yDir == 0)
                {
                    yDir++;
                }
                whatFish();
            }
        });
        
        //start timer
        changeDirection.start();
        Random = RNG.nextInt(5) + 1;
        
    }
    
    //Clock - guts
    void xClock()
    {
        xTimer = new Timer(speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                
                //check if the fish is pass the left or right boundary
                if(xPos > PanWidth)
                {
                    
                    xDir = xDir * -1;
                    xPos = PanWidth -1;
                    whatFish();
                    
                }
                else if(xPos < -1) 
                {
                    xDir = xDir * -1;
                    xPos = 1;
                    whatFish();
                }
                
                //check if the fish passed the bottom or the top
                if(yPos > PanHeight)
                {
                    
                    yDir = yDir * -1;
                    yPos = PanHeight -1;
                    
                }
                else if(yPos < -1) 
                {
                    yDir = yDir * -1;
                    yPos = 1;
                }
               
                
            }
        });
        
        //start timer
        xTimer.start();
        
        
    }
    
    //moves the fish
    void xMove()
    {
        xMove = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                
                //moves the fish
                xPos = xPos + (xDir * run); 
                yPos = yPos + (yDir * rise);
                
            }
        });
        
        //start timer
        xMove.start();
        
        
    }
    
    //Makes the fish grow every so often
    void xGrow()
    {
        RNG = new Random();
        int RandomGrow = RNG.nextInt(45) + 5;
        RandomGrow = RandomGrow * 2000;
           xGrow = new Timer(RandomGrow, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(radius <= 50)
                {
                    radius++;
                }
                
                if(fish == 6 && radius <= 240)
                {
                    radius += 2;
                    heightCntr++;
                }
            }
        });
        
        //start timer
        xGrow.start();
        RandomGrow = RNG.nextInt(45) + 5;
        
        
        
    }
    
    
    //starts the fish
    public void startFish()
    {
        whatFish();
        xMove();
        xClock();
        gainCash();
        changeDir();
        xGrow();
    }
}
