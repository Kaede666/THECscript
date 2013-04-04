package com.thec.kapi.methods.node;

import com.thec.kapi.methods.tabs.Inventory;
import com.thec.kapi.util.Filters;
import com.thec.kapi.wrappers.node.GroundItem;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.util.Random;

/**
 *
 * @author William
 */
public class GroundItems extends org.powerbot.game.api.methods.node.GroundItems {
    public static GroundItem getRandom(GroundItem[] g){
        if (g == null || g.length < 1) return null;
        return g[Random.nextInt(0, g.length - 1)];
    }
    
    public static GroundItem[] getLoaded(Filter<org.powerbot.game.api.wrappers.node.GroundItem> f){
        org.powerbot.game.api.wrappers.node.GroundItem[] g = org.powerbot.game.api.methods.node.GroundItems.getLoaded(f);
        if (g == null) return null;
        GroundItem[] n = new GroundItem[g.length];
        for (int i = 0; i < n.length; i++){
            n[i] = new GroundItem(g[i]);
        }
        return n;
    }
    
    public static GroundItem[] getLoaded(){
        return getLoaded(new Filter(){
            @Override
            public boolean accept(Object t) {
                return true;
            }
        });
    }
    
    public static GroundItem getClosest(Filter<org.powerbot.game.api.wrappers.node.GroundItem> f){
        return getClosest(getLoaded(f));
    }
    
    public static GroundItem getClosest(){
        return getClosest(getLoaded());
    }
    
    public static GroundItem getClosest(GroundItem[] ground){
        if(ground == null || ground.length < 1){
            return null;
        }else if (ground.length == 1){
            return ground[0];
        }
        int minlength = 0;
        if (!(Calculations.distanceTo(ground[minlength]) == 0)){
            for (int i = 1; i < ground.length; i++){
                if (Calculations.distanceTo(ground[i]) == 0){
                    minlength = i;
                    break;
                }
                if (Calculations.distanceTo(ground[i]) < Calculations.distanceTo(ground[minlength])) minlength = i;
            }
        }
        return ground[minlength];
    }
}