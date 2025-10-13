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
public class Fallen extends Enemy {

    public Fallen(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(30);
        setXPReward(80);
        setVisRadius(2);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Fallen";
    }
    
    @Override
    public String getDescription() {
        return "Fallen";
    }
    
    @Override
    public int doDamage() {
        return 8;
    }
    
    
}
