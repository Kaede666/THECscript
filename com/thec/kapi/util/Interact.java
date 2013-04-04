package com.thec.kapi.util;

import com.thec.kapi.wrappers.NPC;
import com.thec.kapi.wrappers.node.GroundItem;
import org.powerbot.core.script.job.Task;
import org.powerbot.core.script.methods.Players;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.node.Menu;
import org.powerbot.game.api.wrappers.node.SceneObject;

/**
 *
 * @author Kaede666
 */
public class Interact{
    public static final String
            MOVE_MOUSE = "MoveMouse",
            CLICK_MOUSE_LEFT = "ClickMouseT",
            CLICK_MOUSE_RIGHT = "ClickMouseF";
    
    public static boolean interact(Object t, String... s){
        if (t == null) return false;
        if (!start(MOVE_MOUSE, t)) return false;
        for (int i = 0; i < s.length; i++){
            if (Menu.contains(s[i])) return start(s[i], t);
        }
        return false;
    }
    
    public static boolean interact(Object t, String s){
        return start(s, t);
    }
    
    private static boolean start(String s, Object t){
        if (t == null) return false;
        Interact_Thread interact = new Interact_Thread();
        if (t instanceof NPC || t instanceof GroundItem || t instanceof SceneObject){
            interact.start(s, t);
        }else{
            return false;
        }
        
        if (t instanceof NPC){
            NPC n = (NPC)t;
            if (n == null) return false;
            while(((n.isOnScreen() || Players.getLocal().isMoving()) && !interact.isAlive())) Task.sleep(1);
            if (n == null) return false;
            while((n.isOnScreen() || Players.getLocal().isMoving()) && interact.isAlive()) Task.sleep(1);
            if (!n.isOnScreen()) {
                System.out.println(String.format("Interaction with NPC %s failed", n.getName()));
                interact.stop();
                return false;
            }else{
                return true;
            }
        }else if (t instanceof GroundItem){
            GroundItem n = (GroundItem)t;
            if (n == null) return false;
            while(((n.isOnScreen() || Players.getLocal().isMoving()) && !interact.isAlive())) Task.sleep(1);
            if (n == null) return false;
            while((n.isOnScreen() || Players.getLocal().isMoving()) && interact.isAlive()) Task.sleep(1);
            if (!n.isOnScreen()) {
                System.out.println(String.format("Interaction with GroundItem %s failed", n.getGroundItem().getName()));
                interact.stop();
                return false;
            }else{
                return true;
            }
        }else if (t instanceof SceneObject){
            SceneObject n = (SceneObject)t;
            if (n == null) return false;
            while(((n.isOnScreen() || Players.getLocal().isMoving()) && !interact.isAlive())) Task.sleep(1);
            if (n == null) return false;
            while((n.isOnScreen() || Players.getLocal().isMoving()) && interact.isAlive()) Task.sleep(1);
            if (!n.isOnScreen()) {
                System.out.println(String.format("Interaction with SceneObject %s failed", n.getDefinition().getName()));
                interact.stop();
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        } 
    }
}

class Interact_Thread extends Thread {
    private final int
            NPC_INDEX = 0,
            GROUND_ITEM = 1,
            SCENE_OBJECT = 2,
            ERROR = -1;
    String interact = null;
    Object t = null;
    int index;
    public void start(String s, Object t){
        if (t == null) return;
        if (t instanceof NPC){
            index = NPC_INDEX;
        }else if (t instanceof GroundItem){
            index = GROUND_ITEM;
        }else if (t instanceof SceneObject){
            index = SCENE_OBJECT;
        }else{
            index = ERROR;
        }
        interact = s;
        this.t = t;
        start();
    }
    @Override
    public void run(){
        if (interact == null) this.interrupt();
        if (t == null) return;
        switch(interact){
            case Interact.MOVE_MOUSE:
                movemouse();
                break;
            case Interact.CLICK_MOUSE_LEFT:
                clickmouse(true);
                break;
            default:
                inter();
                break;
        }
    }
    
    private void clickmouse(boolean b){
        switch(index){
            case NPC_INDEX:
                ((NPC)t).click(b);
                break;
            case GROUND_ITEM:
                ((GroundItem)t).click(b);
                break;
            case SCENE_OBJECT:
                ((SceneObject)t).click(b);
                break;
            case ERROR:
                System.out.println("Error in interaction");
                break;
        }
    }
    
    private void movemouse(){
        switch(index){
            case NPC_INDEX:
                Mouse.move(((NPC)t).getCentralPoint());
                break;
            case GROUND_ITEM:
                Mouse.move(((GroundItem)t).getCentralPoint());
                break;
            case SCENE_OBJECT:
                Mouse.move(((SceneObject)t).getCentralPoint());
                break;
            case ERROR:
                System.out.println("Error in interaction");
                break;
        }
    }
    
    private void inter(){
        switch(index){
            case NPC_INDEX:
                ((NPC)t).interact(interact, ((NPC)t).getName());
                break;
            case GROUND_ITEM:
                ((GroundItem)t).interact(interact, ((GroundItem)t).getGroundItem().getName());
                break;
            case SCENE_OBJECT:
                ((SceneObject)t).interact(interact, ((SceneObject)t).getDefinition().getName());
                break;
            case ERROR:
                System.out.println("Error in interaction");
                break;
        }
    }
}
