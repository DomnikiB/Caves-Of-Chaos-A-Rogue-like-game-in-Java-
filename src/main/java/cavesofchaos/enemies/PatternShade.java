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
public class PatternShade extends Enemy {

    public PatternShade(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(50);
        setXPReward(400);
        setVisRadius(10);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Pattern Shade";
    }
    
    @Override
    public String getDescription() {
        return "Pattern Shade";
    }
    
    @Override
    public int doDamage() {
        Dice dice = new Dice(3, 6);
        return dice.roll(10);
    }
    
}
