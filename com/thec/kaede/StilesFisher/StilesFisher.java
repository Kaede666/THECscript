package com.thec.kaede.StilesFisher;

import com.thec.api.util.Random;
import com.thec.api.util.Time;
import com.thec.kapi.methods.interactive.NPCs;
import com.thec.kapi.methods.tabs.Inventory;
import com.thec.kapi.util.skills.Fishing;
import com.thec.kapi.wrappers.NPC;
import com.thec.kapi.wrappers.map.TilePath;
import com.thec.kore.internal.script.Situations;
import java.awt.Graphics;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.wrappers.Tile;

/**
 *
 * @author William
 */
public class StilesFisher extends ActiveScript implements PaintListener{
    private Fishing.Spots r = Fishing.Spots.Harpoon;
    //ItemTracker t[];
    
    String[] left, right;
    
    private final int STILES = 11267;
    private NPC Stiles;
    private final TilePath Path = new TilePath(new Tile[]{ 
            new Tile(2852, 3143, 0), //Stiles
            new Tile(2861, 3147, 0),
            new Tile(2871, 3150, 0),
            new Tile(2876, 3155, 0),
            new Tile(2883, 3160, 0),
            new Tile(2889, 3164, 0),
            new Tile(2992, 3169, 0),
            new Tile(2899, 3169, 0),
            new Tile(2902, 3172, 0),
            new Tile(2909, 3172, 0),
            new Tile(2918, 3173, 0),
            new Tile(2924, 3177, 0)}); //Docks

    @Override
    public void onStart(){
        Environment.enableRandom(org.powerbot.core.randoms.SpinTickets.class, false);
        Environment.enableRandom(com.thec.kore.internal.script.randoms.SkillsGuide.class, true);
        int length = r.fishID.length;
        System.out.println(length);
        //t = new ItemTracker[length];
        left = new String[length + 1];
        right = new String[length + 1];
        left[0] = "Stiles Fisher by";
        right[0] = "Kaede666";
        for(int i = 0; i < length; i++){
            System.out.println(i);
            //t[i] = new ItemTracker(new int[]{r.fishID[i]});
            //t[i].initialize();
            left[i + 1] = r.fishNames[i] + "'s caught";
        }
    }
    
    
    @Override
    public int loop() {
       if (!Inventory.isFull()){
           if (Path.getEnd().distanceTo() < 8){
                if (r.getNew()){
                    fish();
                }else{
                    return Random.nextInt(250, 500);
                }
           }else{
                Path.traverse();
                return Random.nextInt(250, 500);
           }
        }else{
            while (Path.getStart().distanceTo() > 8){
                if (!Path.reverse().traverse()) return Random.nextInt(250, 500);
            }
            Stiles = NPCs.getClosest(STILES);
            if (Stiles == null) {
                if (Path.getStart().distanceTo() < 9) {
                    Walking.walk(Path.getStart());
                    Stiles = NPCs.getClosest(STILES);
                    if (Stiles == null) return Random.nextInt(250, 500);
                }
            }
            if(Inventory.contains(r.fishID)){
                Stiles.interact("Exchange");
                Time.waitFor(Situations.npcChat("Stiles"));
            }
        }
       return Random.nextInt(750, 1000);
    }
    
    public void fish(){
        while (!Inventory.isFull() && Path.getEnd().distanceTo() < 8){
            r.fish();
        }
    }
    
    @Override
    public void onRepaint(Graphics g){
        
    }
    
}
