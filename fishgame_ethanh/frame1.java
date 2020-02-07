/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fishgame_ethanh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author Ethan
 */
public class frame1 extends javax.swing.JFrame {
    public static Shark shark1;
    public static JPanel shark;
    public static Diver Diver1;
    public static JPanel diver;
    public static JProgressBar HP;
    public static MenuScreen menuS;
    
    //starting cash amount
    int cash = 40;
    
    //Increment price of the fish
    int increment1 = 0;
    int increment2 = 0;
    int increment3 = 0;
    int increment4 = 0;
    int increment5 = 0;
    int increment6 = 0;
    
    //increment for damage
    int increment8 = 0;
    
    //Increment for the shark
    int increment7 = 0;
    
    //number of fish
    int fish = 0;
    
    //Random
    Random myRandom;
    
    //size of the fish soon to change when images are added
    int Radius = 15;
    int x = 1;
    int y = 1;
    
    int mxDir = 1;
    int myDir = 1;
    
    //random time for the diver
    int Random;
    
    //how many whales are in the list?
    int Whalelist = 0;
    
    //order in which the diver searches for a while in a array list
    int order = 0;
    
    //the set amount of damge that the player can do to the diver.
    int playerDamage = 1;
    
    ArrayList<Fish> ALFish = new ArrayList();
    
    //timer for the repaint
    Timer PanelTimer;
    boolean TimerStatus = false;
    int clockTick = 10;
    
    //timer for the notifications
    Timer noti;
    
    //timer to check music
    Timer music;
    
    //move the shark
    Timer xMove;
    
    //timer for the diver
    Timer DiverT;
    
    //timer to start the diver
    Timer DiverS;
    
    //height of the menu
    int jHeight = 0;
    
    //change the width of the notification
    int jLocation = 1040;
    
    //makes the notification box go away
    boolean shrink = false;
    
    //stops the music
    int stopMusic = 0;
    
    //Allows the shark to turn around
    int turn = 0;
    
    //make sure only one shark it out at a time
    boolean sharkTurn = true;
    
    //is the diver out?
    boolean DiverOut = false;
    
