/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author domniki
 */
public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font ubuntu_15;
    
    //variables for GameLog 
    private boolean messageOn = false;
    private String message = "";
    private int messageCounter = 0;
    
    //PlayTime Variables
    private double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    //state handling variables
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int gameOverState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        ubuntu_15 = new Font("Ubuntu MonoSpace", Font.PLAIN, 15);
    }
    
    //method to assign input message to var message
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    //draw method
    public void draw(Graphics2D g2) {
               
        this.g2 = g2;
        
        g2.setFont(ubuntu_15);
        g2.setColor(Color.WHITE);
        
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        if (gp.gameState == gp.pauseState) {
            drawPausedScreen();
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
    }
    
    //method to find x possition of text to be centered
    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); //finds length of text
        int x = ( gp.getScreenWidth() - length ) /2; //computes x possition
        
        return x;
    }
    
    //method to draw title screen
    private void drawTitleScreen() {
        int x, y; //text coordinates on screen
        String text;
        
        if (titleScreenState == 0) { //First TitleScreen
            //title
            text = "CAVES OF CHAOS";

            g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 90F));
            x = getXForCenteredText(text);
            y = gp.getScreenHeight() /2 - gp.getTileSize() * 10;

            //title's shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y);

            //title
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            //menu
            g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 45F));

            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gp.getTileSize() * 20;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.getTileSize() * 3, y);
            }

            text = "QUIT";
            x = getXForCenteredText(text);
            y += gp.getTileSize() * 3;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.getTileSize() * 3, y);
            }
        }
        else if (titleScreenState == 1) { //Second title screen with character class selection
            g2.setColor(Color.WHITE);
            g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 45F));
            
            //game controls info
            text = "GAME CONTROLS :";
            x = gp.getTileSize();
            y = gp.getTileSize() * 3;
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 3, y);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            
            g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 20F));
            text = "W : UP";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "S : DOWN";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "A : LEFT";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "D : RIGHT";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "E : EXIT";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "H : HEALTH POTION";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "M : MANA POTION";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "R : REST";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "P : CHANGE WEAPON";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "SPACE : ATTACK ENEMY";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            text = "ENTER : PAUSE GAME";
            y += 2*gp.getTileSize();
            g2.drawString(text, x, y);
            
            //character class
            text = "CHOOSE CHARACTER";
            x = getXForCenteredText(text);
            y = gp.getScreenHeight() - gp.getTileSize() * 10;
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 2, y);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);
            
            text = "WIZARD";
            x = getXForCenteredText(text);
            y += gp.getTileSize() * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.getTileSize() * 3, y);
            }

            text = "DUELIST";
            x = getXForCenteredText(text);
            y += gp.getTileSize() * 3;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.getTileSize() * 3, y);
            }
        }

        
    }
    
    //method to draw Game Stats and Game Log in play screen
    private void drawPlayScreen() {
        int sideX = gp.getMaxMapCol() * gp.getTileSize();
        
        //Stats Area
        playTime +=(double)1/60;

        g2.drawString("PlayTime = " + dFormat.format(playTime) + " sec", sideX, gp.getTileSize());
        g2.drawString(gp.player.getPlayersClass().toUpperCase(), sideX, gp.getTileSize()*2);
        g2.drawString("Level : " + gp.player.getLevel(), sideX, gp.getTileSize()*3);
        g2.drawString("HP : " + gp.player.getHP() + " / " + gp.player.getMaxHP(), sideX, gp.getTileSize()*4);
        g2.drawString("MP : " + gp.player.getMP() + " / " + gp.player.getMaxMP(), sideX, gp.getTileSize()*5);
        g2.drawString("Weapon : " + gp.player.getEquippedItem(), sideX, gp.getTileSize()*6);
        g2.drawString("Strength : "  + gp.player.getStrength(), sideX, gp.getTileSize()*7);
        g2.drawString("Damage : " + gp.player.getDamage(), sideX, gp.getTileSize()*8);
        g2.drawString("Potions : " + gp.player.getStatsInventory().get(0) + " (H) / " + gp.player.getStatsInventory().get(1) + " (M)", sideX, gp.getTileSize()*9);

        
        //gamelog area
        int sideYHalf = (gp.getMaxScreenRow()/2  + 1) * gp.getTileSize();
        
        if (messageOn == true) {
            int yCount = 0;
            for (String line: wrapText(message, 40)) {
                g2.drawString(line, sideX, sideYHalf + yCount * gp.getTileSize());
                yCount++;
            }
            
            messageCounter++;

            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
    
    //method to draw paused screen
    private void drawPausedScreen() {
        String text = "GAME PAUSED";
        int x = gp.getMaxMapCol() * gp.getTileSize();
        int y = gp.getMaxScreenRow()/2 * gp.getTileSize();
        
        g2.drawString(text, x, y);
    }
    
    //method to draw game over screen
    public void drawGameOverScreen() {
        int x , y;
        String text;
        
        //darken screen
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
        
        //set letters' font
        g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 90F));
        
        text = "GAME OVER";
        x = getXForCenteredText(text);
        y = gp.getScreenHeight() /2 - gp.getTileSize() * 10;

        //title's shadow
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x + 5, y);

        //title
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        
        //win state
        if (gameOverState == 0) {
            text = "YOU WON";
        }
        else { //lose state
            text = "YOU LOST";
        }
        
        g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 70F));
        x = getXForCenteredText(text);
        y += gp.getTileSize() * 7;
        
        //shadow
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, x + 5, y);
        
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        
        g2.setFont(ubuntu_15.deriveFont(Font.BOLD, 45F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.getTileSize() * 13;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.getTileSize() * 3, y);
        }

        text = "QUIT";
        x = getXForCenteredText(text);
        y += gp.getTileSize() * 3;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.getTileSize() * 3, y);
        }
    }
    
    //method to wrap text and return list of the 2 new Strings
    private List<String> wrapText(String text, int maxChars) {
        List<String> lines = new ArrayList<>();
        
        while (text.length() > maxChars) {
            int spaceIndex = text.lastIndexOf(" ", maxChars);
            //find last space with chars <= maxChars 
            if (spaceIndex <=0) {
                spaceIndex = maxChars;
            }
            //add the 2 substrings to list
            lines.add(text.substring(0, spaceIndex));
            text = text.substring(spaceIndex).trim();
        }
        if (!text.isEmpty()) {
            lines.add(text);
        }
        return lines;
    }
}
