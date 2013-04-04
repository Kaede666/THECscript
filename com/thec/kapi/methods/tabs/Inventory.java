package com.thec.kapi.methods.tabs;

import com.thec.kapi.util.Filters;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.node.Item;
/**
 *
 * @author William
 */
public class Inventory extends com.thec.api.methods.tabs.Inventory {
    public static Item getRandomItem(final int... ITEM_ID){
       return getRandomItem(Filters.newIDFilter(ITEM_ID));
    }
    
    public static Item getRandomItem(final Filter f){
        Item[] inv = Inventory.getItems(f);
        if (inv.length < 1) return null;
        int[] index = new int[Inventory.getCount(f)];
        int count = 0;
        for (int i = 0; i < inv.length; i++){
            if (f.accept(inv[i])){
                index[count] = i;
                count++;
            }
        }
        return inv[index[Random.nextInt(0, index.length - 1)]];
    }
    
    public static boolean dropAllBut(final Filter<Item> f){
        Item[] inv = getAllItems(false);
        for (int i = 0; i < inv.length; i++){
            if (inv[i] != null) {
                if (!f.accept(inv[i])) {
                    if (inv[i].getId() == 526){ //Bones
                        inv[i].getWidgetChild().interact("Bury", inv[i].getName());
                    }else{
                        inv[i].getWidgetChild().interact("Drop", inv[i].getName());
                    }
                    Task.sleep(Random.nextInt(400, 800));
                }
            }
        }
        return true;
    }
}
