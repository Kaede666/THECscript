package com.thec.kapi.methods.tabs;

/**
 *
 * @author William
 */
public class Skills extends org.powerbot.game.api.methods.tab.Skills{
    public static final int 
            SKILL_WIDGET = 320, ATTACK_INDEX=1, CONSTITUTION_INDEX=2,  MINING_INDEX=3,
            STRENGTH_INDEX=4, AGILITY_INDEX=5, SMITHING_INDEX=6, DEFENSE_INDEX=7,
            HERBLORE_INDEX=8, FISHING_INDEX=9, RANGE_INDEX=10, THEIVING_INDEX=11,
            COOKING_INDEX=12, PRAYER_INDEX=13, CRAFTING_INDEX=14, FIREMAKING_INDEX=15,
            MAGIC_INDEX=16, FLETCHING_INDEX=17, WOODCUTTING_INDEX=18, RUNECRAFTING_INDEX=19,
            SLAYER_INDEX=20, FARMING_INDEX=21, CONSTRUCTION_INDEX=22, HUNTER_INDEX=23,
            SUMMONING_INDEX=24, DUNGEONEERING_INDEX=25;
    public static String getSkillFromIndex(int index){
        switch (index){
            case ATTACK_INDEX: return "Attack";
            case CONSTITUTION_INDEX: return "Constitution";
            case MINING_INDEX: return "Mining";
            case STRENGTH_INDEX: return "Strength";
            case AGILITY_INDEX: return "Agility";
            case SMITHING_INDEX: return "Smithing";
            case DEFENSE_INDEX: return "Defense";
            case HERBLORE_INDEX: return "Herblore";
            case FISHING_INDEX: return "Fishing";
            case RANGE_INDEX: return "Range";
            case THEIVING_INDEX: return "Theiving";
            case COOKING_INDEX: return "Cooking";
            case PRAYER_INDEX: return "Prayer";
            case CRAFTING_INDEX: return "Crafting";
            case FIREMAKING_INDEX: return "Firemaking";
            case MAGIC_INDEX: return "Magic";
            case FLETCHING_INDEX: return "Fletching";
            case WOODCUTTING_INDEX: return "Woodcutting";
            case RUNECRAFTING_INDEX: return "Runecrafting";
            case SLAYER_INDEX: return "Slayer";
            case FARMING_INDEX: return "Farming";
            case CONSTRUCTION_INDEX: return "Construction";
            case HUNTER_INDEX: return "Hunter";
            case SUMMONING_INDEX: return "Summoning";
            case DUNGEONEERING_INDEX: return "Dungeoneering";
            default: return "Skill not found";
        }
    }
}
