/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import java.util.Random;

/**
 *
 * @author domniki
 */
public class Dice {
    private int dice_num, dice_side;
    
    public Dice(int dice_n, int dice_s) { //constructor
        dice_num = dice_n;
        dice_side = dice_s;
    }
    
    Random ranNum = new Random(); //random obj
    
    public int roll() { //roll without bonus
        
        int roll_outcome = 0;
        
        for (int i=0; i<dice_num; ++i){
            roll_outcome += ranNum.nextInt(1, dice_side+1); //next Int in range [1,dice_side+1)
        }
        return roll_outcome;
    }
    
    public int roll(int bonus) { //roll with bonus
        
        int roll_outcome = 0;
        
        for (int i=0; i<dice_num; ++i){
            roll_outcome += ranNum.nextInt(1, dice_side+1); //next Int in range [1,dice_side+1)
        }
        roll_outcome += bonus; //add bonus
        return roll_outcome;
    }
    
    @Override //Override toString
    public String toString() {
        return this.dice_num + "d" + this.dice_side;
    }
}