/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author domniki
 */
public class KeyHandler implements KeyListener {
    GamePanel gp;
    
    public boolean wizardPlayer, upPressed, downPressed, leftPressed, rightPressed;
    public boolean exitPressed, pPressed, rPressed, hPressed, mPressed, spacePressed;

    
    //constructor
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
        wizardPlayer = false;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
       
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
        int code = ke.getKeyCode();
        
        //title state keys
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }
        else if (gp.gameState == gp.playState) { //play state keys
            playState(code);
        }
        else if (gp.gameState == gp.pauseState) { //pause state keys
            pauseState(code);
        }
        else if (gp.gameState == gp.gameOverState) { //game over keys
            gameOverState(code);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
        int code = ke.getKeyCode();
        
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;            
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_E) { 
            exitPressed = false;
        }
        if (code == KeyEvent.VK_P) { 
            pPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            rPressed = false; 
        }
        if (code == KeyEvent.VK_H) {
            hPressed = false; 
        }
        if (code == KeyEvent.VK_M) {
            mPressed = false; 
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        
    }

    //getters
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isWizardPlayer() {
        return wizardPlayer;
    }

    public boolean ispPressed() {
        return pPressed;
    }

    public boolean isrPressed() {
        return rPressed;
    }

    public boolean ishPressed() {
        return hPressed;
    }

    public boolean ismPressed() {
        return mPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }
       
    private void titleState(int code) {
        //first title screen
        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0 -> {
                        gp.ui.commandNum = 0;
                        gp.ui.titleScreenState = 1;
                    }
                    case 1 -> System.exit(0);
                    default -> gp.gameState = gp.titleState;
                }         
            }
        }
        else if (gp.ui.titleScreenState == 1) { //second title screen
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0 -> wizardPlayer = true;
                    case 1 -> wizardPlayer = false;
                    default -> wizardPlayer = false;
                }  
                gp.ui.titleScreenState = 2; //state to signal choosePlayer() method in GamePanel
            }
        }    
    }
    
    private void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) { 
            leftPressed = true;            
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_E) { 
            exitPressed = true;
        }
        if (code == KeyEvent.VK_ENTER) { 
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_P) {
            pPressed = true; 
        }
        if (code == KeyEvent.VK_R) {
            rPressed = true; 
        }
        if (code == KeyEvent.VK_H) {
            hPressed = true; 
        }
        if (code == KeyEvent.VK_M) {
            mPressed = true; 
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }
    
    private void pauseState(int code) {
        if (code == KeyEvent.VK_ENTER) { 
            gp.gameState = gp.playState;
        }
    }
    
    private void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.commandNum) {
                case 0 -> {
                    gp.ui.commandNum = 0;
                    gp.restartGame();
                }
                case 1 -> System.exit(0);
                default -> {
                    gp.restartGame();
                }
            }         
        }
    }
}
