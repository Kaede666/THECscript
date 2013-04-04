package com.thec.kaede.Dungeon;

import com.thec.api.util.Time;
import com.thec.core.THECscript;
import com.thec.core.internal.wrappers.Situation;
import com.thec.core.unportables.Murfee;
import com.thec.kapi.methods.Tabs;
import com.thec.kapi.methods.node.GroundItems;
import com.thec.kapi.methods.tabs.Inventory;
import com.thec.kapi.util.Filters;
import com.thec.kapi.wrappers.node.GroundItem;
import com.thec.kore.internal.script.Situations;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.util.Random;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Equipment;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 *
 * @author William
 */
public class Dungeon extends THECscript{
    public boolean isStarted = false;
    private int EntranceS = 48496;
    private final int dungeonTextureID = 3028;
    private Filter pickup = Filters.newIDFilter(-1);
    private Room start = null;
    private Room[] rooms = null;
    
    public boolean inDungeon(){
        WidgetChild a = Widgets.get(945, 19);
        return (a.validate() && a.visible());
    }
    public boolean exitDungeon(){
        //Leave dungeon
        return !inDungeon();
    }
    public boolean newDungeon(){
        SceneObject e = SceneEntities.getNearest(EntranceS);
        System.out.println(0);
        if (!e.isOnScreen() && !Walking.walk(e)) return false;
        boolean equipped;
        if (!Inventory.contains(DungItems.ringID)) {
            if (!Equipment.containsOneOf(DungItems.ringID)){
                System.out.println("Could not find ring");
                return false;
            }else{
                equipped = true;
            }
        }else{
            equipped = false;
        }
        WidgetChild a = Tabs.COMBAT_ACADEMY.getWidgetChild();
        System.out.println(1);
        if (!a.validate()) return false;
        if (a.getTextureId() != dungeonTextureID){
            Item t;
            if (equipped){
                System.out.println(2);
                if (!Tabs.EQUIPMENT.open()) return false;
                t = Equipment.getItem(Equipment.Slot.RING);
            }else{
                System.out.println(3);
                if (!Tabs.INVENTORY.open()) return false;
                t = Inventory.getItem(DungItems.ringID);
            }
            System.out.println(4);
            if (t == null) return false;
            t.getWidgetChild().interact("Open party interface", t.getName());
            Time.waitFor(new Situation(){
                @Override
                public boolean accept() {
                    WidgetChild a = Tabs.COMBAT_ACADEMY.getWidgetChild();
                    if (!a.validate()) return false;
                    return (a.getTextureId() == dungeonTextureID);
                }
            });
        }
        System.out.println(5);
        Tabs.PARTY_ORGANISER.open();
        a = Widgets.get(939, 45);
        if (!a.validate()) return false;
        if (a.visible()){
            a.click(true);
            Time.waitForWidget(939, 33);
        }
        System.out.println(7);
        if (!equipped && !Equipment.equip(DungItems.ringID)) return false;
        e.click(true);
        Time.waitFor(Situations.multiWidget(false, Widgets.get(EntranceS, EntranceS)));
        if (!clickWidget(947, 766)) return false;
        System.out.println(8);
        if (!clickWidget(938, 37)) return false;
        System.out.println(9);
        if (!clickWidget(1188, 24)) return false;
        Time.waitForWidget(945, 19);
        return inDungeon();
    }
    
    private boolean clickWidget(int widget, int child){
        Time.waitForWidget(widget, child);
        WidgetChild a = Widgets.get(widget, child);
        if (!a.validate() && !a.visible()) return false;
        return a.click(true);
    }
    
    @Override
    public void onStart(){
        /*if (inDungeon()) {
            if (!exitDungeon()){
                System.out.println("Problems leaving dungeon");
                this.stop();
            }
        }
        if (!newDungeon()){
            System.out.println("Problems entering dungeon");
            this.stop();
        }*/
        isStarted = true;
    }
    
    @Override
    public void onStop(){
        
        isStarted = false;
    }

    @Override
    public int loop() {
        while (!isStarted) Task.sleep(1);
        /*if (!inDungeon() && !newDungeon()) this.stop();
        updateFilter();
        if (start == null) {
            start = new Room();
            pickupTable();
        }*/
        updateFilter();
        pickupTable();
        return Murfee.getReturnTime();
    }
    public void pickupTable() {
        GroundItem[] f = GroundItems.getLoaded(pickup);
        GroundItem x;
        while (isStarted && f != null && !Inventory.isFull()){
            x = GroundItems.getClosest(f);
            System.out.println(f.length);
            if (x != null) {
                if (!x.find()) return;
                String name = x.getGroundItem().getName();
                WidgetChild q = Widgets.get(548,435).getChild(0);
                while (!q.validate() || !q.visible() || !q.getText().contains("Take")){
                    SceneObject y = SceneEntities.getAt(x);
                    Point p;
                    if (y != null){
                        Polygon[] u = y.getModel().getTriangles();
                        int height = u[0].ypoints[0];
                        for (int i = 0; i < u.length; i++){
                            if (u[i].ypoints[0] < height) height = u[i].ypoints[0];
                        }
                        p = x.getModel().getCenterPoint();
                        p.translate(0, (height + Random.nextInt(8, 12)) - p.y);
                    }else{
                        p = x.getModel().getCenterPoint();
                    }
        
                    Mouse.move(p);
                    Task.sleep(Random.nextInt(500, 750));
                    q = Widgets.get(548,435).getChild(0);
                    System.out.println(q.getText());
                }
                if (q.validate() && q.visible() && q.getText().contains("Take")){
                    Mouse.click(false);
                    Task.sleep(Random.nextInt(500, 750));
                    if (Menu.isOpen() && Menu.contains("Take", name)){
                        Menu.clickIndex(Menu.getIndex("Take", name));
                        Time.sleep(Random.nextInt(500, 750));
                        Time.waitForIdle();
                    }
                }
            }
            f = GroundItems.getLoaded(pickup);
        }
    }

    private void updateFilter() {
        int spaceLeft = 28 - Inventory.getCount();
        if (spaceLeft > 20){
            pickup = Filters.newIDFilter(DungItems.Food.getTierAndAbove(0));
        }else if (spaceLeft > 10){
            pickup = Filters.newIDFilter(DungItems.Food.getTierAndAbove(0));
        }else{
            pickup = Filters.newIDFilter(DungItems.Food.getTierAndAbove(0));
        }
    }
    
    @Override
    public void onRepaint(Graphics g){
        /*
        GroundItem[] f = GroundItems.getLoaded(pickup);
        if (f == null) return;
        GroundItem x = GroundItems.getClosest(f);
        if (x == null) return;
        SceneObject y = SceneEntities.getAt(x);
        if (y == null) return;
        g.setColor(Color.blue);
        //
        Polygon[] u = y.getModel().getTriangles();
        int height = u[0].ypoints[0];
        for (int i = 0; i < u.length; i++){
            if (u[i].ypoints[0] < height) height = u[i].ypoints[0];
        }
        
        //
        g.setColor(Color.red);
        Point q = x.getModel().getCenterPoint();
        System.out.println(height + "," + q.y);
        q.translate(0, (height + Random.nextInt(8, 12)) - q.y);
        System.out.println(q);
        g.drawOval(q.x, q.y, 2, 2);
        //x.getModel().drawWireFrame(g);*/
    }
}
