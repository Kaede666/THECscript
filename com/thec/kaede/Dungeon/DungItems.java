package com.thec.kaede.Dungeon;

import com.thec.kapi.util.Filters;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.client.Skill;

/**
 *
 * @author William
 */
public class DungItems {
    public static final int ringID = 15707;
    public enum Food {
        HEIM_CRAB(18159, -1, 150, 1),
        RED_EYE(18161, -1, 200, 2),
        DUSK_EEL(18163, -1, 400, 3),
        GIANT_FLATFISH(18165, -5, 600, 4),
        SHORT_FINNED_EEL(-13, -16, 800, 5),
        WEB_SNIPPER(-10, -18, 1000, 6);
        int ID, RawID, heal, tier;
        Food(int ID, int RawID, int heal, int tier){
            this.ID = ID;
            this.heal = heal;
            this.tier = tier;
            this.RawID = RawID;
        }
        public static int[] getTierAndAbove(int tier){
            Food[] all = Food.values();
            int[] ret = new int[all.length * 2];
            int count = 0;
            for (int i = 0; i < all.length; i++){
                if (all[i].tier >= tier){
                    ret[count++] = all[i].ID;
                    ret[count++] = all[i].RawID;
                }
            }
            int[] ret2 = new int[count];
            System.arraycopy(ret, 0, ret2, 0, count);
            return ret2;
        }
    }
    public enum Tools {
        UNK(-1);
        
        Tools(int ID){
            
        }
    }
    public enum Weapons {
        //DAGGERS
        NOVITE_DAGGER(-1, 48, 50, "Stab", "Melee", 2.4, 1, "dagger", 1),
        BATHUS_DAGGER(-1, 96, 100, "Stab", "Melee", 2.4, 10, "dagger", 2),
        MARMAROS_DAGGER(-1, 192, 163, "Stab", "Melee", 2.4, 20, "dagger", 3),
        KRATONITE_DAGGER(-1, 288, 266, "Stab", "Melee", 2.4, 30, "dagger", 4),
        FRACTITE_DAGGER(-1, 384, 433, "Stab", "Melee", 2.4, 40, "dagger", 5),
        ZEPHYRIUM_DAGGER(-1, 480, 704, "Stab", "Melee", 2.4, 50, "dagger", 6),
        ARGONITE_DAGGER(-1, 576, 1147, "Stab", "Melee", 2.4, 60, "dagger", 7),
        KATAGON_DAGGER(-1, 672, 1868, "Stab", "Melee", 2.4, 70, "dagger", 8),
        GORGONITE_DAGGER(-1, 768, 3043, "Stab", "Melee", 2.4, 80, "dagger", 9),
        PROMETHIUM_DAGGER(-1, 864, 4957, "Stab", "Melee", 2.4, 90, "dagger", 10),
        PRIMAL_DAGGER(-1, 1049, 7689, "Stab", "Melee", 2.4, 99, "dagger", 11),
        //RAPIERS
        NOVITE_RAPIER(-1, 61, 50, "Stab", "Melee", 3.0, 1, "rapier", 1),
        BATHUS_RAPIER(-1, 122, 100, "Stab", "Melee", 3.0, 10, "rapier", 2),
        MARMAROS_RAPIER(-1, 245, 163, "Stab", "Melee", 3.0, 20, "rapier", 3),
        KRATONITE_RAPIER(-1, 367, 266, "Stab", "Melee", 3.0, 30, "rapier", 4),
        FRACTITE_RAPIER(-1, 490, 433, "Stab", "Melee", 3.0, 40, "rapier", 5),
        ZEPHYRIUM_RAPIER(-1, 612, 704, "Stab", "Melee", 3.0, 50, "rapier", 6),
        ARGONITE_RAPIER(-1, 735, 1147, "Stab", "Melee", 3.0, 60, "rapier", 7),
        KATAGON_RAPIER(-1, 857, 1868, "Stab", "Melee", 3.0, 70, "rapier", 8),
        GORGONITE_RAPIER(-1, 980, 3043, "Stab", "Melee", 3.0, 80, "rapier", 9),
        PROMETHIUM_RAPIER(-1, 1102, 4957, "Stab", "Melee", 3.0, 90, "rapier", 10),
        PRIMAL_RAPIER(-1, 1212, 7689, "Stab", "Melee", 3.0, 99, "rapier", 11),
        //LONGSWORDS
        NOVITE_LONGSWORD(-1, 61, 50, "Slash", "Melee", 3.0, 1, "longsword", 1),
        BATHUS_LONGSWORD(-1, 122, 100, "Slash", "Melee", 3.0, 10, "longsword", 2),
        MARMAROS_LONGSWORD(-1, 245, 163, "Slash", "Melee", 3.0, 20, "longsword", 3),
        KRATONITE_LONGSWORD(-1, 367, 266, "Slash", "Melee", 3.0, 30, "longsword", 4),
        FRACTITE_LONGSWORD(-1, 490, 433, "Slash", "Melee", 3.0, 40, "longsword", 5),
        ZEPHYRIUM_LONGSWORD(-1, 612, 704, "Slash", "Melee", 3.0, 50, "longsword", 6),
        ARGONITE_LONGSWORD(-1, 735, 1147, "Slash", "Melee", 3.0, 60, "longsword", 7),
        KATAGON_LONGSWORD(-1, 857, 1868, "Slash", "Melee", 3.0, 70, "longsword", 8),
        GORGONITE_LONGSWORD(-1, 980, 3043, "Slash", "Melee", 3.0, 80, "longsword", 9),
        PROMETHIUM_LONGSWORD(-1, 1102, 4957, "Slash", "Melee", 3.0, 90, "longsword", 10),
        PRIMAL_LONGSWORD(-1, 1212, 7689, "Slash", "Melee", 3.0, 99, "longsword", 11),
        //BATTLEAXES
        NOVITE_BATTLEAXE(-1, 74, 50, "Slash", "Melee", 3.6, 1, "battleaxe", 1),
        BATHUS_BATTLEAXE(-1, 149, 100, "Slash", "Melee", 3.6, 10, "battleaxe", 2),
        MARMAROS_BATTLEAXE(-1, 298, 163, "Slash", "Melee", 3.6, 20, "battleaxe", 3),
        KRATONITE_BATTLEAXE(-1, 447, 266, "Slash", "Melee", 3.6, 30, "battleaxe", 4),
        FRACTITE_BATTLEAXE(-1, 596, 433, "Slash", "Melee", 3.6, 40, "battleaxe", 5),
        ZEPHYRIUM_BATTLEAXE(-1, 745, 704, "Slash", "Melee", 3.6, 50, "battleaxe", 6),
        ARGONITE_BATTLEAXE(-1, 895, 1147, "Slash", "Melee", 3.6, 60, "battleaxe", 7),
        KATAGON_BATTLEAXE(-1, 1043, 1868, "Slash", "Melee", 3.6, 70, "battleaxe", 8),
        GORGONITE_BATTLEAXE(-1, 1192, 3043, "Slash", "Melee", 3.6, 80, "battleaxe", 9),
        PROMETHIUM_BATTLEAXE(-1, 1341, 4957, "Slash", "Melee", 3.6, 90, "battleaxe", 10),
        PRIMAL_BATTLEAXE(-1, 1475, 7689, "Slash", "Melee", 3.6, 99, "battleaxe", 11),
        //2H SWORDS
        NOVITE_2H_SWORD(-1, 114, 50, "Slash", "Melee", 3.6, 1, "2h_sword", 1),
        BATHUS_2H_SWORD(-1, 228, 100, "Slash", "Melee", 3.6, 10, "2h_sword", 2),
        MARMAROS_2H_SWORD(-1, 457, 163, "Slash", "Melee", 3.6, 20, "2h_sword", 3),
        KRATONITE_2H_SWORD(-1, 685, 266, "Slash", "Melee", 3.6, 30, "2h_sword", 4),
        FRACTITE_2H_SWORD(-1, 914, 433, "Slash", "Melee", 3.6, 40, "2h_sword", 5),
        ZEPHYRIUM_2H_SWORD(-1, 1142, 704, "Slash", "Melee", 3.6, 50, "2h_sword", 6),
        ARGONITE_2H_SWORD(-1, 1371, 1147, "Slash", "Melee", 3.6, 60, "2h_sword", 7),
        KATAGON_2H_SWORD(-1, 1599, 1868, "Slash", "Melee", 3.6, 70, "2h_sword", 8),
        GORGONITE_2H_SWORD(-1, 1828, 3043, "Slash", "Melee", 3.6, 80, "2h_sword", 9),
        PROMETHIUM_2H_SWORD(-1, 2056, 4957, "Slash", "Melee", 3.6, 90, "2h_sword", 10),
        PRIMAL_2H_SWORD(-1, 2262, 7689, "Slash", "Melee", 3.6, 99, "2h_sword", 11),
        
