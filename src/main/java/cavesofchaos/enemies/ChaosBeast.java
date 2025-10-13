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
public class ChaosBeast extends Enemy {

    public ChaosBeast(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(80);
        setXPReward(200);
        setVisRadius(5);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Chaos Beast";
    }
    
    @Override
    public String getDescription() {
        return "Chaos Beast";
    }
    
    @Override
    public int doDamage() {
        Dice dice = new Dice(2, 6);
        return dice.roll(10);
    }
    
}
