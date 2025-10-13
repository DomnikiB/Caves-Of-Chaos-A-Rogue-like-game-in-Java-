/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import cavesofchaos.items.*;
import cavesofchaos.items.consumable.*;
import cavesofchaos.items.equippable.*;
import cavesofchaos.tile.Tile;
import cavesofchaos.tile.TileManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author domniki
 */

//class to handle setting items in levels
public class ItemSetter {
    GamePanel gp;
    GameWorld gw;
    TileManager tileM;
    private final int itemsPerLevel = 10;
    Random random = new Random();

    public ItemSetter(GamePanel gp, GameWorld gw, TileManager tileM) {
        this.gp = gp;
        this.gw = gw;
        this.tileM = tileM;        
    }
    
    
    //method to set items on floor tiles of each level
    public void setItems() {        
        for (int level = 0; level < gw.getNumLevels(); ++level) {
            //get map's floors
            String[][] map = gw.getSpesificLevelMap(level);
            List<GameMap.Possition> floors = findFloors(map);
            
            //randomize floors
            Collections.shuffle(floors, random);
            
            for (int itemNum = 0; itemNum < itemsPerLevel; ++itemNum) {
                //create random item and assign random possition
                GameMap.Possition pos = floors.get(itemNum);
                
                Item item = createRandomItem(level);
                
                item.setX(pos.x * gp.getTileSize());
                item.setY(pos.y * gp.getTileSize());
                
                //enter item to list
                gp.item[level][itemNum] = item;
            }
                        
        }      
    }
    
    //method to set a single item
    public void setSingleItem(int x, int y, boolean reptile) {
        int level  = gw.getCurrentLevel();
        
        //if enemy is not reptile create random potion if there is empty place in list
        if (reptile == false) {
            for (int i = 0; i < gp.item[level].length; ++i) {
                if (gp.item[level][i] == null) {
                    Item item = createRandomPotion();

                    item.setX(x);
                    item.setY(y);

                    gp.item[level][i] = item;
                    break;
                }
            }
        }
        else {
            //if enemy is reptile create Jewel Of Judgement item always
            Item item = new JewelOfJudgment();
            
            item.setX(x);
            item.setY(y);
            
            gp.item[level][0] = item;
        }
        
    }
    
    //method to find all floor tiles' coordinates
    private List<GameMap.Possition> findFloors(String[][] map) {
        List<GameMap.Possition> list = new ArrayList<>();
        for (int x = 0; x < map.length; ++x) {
            for(int y = 0; y < map[0].length; ++y) {
                if (map[x][y].equals("floor")) {
                    list.add(new GameMap.Possition(x, y));
                }
            }
        }
        return list;
    }
    
    //method to create random item 
    private Item createRandomItem(int level) {
        int ran = random.nextInt(7);
        return switch (ran) {
            case 0 -> new MinorHealthPotion();
            case 1 -> new HealthPotion();
            case 2 -> new MajorHealthPotion();
            case 3 -> new ManaPotion();
            case 4 -> new DangerousKineticSand();
            case 5 -> new DeadlySpiderNet();    
            case 6 -> createRandomWeapon(level); 
            default -> new MinorHealthPotion();
        };
    }
    
    //method to create random potion 
    private Item createRandomPotion() {
        int ran = random.nextInt(4);
        return switch (ran) {
            case 0 -> new MinorHealthPotion();
            case 1 -> new HealthPotion();
            case 2 -> new MajorHealthPotion();
            case 3 -> new ManaPotion(); 
            default -> new MinorHealthPotion();
        };
    }
    
    //method to create random weapon item based on cave level = [0, 9]
    //item's stats = 10 + level * 5 
    private Item createRandomWeapon(int level) {
        int ranNum = random.nextInt(2);
        switch (level) {
            case 0 -> {
                if (ranNum == 0) {
                    return new AmazingLightningSword();
                }
                else {
                    return new DeadlyPointyStaff();
                }
            }
            case 1 -> {
                if (ranNum == 0) {
                    return new AncientNobleSword();
                }
                else {
                    return new InvocationStaff();
                }
            }
            case 2 -> {
                if (ranNum == 0) {
                    return new BladeOfGraveSword();
                }
                else {
                    return new PeaceKeeperStaff();
                }
            }
            case 3 -> {
                if (ranNum == 0) {
                    return new SilverlightSword();
                }
                else {
                    return new ExtinctionStaff();
                }                
            }
            case 4 -> {
                if (ranNum == 0) {
                    return new DragonbreathReaverSword();
                }
                else {
                    return new FrozenCaneStaff();
                }
            }
            case 5 -> {
                if (ranNum == 0) {
                    return new NightfallSword();
                }
                else {
                    return new ReaperStaff();
                }
            }   
            case 6 -> {
                if (ranNum == 0) {
                    return new TheUntamedSword();
                }
                else {
                    return new CorruptedWillStaff();
                }
            }
            case 7 -> {
                if (ranNum == 0) {
                    return new BrutalitySword();
                }
                else {
                    return new VilePoleStaff();
                }
            }
            case 8 -> {
                if (ranNum == 0) {
                    return new DragonsShortSword();
                }
                else {
                    return new ThunderstormWarStaff();
                }
            }
            case 9 -> {
                if (ranNum == 0) {
                    return new PolishedShortSword();
                }
                else {
                    return new SinisterGreatStaff();
                }
            }
            default -> {
                return new AmazingLightningSword();
            } 
        }
    }
    
    //update color status based on tiles' visibility status
    private void getRenderColor() {
        int level = gw.getCurrentLevel();
        Tile[][] tiles = tileM.getTiles();
        
        for (int itemNum = 0; itemNum < gp.item[level].length; ++itemNum) { 
            if (gp.item[level][itemNum] != null) {
                int tileX = gp.item[level][itemNum].getX() / gp.getTileSize();
                int tileY = gp.item[level][itemNum].getY() / gp.getTileSize();

                Tile targetTile = tiles[tileX][tileY];

                switch (targetTile.getVisibility()) {
                    case UNKNOWN -> {gp.item[level][itemNum].setColor(tiles[tileX][tileY].getRenderColor().darker());
                    }
                    case FOGGED -> gp.item[level][itemNum].setColor(Color.CYAN.darker());
                    default -> gp.item[level][itemNum].setColor(Color.CYAN);
                }
            }
            
        }
    }
    
    public void update() {
        getRenderColor();
    }
    
    public void draw(Graphics2D g2) {
        int i = gw.getCurrentLevel();
        
        for (int j = 0; j < gp.item[i].length; ++j) { 
            if (gp.item[i][j] != null) {
                g2.setColor(gp.item[i][j].getColor());
                
                //Jewel is a triangle and everything else a circle
                if (gp.item[i][j].getName().equals("Jewel Of Judgment")) { 
                    int x = gp.item[i][j].getX();
                    int y = gp.item[i][j].getY();

                    int int1[] = {x + 4, x, x + 8};
                    int int2[] = {y, y + 8, y + 8};
                    g2.fillPolygon(int1, int2, 3);
                }
                else {
                    g2.fillOval(gp.item[i][j].getX(), gp.item[i][j].getY(), gp.getTileSize() - 8, gp.getTileSize() - 8);
                }
            }
        }
        
    }
    
}
