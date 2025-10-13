/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.enemies;

import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import java.awt.Color;

/**
 *
 * @author domniki
 */
public class ChaosKnight extends Enemy {

    public ChaosKnight(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(60);
        setXPReward(150);
        setVisRadius(9);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Chaos Knight";
    }
    
    @Override
    public String getDescription() {
        return "Chaos Knight";
    }
    
    @Override
    public int doDamage() {
        return 20;
    }
    
}
