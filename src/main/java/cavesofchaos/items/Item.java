/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cavesofchaos.items;

import cavesofchaos.items.effects.ItemEffect;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.List;

/**
 *
 * @author domniki
 */
public interface Item { //basic Item  
    String getName(); //Item's name
    String getDescription(); //description
    String getType(); //type
    List<ItemEffect> getEffects(); //list of effects
    void setX(int x);
    void setY(int y);
    Integer getX(); //X coordinate
    Integer getY(); //Y coordinate
    Boolean collision(); //collision getter
    void setColor(Color color); //color setter
    Color getColor(); //color getter
    Rectangle solidArea = new Rectangle(0, 0, 8, 8); //solidArea dimensions
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    
}
