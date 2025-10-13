/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.items.equippable;

import cavesofchaos.items.effects.ItemEffect;
import cavesofchaos.items.effects.ItemEffectType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author domniki
 */
public class AncientNobleSword extends Weapon {
    
    @Override
    public String getName() {
        return "Ancient Noble Sword";
    }

    @Override
    public String getDescription() {
        return "Ancient Noble Sword";
    }

    @Override
    public String getType() {
        return "Equippable";
    }

    @Override
    public List<ItemEffect> getEffects() {
        ItemEffect effectA = new ItemEffect(ItemEffectType.HP_BONUS, 8);
        ItemEffect effectB = new ItemEffect(ItemEffectType.STR_BONUS, 7);
        
        List<ItemEffect> list = new ArrayList<>();
        Collections.addAll(list, effectA, effectB);
        
        return list;
    }

}
