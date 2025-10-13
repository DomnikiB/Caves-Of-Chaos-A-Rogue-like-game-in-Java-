/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.entity;
import java.awt.Rectangle;

/**
 *
 * @author domniki
 */
public abstract class AbstractEntity {   
    
    public int x, y; //player's coordinates
    public int speed; //player's speed
    public String direction; //direction of next move
    public Rectangle solidArea; //solid area for collision
    public int solidAreaDefaultX, solidAreaDefaultY; 
    public boolean collisionOn = false; //collision flag
    public boolean inMap = true; //map's borders flag
    public boolean startFlag = false; //start flag
    public boolean stairsFlag = false; //stairs flag
    
}
