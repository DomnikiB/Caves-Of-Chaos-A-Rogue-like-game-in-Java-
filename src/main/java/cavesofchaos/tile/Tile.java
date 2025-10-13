/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.tile;

import java.awt.Color;

/**
 *
 * @author domniki
 */
public class Tile {  
    public enum Visibility {
        UNKNOWN, FOGGED, VISIBLE
    }
    private boolean collision = false;
    private final String type;
    private final Color baseColor;
    private Visibility visibility;

    //constructor
    public Tile(String type) {
        this.collision = collisionType(type);
        this.type = type;
        this.baseColor = chooseColor(type);
        this.visibility = Visibility.UNKNOWN;
    }
    
    //method to assign color to each tile based on type
    private Color chooseColor(String type) {
        return switch (type) {
            case "wall" -> new Color(128, 128, 128); //grey
            case "floor" -> new Color(0, 0, 153); //dark blue
            case "start" -> Color.GREEN;
            case "stairs" -> Color.RED;
            default -> Color.GRAY;
        };
    }
    
    //method to change collision status
    public boolean collisionType(String type) {
        return switch (type) {
            case "floor" , "start", "stairs" -> false;
            case "wall" -> true;
            default -> true;
        };
    }
    
    //method to assign color to each tile based on visibility status
    public Color getRenderColor() {
        return switch (visibility) {
            case UNKNOWN -> new Color(32, 32, 32); //dark grey
            case FOGGED -> baseColor.darker().darker();
            case VISIBLE -> baseColor;
        };
    }
    
    //getters
    public String getType() {
        return type;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean getCollision() {
        return collision;
    }
    
    //setters
    public void setVisibility(Visibility v) {
        this.visibility = v;
    }
       
}
