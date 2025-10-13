/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.items.consumable;

import cavesofchaos.items.effects.ItemEffectType;
import cavesofchaos.items.effects.ItemEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author domniki
 */
//7 uses and replenishes HP by 30
public class MajorHealthPotion extends Potion {

    public MajorHealthPotion() {
        super.usesLeft = 7;
    }

    //method to use item
    @Override
    public List<ItemEffect> use() {
        if (usesLeft <= 0) {
            return Collections.emptyList();
        }
        
        usesLeft--;
        
        ItemEffect effect = new ItemEffect(ItemEffectType.HP_REPLENISH, 30); 
        
        List<ItemEffect> list = new ArrayList<>();
        Collections.addAll(list, effect);
        
        return list;
    }

    @Override
    public String getName() {
        return "Major Health Potion";
    }

    @Override
    public String getDescription() {
        return "Major Health Potion";
    }

    @Override
    public String getType() {
        return "Consumable Potion";
    }

    //method to return list of effects of item
    @Override
    public List<ItemEffect> getEffects() {
        ItemEffect effect = new ItemEffect(ItemEffectType.HP_REPLENISH, 30); 
        
        List<ItemEffect> list = new ArrayList<>();
        Collections.addAll(list, effect);
        
        return list;
    }
    
}
