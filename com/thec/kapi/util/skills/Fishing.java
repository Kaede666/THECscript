/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thec.kapi.util.skills;

import com.thec.kapi.methods.interactive.NPCs;
import com.thec.kapi.methods.tabs.Inventory;
import com.thec.kapi.util.Interact;
import org.powerbot.game.api.util.Time;
import com.thec.kapi.wrappers.NPC;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Random;

/**
 *
 * @author William
 */
public class Fishing {
    public static final int
            LOBSTER_ID=0,
            TUNA_ID=359,
            SWORDFISH_ID=371;
    public static String getName(int id){
        switch (id){
            case LOBSTER_ID:    
                return "Lobster";
            case TUNA_ID:    
                return "Tuna";
            case SWORDFISH_ID:    
                return "Swordfish";
        }
        return "Error";
    }
    public static final String[] Names = {
        
    };
    public enum Spots {
        Net(323, "Net", 0, new int[0]),
        Bait(323, "Bait", 1, new int[0]),
        Cage(324, "Cage", 0, new int[] {LOBSTER_ID}),
        Harpoon(324, "Harpoon", 1, new int[] {TUNA_ID, SWORDFISH_ID});
        
        public NPC n;
        public int ID, index;
        public int[] fishID;
        public String[] fishNames;
        public String interact;
        
        private Spots(int ID, String interact, int index, int[] fishID){
            this.ID = ID;
            this.interact = interact;
            this.index = index;
            this.fishID = fishID;
            fishNames = new String[fishID.length];
            for (int i = 0; i < fishID.length; i++){
                fishNames[i] = getName(fishID[i]);
            }
            n = getnearest();
        }
        public boolean getNew(){
            n = getnearest();
            return !(n == null);
        }
        public boolean fish(){
            if (n == null) getNew();
            if (n == null || !n.find() || Inventory.isFull() || !Fishing.fish(n, interact)) return false;
            int counter = 0;
            while (n != null && n.find() && !Inventory.isFull()) {
                if (Players.getLocal().isIdle()) {
                    counter++;
                }else{
                    counter = 0;
                }
                if (counter > 4) return false;
                Task.sleep(Random.nextInt(200, 550));
            }
            return true;
        }
        public NPC getnearest(){
            return NPCs.getClosest(ID);
        }
    }
    
    public static boolean fish(NPC n, String type){
        if (n == null || !n.find()) return false;
        return Interact.start(type, n);
    }
}
