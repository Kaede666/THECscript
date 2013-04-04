import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

@Manifest(
        authors = { "Kaede666" }, 
        name = "DynCoder v1.7", 
        description = "Dynamically loads class files", 
        version = 1.7)
public class DynamicCoder extends ActiveScript 
    implements PaintListener, KeyListener, MouseListener, MessageListener {
    
    ActiveScript inst = null;
    BrowseFile GUI = new BrowseFile();
    Class cls;
    boolean running = false;
    
    @Override
    public void onStart(){
        GUI.addLog("Waiting for class selection.");
        GUI.setSize(300, 200);
        
    }
    
    @Override
    public void onStop(){
        running = false;
        GUI.dispose();
        stopScript();
    }
    
    @Override
    public int loop() {
        try {
            while (!GUI.isReady) {
                sleep(50);
                if (isInterrupted()) return 0;
            }
            GUI.addLog("Creating new bot instance.");
            createBot(GUI.getClassFile());
            while (GUI.isReady){
                if (isInterrupted()) return 0;
                if (GUI.reload){
                    GUI.addLog("Updating bot.");
                    GUI.reload = false;
                    stopScript();
                    createBot(GUI.getClassFile());
                }
                sleep(1000);
            }
            GUI.addLog("Killing old bot instance.");
            GUI.reload = false;
            stopScript();
            while (inst.isAlive()) {
                if (isInterrupted()) return 0;
                sleep(50);
            }
        } catch (NullPointerException |NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
        }
        return 500;
    }
    private void stopScript(){
        if (inst != null){
            inst.interrupt();
            inst.onStop();
            inst = null; 
        }
    }
    
    private void createBot(File classFile) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        running = false;
        stopScript();
        cls = null;
        try {
            String URI = classFile.getPath().replace(classFile.getName(), "").replace("\\", "/");
            String URI2 = null;
            if (URI.contains("com")){
                URI2 = classFile.getPath();
                URI2 = URI2.substring(URI2.lastIndexOf("com"));
                URI2 = URI2.substring(0, URI2.lastIndexOf("."));
                URI2 = URI2.replace("\\", ".");
                URI = URI.substring(0, URI.indexOf("com"));
            }
            URL url = new URL("file:/" + URI);
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);

            if (URI2 == null){
                cls = cl.loadClass(classFile.getName().replace(".class", ""));
            }else{
                cls = cl.loadClass(URI2);
            }
            
            cl = null;
            
            inst = (ActiveScript)cls.newInstance();
            inst.start();
            
            running = true;
            
        }catch  (SecurityException | InstantiationException |
                IllegalAccessException | IllegalArgumentException | 
                MalformedURLException |
                ClassNotFoundException ex) {
        }
    }
    @Override
    public void onRepaint(Graphics g){
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("onRepaint", Graphics.class).invoke(inst, g);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("keyPressed", KeyEvent.class).invoke(inst, e);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
 
    @Override
    public void keyReleased(KeyEvent e) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("keyReleased", KeyEvent.class).invoke(inst, e);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("keyTyped", KeyEvent.class).invoke(inst, e);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("mouseClicked", MouseEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void mouseEntered(MouseEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("mouseEntered", MouseEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void mouseExited(MouseEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("mouseExited", MouseEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("mousePressed", MouseEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void mouseReleased(MouseEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("mouseReleased", MouseEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
    @Override
    public void messageReceived(MessageEvent arg) {
        if (inst != null && cls != null && running){
            try {
                cls.getMethod("messageReceived", MessageEvent.class).invoke(inst, arg);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            }
        }
    }
}