    //the panel clock to run most things
    private void panelClock()
    {
        PanelTimer = new Timer(clockTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                
                for(int cntr = 0; cntr < ALFish.size(); cntr++)
                {
                    if(ALFish.get(cntr).getBeenCounted() == true)
                    {
                        //grab the cash back from the classes
                        cash = cash + ALFish.get(cntr).getCash();
                        ALFish.get(cntr).setFalse();
                        ALFish.get(cntr).setCash(0);
                    }
                }
                
                //update the cash
                jLabel1.setText("Cash: " + cash);
                jLabel2.setText("Fish: " + fish);
                
                //increment the price after each purchas - updates instantly
                jLabel5.setText( "$" + (increment1 + 6));
                jLabel9.setText( "$" + (increment2 + 15));
                jLabel10.setText( "$" + (increment3 + 30));
                jLabel12.setText( "$" + (increment4 + 70));
                jLabel16.setText( "$" + (increment5 + 120));
                jLabel18.setText( "$" + (increment6 + 1000));
                jLabel21.setText( "$" + (increment7 + 50));
                jLabel20.setText( "$" + (increment8 + 200));
                
                //display the player damage as max
                if(playerDamage >= 9)
                {
                  jLabel20.setText("Maxed");   
                }
                    // if the diver is out and if there are no fish left then leave
                    if(ALFish.size() == 0 && DiverOut == true)
                    {
                        DiverOut = false;
                        DiverT.stop();
                        remove(diver);
                        remove(Diver1);
                        remove(HP);
                        startDiver();
                    }
                    
                
                //repaint everything
                repaint();
                
            }
        });
        
        PanelTimer.start();
    }
    
    public frame1() {
        initComponents();
        
        //make sure the frame is visable and set to be diaplayed in the middle of the screen
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        //Set the cash and fish when first starting the game
        jLabel1.setText("Cash: " + cash);
        jLabel2.setText("Fish: " + fish);
        
        //set the background transparent
        jPanel1.setBackground(new Color(255, 255, 255, 0));
        jPanel2.setBackground(new Color(255, 255, 255, 40));
        
        //set the size of jPanels
        jPanel2.setSize(1030, jHeight);
        
        //start the first notification
        notification("To play, press the \"Shop\" button and buy a fish!");
        
        //add game music
        gameMusic("Sounds\\gameMusic.wav");
        
        //start the diver randomly
        startDiver();
        
        jButton8.setVisible(false); //Test out the diver code
        
    }
    
    //Notifications
    void notification(String text)
    {
        jLocation = 1040;
        shrink = false;
        jLabel11.setText(text);
        noti = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(jLocation > 700 && shrink != true)
                {
                    jLocation--;
                    jPanel3.setLocation(jLocation, 20);
                }
                else if(jLocation < 1040 && shrink == true)
                {
                    jLocation++;
                    jPanel3.setLocation(jLocation, 20);
                    
                }
            }
        });
        noti.start();
        
    }
    
    //Game music, any game music can be passed into the fucntion
    private void gameMusic(String soundpath)
    {
        try{
           
            Clip addclip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundpath));
            
            AudioInputStream.nullInputStream();
            
            addclip.open(inputStream);
            
            music = new Timer(clockTick, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(stopMusic == 0)
                {
                    addclip.start();
                    addclip.loop(addclip.LOOP_CONTINUOUSLY);  
                }
                else if(stopMusic == 1)
                {
                    addclip.stop();
                }

            }
        });
        music.start();

        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        catch(LineUnavailableException le)
        {
            System.out.println("Did not work" + le.getMessage());
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Did not work 2 " + ex.getMessage());
        }
    }

    //Pass a sound into the function to be able to play it
    private void playSound(String soundpath)
    {
        try{
           
            Clip addclip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundpath));
            
            AudioInputStream.nullInputStream();
            
            addclip.open(inputStream);
            
            addclip.start();
            
            
        }
        catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        catch(LineUnavailableException le)
        {
            System.out.println("Did not work" + le.getMessage());
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("Did not work 2 " + ex.getMessage());
        }
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jToggleButton1 = new javax.swing.JToggleButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton4 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1040, 656));
        setPreferredSize(new java.awt.Dimension(1000, 656));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 4, true));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("To play, press the \"Shop\" button and buy a fish");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);
        jPanel3.setBounds(1040, 20, 320, 50);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setName(""); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/Fish_Icon.png"))); // NOI18N
        jLabel4.setToolTipText("Press \"Buy\" for this fish");
        jLabel4.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel4.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        jButton1.setText("Buy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("$6");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("$15");

        jButton2.setText("Buy");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/fish-tropical_icon.png"))); // NOI18N
        jLabel6.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel6.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel6.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jButton3.setText("Buy");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("$30");

        jToggleButton1.setText("X");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jCheckBox1.setBackground(new java.awt.Color(153, 153, 153));
        jCheckBox1.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBox1.setText("Music");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jButton4.setText("Buy");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("$70");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/Starfish_Icon.png"))); // NOI18N
        jLabel13.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel13.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel13.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/BYFish_Icon.png"))); // NOI18N
        jLabel14.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel14.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel14.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel14MouseExited(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/green_fish_Icon.png"))); // NOI18N
        jLabel15.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel15.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel15.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel15MouseExited(evt);
            }
        });

        jButton5.setText("Buy");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("$120");

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/whale_Icon.png"))); // NOI18N
        jLabel17.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel17.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel17.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel17MouseExited(evt);
            }
        });

        jButton6.setText("Buy");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("$1000");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/Shark_Icon.PNG"))); // NOI18N
        jLabel19.setMaximumSize(new java.awt.Dimension(300, 300));
        jLabel19.setMinimumSize(new java.awt.Dimension(300, 300));
        jLabel19.setPreferredSize(new java.awt.Dimension(300, 300));
        jLabel19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel19MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel19MouseExited(evt);
            }
        });

        jButton7.setText("Buy");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("$200");

        jButton8.setText("Diver Test");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Buy");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jLabel21.setBackground(new java.awt.Color(255, 255, 255));
        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("$50");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Damage Up");

        jButton10.setText("Menu");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(108, 108, 108)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBox1)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jButton1)
                            .addGap(7, 7, 7)
                            .addComponent(jLabel5))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addGap(7, 7, 7)
                                .addComponent(jLabel12))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton7)
                                        .addComponent(jButton9))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel20)
                                        .addComponent(jLabel21))
                                    .addGap(1, 1, 1))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton5)
                                    .addGap(7, 7, 7)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel16)
                                        .addComponent(jLabel18)))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jButton2)
                            .addGap(7, 7, 7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton6)
                            .addGap(29, 29, 29))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(0, 480, 1030, 140);

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Shop");
        jLabel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel7);
        jLabel7.setBounds(440, 580, 120, 30);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setForeground(new java.awt.Color(153, 153, 153));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cash:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Fish:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(824, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addContainerGap(554, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1020, 618);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fishgame_ethanh/Images/background.gif"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(0, 0, 1030, 630);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Button to make the jPanel2 show itself then disapear
    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        
        jLabel7.setVisible(false);
        
        int maxHeight = 144;
        
        for(int cntr = 0; cntr < maxHeight; cntr++)
        {
            jPanel2.setSize(1030, jHeight);
            jHeight++;
        }
        
    }//GEN-LAST:event_jLabel7MouseClicked

    //BYFish
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        //Check to see if you have enough money
        if(cash >= (increment4 + 70))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav"); 

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(4);

            //Direction of the fish
            fish1.setxDir(mxDir); //Play with this too change the way the fish move in directions
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment4 + 70);
            fish++;
            increment4 += 5;
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    //Toggle the Music on and off
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

        stopMusic++;
        if(stopMusic == 2)
        {
            stopMusic = 0;
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    //Button to make the jPanel2 disapear
    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed

        jLabel7.setVisible(true);
        jHeight = 0;
        jPanel2.setSize(1030, jHeight);

    }//GEN-LAST:event_jToggleButton1ActionPerformed

    //Starfish
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //Check to see if you have enough money
        if(cash >= (increment3 + 30))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav");

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(3);

            //Direction of the fish
            fish1.setxDir(mxDir);
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment3 + 30);
            fish++;
            increment3 += 2;
            shrink = true;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    //tropical fish
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //Check to see if you have enough money
        if(cash >= (increment2 + 15))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav");

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(2);

            //Direction of the fish
            fish1.setxDir(mxDir); //Play with this too change the way the fish move in directions
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment2 + 15);
            fish++;
            increment2++;
            shrink = true;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    //First fish
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        //Check to see if you have enough money
        if(cash >= (increment1 + 6))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav");

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(1);

            //Direction of the fish
            fish1.setxDir(mxDir); //Play with this too change the way the fish move in directions
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment1 + 6);
            fish++;
            increment1++;
            shrink = true;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    //green fish
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                //Check to see if you have enough money
        if(cash >= (increment5 + 120))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav");

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(5);

            //Direction of the fish
            fish1.setxDir(mxDir); //Play with this too change the way the fish move in directions
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment5 + 120);
            fish++;
            increment5 += 20;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    //Whale
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                //Check to see if you have enough money
        if(cash >= (increment6 + 1000))
        {

            //play sound when new fish is added
            playSound("Sounds\\plop.wav");

            //create a Fish
            Fish fish1 = new Fish();

            //set the values of the fish
            fish1.setVisible(true);
            fish1.setRadius(Radius + 30);
            fish1.setPanHeight(jPanel1.getHeight());
            fish1.setPanWidth(jPanel1.getWidth());
            fish1.setxPos(510);
            fish1.setyPos(309);
            fish1.setFishType(6);

            //Direction of the fish
            fish1.setxDir(mxDir); //Play with this too change the way the fish move in directions
            fish1.setyDir(myDir);

            //set the size of the fish
            fish1.setSize(jPanel1.getWidth(), jPanel1.getHeight());

            //add the fish to the arraylist and the jpanel
            jPanel1.add(fish1);
            ALFish.add(fish1);

            //start the timer for the fish
            fish1.startFish();

            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }

            //deduct cash off of a buy
            cash = cash - (increment6 + 1000);
            fish++;
            Whalelist++;
            increment6 += 500;
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    //Shark
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        
        //checks to make sure that it is time for the shark to move
        if(cash >= 50 && sharkTurn == true)
        { 
            
            //Play the sound of the shark once it spawns
            playSound("Sounds\\RoarShark.wav");
            
            //Create the shark
            shark1 = new Shark();
            
            
            shark1.setVisible(true);
            
            //start the timer for the fish
            shark1.startShark();
            
            //set the icon
            ImageIcon Icon = shark1.sharkDir();
            
            //add the label
            JLabel jLabelShark = new JLabel();
            
            //set the size, location, and icon of the label
            jLabelShark.setSize(Icon.getIconWidth(), Icon.getIconHeight());
            jLabelShark.setLocation(shark1.getxPos(), shark1.getyPos());
            jLabelShark.setIcon(Icon);
            jLabelShark.setSize(jLabelShark.getWidth(), jLabelShark.getHeight());
            
            //add shark to the panel
            shark = new JPanel();
            shark.setSize(Icon.getIconWidth(), Icon.getIconHeight());
            shark.add(jLabelShark);
            shark.setVisible(true);
            shark.setOpaque(false);
            shark.setLocation(shark1.getxPos(), shark1.getyPos());
            this.add(shark, 0);
            
            
            
            xMove = new Timer(1, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) 
                {
                    //check to see if its time to turn or not

                    if(shark.getX() <= 1100 && turn == 0)
                    {
                        shark1.setxPos(shark.getX() + 1);
                        shark.setLocation(shark1.getxPos(), shark1.getyPos());

                    }
                    
                    //once it is time to turn the shark changes direction
                    if(shark.getX() == 1100 && turn == 0)
                    {
                        turn = 1;
                        ImageIcon Icon = shark1.sharkDir2();
                        shark1.sharkDir2(); 
                        jLabelShark.setIcon(Icon);
                    }

                    //makes sure the shark it going backwards
                    if(shark.getX() > -200 && turn == 1)
                    {
                        shark1.setxPos(shark.getX() - 1);
                        shark.setLocation(shark1.getxPos(), shark1.getyPos());
                        

                    }

                    //if the position of x is -200 the shark will be deleted
                    if(shark.getX() == -200 && turn == 1)
                    {
                        turn = 0;
                        sharkTurn = true;
                        xMove.stop();
                        remove(shark);
                        
                    }
                    
                    deletefish();

                }
                });

            //start timer
            xMove.start();
            
            
            //deduct cash off of a buy
            cash = cash - (increment7 + 50); 
            increment7 += 10;
            sharkTurn = false;
        }
        
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jLabel19MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseEntered
        notification("This is the shark, you can use him to eat fish.");
        jPanel3.setBackground(Color.red);
    }//GEN-LAST:event_jLabel19MouseEntered

    private void jLabel19MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel19MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel19MouseExited

    private void jLabel17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseEntered
        notification("The Whale, +80 cash, increment + 100.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel17MouseEntered

    private void jLabel17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel17MouseExited

    private void jLabel15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseEntered
        notification("Tropical Fish, +14 cash, increment + 20.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel15MouseEntered

    private void jLabel15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel15MouseExited

    private void jLabel14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseEntered
        notification("Yellow Tropical Fish, +8 cash, increment + 5.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel14MouseEntered

    private void jLabel14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel14MouseExited

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        notification("Starfish, +4 cash, increment + 2.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel13MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        notification("Striped Tropical Fish, +2 cash, increment + 1.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        notification("Zebra Fish, +1 cash, increment + 1.");
        jPanel3.setBackground(Color.blue);
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        shrink = true;
    }//GEN-LAST:event_jLabel4MouseExited

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        //Spawn in the diver once clicked
        diver();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if(cash >= (increment8 + 200) && playerDamage <= 9)
        {
            
            if(TimerStatus == false)
            {
                panelClock();
                TimerStatus = true;
            }
            
            //deduct cash off of a buy
            cash = cash - (increment8 + 200);
            playerDamage++;
            increment8 += 1000;
            
            //display the amount of player damage
            System.out.println("Player damage: " + playerDamage);

        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
        //create the message
        JFrame mFrame = new JFrame("Message Examples");
        
        mFrame.setAlwaysOnTop(true);
        
        //check to see if the user wants to leave
        int choice = JOptionPane.showConfirmDialog(mFrame, 
                    "Return to the menu?\nProgress will be deleted!",
                    "Return to the menu?",
                    JOptionPane.YES_NO_OPTION
                    );
        
            if(choice == 0)
            {
                //create the menu again
                menuS = new MenuScreen();
                menuS.alreadyDone = 1;
                menuS.stopMusic = stopMusic;
                menuS.Test = 1;
                
                //Get rid of the game frame
                this.dispose();

                 //stop the music
                stopMusic = 1;
            }
        
    }//GEN-LAST:event_jButton10ActionPerformed

    void diver()
    {
        //while the arraylist is full run
        if(ALFish.size() > 0)
        {  
            //diver is out
            DiverOut = true;
            
            //play the sound as soon as the diver spawns
            playSound("Sounds\\Splash.wav");
            
            //create the diver
            Diver1 = new Diver();
            Diver1.setVisible(true);
            
            //set the image icon
            ImageIcon Icon = Diver1.DiverImage();

            //add the label
            JLabel jLabelDiver = new JLabel();

            //set the size, location, and icon of the label
            jLabelDiver.setSize(Icon.getIconWidth(), Icon.getIconHeight());
            jLabelDiver.setLocation(Diver1.getxPos(), Diver1.getyPos());
            jLabelDiver.setIcon(Icon);

            HP = new JProgressBar();
            HP.setValue(30);
            HP.setSize(30, 15);
            HP.setVisible(true);
            HP.setMinimum(0);
            HP.setMaximum(30);
            
            //add diver to the panel
            diver = new JPanel();
            diver.setSize(Icon.getIconWidth(), Icon.getIconHeight());
            diver.add(jLabelDiver);
            diver.setVisible(true);
            diver.setOpaque(false);
            diver.setLocation(Diver1.getxPos(), Diver1.getyPos());
            this.add(diver, 0);
            this.add(HP, 0);
            
                diver.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent arg0) {



                            }

                            @Override
                            public void mousePressed(MouseEvent arg0) {
                                //take health off the diver
                                System.out.println("Ouch!!");
                                
                                //play the oof sound when the diver get clicked
                                playSound("Sounds\\ooof.wav");
                                
                                //set the health to the health of the diver
                                int Health = Diver1.getHealth();
                                
                                HP.setValue(Health - playerDamage);
                                
                                Diver1.setHealth(Health - playerDamage);
                                
                                if(Diver1.getHealth() <= 0)  
                                {
                                    playSound("Sounds\\dead.wav");
                                    DiverOut = false;
                                    DiverT.stop();
                                    remove(diver);
                                    remove(Diver1);
                                    remove(HP);
                                    startDiver();
                                }
                                
                            }

                            @Override
                            public void mouseReleased(MouseEvent arg0) {

                            }

                            @Override
                            public void mouseEntered(MouseEvent arg0) {

                            }

                            @Override
                            public void mouseExited(MouseEvent arg0) {

                            }
                        });
                    
                DiverT = new Timer(12, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) 
                {
                    
                    //set the postion of the diver relative to the fish
                    int Dx = 0;
                    int Dy = 0;
                    ImageIcon Icon;
                    
                    try{
                       
                    //check to see if the diver is chasing a whale
                    checkWhale();
                        
                    if(diver.getX() <= ALFish.get(order).getxPos())
                    {
                        Dx++;
                        Icon = Diver1.DiverImage();
                        jLabelDiver.setIcon(Icon);
                    }
                    else
                    {
                        Dx--;
                        Icon = Diver1.DiverImageLeft();
                        jLabelDiver.setIcon(Icon);
                    }
                    if(diver.getY() <= ALFish.get(order).getyPos())
                    {
                        Dy++;
                    }
                    else
                    {
                        Dy--;
                    }
                    
                    //set the diver location and the HP
                    diver.setLocation(diver.getX() + Dx, diver.getY() + Dy);
                    HP.setLocation((diver.getX() + Dx) + 40, diver.getY() + Dy);
                    DiverDeleteFish();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                });
                
                //start timer
                DiverT.start();

        }
        
    }
    
    void checkWhale()
    {
        //check to see if the target is a whale
        for(int c = 0; c < ALFish.size(); c++)
        {
            if(ALFish.get(order).getFishType() == 6)
            {
                if(ALFish.size() == Whalelist)
                {
                    DiverOut = false;
                    DiverT.stop();
                    remove(diver);
                    remove(Diver1);
                    remove(HP);
                    startDiver();
                }
                else
                {
                    order++;
                }

            }
            else if(ALFish.indexOf(order) > ALFish.size())
            {

                // modify to delete the diver once he collects all the fish
                if(ALFish.size() == Whalelist)
                {
                    DiverOut = false;
                    DiverT.stop();
                    remove(diver);
                    remove(Diver1);
                    remove(HP);
                    startDiver();
                }
                
                order = 0;

            }
        }
    }
    
    //function to delete fish, which allows you to earn money off them
    void deletefish()
    {
        for(int c = 0; c < ALFish.size(); c++)
        {
            if(ALFish.get(c).getBoundary().intersects(shark1.getBoundary()))
            {
                //check to see if you are in contact with a whale
                if(ALFish.get(c).getFishType() == 6)
                {
                    System.out.println("IM A BIG BOI");
                
                }else
                {
                    //check to see what fish type you are eating
                    if(ALFish.get(c).getFishType() == 1)
                    {
                        cash += (3);
                    }
                    else if(ALFish.get(c).getFishType() == 2)
                    {
                        cash += (7);
                    }
                    else if(ALFish.get(c).getFishType() == 3)
                    {
                        cash += (15);
                    }
                    else if(ALFish.get(c).getFishType() == 4)
                    {
                        cash += (35);
                    }
                    else if(ALFish.get(c).getFishType() == 5)
                    {
                        cash += (60);
                    }
                    
                    //remove it then add the cash
                    ALFish.get(c).setVisible(false);
                    remove(ALFish.get(c));
                    ALFish.remove(ALFish.get(c)); 
                    fish = ALFish.size();
 
                }
                
            }
        }
        
//        if(shark1.getBoundary().intersects(Diver1.getBoundary(diver.getX(), diver.getY())))
//        {
//            DiverOut = false;
//            DiverT.stop();
//            remove(Diver1);
//            remove(diver);
//            remove(HP);
//            startDiver();
//            //cash += (1); Change later once you can get the box to be removed, maybe add a true or fals for the intersect
//        }
// DISABLED FOR NOW - need to fix the box

        
    }
    
    void DiverDeleteFish()
    {
        for(int c = 0; c < ALFish.size(); c++)
        {
            if(ALFish.get(c).getBoundary().intersects(Diver1.getBoundary(diver.getX(), diver.getY())))
            {
                //check to see if you are in contact with a whale
                if(ALFish.get(c).getFishType() == 6)
                {
                    System.out.println("IM A BIG BOI");
                
                }else
                {
                    
                    //remove it then add the cash
                    ALFish.get(c).setVisible(false);
                    remove(ALFish.get(c));
                    ALFish.remove(ALFish.get(c)); 
                    fish = ALFish.size();
                    
                }
                
            }
        }
        
    }
 
        void startDiver()
    {
            //diver will spawn at a random time
            myRandom = new Random();
            Random = myRandom.nextInt(500) + 60;
            Random = Random * 1000;
            DiverS = new Timer(Random, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) 
            {
                if(DiverOut == true)
                {
                    System.out.println("Diver is already out");
                }
                else
                {
                    diver();
                    DiverOut = true;
                    DiverS.stop();
                }

            }
            });
            DiverS.start();
            System.out.println(Random);
    }
    
    
    
    
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
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frame1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}




/* NOTES - To Do List
    "*" = Finished, "-" = In progress, "^" = Done but could change

    Need to make the ball change dir in a full 360 "*"
    Need to make the ball slow down, maybe remake or take part the old code "*"
    Work on ballDir being slower and look at results "*"

    Work on adding in a system that will increase the amount of money needed to buy a fish when one it bought "*"

    Add Sounds and sound effects - More to come "-"

    Add a diver - Chase or swim around the tank? Diver will have a HP bar, when clicked it will be depleated by one.
    Add a shark - shark will swim across the screen 
    Add new fish types "-"
    Add sprites to the fish - positive or negative on dir when changing the sprite directional look "*"

    Add a main menu
    Add a button to disable sounds or music "-"
    
    Fix the fish so the fish don't go out of the frame

*/