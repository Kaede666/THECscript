package com.thec.kapi.methods.interactive;

import com.thec.kapi.util.Filters;
import com.thec.kapi.wrappers.NPC;
import java.util.Arrays;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.util.Filter;

/**
 *
 * @author William
 */
public class NPCs extends org.powerbot.game.api.methods.interactive.NPCs{
    
    public static NPC getClosest(boolean inBattle, NPC[] n){
        int dist = 0;
        if (n.length < 1) return null;
        for(int i = 1; i < n.length; i++){
            if (n[i].getHealthPercent() > 0){
                if (!inBattle && n[i].isInCombat()) continue;
                if((Calculations.distanceTo(n[i]) < Calculations.distanceTo(n[dist])))
                    dist = i;
            }
        }
        return n[dist];
    }
    
    public static NPC getClosest(NPC[] n){
        return getClosest(true, n);
    }
    
    public static NPC getClosest(){
        return getClosest(getLoaded());
    }
    public static NPC getClosest(int... NPC_ID){
        return getClosest(true, NPC_ID);
    }
    public static NPC getClosest(boolean inBattle, int... NPC_ID){
        return getClosest(inBattle, getLoaded(NPC_ID));
    }
    public static NPC[] getLoaded(){
        return (NPC[]) org.powerbot.game.api.methods.interactive.NPCs.getLoaded();
    }
    public static NPC[] getLoaded(int... NPC_ID){
        return getLoaded(Filters.newIDFilter(NPC_ID));
    }
    public static NPC[] getLoaded(Filter<org.powerbot.game.api.wrappers.interactive.NPC> f){
        org.powerbot.game.api.wrappers.interactive.NPC[] g = org.powerbot.game.api.methods.interactive.NPCs.getLoaded(f);
        NPC[] n = new NPC[g.length];
        for (int i = 0; i < n.length; i++){
            n[i] = new NPC(g[i]);
        }
        return n;
    }
}
