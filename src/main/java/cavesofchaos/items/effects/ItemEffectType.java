/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package cavesofchaos.items.effects;

/**
 *
 * @author domniki
 */
public enum ItemEffectType {
    NONE(false),
    INT_BONUS(false),
    STR_BONUS(false),
    MP_BONUS(false),
    HP_BONUS(false),
    HP_REPLENISH(true),
    MP_REPLENISH(true);
    
    private final boolean useOrEquip;
    
    private ItemEffectType(boolean useOrEquip) { //constructor
        this.useOrEquip = useOrEquip;
    }
    
    public boolean isUseEffect() { //getter
        return useOrEquip;
    }
}
