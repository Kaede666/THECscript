package com.thec.kaede.spider;

import com.thec.api.methods.widgets.AbilityBar;
import com.thec.api.util.Random;
import com.thec.api.util.Time;
import com.thec.core.Overlord;
import com.thec.core.THECscript;
import com.thec.core.internal.script.statistics.ItemTracker;
import com.thec.core.internal.script.statistics.Statistician;
import com.thec.core.ui.THECPaint;
import com.thec.core.unportables.Murfee;
import com.thec.kapi.methods.interactive.NPCs;
import com.thec.kapi.methods.node.GroundItems;
import com.thec.kapi.methods.tabs.Inventory;
import com.thec.kapi.util.AntiBan;
import com.thec.kapi.util.Filters;
import com.thec.kapi.wrappers.NPC;
import com.thec.kapi.wrappers.node.GroundItem;
import com.thec.kore.internal.script.Situations;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Environment;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

@Manifest(
        authors = { "Kaede666" }, 
        name = "Lumbridge Spider Murderer", 
        description = "Money making.", 
        version = 0.6)
public class Spider extends THECscript{
    //Class-wide variables:
    private AntiBan unban = AntiBan.S_Spider;
    private Area space = new Area(new Tile[]{
        new Tile(3976, 5551, 0), new Tile(3976, 5553, 0),
        new Tile(3979, 5551, 0), new Tile(3979, 5553, 0),
        new Tile(3981, 5549, 0), new Tile(3983, 5550, 0),
        new Tile(3984, 5550, 0), new Tile(3985, 5551, 0),
        new Tile(3986, 5551, 0), new Tile(3987, 5550, 0),
        new Tile(3988, 5550, 0), new Tile(3989, 5547, 0),
        new Tile(3990, 5550, 0), new Tile(3993, 5550, 0),
        new Tile(3995, 5552, 0), new Tile(3995, 5554, 0),
        new Tile(3993, 5556, 0), new Tile(3990, 5556, 0),
        new Tile(3989, 5557, 0), new Tile(3984, 5557, 0),
        new Tile(3982, 5556, 0), new Tile(3979, 5556, 0),
        new Tile(3979, 5553, 0), });
    private final int
            COINS=995, SPIDER_SILK=25547, THREAD=1734, FIRE_RUNES=554, NATURE_RUNES=561,
            HEARTS=27365, ROBE_TOP=25837, WIDGET_ROBE=16, WIDGET_ROBE_NAME=56,
            WIDGET_NEEDLE=1179, WIDGET_NEEDLE_CHILD=16, WIDGET_CRAFT=1371,
            WIDGET_CRAFT_SELECTION=44, WIDGET_CRAFT2=1370, WIDGET_CRAFT2_CHILD=38,
            SPIDER_ID=7914, SPIN_TICKET=24154, ALCH=0, CRAFT=1, KILL=2, DROP=3,
            PICKUP=4, TICKET=5, EMPTY=6, ERROR=-1;
    private final String ROBE_TOP_NAME = "Spider silk robe top";
    private boolean isStarted = false;
    private final Filter itemfil = Filters.newIDFilter(SPIDER_SILK, THREAD, FIRE_RUNES,
        HEARTS, ROBE_TOP, SPIN_TICKET, COINS, NATURE_RUNES);
    private final Filter spiderSilk = Filters.newIDFilter(SPIDER_SILK);
    private final Filter isInSpace = Filters.newSpaceFilter(space);
    private final Filter isOnScreen = Filters.newOnScreenFilter(true);
    //End Class-wide variables;
    
    @Override
    public void onStart(){
        if (!Game.isLoggedIn()) this.stop();
        Environment.enableRandom(org.powerbot.core.randoms.SpinTickets.class, false);
        Environment.enableRandom(com.thec.kore.internal.script.randoms.SkillsGuide.class, true);
        Statistician.initialize(Skills.ATTACK, Skills.CRAFTING, Skills.DEFENSE, Skills.MAGIC, Skills.STRENGTH);
        Overlord.itemTracker = new ItemTracker(NATURE_RUNES);
        THECPaint.detail = "Alches done";
        unban.setFrequency(32);
        isStarted = true;
    }   
    
    @Override
    public void onStop(){
        
    }
    
    @Override
    public int loop(){
        while (!isStarted) Task.sleep(1);
        switch(checkLoc()){
            case EMPTY: Game.logout(true); this.stop(); break;
            case TICKET: Inventory.getItem(SPIN_TICKET).getWidgetChild()
                    .interact("Claim", Inventory.getItem(SPIN_TICKET).getName());
                break;
            case ALCH: alchRobe(); break;
            case CRAFT: craftRobe(); break;
            case DROP: Inventory.dropAllBut(itemfil); break;
            case PICKUP: checkBattle(); pickup(); break;
            case KILL: battle(); break;
            case ERROR: System.out.println("Encountered an error."); break;
        }
        unban.execute();
        return (Murfee.getReturnTime() / 2);
    }
    
