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
public class Wizard extends Player {
    
    public Wizard(GamePanel gp, GameWorld gw, KeyHandler keyH, TileManager tileM) {
        super(gp, gw, keyH, tileM);
        //Tables of StatsPerLevel
        this.hpPerLevel = List.of(20, 40, 50, 55, 60, 80); //index 0 for level 1
        this.mpPerLevel = List.of(30, 50, 70, 90, 110, 140);
        this.strPerLevel = List.of(0, 0, 0, 0, 0, 0);
        this.intPerLevel = List.of(10, 20, 30, 40, 50, 70);
        
        //Set starting HP/MP as base Stats
        this.setCurrentHP(getBaseHP());
        this.setCurrentMP(getBaseMP());
    }
    
        
    @Override
    public String getPlayersClass() {
        return "Wizard";
    }
    
    @Override
    public int attack() {
        //if there are more than 5 MP left, then do spell 
        if (getMP() >= 5) {
            gp.ui.showMessage("Wizard's magic spell attack!");
            setCurrentMP(getMP() - 5);
            return getDamage();
        }
        else {
            gp.ui.showMessage("Wizard's MP is not enough.");
            return 0;
        }
    }
}
