package com.thec.kapi.wrappers.node;

import com.thec.api.methods.Walking;
import com.thec.api.util.Time;
import com.thec.core.internal.wrappers.Situation;
import com.thec.kapi.util.Interact;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.node.Item;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


/**
 *
 * @author William
 */
public class GroundItem extends org.powerbot.game.api.wrappers.node.GroundItem{
    public GroundItem(Tile t, Item i){
        super(t, i);
    }
    
    public GroundItem(org.powerbot.game.api.wrappers.node.GroundItem g){
        super(g.getLocation(), g.getGroundItem());
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
    
    @Override
    public boolean isOnScreen(){
        WidgetChild a = Widgets.get(640, 21);
        return (super.isOnScreen() && !(a.visible() && 
                a.contains(getCentralPoint())));
    }
    
    public boolean pickup() {
        if (this == null || Inventory.isFull()) return false;
        final Tile loc = this.getLocation();
        final GroundItem t = this;
        try {
            if (find() && !Inventory.isFull() && Interact.interact(this, "Take")){
                Time.waitFor(new Situation(){
                    @Override
                    public boolean accept() {
                        return (Calculations.distanceTo(loc) == 0.0D || !t.validate());
                    }
                });
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