    public void checkBattle(){
        try {
            GroundItem[] g = GroundItems.getLoaded(Filters.mergeFilters(true, spiderSilk, isInSpace));
            GroundItem[] f = GroundItems.getLoaded(Filters.mergeFilters(true, spiderSilk, isInSpace, isOnScreen));
            int rand = 0, space = 28 - Inventory.getCount();
            if (f == null || f.length < 1) battle();
            if (g == null || g.length < 1){
                g = new GroundItem[1];
            }else{
                if (f.length >= space) return;
                if (g.length < 2) battle();
            }
            while((g.length <= (space) && rand < Random.nextInt(6, 9)) && (g.length < Random.nextInt(6, 9))){
                battle();
                rand++;
                g = GroundItems.getLoaded(Filters.mergeFilters(true, spiderSilk, isInSpace));
                space = 28 - Inventory.getCount();
                if (g.length > space) return;
            }
        }catch(NullPointerException ex) {}
    }
    
    public void battle(){
        NPC k = NPCs.getClosest(false, SPIDER_ID);
        if (!k.find()) return;
        k.attack();
    }
    
    public void pickup() throws NullPointerException {
        GroundItem[] f = GroundItems.getLoaded(Filters.mergeFilters(true, itemfil, isInSpace, isOnScreen));
        GroundItem x;
        while (f != null && f.length > 0 && !Inventory.isFull()){
            x = GroundItems.getClosest(f);
            if (x != null) x.pickup();
            if (Inventory.isFull()) return;
            f = GroundItems.getLoaded(Filters.mergeFilters(true, itemfil, isInSpace, isOnScreen));
        }
        f = GroundItems.getLoaded(Filters.mergeFilters(true, itemfil, isInSpace));
        if (!Inventory.isFull() && f != null && f.length > 0){
            x = GroundItems.getRandom(f);
            if (x != null && x.find()) pickup();
        }
    }
    
    public int checkLoc(){
        if (!Inventory.contains(NATURE_RUNES) || !Inventory.contains(THREAD)
                || Inventory.getCount(true, FIRE_RUNES) < 5) return EMPTY;
        if (Inventory.contains(SPIN_TICKET)) return TICKET;
        if (Inventory.getCount(itemfil) != Inventory.getCount()) return DROP;
        if (Inventory.isFull() && Inventory.getCount(SPIDER_SILK) >= 3) return CRAFT;
        if (Inventory.contains(ROBE_TOP)) return ALCH;
        if (!Inventory.isFull() && GroundItems.getLoaded(Filters.mergeFilters(true, itemfil, isInSpace)) != null) return PICKUP;
        if (NPCs.getNearest(SPIDER_ID) != null) return KILL;
        return ERROR;
    }
    
    private void alchRobe(){
        try {
            if (!Inventory.contains(NATURE_RUNES)) return;
            if (!AbilityBar.open()) return;
            WidgetChild b;
            b = Inventory.getLastItem(ROBE_TOP).getWidgetChild();
            if (!b.contains(Mouse.getLocation())) b.hover();
            while (Inventory.contains(ROBE_TOP)){
                b = Inventory.getLastItem(ROBE_TOP).getWidgetChild();
                if (!Inventory.contains(NATURE_RUNES)) return;
                if (!Widgets.get(548, 435).getChild(0).getText().contains("Cast")) {
                    Keyboard.sendKey('9');
                    //Alch must be bound to 9 right now.
                    Task.sleep(Random.nextInt(200,400));
                    if (b == null) {
                        b = Inventory.getItem(ROBE_TOP).getWidgetChild();
                    }
                    b.click(true);
                }else{
                    if (b == null) {
                        b = Inventory.getItem(ROBE_TOP).getWidgetChild();
                    }
                    b.click(true);
                }
                Time.waitForIdle();
                Task.sleep(Random.nextInt(700,900));
            }
        }catch(NullPointerException ex){
            return;
        }
    }
    
    private void craftRobe() throws NullPointerException{
        try {
            Time.waitForIdle();
            WidgetChild needle = Widgets.get(WIDGET_NEEDLE, WIDGET_NEEDLE_CHILD);
            Widget Left = Widgets.get(WIDGET_CRAFT);
            Widget Right = Widgets.get(WIDGET_CRAFT2);
            if (Left.validate() && Right.validate()) {
                if (!Right.getChild(WIDGET_ROBE_NAME).getText().equalsIgnoreCase(ROBE_TOP_NAME)) {
                    Left.getChild(WIDGET_CRAFT_SELECTION).getChild(WIDGET_ROBE).click(true);
                    Task.sleep(Random.nextInt(400, 690));
                    return;
                }
                Right.getChild(WIDGET_CRAFT2_CHILD).click(true);
                Time.finishProduction();
                return;
            }else if (needle.validate()){
                needle.click(true);
                Time.waitForProduction();
                return;
            }else{
                if ((Left.getChild(WIDGET_CRAFT_SELECTION).visible() || Right.getChild(WIDGET_CRAFT2_CHILD).visible()) || needle.visible()) return;
                Inventory.getRandomItem(SPIDER_SILK).getWidgetChild().interact("Craft");
                Time.waitFor(Situations.multiWidget(false, needle, Left.getChild(WIDGET_CRAFT_SELECTION), Right.getChild(WIDGET_CRAFT2_CHILD)));
                return;
            }
        }catch(NullPointerException ex){
        }
    }
}