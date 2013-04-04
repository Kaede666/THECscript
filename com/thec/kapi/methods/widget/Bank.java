package com.thec.kapi.methods.widget;

import com.thec.kapi.util.Arrays;
import com.thec.kapi.methods.interactive.NPCs;
import com.thec.kapi.util.Filters;
import com.thec.kapi.util.Interact;
import com.thec.kapi.wrappers.NPC;

/**
 *
 * @author William
 */
public class Bank extends org.powerbot.game.api.methods.widget.Bank{
    
    public static boolean open() {
        NPC[] n = NPCs.getLoaded(Filters.newIDFilter(
            Arrays.merge(BANK_NPC_IDS, BANK_BOOTH_IDS, BANK_COUNTER_IDS,
            BANK_CHEST_IDS)));
        if (n == null || n.length < 1) return false;
        NPC b = NPCs.getClosest(n);
        if (b == null) return false;
        int ID = b.getId();
        if (Arrays.contains(BANK_NPC_IDS, ID) ||
                Arrays.contains(BANK_BOOTH_IDS, ID) ||
                Arrays.contains(BANK_COUNTER_IDS, ID)){
            return Interact.interact(b, "Bank");
        }else if (Arrays.contains(BANK_CHEST_IDS, ID)){
           return Interact.interact(b, "Open");
        }
        return false;
    }
}
