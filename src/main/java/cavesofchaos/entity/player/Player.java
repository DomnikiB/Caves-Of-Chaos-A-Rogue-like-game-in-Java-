/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.entity.player;

import cavesofchaos.entity.AbstractEntity;
import cavesofchaos.items.*;
import cavesofchaos.items.consumable.*;
import cavesofchaos.items.equippable.*;
import cavesofchaos.items.effects.*;
import java.util.*;
import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import cavesofchaos.main.KeyHandler;
import cavesofchaos.tile.TileManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author domniki
 */
public abstract class Player extends AbstractEntity {
    GamePanel gp;
    KeyHandler keyH;
    TileManager tileM;
    GameWorld gw;
    
       
    private int XP = 0;
    private int currentHP, currentMP;
    private final int visibilityRadius = 6;
    
    private boolean changeLevel = false;
    
    //constructor
    public Player(GamePanel gp, GameWorld gw, KeyHandler keyH, TileManager tileM) {
        this.gp = gp;
        this.keyH = keyH;
        this.gw = gw;
        this.tileM = tileM;
        setDefaultValues();
        
        solidArea = new Rectangle(0, 0, getPlayersDimensions(), getPlayersDimensions());
        
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
         
    //Inventory Map
    protected Map<Item, Integer> inventory = new HashMap<>(); //inventory of items, with storing multiple items
    
    //Equipment On Hand
    protected Equippable equippedItem; //equippedItem
    
    //Statistics evolution per level (index 0 is level 1)
    protected List<Integer> hpPerLevel;
    protected List<Integer> mpPerLevel;
    protected List<Integer> strPerLevel;
    protected List<Integer> intPerLevel;
    
    //Treemap of Levels Based on XP
    private static final TreeMap<Integer, Integer> LEVEL_XP = new TreeMap<>();
    static {
        LEVEL_XP.put(0, 1);
        LEVEL_XP.put(300, 2);
        LEVEL_XP.put(900, 3);
        LEVEL_XP.put(2700, 4);
        LEVEL_XP.put(6500, 5);
        LEVEL_XP.put(14000, 6);
    }
    
    
    //Abstract methods to be overriden by subclasses
    public abstract String getPlayersClass();
    public abstract int attack();
    
    //method to return players rectangle dimensions
    public int getPlayersDimensions() {
        return gp.getTileSize() - 3;
    }
    
    //set default values
    public void setDefaultValues() {
        x = gw.getStartPossition().x * gp.getTileSize();
        y = gw.getStartPossition().y * gp.getTileSize();
        speed = 1;
    }
        
    //setters for x, y
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    

    //getter for visibilityRadius
    public int getVisibilityRadius() {
        return visibilityRadius;
    }
    
    //update player's gameState
    public void update() {
        
        int nextX, nextY, potionIndex = 0;
        nextX = x;
        nextY = y;
        //direction string is used in tileNavChecker as switch
        direction = "default";
        
        if (keyH.upPressed == true) {
            direction = "up";
            nextY -= speed;
        }
        else if (keyH.downPressed == true) {
            direction = "down";
            nextY += speed;
        }
        else if (keyH.rightPressed == true) {
            direction = "right";
            nextX += speed;
        }
        else if (keyH.leftPressed == true) {
            direction = "left";
            nextX -= speed;
        }
        else if (keyH.exitPressed == true) {
            direction = "exit";
            keyH.exitPressed = false; //setting false in order to not go back multiple levels at once
        }
        else if (keyH.pPressed == true) {
            keyH.pPressed = false; //setting false in order to not exchange weapons constantly
        }
        else if (keyH.rPressed == true) {
            keyH.rPressed = false;
            rest();
            gp.ui.showMessage("Rest activated !");
        }
        else if (keyH.hPressed == true) {
            keyH.hPressed = false;
            potionIndex = 1;
        }
        else if (keyH.mPressed == true) {
            keyH.mPressed = false;
            potionIndex = 2;
        }
        else if (keyH.spacePressed == true) {
            keyH.spacePressed = false;
            gp.battleM.playerAttack();
            gp.battleM.setBattleOn(false);
        }
        
        //flags    
        collisionOn = false;
        inMap = false;
        startFlag = false;
        stairsFlag = false;
        changeLevel = false;
        
        //check kind of tile
        gp.navChecker.checkTile(this);
        
        //check object collision and pick-up
        int objIndex = gp.navChecker.checkObject(this, true, gw.getCurrentLevel());
        pickUp(objIndex);
        
        //check other enemies collision
        int enIndex;
        if (gw.getCurrentLevel() < 9) {
            enIndex = gp.navChecker.checkEntityList(this, gp.enemyS.getEnemies());
        }
        else {
            enIndex = gp.navChecker.checkEntityList(this, gp.enemyS.getFullEnemies());
        }
        gp.battleM.addEnemiesIndex(enIndex);
        
        //check for use potion
        usePotion(potionIndex);
        
        //if there is not collision, next possition is in map
        if (!collisionOn && inMap) {
            setX(nextX);
            setY(nextY);
            if (startFlag == true && gw.getCurrentLevel() > 0) {
                changeLevel = true;
                gw.goToPreviousLevel();
                tileM.makeTiles();
                setX(gw.getStartPossition().x* gp.getTileSize());
                setY(gw.getStartPossition().y * gp.getTileSize());
            }
            else if (stairsFlag == true) {
                changeLevel = true;
                gw.goToNextLevel();
                tileM.makeTiles();
                setX(gw.getStartPossition().x* gp.getTileSize());
                setY(gw.getStartPossition().y * gp.getTileSize());
            }
        }
        
        //death handling 
        if (!isAlive()) {
            gp.ui.showMessage("You died!");
            new Thread(() -> {
                try {
                    Thread.sleep(1500); //1.5sec delay between messages
                } catch (InterruptedException ignored) {}
                gp.ui.gameOverState = 1;
                gp.gameState = gp.gameOverState;
            }).start();
        }
        
        //win handling
        if (getEquippedItem().equals("Jewel Of Judgment")) {
            gp.ui.showMessage("You won!");
            new Thread(() -> {
                try {
                    Thread.sleep(1500); //1.5sec delay between messages
                } catch (InterruptedException ignored) {}
                gp.ui.gameOverState = 0;
                gp.gameState = gp.gameOverState;
            }).start();
        }
    }
    
    public void draw(Graphics2D g2) {
        
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, getPlayersDimensions(), getPlayersDimensions());
    }
    
