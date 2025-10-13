/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.items.effects;

/**
 *
 * @author domniki
 */
public class ItemEffect {
    private final ItemEffectType effect;
    private final int amount;
    
    public ItemEffect(ItemEffectType effect, int amount) { //constructor
        this.effect = effect;
        this.amount = amount;
    }
    
    public ItemEffectType getEffect() { //getters
        return effect;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public boolean getUseEffect() {
        return effect.isUseEffect();
    }
    
    @Override
    public String toString() {
        return ("Effect : " + effect.toString() + "\nAmount : " + amount);
    }
}
