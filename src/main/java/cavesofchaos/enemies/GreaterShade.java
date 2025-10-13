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
public class GreaterShade extends Enemy {

    public GreaterShade(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(50);
        setXPReward(120);
        setVisRadius(7);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Greater Shade";
    }
    
    @Override
    public String getDescription() {
        return "Greater Shade";
    }
    
    @Override
    public int doDamage() {
        return 12;
    }
}
