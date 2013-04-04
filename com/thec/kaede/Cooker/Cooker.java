package com.thec.kaede.Cooker;

import com.thec.core.THECscript;
import com.thec.core.unportables.Murfee;

/**
 *
 * @author William
 */
public class Cooker extends THECscript{

    @Override
    public int loop() {
        return Murfee.getReturnTime();
    }
    
}