    //Level computation methods
    public int getXP() { //getter 
        return XP;
    }
    
    public void addXP(int XP) { //adds XP
        this.XP += Math.max(0, XP); //if XP negative, then Math.max(0,XP)=0 so it never reduces XP
    }
    
    public void removeXP(int XP) {
        if (this.XP < XP) //Avoids negative XP
            this.XP = 0;
        else 
            this.XP -= Math.min(0, XP); //always non negative XP to subtract
    }
    
    public int getLevel() { //level getter
        return LEVEL_XP.floorEntry(XP).getValue();
    }
    
    //Base Stats getters
    public int getBaseHP() {
        int level = getLevel();
        return hpPerLevel.get(level - 1);
    }
    
    public int getBaseMP() {
        int level = getLevel();
        return mpPerLevel.get(level - 1);
    }
    
    public int getBaseStr() {
        int level = getLevel();
        return strPerLevel.get(level - 1);
    }
    
    public int getBaseInt() {
        int level = getLevel();
        return intPerLevel.get(level - 1);
    }
    
    //Other computation methods
    public int getStrength() {
        int level = getLevel(); //find level
        
        int str = strPerLevel.get(level - 1); //find base Str
        if (equippedItem != null) {
            for (ItemEffect effect : equippedItem.getEffects()) { //find their effects
                if (effect.getEffect() == ItemEffectType.STR_BONUS) { //if effect is type STR_BONUS
                    str += effect.getAmount(); //add the amount of strength given by item
                }
            }
        }
        return str;
    }     
    
