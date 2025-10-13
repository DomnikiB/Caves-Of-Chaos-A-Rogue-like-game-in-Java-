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
public class ShadowSoldier extends Enemy {

    public ShadowSoldier(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(5);
        setXPReward(30);
        setVisRadius(4);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Shadow Soldier";
    }
    
    @Override
    public String getDescription() {
        return "Shadow Soldier";
    }
    
    @Override
    public int doDamage() {
        return 2;
    }
    
}

