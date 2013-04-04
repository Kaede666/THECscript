package com.thec.kapi.util;

import com.thec.api.util.Arrays;
import com.thec.api.util.Random;
import com.thec.api.util.Time;
import com.thec.core.internal.wrappers.Situation;
import com.thec.kapi.methods.Tabs;
import com.thec.kapi.methods.tabs.Skills;
import com.thec.kore.internal.script.Situations;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.methods.widget.Lobby;
import org.powerbot.game.api.methods.widget.Lobby.World;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 *
 * @author William
 */
public enum AntiBan {
    Combat("Combat", new int[]{Skills.ATTACK_INDEX, Skills.CONSTITUTION_INDEX, Skills.STRENGTH_INDEX, Skills.DEFENSE_INDEX}),
    S_Spider("SpiderBot", new int[]{Skills.ATTACK_INDEX, Skills.CONSTITUTION_INDEX, Skills.STRENGTH_INDEX, Skills.DEFENSE_INDEX, Skills.CRAFTING_INDEX, Skills.MAGIC_INDEX}),
    Firemaking("Firemaking", new int[]{Skills.FIREMAKING_INDEX}),
    Empty("Empty", new int[0]);    
    
    private String
            Name;
    private int 
            frequency = 10,
            hoverTimeSmall = 500,
            hoverTimeBig = 1000,
            rotationAngle = 90;
    private int[]
            skillIndicies;
    private boolean
            switchWorlds = false,
            forceMembersWorld = false;
    private long
            lastHopTime,
            worldSwitchTimeSmallMS = -1,
            worldSwitchTimeBigMS = -1;
    private AntiBan(String Name, int[] skillIndicies){
        this.Name = Name;
        this.skillIndicies = skillIndicies; 
        lastHopTime = System.currentTimeMillis();
        
    }

    public void forceMembers(boolean m){
        log(String.format("Setting members world to %s.", Boolean.toString(m)));
        forceMembersWorld = m;
    }

    public boolean checksSkill(int index){
        return Arrays.contains(skillIndicies, index);
    }

    public void setHoverTime(int min, int max){
        if (min > 0 && max > min){
            hoverTimeSmall = min;
            hoverTimeBig = max;
        }else{
            log("Invalid hover time selection");
        }
    }

    public void enableWorldHop(boolean a){
        if (a){
            if (worldSwitchTimeSmallMS == -1 || worldSwitchTimeBigMS == -1){
                log("Please chose time to hop world first");
                return;
            }else{
                switchWorlds = true;
                lastHopTime = System.currentTimeMillis();
            }
        }else{
            switchWorlds = false;
        }
    }

    public void setHopTime(long s, long b){
        setHopTime(s, b, false);
    }
    public void setHopTime(long s, long b, boolean force){
        if (!force){
            if (b < 60000L) {
                log("Time too low. If you want to force this time, use changeHopTime(timeSmall, timeBig, true)");
                return;
            }
            if (s > b){
                log("Min time must be smaller than Max time");
            }
        }
        
        worldSwitchTimeSmallMS = s;
        worldSwitchTimeBigMS = b;
        lastHopTime = System.currentTimeMillis();
    }

    public void setRotationAngle(int angle){
        if (angle < 181){
            rotationAngle = angle;
        }else{
            log("Invalid angle selection");
        }
    }

    public void addSkill(int index){
        String name = Skills.getSkillFromIndex(index);
        if (checksSkill(index)){
            log(String.format("Already checking %s skill.", name));
        }else{
            if (!name.equalsIgnoreCase("Skill not found")){
                log(String.format("Adding %s skill." + name));
                skillIndicies = Arrays.add(skillIndicies, index);
            }else{
                log("Trying to add an unknown skill");
            }
        }
    }

    public void setFrequency(int freq){
        if (freq > 0){
            frequency = freq;
        }else{
            log("Invalid frequency selection.");
        }
    }

    public void hopWorld(){
        if (!Lobby.isOpen()){
            Tabs.LOGOUT.open();
            Time.waitFor(new Situation(){
                @Override
                public boolean accept() {
                    return Tabs.LOGOUT.isOpen();
                }
            });
            WidgetChild a = Widgets.get(182, 2);
            if (!(a.validate() && a.visible())) return;
            a.click(true);
            Time.waitForWidget(906, 0);
        }
        World w = Lobby.getSelectedWorld();
        World[] t;
        if (w.isMembers() || forceMembersWorld) {
            t = Lobby.getWorlds(new Filter(){
                @Override
                public boolean accept(Object t) {
                    return ((World)t).isMembers();
                }
            });
        }else{
            t = Lobby.getWorlds(new Filter(){
                @Override
                public boolean accept(Object t) {
                    return !((World)t).isMembers();
                }
            });
        }
        if (t == null || t.length < 1){
            System.out.println("[SEVERE] No worlds found.");
            return;
        }
        int rnd = Random.nextInt(0, t.length);
        while (t[rnd] == w){
            rnd = Random.nextInt(0, t.length);
        }
        Lobby.enterGame(t[rnd]);
        WidgetChild a = Widgets.get(548, 0);
        WidgetChild b = Widgets.get(906, 258);
        Time.waitFor(Situations.multiWidget(false, a, b));
        if (b.validate() && b.visible()){
            System.out.println("[SEVERE] Tried to log into members world as non members.");
            return;
        }
        lastHopTime = System.currentTimeMillis();
    }

    private void checkHop(){
        if (switchWorlds){
            long d = System.currentTimeMillis();
            if ((d - lastHopTime) >= Random.nextDouble(worldSwitchTimeSmallMS, worldSwitchTimeBigMS)){
                hopWorld();
            }
        }
    }

    public void execute(){
        checkHop();
        if (Random.nextInt(0, frequency) == 0){
            if (Random.nextBoolean()){
                log("Hovering");
                hoverRandomSkill();
            }else{
                log("Turning");
                Camera.setAngle(Camera.getYaw() + Random.nextInt(-rotationAngle, rotationAngle));
            }
        }
    }

    public void hoverRandomSkill(){
        hoverSkill(Random.nextInt(0, skillIndicies.length));
    }

    public void hoverSkill(int index){
        if (!Tabs.STATS.isOpen()){
            Tabs.STATS.open();
            Time.waitFor(new Situation(){
                @Override
                public boolean accept() {
                    return Tabs.STATS.isOpen();
                }
            });
        }
        WidgetChild a = Widgets.get(Skills.SKILL_WIDGET, index);
        if (!Mouse.move(a.getCentralPoint())){
            return;
        }
        Task.sleep(Random.nextInt(hoverTimeSmall, hoverTimeBig));
        Tabs.INVENTORY.open();
        Time.waitFor(new Situation(){
                @Override
                public boolean accept() {
                    return Tabs.INVENTORY.isOpen();
                }
            });
    }

    public void removeSkill(int index){
        String name = Skills.getSkillFromIndex(index);
        if (Arrays.contains(skillIndicies, index)){
            log(String.format("Removing skill %s." + name));
            skillIndicies = Arrays.add(skillIndicies, index);
        }else{
            log(String.format("Not using skill %s.", name));
        }
    }

    private void log(String s){
        System.out.println("[KAEDE-ANTIBAN] - " + s);
    }
}