    public int getIntellect() {
        int level = getLevel(); //find level
        
        int intel = intPerLevel.get(level - 1); //find base Intel
        if (equippedItem != null) {
            for (ItemEffect effect : equippedItem.getEffects()) { //find their effects
                if (effect.getEffect() == ItemEffectType.INT_BONUS) { //if effect is type INT_BONUS
                    intel += effect.getAmount(); //add the amount of intelligence given by item
                }
            }
        }
        return intel;
    }  
    
    public int getMaxHP() {
        int level = getLevel(); //find level
        
        int HP = hpPerLevel.get(level - 1); //find base HP
        if (equippedItem != null) {
            for (ItemEffect effect : equippedItem.getEffects()) { //find their effects
                if (effect.getEffect() == ItemEffectType.HP_BONUS) { //if effect is type HP_BONUS
                    HP += effect.getAmount(); //add the amount of HP bonus given by item
                }
            }
        }
        
        return HP;
    }
    
    public int getHP() {
        return Math.min(currentHP, getMaxHP());
    }
    
    public int getMaxMP() {
        int level = getLevel(); //find level
        
        int MP = mpPerLevel.get(level - 1); //find base MP
        if (equippedItem != null) {
            for (ItemEffect effect : equippedItem.getEffects()) { //find their effects
                if (effect.getEffect() == ItemEffectType.MP_BONUS) { //if effect is type MP_BONUS
                    MP += effect.getAmount(); //add the amount of MP bonus given by item
                }
            }
        }
        return MP;
    }
    
    public int getMP() {
        return Math.min(currentMP, getMaxMP());
    }
    
    public int getDamage() { 
        int dam; //Damage is the mean of Strength and Intellect
        dam = (getStrength() + getIntellect())/2;
        return dam;
    }
    
    public void takeDamage(int amount) {
        currentHP = Math.max(0, currentHP - amount);
    }
    
