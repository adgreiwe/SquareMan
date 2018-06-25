package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Listener implements KeyListener {
	private static final int MS_BETWEEN_MOVEMENT = 100;
	
	private ArrayList<Moves> moves;
	private TemporalLimiter movementCheck;
	
	public Listener() {
		moves = new ArrayList<Moves>(2);
		
		this.movementCheck = new TemporalLimiter(MS_BETWEEN_MOVEMENT);
	}

	public ArrayList<Moves> getMoves() {
		ArrayList<Moves> prevs = moves;
		this.moves = new ArrayList<Moves>(2);
		return prevs;
	}
	
	@Override
	public void keyPressed(KeyEvent event) {
		boolean shiftSelected = (event.getModifiers() & 1) == 1;
		switch (event.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (shiftSelected) {
					moves.add(Moves.ROTATE_LEFT);
				} else {
					if (movementCheck.isReady()) {
						moves.add(Moves.LEFT);
					}
				}
				break;
			case KeyEvent.VK_UP:
				if (shiftSelected) {
					moves.add(Moves.ROTATE_UP);
				} else {
					if (movementCheck.isReady()) {
						moves.add(Moves.UP);
					}
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (shiftSelected) {
					moves.add(Moves.ROTATE_RIGHT);
				} else {
					if (movementCheck.isReady()) {
						moves.add(Moves.RIGHT);
					}
				}
				break;
			case KeyEvent.VK_DOWN:
				if (shiftSelected) {
					moves.add(Moves.ROTATE_DOWN);
				} else {
					if (movementCheck.isReady()) {
						moves.add(Moves.DOWN);
					}
				}
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		switch (event.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				System.out.println("space bar hit");
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent event) {
		// not using
	}
	
}