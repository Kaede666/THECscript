package com.thec.kore.internal.script;

import com.thec.core.internal.wrappers.Situation;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 *
 * @author William
 */
public class Situations extends com.thec.core.internal.script.Situations{
    public static Situation npcChat(final String Name){
        return new Situation(){
            @Override
            public boolean accept(){
                WidgetChild a = Widgets.get(1184, 17);
                return (a.validate() && a.visible() && a.getText().matches(Name));
            }
        };
    }
    
    public static Situation multiWidget(final boolean AND, final WidgetChild... a){
        return new Situation(){
            @Override
            public boolean accept(){
                for (int i = 0; i < a.length; i++){
                    if (AND) {
                        if (!(a[i].validate() && a[i].visible())) return false;
                    }else{
                        if (a[i].validate() && a[i].visible()) return true;
                    }
                }
                return AND;
            }
        };
    }
    public static Situation widgetVisible(final WidgetChild a, final boolean visible){
        return new Situation(){
            @Override
            public boolean accept(){
                if (visible){
                    return (a.validate() && a.visible());
                }else{
                    return !(a.validate() && a.visible());
                }
            }
        };
    }
}