    //Methods for item pickUp
    public void pickUp(int i) {
        if (i != 999) {
            int level = gw.getCurrentLevel();
            Item newItem = gp.item[level][i];
            
            if (newItem == null) return;
            if (level >= gp.item.length) return;
            
            
            gp.ui.showMessage("There is a " + newItem.getName() + "!");
            
            if (newItem instanceof Weapon weapon) { //if item is weapon
                if (equippedItem!= null) { //if a weapon in hand already exists
                    if (keyH.pPressed) { 
                        keyH.pPressed = false;
                        int xItem = gp.item[level][i].getX(); //get coordinates of new weapon
                        int yItem = gp.item[level][i].getY();
                        gp.item[level][i] = equippedItem; //set old weapon in map at place of new weapon
                        gp.item[level][i].setX(xItem); //coordinates of new weapon
                        gp.item[level][i].setY(yItem);
                        equippedItem = weapon;
                        
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000); //2sec delay between messages
                            } catch (InterruptedException ignored) {}
                            gp.ui.showMessage("Weapons exchanged!");
                        }).start();
                        
                        addXP(10); //when exchanging weapon, XP increases by 5
                    }  
                    else { 
                        new Thread(() -> {
                            try {
                                Thread.sleep(2000); //2sec delay between messages
                            } catch (InterruptedException ignored) {}
                            gp.ui.showMessage("Press P to swap weapons.");
                        }).start();
                    }
                }
                else { //if a weapon in hand does not exist, then get weapon anyways
                    equippedItem = weapon;
                    gp.item[level][i] = null;
                    gp.ui.showMessage("You got a " + equippedItem.getName() + "!");
                    addXP(10); //when collecting weapon, XP increases by 10
                } 
            }
            else if (newItem instanceof Potion) { //if item is potion put in inventory
                inventory.put(newItem, inventory.getOrDefault(newItem, 0) + 1);
                gp.ui.showMessage("You got a " + newItem.getName() + "!");
                gp.item[level][i] = null;
                addXP(5); //when finding Potion, XP increases by 5
            }
            else if (newItem instanceof Trap trap) { //if item is trap put in inventory but use immediately
                inventory.put(newItem, inventory.getOrDefault(newItem, 0) + 1);
                gp.ui.showMessage("You were trapped in " + newItem.getName() + "!");
                useItem(trap);
                gp.item[level][i] = null;
                removeXP(5); //when being trapped, XP decreases by 5
            }
        } 
    }
    
    
    //Method for Consumable items (potions or traps)
    public void useItem(Consumable u) {
        if (!inventory.containsKey(u) || u == null) return;
        
        List<ItemEffect> effects = u.use(); //use and get effects from Consumable
        
        for (ItemEffect effect : effects) { //browse effects
            if (effect.getEffect() == ItemEffectType.HP_REPLENISH) {
                //update currentHP only if the value x is 0 <= x < MaxHP
                currentHP = Math.min(getMaxHP(), Math.max(0, currentHP + effect.getAmount())); 
            }
            else if (effect.getEffect() == ItemEffectType.MP_REPLENISH) {
                //update currentMP only if the value x is 0 <= x < MaxMP
                currentMP = Math.min(getMaxMP(), Math.max(0, currentMP + effect.getAmount())); 
            }
        }
        
        int usesLeft = u.usesLeft(); //get uses from Consumable
        if (usesLeft <= 0) { //if there aren't uses left, check how many such items are in the inventory and remove 1
            int count = inventory.get(u);
            if (count > 1) {
                inventory.put(u, count - 1);
            }
            else {
                inventory.remove(u);
            }
        }
    }
    
    //Method to use specific type of potion from inventory
    public void usePotion(int index) {
        ItemEffectType typeToUse = switch (index) { //switch based on which type of potion to use
            case 1 -> ItemEffectType.HP_REPLENISH;
            case 2 -> ItemEffectType.MP_REPLENISH;
            default -> null;
        };
        
        if (typeToUse == null) return;
        
        Potion selectedPotion = null; 
        
        for (Item item: inventory.keySet()) { //browse through inventory
            if (item instanceof Potion potion){ //if inventory is potion (just for safety because inventory contains only potions)
                for (ItemEffect effect : potion.getEffects()) { //browse potion's effects
                    if (effect.getEffect() == typeToUse) { //if effect is of selected type
                        selectedPotion = potion; //choose potion
                    }
                }
            }
        }
        
        if (selectedPotion == null) { 
            gp.ui.showMessage("No potion available !");
        }
        else {
            gp.ui.showMessage(selectedPotion.getName() + " was used !");
            useItem(selectedPotion); //use chosen potion
        }
    }
   
    //Method for rest
    public void rest() {
        double value; 
        
        value = currentHP + getMaxHP()*0.05;
        setCurrentHP((int)value);
        
        value = currentMP + getMaxMP()*0.05;
        setCurrentMP((int)value);
    }
    
    //Setters
    public void setCurrentHP(int currentHP) {
        this.currentHP = Math.min(currentHP, getMaxHP());
    }

    public void setCurrentMP(int currentMP) {
        this.currentMP = Math.min(currentMP, getMaxMP());
    }
    
    //Accessors
    public ArrayList<Integer> getStatsInventory() {
        int countHealth = 0, countMana = 0;
        ArrayList<Integer> list = new ArrayList<>();
        
        for (Item item : inventory.keySet()) {
            for (ItemEffect effect : item.getEffects()) {
                if (effect.getEffect() == ItemEffectType.MP_REPLENISH) {
                    countMana += inventory.get(item);
                }
                else if (effect.getEffect() == ItemEffectType.HP_REPLENISH) {
                    countHealth += inventory.get(item);
                }
            }
        }
        Collections.addAll(list, countHealth, countMana);
        return list;
    }
    
    public String getEquippedItem() {
        if (equippedItem == null) {
            return "No weapon in hand.";
        }
        else {
            return equippedItem.getName();
        }
    }

    //method to return flag to indicate change of cave
    public boolean isChangeLevel() {
        return changeLevel;
    }
    
    //method to return flag to indicate player's death
    public boolean isAlive() {
        return currentHP > 0;
    }
  
}