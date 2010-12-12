package org.rhok.findmebynoise;

import java.util.Date;

import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class BaconRegistryPusher extends MIDlet implements CommandListener {

	private Displayable screen1;
	private Command back;
	private Command next;

	/**
	 * Creates several screens and navigates between them.
	 */
	public BaconRegistryPusher() {
		this.back = new Command("Exit", Command.BACK, 1);
		this.next = new Command("Push", Command.OK, 1);

		this.screen1 = getSreen1();
		this.screen1.setCommandListener(this);
		this.screen1.addCommand(this.back);
		this.screen1.addCommand(this.next);
	}

	private Displayable getSreen1() {
		return new TextBox(
				"Registry Pusher",
				"Pushes Rescue Application FindMeByNoise to be autostarted in case of a Natural Disaster Situation.",
				255, TextField.UNEDITABLE);
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		Display display = Display.getDisplay(this);
		display.setCurrent(screen1);
	}

	/**
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command,
	 *      javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command command, Displayable displayable) {
		if (command == this.next) {
			try {
				long milis = new Date().getTime() + 1000 * 30;
				PushRegistry.registerAlarm(
						"org.rhok.findmebynoise.HelpThemHearMe", milis);
			} catch (ConnectionNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			notifyDestroyed();
		}

		if (command == this.back) {
			notifyDestroyed();
		}
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
	}
}
