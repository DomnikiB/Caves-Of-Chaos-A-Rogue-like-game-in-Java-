/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.items.equippable;

import cavesofchaos.items.effects.ItemEffect;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author domniki
 */
public class Weapon implements Equippable {
    int x, y;
    boolean collision = true;
    private Color color = Color.CYAN; 
    
    //getters to be overriden by each subclass
    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getType() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ItemEffect> getEffects() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    //coordinates setters & getters
    @Override
    public void setX(int inputX) {
        x = inputX;
    }

    @Override
    public void setY(int inputY) {
        y = inputY;
    }

    @Override
    public Integer getX() {
        return x;
    }

    @Override
    public Integer getY() {
        return y;
    }

    //collision getter & color setters & getters
    @Override
    public Boolean collision() {
        return collision;
    }
    
    @Override
    public void setColor(Color inputC) {
        color = inputC;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
