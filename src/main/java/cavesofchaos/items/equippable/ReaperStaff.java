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
public class ReaperStaff extends Weapon {
    
    @Override
    public String getName() {
        return "Reaper Staff";
    }

    @Override
    public String getDescription() {
        return "Reaper Staff";
    }

    @Override
    public String getType() {
        return "Equippable";
    }

    @Override
    public List<ItemEffect> getEffects() {
        ItemEffect effectA = new ItemEffect(ItemEffectType.INT_BONUS, 9);
        ItemEffect effectB = new ItemEffect(ItemEffectType.MP_BONUS, 26);
        
        List<ItemEffect> list = new ArrayList<>();
        Collections.addAll(list, effectA, effectB);
        
        return list;
    }
}
