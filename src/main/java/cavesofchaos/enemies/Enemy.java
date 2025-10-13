/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cavesofchaos.enemies;

import cavesofchaos.entity.AbstractEntity;
import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author domniki
 */
public abstract class Enemy extends AbstractEntity{
    
    GamePanel gp;
    GameWorld gw;
    
    private int HP, XPReward, visRadius;
    private int currentHP;
    private Color color;
    private Color baseColor;
    private boolean removalFlag = false;
    private boolean playerCollisionFlag = false;
    
    //constructor
    public Enemy(GamePanel gp, GameWorld gw) {
        this.gp = gp;
        this.gw = gw;
        
        solidArea = new Rectangle(0, 0, 8, 8);
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        setSpeed(1);
    }
    
    //abstract methods to be overriden by each subclass
    public abstract String getName();
    public abstract String getDescription();
    public abstract int doDamage();
  
    
    //setters
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    private void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setXPReward(int XPReward) {
        this.XPReward = XPReward;
    }

    public void setVisRadius(int visRadius) {
        this.visRadius = visRadius;
    }    

    public void setColor(Color color) {
        this.color = color;
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
    } 

    public void setRemovalFlag(boolean removalFlag) {
        this.removalFlag = removalFlag;
    }
    
    public void setInitialCurrentHP() {
        this.currentHP = this.HP;
    }

    //getters 
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
    
    public int getHP() {
        return HP;
    }
    
    public int getXPReward() {
        return XPReward;
    }
    
    public int getVisRadius() {
        return visRadius;
    }

    public Color getColor() {
        return color;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public boolean isRemovalFlag() {
        return removalFlag;
    }

    public boolean isPlayerCollisionFlag() {
        return playerCollisionFlag;
    }
    
    public String getDirection() {
        return direction;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public boolean isInMap() {
        return inMap;
    }
    
    //boolean method that returns true if enemy is alive
    public boolean isAlive() {
        return currentHP > 0;
    }
    
    //method to receive amount of damage
    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }
    
    //method to navigate based on player's possition
    private void findDirection(int playerX, int playerY) {
        direction = "none";
        int dx = playerX - x;
        int dy = playerY - y;
        
        //if player is outside of radius then return
        if ((Math.abs(dx) + Math.abs(dy)) > visRadius * gp.getTileSize() ) return;
        
        //check which absolute distance -x or y- is largest 
        //then choose direction based on player's possition
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                direction = "right";
            }
            else if (dx < 0) {
                direction = "left";
            }
            else {
                direction = "none";
            }
        }
        else {
            if (dy > 0) {
                direction = "down";
            }
            else if (dy < 0) {
                direction = "up";
            }
            else {
                direction = "none";
            }
        }
    }
    
    //method to handle enemy's death
    public void die() {
        gp.ui.showMessage("Enemy " + getName() + " was defeated!");
        
        if (this instanceof ChaosReptile) {
            //set Jewel Of Judgement
            gp.itemS.setSingleItem(x, y, true);
        }
        else {
            double p = 0.25; 
         
            Random random = new Random();

            //set random item in place
            if (random.nextDouble(1) <= p) {
                gp.itemS.setSingleItem(x, y, false);
            }
        }        
        //add XP reward to player
        gp.player.addXP(getXPReward());
        
    }

    
    public void draw(Graphics2D g2) {
        g2.setColor(getColor());
        g2.fillOval(getX(), getY(), gp.getTileSize() - 8, gp.getTileSize() - 8);
    }
    
    public void update() {
        findDirection(gp.player.x, gp.player.y);
        
        //flags    
        collisionOn = false;
        inMap = false;
        
        //check for collision with player
        playerCollisionFlag = gp.navChecker.checkEntity(this, gp.player);
        
        //check for object collision
        gp.navChecker.checkTile(this);
        int objIndex = gp.navChecker.checkObject(this, false, gw.getCurrentLevel());
        
        //check for other enemies collision
        int entIndex = gp.navChecker.checkEntityList(this, gp.enemyS.getEnemies());
        
        //movement configuration
        if (!collisionOn && inMap) {
            switch (direction) {
                case "up" -> setY(y - speed);                    
                case "down" -> setY(y + speed);
                case "right" -> setX(x + speed);
                case "left" -> setX(x - speed);
                default -> { //if direction = default or none, then don't move
                }
            }
        }
        
        if(!isAlive()) {
            die();
            setRemovalFlag(true);
        }
    }
}
