/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import cavesofchaos.enemies.*;
import cavesofchaos.tile.*;
import cavesofchaos.tile.TileManager;
import java.awt.Graphics2D;
import java.util.*;

/**
 *
 * @author domniki
 */
public class EnemySetter {
    GamePanel gp;
    GameWorld gw;
    TileManager tileM;

    public EnemySetter(GamePanel gp, GameWorld gw, TileManager tileM) {
        this.gp = gp;
        this.gw = gw;
        this.tileM = tileM;
        
        //initialize reptile
        this.reptile = new ChaosReptile(gp, gw);
        
        //initially place reptile
        placeReptile();
    }
    
    private final int numEnemies = 8; //max number of enemies
    private static int countEnemies = 0; //enemy counter per level
    private final Enemy[] enemiesList = new Enemy[numEnemies];
    private final Random random = new Random();
    
    //Chaos Reptile
    private ChaosReptile reptile;
    
    //method set all enemies of current cave
    private void enemySetter(int level, int playerX, int playerY, int visRadius, int HP, int maxHP) {
        if (enemiesList.length > numEnemies) return;
        
    
        double p = 0.1 * Math.exp(-4 * HP / maxHP);
        
        
        if (random.nextDouble(1) < p) {
            placeEnemy(level, playerX, playerY, visRadius);
        }
        
    }
    
    //method to place reptile
    private void placeReptile() {
        //get cave 10 map
        String[][] map = gw.getSpesificLevelMap(gw.getNumLevels() - 1);
        
        //find floors
        List<GameMap.Possition> floors = findFloors(map);
        Collections.shuffle(floors, random);
        
        //get first after random shuffling
        GameMap.Possition reptilePos = floors.get(0);
        
        //set reptile's initial possition
        reptile.setX(reptilePos.x * gp.getTileSize());
        reptile.setY(reptilePos.y * gp.getTileSize());
    }
    
    //method to place all enemies based on players coordinates
    private void placeEnemy(int level, int playerX, int playerY, int visRadius) {
        
        String[][] map = gw.getCurrentMap();
        List<GameMap.Possition> floors = findFloors(map);
        
        if (countEnemies < numEnemies) {
            Enemy enemy = createRandomEnemy(level);
            
            int dist = (Math.max(enemy.getVisRadius(), visRadius) + 1) * gp.getTileSize(); 
            
            List<GameMap.Possition> posList = new ArrayList<>();
            
            //abs(dx) + abs(dy) = dist
            //where dx,dy are distances from player's x,y 
            for(int dx = -dist; dx <= dist; ++dx) {
                int absDx = Math.abs(dx);
                int dy= dist - absDx;
                
                for(int sign : new int[]{-1, 1}) {
                    //solve the equation abs(dx) + abs(dy) = dist and find the eligible tile
                    int x = playerX + dx;
                    int y = playerY + sign * dy;
                    int x_tile = x /gp.getTileSize();
                    int y_tile = y / gp.getTileSize();
                    
                    //avoid same value for y = 0 sign = +/- 1
                    if (dy == 0 && sign == -1) continue;
                    
                    //if tile is floor and is not occupied
                    if (floors.stream().anyMatch(s -> s.x == x_tile && s.y == y_tile) && !isOccupied(x_tile, y_tile)) {
                        //add all eligible possitions to posList
                        GameMap.Possition pos = new GameMap.Possition(x_tile, y_tile);
                        posList.add(pos);
                    }

                }
            }

            
            if (!posList.isEmpty()) {
                GameMap.Possition newPos = posList.get(random.nextInt(posList.size()));
                enemy.setX(newPos.x * gp.getTileSize());
                enemy.setY(newPos.y * gp.getTileSize());
                for (int i = 0; i < numEnemies; ++i) {
                    //find next empty space in list and put enemy
                    if (enemiesList[i] == null) {
                        enemiesList[i] = enemy;
                        countEnemies ++;
                        break;
                    } 
                }
            }
            
        }
    }
    
    //random enemy creator based on player's level (XP)
    private Enemy createRandomEnemy(int level) {
        int ran; 
        Enemy newEnemy;
        
        switch (level) {
            case 1 -> {
                ran = random.nextInt(2);
                switch (ran) {
                    case 0 -> newEnemy = new ShadowSoldier(gp, gw);
                    case 1 -> newEnemy = new AncientGuard(gp, gw);
                    default -> newEnemy = new ShadowSoldier(gp, gw);
                }
            }
            case 2 -> {
                ran = random.nextInt(3);
                switch (ran) {
                    case 0 -> newEnemy = new ShadowSoldier(gp, gw);
                    case 1 -> newEnemy = new AncientGuard(gp, gw);
                    case 2 -> newEnemy = new Fallen(gp, gw);
                    default -> newEnemy = new ShadowSoldier(gp, gw);
                }
            }
            case 3 -> {
                ran = random.nextInt(5);
                switch (ran) {
                    case 0 -> newEnemy = new AncientGuard(gp, gw);
                    case 1 -> newEnemy = new Fallen(gp, gw);
                    case 2 -> newEnemy = new Shade(gp, gw);
                    case 3 -> newEnemy = new GreaterShade(gp, gw);
                    case 4 -> newEnemy = new ShadowSerpent(gp, gw);
                    default -> newEnemy = new AncientGuard(gp, gw);
                }
            }
            case 4 -> {
                ran = random.nextInt(4);
                switch (ran) {
                    case 0 -> newEnemy = new Fallen(gp, gw);
                    case 1 -> newEnemy = new Shade(gp, gw);
                    case 2 -> newEnemy = new GreaterShade(gp, gw);
                    case 3 -> newEnemy = new ChaosKnight(gp, gw);
                    default -> newEnemy = new Fallen(gp, gw);
                }
            }
            case 5 -> {
                ran = random.nextInt(5);
                switch (ran) {
                    case 0 -> newEnemy = new GreaterShade(gp, gw);
                    case 1 -> newEnemy = new ChaosKnight(gp, gw);
                    case 2 -> newEnemy = new ShadowSerpent(gp, gw);
                    case 3 -> newEnemy = new ChaosBeast(gp, gw);
                    case 4 -> newEnemy = new PatternShade(gp, gw);
                    default -> newEnemy = new GreaterShade(gp, gw);
                }
            }
            case 6 -> {
                ran = random.nextInt(3);
                switch (ran) {
                    case 0 -> newEnemy = new ChaosKnight(gp, gw);
                    case 1 -> newEnemy = new ChaosBeast(gp, gw);
                    case 2 -> newEnemy = new PatternShade(gp, gw);
                    default -> newEnemy = new ChaosKnight(gp, gw);
                }
            }
            default -> {
                newEnemy = new ShadowSoldier(gp, gw);
            }
        }
        return newEnemy;
    }
    
