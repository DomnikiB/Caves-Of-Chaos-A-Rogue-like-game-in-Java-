/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.tile;

import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import java.awt.Graphics2D;
import static java.lang.Math.abs;

/**
 *
 * @author domniki
 */
public class TileManager {
    GamePanel gp;
    GameWorld gw;
    Tile[][] tiles;

    //constructor
    public TileManager(GamePanel gp, GameWorld gw) {
        this.gp = gp;
        this.gw = gw;
        
        makeTiles();
    }
        
    //method to initiate tiles
    public void makeTiles() {
        String[][] map = gw.getCurrentMap();
        int row = map.length;
        int col = map[0].length;
        
        tiles = new Tile[row][col];
        
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                tiles[i][j] = new Tile(map[i][j]);
            }
        }
    }
    
    //method to update visibility around player
    public void update(int playerX, int playerY) {
        int visibilityRadius = gp.player.getVisibilityRadius();
        
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[0].length; ++j) {
                double dist = abs(playerX/gp.getTileSize() - i) + abs(playerY/gp.getTileSize()  - j);
                
                Tile tile = tiles[i][j];
                if (dist < visibilityRadius) {
                    tile.setVisibility(Tile.Visibility.VISIBLE);
                }
                else if (tile.getVisibility() == Tile.Visibility.VISIBLE) {
                    tile.setVisibility(Tile.Visibility.FOGGED);
                }
            }
        }
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    
    public void draw(Graphics2D g2) {  
        int tileSize = gp.getTileSize();
               
        for (int i=0; i<tiles.length; ++i) {
            for (int j=0; j<tiles[0].length; ++j) {
                Tile tile = tiles[i][j];
                g2.setColor(tile.getRenderColor());
                g2.fill3DRect(i * tileSize, j * tileSize, tileSize, tileSize, false);
            }
        }  
    }
    
}
