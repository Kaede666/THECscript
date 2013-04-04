package com;

import com.thec.core.THECscript;
import com.thec.kaede.Dungeon.DungItems;
import com.thec.kapi.methods.Tabs;




/**
 *
 * @author William
 */
public class Test extends THECscript{
    
    
    @Override
    public void onStart(){
    }
    
    @Override
    public int loop() {
        System.out.println(Tabs.COMBAT_ACADEMY.getWidgetChild().getTextureId());
        this.stop();
        return 1000;
    }
    
}
