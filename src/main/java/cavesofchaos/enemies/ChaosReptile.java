/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.enemies;

import cavesofchaos.main.Dice;
import cavesofchaos.main.GamePanel;
import cavesofchaos.main.GameWorld;
import java.awt.Color;

/**
 *
 * @author domniki
 */
public class ChaosReptile extends Enemy {
    
    public ChaosReptile(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(1500);
        setXPReward(0);
        setVisRadius(10);
        setBaseColor(Color.MAGENTA);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Chaos Reptile";
    }
    
    @Override
    public String getDescription() {
        return "Chaos Reptile";
    }
    
    @Override
    public int doDamage() {
        Dice dice = new Dice(8, 6);
        return dice.roll(10);
    }
}
