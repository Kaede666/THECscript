package com.thec.kapi.methods.widgets;

/**
 *
 * @author William
 */
public class AbilityBar extends com.thec.api.methods.widgets.AbilityBar{
    public static enum Skills {
        Alch("Alch", 0);
        private int Value;
        private String name;
        private Skills(String name, int Value){
            this.Value = Value;
            this.name = name;
        }
    }
    
    public static boolean hasAbility(String name, boolean combat){
        for (int i = 0; i < abilitiesOnBar.length; i++){
            
        }
        return true;
    }
}
