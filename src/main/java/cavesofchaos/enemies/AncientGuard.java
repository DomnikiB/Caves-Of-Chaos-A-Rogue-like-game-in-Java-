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
public class AncientGuard extends Enemy {

    public AncientGuard(GamePanel gp, GameWorld gw) {
        super(gp, gw);
        setHP(15);
        setXPReward(50);
        setVisRadius(7);
        setBaseColor(Color.ORANGE);
        setInitialCurrentHP();
    }
    
    @Override
    public String getName() {
        return "Ancient Guard";
    }
    
    @Override
    public String getDescription() {
        return "Ancient Guard";
    }
    
    @Override
    public int doDamage() {
        return 5;
    }
}
