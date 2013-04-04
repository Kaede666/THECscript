package com.thec.kapi.wrappers;

import com.thec.api.methods.Walking;
import com.thec.api.util.Time;
import com.thec.core.internal.wrappers.Situation;
import com.thec.kapi.util.Interact;
import java.awt.Point;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.client.RSNPC;

/**
 *
 * @author William
 */
public class NPC extends com.thec.api.wrappers.NPC{
    
    public NPC(RSNPC r){
        super(r);
    }
    public NPC(com.thec.api.wrappers.NPC r){
        super(r.get());
    }
    public NPC(org.powerbot.game.api.wrappers.interactive.NPC r){
        super(r.get());
    }
    
    @Override
    public boolean isOnScreen(){
        if (this == null) return false;
        WidgetChild a = Widgets.get(640, 4);
        WidgetChild b = Widgets.get(548, 5);
        Point us = getCentralPoint();
        if (us == null) return false;
        return (b.contains(us) && 
                !(a.visible() && 
                a.contains(us)));
    }
    
    public boolean find(){
        if (this == null) return false;
        if (isOnScreen()){
            return true;
        }else{
            Camera.turnTo(this);
            if ((!isOnScreen() || Calculations.distanceTo(this) > 15) && !(Calculations.distanceTo(this) == 0.0D)) {
                Walking.sleepwalk(this);
            }
        }
        return isOnScreen() || (Calculations.distanceTo(this) == 0.0D);
    }
    
    public boolean isAlive(){
        return (validate() && (getHealthRatio() > 0));
    }
  
    public boolean attack(){
        if (this == null) return false;
        if (this == null || !find() || !(getHealthRatio() > 0)) return false;
        if (!Interact.interact(this, "Attack")) return false;
        final NPC n = this;
        Time.waitFor(new Situation(){
            @Override
            public boolean accept() {
                return !n.isAlive();
            }
        });
        return true;
    }
}
