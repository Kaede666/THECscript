/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thec.kaede.Dungeon;

/**
 *
 * @author William
 */
public class Doors {
    public static final int[] DOOR_IDS = new int[]{50342, 50392, 50317, 50385, 50346};
    public enum Barriers{
        MAGICAL_BARRIER("Dispel", "Magic", new int[]{50329});
        Barriers(String type, String req, int[] IDs){
            
        }
    }
}