        NOVITE_WARHAMMER(17019, 74, 50, "Crush", "Melee", 3.6, 1, "warhammer", 1);
        //MARMAROS_RAPIER(16037, 245, 163, "Stab", "Melee", 3.0, 20, "rapier", 3),
        //KRATONITE_2H_SWORD(16895, 685, 266, "Slash", "Melee", 3.6, 30, "2h", 4);
		
        private int ID, dam, level, tier, accuracy;
        private double speed;
        private String style, type, Wclass;
        private boolean shield;
		
        Weapons(int ID, int dam, int acc, String style, String type, double speed, int level, String Wclass, int tier){
            
            if(Wclass.matches("2h_sword")){
                shield = false;
            }else{
                shield = true;
            }
        }
        
        public static Weapons[] getBetter(Weapons r, boolean sameClass){
            Weapons[] all = Weapons.values();
            for (int i = 0; i < all.length; i++){
                if (sameClass){
                    if (!((r.Wclass.matches(all[i].Wclass)) && all[i].dam > r.dam)) all[i] = null;
                }else{
                    if (!(all[i].dam > r.dam)) all[i] = null;
                }
            }
            return null;
        }
    }
    public enum Armour {
        BATHUS_PLATESKIRT(16649, 22, 180, "Melee", 10, "platelegs", 1);
        
        Armour(int ID, int armour, int lifeBonus, String type, int level, String Wclass, int tier){
            
        }
    }
}
