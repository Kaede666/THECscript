package com.thec.core.loaders;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.Manifest;

import com.thec.api.util.Time;
import com.thec.core.Overlord;
import com.thec.core.THECscript;
import com.thec.core.internal.script.randoms.RandomEvent.RandomEvents;
import com.thec.core.ui.ScriptLoader;

@Manifest(authors = { "NeverDead","Apophis","marktero","Kaede666" }, description = "A private slice of botting heaven!", name = "The New THECscripts [PRIVATE]", version = 2.0, hidden = true)
public class SHELL extends ActiveScript implements PaintListener,
		KeyListener, MouseListener, MessageListener {

	@Override
	public int loop() {
		Overlord.activate(this);
		if (!Overlord.isStarted()){
			return Time.getReturn();
		}
		if (Overlord.isStopRequested()){
			shutdown();
			return -1;
		}
		if (RandomEvents.check()){
			return Time.getReturn();
		}
		if (Overlord.getCurrentScript() != null){
			return Overlord.getCurrentScript().run();
		}
		return Time.getReturn();
	}

	@Override
	public void onStart() {
		ScriptLoader loader = new ScriptLoader();
		loader.setVisible(true);
		while (loader.isVisible()) {
			sleep(50);
		}
		if (Overlord.getCurrentScript() == null) {
			System.out.println("User Failed To Select Script. Stopping Script.");
			stop();
		}
		Overlord.start();
	}

	@Override
	public void onStop() {
		Overlord.stopScript();
		super.onStop();
	}

	@Override
	public void messageReceived(MessageEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().messageReceived(m);
		}
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().mouseClicked(m);
		}
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().mouseEntered(m);
		}
	}

	@Override
	public void mouseExited(MouseEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().mouseExited(m);
		}
	}

	@Override
	public void mousePressed(MouseEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().mousePressed(m);
		}
	}

	@Override
	public void mouseReleased(MouseEvent m) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().mouseReleased(m);
		}
	}

	@Override
	public void keyPressed(KeyEvent k) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().keyPressed(k);
		}
	}

	@Override
	public void keyReleased(KeyEvent k) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().keyReleased(k);
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().keyTyped(k);
		}
	}

	@Override
	public void onRepaint(Graphics g) {
		if (Overlord.getCurrentScript() != null) {
			Overlord.getCurrentScript().onRepaint(g);
		}
	}

	public enum PrivateScripts {
		/*Example:
		 * Name_in_Proper_Case_With_Underscores(new YourScriptHere());
		 * Banside_Trainer(new BankSideTrainer());
		 */
		//TODO ADD YOUR PERSONAL SCRIPTS BELOW THIS POINT
		Spider(new com.thec.kaede.spider.Spider()),
                Test(new com.Test()),
                Dungeon(new com.thec.kaede.Dungeon.Dungeon());
		;

		PrivateScripts(THECscript script) {
			this.script = script;
		}

		@Override
		public String toString() {
			return super.toString().replace('_', ' ');
		}
		
		private THECscript script;

		public THECscript getScript() {
			return this.script;
		}

		public static String[] getStrings() {
			PrivateScripts[] a = values();
			String[] b = new String[a.length];
			for (int i = 0; i < b.length; i++) {
				b[i] = a[i].name();
			}
			return b;
		}
		
	}

	
}
