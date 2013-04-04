package com.thec.kapi.util;

import com.thec.api.util.Arrays;
import com.thec.kapi.wrappers.NPC;
import com.thec.kapi.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 *
 * @author William
 */
public class Filters{
    public static org.powerbot.game.api.util.Filter newIDFilter(final int... q){
        if (q == null || q.length < 1) return null;
        return new org.powerbot.game.api.util.Filter(){
            @Override
            public boolean accept(Object t) {
                if (t == null) return false;
                int id;
                if (t instanceof GroundItem || t instanceof org.powerbot.game.api.wrappers.node.GroundItem){
                    id = new GroundItem((org.powerbot.game.api.wrappers.node.GroundItem)t).getGroundItem().getId();
                }else if (t instanceof Item){
                    id = ((Item)t).getId();
                }else if (t instanceof NPC || t instanceof org.powerbot.game.api.wrappers.interactive.NPC){
                    id = new NPC((org.powerbot.game.api.wrappers.interactive.NPC)t).getId();
                }else if (t instanceof SceneObject){
                    id = ((SceneObject)t).getId();
                }else{
                    return false;
                }
                return Arrays.contains(q, id);
            }
        };
    }
    public static org.powerbot.game.api.util.Filter newSpaceFilter(final Area a){
        return new org.powerbot.game.api.util.Filter(){
            @Override
            public boolean accept(Object t) {
                if (t == null) return false;
                Tile loc;
                if (t instanceof GroundItem || t instanceof org.powerbot.game.api.wrappers.node.GroundItem){
                    loc = new GroundItem((org.powerbot.game.api.wrappers.node.GroundItem)t).getLocation();
                }else if (t instanceof NPC || t instanceof org.powerbot.game.api.wrappers.interactive.NPC){
                    loc = new NPC((org.powerbot.game.api.wrappers.interactive.NPC)t).getLocation();
                }else if (t instanceof SceneObject){
                    loc = ((SceneObject)t).getLocation();
                }else{
                    return false;
                }
                return a.contains(loc);
            }
        };
    }
    public static org.powerbot.game.api.util.Filter newOnScreenFilter(final boolean b){
        return new org.powerbot.game.api.util.Filter(){
            @Override
            public boolean accept(Object t) {
                if (t == null) return false;
                if (t instanceof GroundItem || t instanceof org.powerbot.game.api.wrappers.node.GroundItem){
                    if (b){
                        return new GroundItem((org.powerbot.game.api.wrappers.node.GroundItem)t).isOnScreen();
                    }else{
                        return !new GroundItem((org.powerbot.game.api.wrappers.node.GroundItem)t).isOnScreen();
                    }
                }else if (t instanceof NPC || t instanceof org.powerbot.game.api.wrappers.interactive.NPC){
                    if (b){
                        return new NPC((org.powerbot.game.api.wrappers.interactive.NPC)t).isOnScreen();
                    }else{
                        return !new NPC((org.powerbot.game.api.wrappers.interactive.NPC)t).isOnScreen();
                    }
                }else if (t instanceof SceneObject){
                    if (b){
                        return ((SceneObject)t).isOnScreen();
                    }else{
                        return !((SceneObject)t).isOnScreen();
                    }
                }else{
                    return false;
                }
            }
        };
    }
    public static org.powerbot.game.api.util.Filter mergeFilters(final boolean AND, final org.powerbot.game.api.util.Filter... f){
        return new org.powerbot.game.api.util.Filter(){
            @Override
            public boolean accept(Object t) {
                if (AND){
                    for (int i = 0; i < f.length; i++){
                        if (!f[i].accept(t)) return false;
                    }
                }else{
                    for (int i = 0; i < f.length; i++){
                        if (f[i].accept(t)) return true;
                    }
                }
                return AND;
            }
        };
    }
}
