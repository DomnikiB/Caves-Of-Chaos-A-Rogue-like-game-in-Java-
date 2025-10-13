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
public class Shade extends Enemy {

    public Shade(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(40);
        setXPReward(100);
        setVisRadius(6);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Shade";
    }
    
    @Override
    public String getDescription() {
        return "Shade";
    }
    
    @Override
    public int doDamage() {
        return 10;
    }
}