    //method to check if tile is occupied by another enemy
    private boolean isOccupied(int xTile, int yTile) {
        for (Enemy enemy : enemiesList) {
            if (enemy != null) {
                if (enemy.getX() / gp.getTileSize() == xTile && enemy.getY() / gp.getTileSize() == yTile) {
                    return true;
                }
            }
        }
        return false;
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
    
    //update color status based on tiles' visibility status
    private void getRenderColor() {
        Tile[][] tiles = tileM.getTiles();
        
        //copy enemies and reptile to a new Array
        Enemy[] enemiesListWithReptile = new Enemy[numEnemies +1];
        System.arraycopy(enemiesList, 0, enemiesListWithReptile, 0, enemiesList.length);
        enemiesListWithReptile[numEnemies] = reptile;
        
        for (Enemy enemy: enemiesListWithReptile) { 
            if (enemy != null) {
                int tileX = enemy.getX() / gp.getTileSize();
                int tileY = enemy.getY() / gp.getTileSize();

                Tile targetTile = tiles[tileX][tileY];

                //set color based on tile visibility
                switch (targetTile.getVisibility()) {
                    case UNKNOWN -> enemy.setColor(tiles[tileX][tileY].getRenderColor().darker());
                    case FOGGED -> enemy.setColor(enemy.getBaseColor().darker());
                    default -> enemy.setColor(enemy.getBaseColor());
                }
            }
        }
    }
    
    //method to handle player's cave change 
    private void emptyListInCaveChange() {
        if (gp.player.isChangeLevel()) {
            //if player is changing cave, delete enemies
            for (int i = 0; i < numEnemies; ++i) {
                enemiesList[i] = null;
            }
            countEnemies = 0;
            //if current cave is the final, then change possition reptile
            if (gw.getCurrentLevel() == gw.getNumLevels() - 1) {
                placeReptile();
            }
        }
    }
    
    //get enemies list
    public Enemy[] getEnemies() {
        return enemiesList;
    }
    
    //get full enemies list with reptile
    public Enemy[] getFullEnemies() {
        Enemy[] enemiesFullList = new Enemy[numEnemies + 1];
        System.arraycopy(enemiesList, 0, enemiesFullList, 0, enemiesList.length);
        enemiesFullList[numEnemies] = reptile;
        return enemiesFullList;
    }
    
    public void update() {
        if (gp.player != null) {
            getRenderColor();
            
            int level = gp.player.getLevel();
            int playerX = gp.player.x;
            int playerY = gp.player.y;
            int visRadius = gp.player.getVisibilityRadius();
            int HP = gp.player.getHP();
            int maxHP = gp.player.getMaxHP();
            
            //if there is not a battle going on set more enemies
            if (gp.battleM.isBattleOn() == false) {
                enemySetter(level, playerX, playerY, visRadius, HP, maxHP);
            }
            
            //update enemies
            for (int i = 0; i < enemiesList.length; ++i) {
                Enemy enemy = enemiesList[i];
                
                if (enemy != null) {
                    //update
                    enemy.update();
                    
                    //if after update, enemy collides with player
                    //add enemy to list of indeces
                    if (enemy.isPlayerCollisionFlag()) {
                        gp.battleM.addEnemiesIndex(i);
                    }
                    
                    //if after update, enenmy died
                    if (enemy.isRemovalFlag()) {
                        enemiesList[i] = null;
                    }
                }
            }
                     
            //delete enemies if player changes cave
            emptyListInCaveChange();
            
            //if player is in cave 10, then update reptile
            if (gw.getCurrentLevel() == gw.getNumLevels() - 1 && reptile != null) {
                reptile.update();
                //if after update, enemy collides with player
                //add enemy to list of indeces
                if (reptile.isPlayerCollisionFlag()) {
                    gp.battleM.addEnemiesIndex(getFullEnemies().length - 1);
                }
                
                //if after update, reptile died
                if (reptile.isRemovalFlag()) {
                    reptile = null;
                }
            }
        }
         
    }
    
    public void draw(Graphics2D g2) {
        //draw enemies
        for (Enemy enemies: enemiesList) {
            if (enemies != null) {
                enemies.draw(g2);
            }
        }
        
        //if player is in cave 10, then update reptile
        if (gw.getCurrentLevel() == gw.getNumLevels() - 1) {
            if (reptile != null) {
                reptile.draw(g2);
            }
        }        
    }
}
