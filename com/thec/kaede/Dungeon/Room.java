package com.thec.kaede.Dungeon;

import com.thec.api.util.Arrays;
import com.thec.kapi.util.Filters;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 *
 * @author William
 */
public class Room {
    private static Tile[] usedTiles = new Tile[]{}; 
    private Tile[] doors;
    public Room(){
        SceneObject[] a = SceneEntities.getLoaded(Filters.newIDFilter(Doors.DOOR_IDS));
        Tile[] foundDoors = new Tile[a.length];
        int count = 0;
        for (int i = 0; i < a.length; i++){
            Tile loc = a[i].getLocation();
            boolean b = false;
            for (int j = 0; j < usedTiles.length; j++){
                if (loc.equals(usedTiles[j])) {
                    b = true;
                    break;
                }
            }
            if (!b){
                foundDoors[count] = loc;
                count++;
            }
        }
        if (count == 0){
            System.out.println("No doors found!");
            return;
        }
        doors = new Tile[count];
        
        System.arraycopy(foundDoors, 0, doors, 0, count);
        Tile[] newT = new Tile[usedTiles.length + count];
        System.arraycopy(doors, 0, newT, usedTiles.length, count - 1);
        usedTiles = newT;
        
        for (int i = 0; i < doors.length; i++){
            System.out.println(doors[i].getLocation());
        }
    }
}
