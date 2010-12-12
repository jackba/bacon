package org.rhok.findmebynoise;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.lcdui.TextField;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * 
 * @author alejandrotkachuk
 * 
 */
public class HelpThemHearMe extends MIDlet implements CommandListener {

	private Displayable screen1;
	private Command back;
	private Command next;
	private MIDlet instance;

	/**
	 */
	public HelpThemHearMe() {
		System.out.println("HelpThemHearMe");
		this.back = new Command("Rescue Me!", Command.BACK, 1);
		this.next = new Command("Exit", Command.OK, 1);
		// this.screen1 = new TextBox("ALERT - Rescue System",
		// "FindMeByNoise is an application that will let Rescuers find you in a Natural Disaster Situation. If this is not the case, you have 10 seconds to click Exit and not sound the alarm. Thanks. RHOK.org",
		// 255, TextField.UNEDITABLE);
		// this.screen1.setCommandListener(this);
		// this.screen1.addCommand(this.back);
		// this.screen1.addCommand(this.next);
		this.instance = this;
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		Display display = Display.getDisplay(instance);
		display.setCurrent(null);

		TimerTask rescueNoisePlayer = new TimerTask() {

			public void run() {
				try {
					InputStream is = getClass().getResourceAsStream(
							"/pitch.mp3");
					Player player = Manager.createPlayer(is, "audio/mpeg");

					player.realize();
					VolumeControl vc = (VolumeControl) player
							.getControl("VolumeControl");
					if (vc != null) {
						vc.setLevel(100);
					}
					player.prefetch();
					player.start();
					screen1.removeCommand(next);
					screen1.removeCommand(back);
					Display d = Display.getDisplay(instance);
					d.setCurrent(screen1);
					screen1.removeCommand(next);
					screen1.removeCommand(back);
					((TextBox) screen1)
							.setString("FindMeByNoise is now helping you get rescued. Please follow this steps: \n- Stay as calm as possible\n- Do not move\n- Call 911 (if you have service)");

				} catch (Exception e) {
					e.printStackTrace();
					if (null != screen1) {
						((TextBox) screen1).setString(e.getMessage());
					}
				}
			}
		};

		synchronized (this) {
			try {
				this.wait(1000 * 15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		screen1 = new TextBox(
				"ALERT - Rescue System",
				"FindMeByNoise is an application that will let Rescuers find you in a Natural Disaster Situation. If this is not the case, you have 15 seconds to click Exit and not sound the alarm. Thanks. RHOK.org",
				255, TextField.UNEDITABLE);
		Display d = Display.getDisplay(instance);
		d.setCurrent(screen1);
		screen1.addCommand(next);
		screen1.addCommand(back);

		long waitTime = 1000 * 120;
		Timer timer2 = new Timer();
		timer2.schedule(rescueNoisePlayer, 1000 * 15, waitTime);
	}

	/**
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command,
	 *      javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command command, Displayable displayable) {
		if (command == this.next) {
			if (displayable == this.screen1) {
				System.out.println("Destroying to Run noise..");
				notifyDestroyed();
				// Display d = Display.getDisplay(instance);
				// d.setCurrent(null);
			}
		}
		if (command == this.back) {
			screen1.removeCommand(next);
			screen1.removeCommand(back);
			((TextBox) screen1)
					.setString("FindMeByNoise is now helping you get rescued. Please follow this steps: \n- Stay as calm as possible\n- Do not move\n- Call 911");
		}
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		System.out.println("destroyApp");
	}

	/**
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		System.out.println("pauseApp");
	}
}
