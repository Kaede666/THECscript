package com.thec.kore.internal.script.randoms;

import org.powerbot.core.randoms.AntiRandom;
import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;

/**
 *
 * @author William
 */
public class SkillsGuide extends AntiRandom{

    @Override
    public boolean activate() {
        return Widgets.get(1218, 0).isOnScreen();
    }

    @Override
    public void execute() {
         Widgets.get(1218, 0).click(true);
         Task.sleep(500);
    }
    
}
