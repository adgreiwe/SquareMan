package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class TemporalLimiter {
	private Timer toggler;
	private boolean ready;

	public TemporalLimiter(int delay) {
		ActionListener readyToggler = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ready = true;
			}
		};
		this.toggler = new Timer(delay, readyToggler);
		this.toggler.setRepeats(false);
		this.ready = false;
		this.toggler.start();
	}
	
	/*
	 * If ready, returns true and restarts TemporalLimiter, meaning that it will not be ready for 'delay' MS
	 */
	public boolean isReady() {
		if (this.ready) {
			// set to false, restart toggler, return true
			this.ready = false;
			this.toggler.start();
			return true;
		}
		return false;
	}
}