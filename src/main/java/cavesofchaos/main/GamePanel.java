/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import cavesofchaos.tile.TileNavChecker;
import cavesofchaos.items.Item;
import cavesofchaos.entity.player.*;
import cavesofchaos.tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.event.*;

/**
 *
 * @author domniki
 */
public class GamePanel extends JPanel implements Runnable {
    //screen settings
    private final int tileSize = 16; //16x16 tile
    private final int maxMapCol = 60; //map's columns
    private final int maxMapRow = 40; //map's rows
    private final int maxScreenCol = 80; //screen's columns
    private final int maxScreenRow = 40; //screen's rows
    
    private final int screenWidth = tileSize * maxScreenCol; //screen's width 1280px
    private final int screenHeight = tileSize * maxScreenRow; //screen's heigth 960px
    
    
    //Frames Per Second
    private int FPS = 60;
    
    //handlers
    GameWorld gameW = new GameWorld(this);
    TileManager tileM = new TileManager(this, gameW);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;  
    public UI ui = new UI(this);
    public TileNavChecker navChecker = new TileNavChecker(this, tileM);
    
    public Player player;
    
    //object handling
    public Item item[][] = new Item[gameW.getNumLevels()][10]; 
    public ItemSetter itemS = new ItemSetter(this, gameW, tileM);
    
    //enemy handling
    public EnemySetter enemyS = new EnemySetter(this, gameW, tileM);
    
    //battle handling 
    public BattleManager battleM = new BattleManager(this, enemyS);
    
    //game state handling
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 3;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    
    public void setUpGame() {
        itemS.setItems();
        gameState = titleState;
    }
    
    //method to choose player class based on input in titleScreen
    private void choosePlayer(){ 
        if (keyH.wizardPlayer == true) {
            player = new Wizard(this, gameW, keyH, tileM);
        }
        else {
            player = new Duelist(this, gameW, keyH, tileM);
        }
    }
    
    //start thread
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    //game loop to update and draw 
    @Override
    public void run() {
        
        //gameloop running at 60FPS process
        double drawInterval = 1000000000/FPS; //10^9nanosec/60FPS = 1sec/60FPS interval at which the screen is drawn
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while (gameThread != null) {
            update();
            
            repaint();
                       
            try {
                
                double remainingTime = nextDrawTime - System.nanoTime(); //remaining time after update & repaint
                remainingTime = remainingTime/1000000; //nanosec -> milisec
                
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime); //sleep until remaining
                
                nextDrawTime += drawInterval; //update drawTime
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
        
    public void update() {
        if (gameState == titleState) {
            if (ui.titleScreenState == 2) {
                choosePlayer();
                gameState = playState;
            }
        }
        if (gameState == playState)
        {
            player.update();
            tileM.update(player.x, player.y);
            itemS.update();
            enemyS.update();
            battleM.update();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        if (gameState == titleState) {
            ui.draw(g2);
        }
        else {
            tileM.draw(g2);
            itemS.draw(g2);
            player.draw(g2);
            enemyS.draw(g2);
            ui.draw(g2);
        }
        
        g2.dispose();
    }

    //getters
    public int getTileSize() {
        return tileSize;
    }

    public int getMaxMapCol() {
        return maxMapCol;
    }

    public int getMaxMapRow() {
        return maxMapRow;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }
    
    //method to restart game after game over
    public void restartGame() {
        new Thread(() -> {
            try {
                Thread.sleep(1000); //1.5sec delay and then restart
            } catch (InterruptedException ignored) {}
                //restart everything
                this.gameW = new GameWorld(this);
                this.tileM = new TileManager(this, gameW);
                this.keyH = new KeyHandler(this);
                this.ui = new UI(this);
                this.navChecker = new TileNavChecker(this, tileM);
                this.item = new Item[gameW.getNumLevels()][10]; 
                this.itemS = new ItemSetter(this, gameW, tileM);
                this.enemyS = new EnemySetter(this, gameW, tileM);
                this.battleM = new BattleManager(this, enemyS);

                //remove all old keyListeners and add the new one
                for (KeyListener listener : this.getKeyListeners()) {
                    this.removeKeyListener(listener);
                }
                this.setDoubleBuffered(true);
                this.addKeyListener(this.keyH);
                this.setFocusable(true);
                
                setUpGame();
                
                gameState = titleState;
                ui.titleScreenState = 1;
        }).start();
    }
}
