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
public class ShadowSerpent extends Enemy {

    public ShadowSerpent(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(20);
        setXPReward(100);
        setVisRadius(4);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Shadow Serpent";
    }
    
    @Override
    public String getDescription() {
        return "Shadow Serpent";
    }
    
    @Override
    public int doDamage() {
        Dice dice = new Dice(4, 6);
        return dice.roll(10);
    }
}
