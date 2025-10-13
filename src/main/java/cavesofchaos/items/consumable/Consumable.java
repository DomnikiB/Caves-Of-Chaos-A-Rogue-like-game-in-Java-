/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cavesofchaos.items.consumable;

import cavesofchaos.items.Item;
import cavesofchaos.items.effects.ItemEffect;
import java.util.List;

/**
 *
 * @author domniki
 */
public interface Consumable extends Item { //consumable item
    int usesLeft(); //how main uses are left
    List<ItemEffect> use(); //effects to be used to player
}
