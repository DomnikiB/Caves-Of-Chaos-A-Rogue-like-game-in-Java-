/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.tile;

import cavesofchaos.entity.AbstractEntity;
import cavesofchaos.main.GamePanel;

/**
 *
 * @author domniki
 */
public class TileNavChecker {
    GamePanel gp;
    TileManager tileM;

    public TileNavChecker(GamePanel gp, TileManager tileM) {
        this.tileM = tileM;
        this.gp = gp;
    }
    
    //method to check whether next player's possition is in walkable tile and in map's tiles range
    public void checkTile(AbstractEntity entity) {
        int entityLeftX = entity.x;
        int entityRightX = entity.x + entity.solidArea.width;
        int entityTopY = entity.y;
        int entityBottomY = entity.y + entity.solidArea.height;
        
        int entityLeftCol = entityLeftX/gp.getTileSize();
        int entityRightCol = entityRightX/gp.getTileSize();
        int entityTopRow = entityTopY/gp.getTileSize();
        int entityBottomRow = entityBottomY/gp.getTileSize();
        
        Tile tile1, tile2;
        Tile[][] tiles = tileM.getTiles();
        
        //find direction,check if it is in range of map
        //then check if next 2 tiles (left,right or up,down) in that direction are walls
        //raise appropriate flags
        switch (entity.direction) {
            case "up" -> {
                entityTopRow = entityTopY - entity.speed;
                if (entityTopRow >= 0) {
                    entity.inMap = true;
                    tile1 = tiles[entityLeftCol][entityTopRow / gp.getTileSize()];
                    tile2 = tiles[entityRightCol][entityTopRow / gp.getTileSize()];
                    if (tile1.getCollision() || tile2.getCollision()) {
                        entity.collisionOn = true;
                    }
                }
            }
            case "down" -> {
                entityBottomRow = (entityBottomY + entity.speed)/gp.getTileSize();
                if (entityBottomRow < tiles[0].length) {
                    entity.inMap = true;
                    tile1 = tiles[entityLeftCol][entityBottomRow];
                    tile2 = tiles[entityRightCol][entityBottomRow];
                    if (tile1.getCollision() || tile2.getCollision()) {
                        entity.collisionOn = true;
                    }
                }
            }
            case "left" -> {
                entityLeftCol = entityLeftX - entity.speed;
                if (entityLeftCol >= 0) {
                    entity.inMap = true;
                    tile1 = tiles[entityLeftCol / gp.getTileSize()][entityTopRow];
                    tile2 = tiles[entityLeftCol / gp.getTileSize()][entityBottomRow];
                    if (tile1.getCollision() || tile2.getCollision()) {
                        entity.collisionOn = true;
                    }
                }
            }
            case "right" -> {
                entityRightCol = (entityRightX + entity.speed)/gp.getTileSize();
                if (entityRightCol < tiles.length) {
                    entity.inMap = true;
                    tile1 = tiles[entityRightCol][entityTopRow];
                    tile2 = tiles[entityRightCol][entityBottomRow];
                    if (tile1.getCollision() || tile2.getCollision()) {
                        entity.collisionOn = true;
                    }
                }
            }
            case "exit" -> { //this case should be true only for player entities
                entity.inMap = true;
                tile1 = tiles[entityLeftCol][entityTopRow];
                if (tile1.getType().equals("stairs")) {
                    entity.stairsFlag = true;
                }
                else if (tile1.getType().equals("start")) {
                    entity.startFlag = true;
                }
            }
        }
    }
    
    //method to check entity's to object collision and return index of object that collides with entity
    public int checkObject(AbstractEntity entity, boolean player, int level) {
        int index = 999;
        
        for (int i = 0; i< gp.item[level].length; ++i) {
            
            if (gp.item[level][i] != null) {
                //get entity's solidArea possition
                entity.solidArea.x = entity.x;
                entity.solidArea.y = entity.y;
                
                //get object's solidArea possition
                gp.item[level][i].solidArea.x = gp.item[level][i].getX() + gp.item[level][i].solidArea.x;
                gp.item[level][i].solidArea.y = gp.item[level][i].getY() + gp.item[level][i].solidArea.y;
                
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    default -> {
                    }
                }
                if (entity.solidArea.intersects(gp.item[level][i].solidArea)) {
                    if (gp.item[level][i].collision() == true) {
                        entity.collisionOn = true;
                    }
                    if (player == true) {
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.item[level][i].solidArea.x = gp.item[level][i].solidAreaDefaultX; 
                gp.item[level][i].solidArea.y = gp.item[level][i].solidAreaDefaultY; 
            }
        }
        return index;
    }
    
    //method to check entity's collision with list of other entities (enemies) and return index of entity that collides with entity
    public int checkEntityList(AbstractEntity entity, AbstractEntity[] target) {
        int index = 999;
        
        for (int i = 0; i< target.length; ++i) {
            
            if (target[i] != null) {
                //get entity's solidArea possition
                entity.solidArea.x = entity.x;
                entity.solidArea.y = entity.y;
                
                //get target entity's solidArea possition
                target[i].solidArea.x = target[i].x + target[i].solidArea.x;
                target[i].solidArea.y = target[i].y + target[i].solidArea.y;
                
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                    default -> {
                    }
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        target[i].collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX; 
                target[i].solidArea.y = target[i].solidAreaDefaultY; 
            }
        }
        return index;
    }
    
    //method to check collision between 2 entities
    public boolean checkEntity(AbstractEntity entity, AbstractEntity target) {
        boolean flag = false;
                    
        if (target != null) {
            //get entity's solidArea possition
            entity.solidArea.x = entity.x;
            entity.solidArea.y = entity.y;

            //get target entity's solidArea possition
            target.solidArea.x = target.x + target.solidArea.x;
            target.solidArea.y = target.y + target.solidArea.y;

            switch (entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
                default -> {
                }
            }
            if (entity.solidArea.intersects(target.solidArea)) {
                if (target != entity) {
                    entity.collisionOn = true;
                    target.collisionOn = true;
                    flag = true;                    
                }
            }
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            target.solidArea.x = target.solidAreaDefaultX; 
            target.solidArea.y = target.solidAreaDefaultY; 
        }
        return flag;
    }
}
