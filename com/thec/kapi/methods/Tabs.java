/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thec.kapi.methods;

import com.thec.api.util.Time;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.wrappers.widget.Widget;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 *
 * @author William
 */
public enum Tabs {
    COMBAT("Combat", 464, 124),
    NOTICE_BOARD("Noticeboard", 1056, 125),
    STATS("Stats", 320, 126),
    COMBAT_ACADEMY("Combat Academy", 1374, 127),
    PARTY_ORGANISER("Party Organiser", 939, 127),
    INVENTORY("Inventory", 679, 128),
    EQUIPMENT("Worn Equipment", 387, 129),
    PRAYER("Prayer List", 271, 130),
    ABILITY_BOOK("Ability Book", 275, 131),
    EXTRAS("Extras", 1139, 95),
    FRIENDS_LIST("Friends List", 550, 96),
    FRIENDS_CHAT("Friends Chat", 1109, 97),
    CLAN_CHAT("Clan Chat", 1110, 98),
    OPTIONS("Options", 261, 99),
    EMOTES("Emotes", 590, 100),
    MUSIC("Music Player", 187, 101),
    NOTES("Notes", 34, 102),
    LOGOUT("Logout", 182, 163);
    
    private String Name;
    private int widget;
    private int child;
    private static Tabs lastTab = null;
    private static final int tab = 548;
    private Tabs(String Name, int widget, int child){
        this.Name = Name;
        this.widget = widget;
        this.child = child;
    }
    public String getDescription(){
        return Name;
    }
    public int getIndex(){
        return child;
    }
    public boolean open() {
        return open(true);
    }

    public boolean open(boolean bln) {
        if (isOpen()) return true;
        WidgetChild b = Widgets.get(tab, child);
        if (!(b.validate() && b.visible())) return false;
        if (!b.click(bln)) return false;
        lastTab = this;
        Time.waitForWidget(widget, 0);
        return isOpen();
    }

    public boolean isOpen() {
       WidgetChild a = Widgets.get(widget, 0);
       return (a.validate() && a.visible());
    }
    
    public WidgetChild getWidgetChild(){
        return Widgets.get(tab, child);
    }
    
    public Widget getWidget(){
        return Widgets.get(widget);
    }
    
    public static Tabs getCurrent() {
        if (lastTab != null && lastTab.isOpen()) return lastTab;
        if (COMBAT.isOpen()) return COMBAT;
        if (NOTICE_BOARD.isOpen()) return NOTICE_BOARD;
        if (STATS.isOpen()) return STATS;
        if (COMBAT_ACADEMY.isOpen()) return COMBAT_ACADEMY;
        if (INVENTORY.isOpen()) return INVENTORY;
        if (EQUIPMENT.isOpen()) return EQUIPMENT;
        if (PRAYER.isOpen()) return PRAYER;
        if (ABILITY_BOOK.isOpen()) return ABILITY_BOOK;
        if (EXTRAS.isOpen()) return EXTRAS;
        if (FRIENDS_LIST.isOpen()) return FRIENDS_LIST;
        if (FRIENDS_CHAT.isOpen()) return FRIENDS_CHAT;
        if (CLAN_CHAT.isOpen()) return CLAN_CHAT;
        if (OPTIONS.isOpen()) return OPTIONS;
        if (EMOTES.isOpen()) return EMOTES;
        if (MUSIC.isOpen()) return MUSIC;
        if (NOTES.isOpen()) return NOTES;
        System.out.println("[SEVERE] Cannot find a tab!");
        return null;
    }
}
