/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.entity.player;

import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import cavesofchaos.main.KeyHandler;
import cavesofchaos.tile.TileManager;
import java.util.List;

/**
 *
 * @author domniki
 */
public class Duelist extends Player {
    
    public Duelist(GamePanel gp, GameWorld gw, KeyHandler keyH, TileManager tileM) {
        super(gp, gw, keyH, tileM);
        //Tables of StatsPerLevel
        this.hpPerLevel = List.of(30, 60, 80, 90, 100, 140); //index 0 for level 1
        this.mpPerLevel = List.of(0, 0, 0, 0, 0, 0);
        this.strPerLevel = List.of(10, 20, 25, 30, 35, 45);
        this.intPerLevel = List.of(0, 0, 0, 0, 0, 0);
        
        //Set starting HP/MP as base Stats
        this.setCurrentHP(getBaseHP());
        this.setCurrentMP(getBaseMP());
    }
    
    @Override
    public String getPlayersClass() {
        return "Duelist";
    }
    
    @Override
    public int attack() {
        gp.ui.showMessage("Duelist's attack!");
        return getDamage();
    }
    
}
