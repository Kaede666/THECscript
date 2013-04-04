package com.thec.kapi.wrappers.map;

import com.thec.api.methods.Walking;
import com.thec.api.util.Time;
import com.thec.kore.internal.script.Situations;
import org.powerbot.game.api.wrappers.Tile;

/**
 *
 * @author William
 */
public class TilePath extends org.powerbot.game.api.wrappers.map.TilePath{
    public TilePath(org.powerbot.game.api.wrappers.Tile[] t){
        super(t);
    }
    @Override
    public boolean traverse(){
        System.out.println(getEnd().distanceTo());
        while (getEnd().distanceTo() > 12){
            Tile dest = getFuthestOnScreen();
            if (dest == null) return false;
            Walking.walk(getFuthestOnScreen().randomize(2, 2));
            Time.waitFor(Situations.isMoving);
            Time.waitFor(Situations.destinationIsOnScreen);
        }
        return false;
    }
    
    @Override
    public TilePath reverse(){
        Tile[] q = new Tile[tiles.length];
        for (int i = 0; i < orig.length; i++){
            q[i] = tiles[tiles.length - i - 1];
        }
        return new TilePath(q);
    }
    
    public Tile getFuthestOnScreen(){
        for (int i = tiles.length - 1; i >= 0; i--){
            if (tiles[i].isOnMap()){
                System.out.println("Found tile at:" + tiles[i].getLocation().toString());
                return tiles[i];
            }
        }
        System.out.println("Cannot find a tile!");
        return null;
    }
    
    public int getCurrIndex(){
        int index = 0;
        if (tiles == null) return -1;
        for (int i = 1; i < tiles.length; i++){
            if (tiles[i].distanceTo() < tiles[index].distanceTo()) 
                index = i;
        }
        return index;
    }
    
    public Tile getCurr(){
        if (tiles == null) return null;
        return tiles[getCurrIndex()];
    }
    
    @Override
    public Tile getNext(){
        int next = getCurrIndex() + 1;
        if (next == 0 || tiles == null || tiles.length < next) return null;
        return tiles[next];
    }
}