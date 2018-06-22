package Main;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.Panel;
import javax.swing.Timer;
import java.util.ArrayList;

public class Engine {
	private static final String TITLE = "Square Man";
	private static final int MILLISECONDS_PER_SECOND = 1000;
	private static final int MAX_FPS = 80;
	private static final int GAME_WIDTH = 1000;
	private static final int GAME_HEIGHT = 600;
	private static final int SQUARE_WIDTH = 50;
	
	private static Timer timer;
	private static int frameNum;
	private static int fps;
	
	private static Frame frame;
	private static Canvas screen;
	private static Listener listener;
	private static Board game;
	
	public static void main(String[] args) {
		Engine e = new Engine(Integer.parseInt(args[0]));
		runGame();
	}
	
	public Engine(int desiredFPS) {
		prepareGUI();
		prepareTimer(desiredFPS);
		frame.setVisible(true);
	}
	
	private void prepareGUI() {
		frame = new Frame(TITLE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
	    	}        
	    });
		
		int numCols = (GAME_WIDTH - 1) / (SQUARE_WIDTH + 1);
		int numRows = (GAME_HEIGHT - 1) / (SQUARE_WIDTH + 1);
		game = Generator.getLevel(1, numRows, numCols);
		
		screen = new View(game, SQUARE_WIDTH);
		screen.setFocusable(true);
		screen.requestFocusInWindow();
		listener = new Listener();
		screen.addKeyListener(listener);
		
		frame.add(screen);
		frame.pack();
	}
	
	private void prepareTimer(int desiredFPS) {
		fps = Math.min(MILLISECONDS_PER_SECOND / MAX_FPS, MILLISECONDS_PER_SECOND / desiredFPS);
		frameNum = 0;
		
		ActionListener frameChanger = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				ArrayList<Moves> moves = listener.getMoves();
				interpretMoves(moves);
				frameNum++;
			}
		};
		timer = new Timer(fps, frameChanger);
	}
	
	private void interpretMoves(ArrayList<Moves> moves) {
		boolean changeOccurred = false;
		int playerRow = game.getPlayerRow();
		int playerCol = game.getPlayerCol();
		ArrayList<ArrayList<Pieces>> board = game.getBoard();
		int numRows = board.size();
		if (numRows == 0) {
			// avoid failing at invalid state
			return;
		}
		int numCols = board.get(0).size();
		for (int i = 0; i < moves.size(); i++) {
			switch (moves.get(i)) {
				case UP:
					int targetRow = playerRow - 1 < 0 ? numRows - 1 : playerRow - 1;
					if (game.checkMovable(Pieces.PLAYER, targetRow, playerCol)) {
						game.movePlayer(targetRow, playerCol, Directions.NORTH);
						playerRow = targetRow;
						changeOccurred = true;
					} else {
						changeOccurred = changeOccurred || rotate(Directions.NORTH);
					}
					break;
				case DOWN:
					targetRow = (playerRow + 1) % numRows;
					if (game.checkMovable(Pieces.PLAYER, targetRow, playerCol)) {
						game.movePlayer(targetRow, playerCol, Directions.SOUTH);
						changeOccurred = true;
						playerRow = targetRow;
					} else {
						changeOccurred = changeOccurred || rotate(Directions.SOUTH);
					}
					break;
				case LEFT:
					int targetCol = playerCol - 1 < 0 ? numCols - 1 : playerCol - 1;
					if (game.checkMovable(Pieces.PLAYER, playerRow, targetCol)) {
						game.movePlayer(playerRow, targetCol, Directions.WEST);
						changeOccurred = true;
						playerCol = targetCol;
					} else {
						changeOccurred = changeOccurred || rotate(Directions.WEST);
					}
					break;
				case RIGHT:
					targetCol = (playerCol + 1) % numCols;
					if (game.checkMovable(Pieces.PLAYER, playerRow, targetCol)) {
						game.movePlayer(playerRow, targetCol, Directions.EAST);
						changeOccurred = true;
						playerCol = targetCol;
					} else {
						changeOccurred = changeOccurred || rotate(Directions.EAST);
					}
					break;
				case ROTATE_UP:
					changeOccurred = changeOccurred || rotate(Directions.NORTH);
					break;
				case ROTATE_DOWN:
					changeOccurred = changeOccurred || rotate(Directions.SOUTH);
					break;
				case ROTATE_LEFT:
					changeOccurred = changeOccurred || rotate(Directions.WEST);
					break;
				case ROTATE_RIGHT:
					changeOccurred = changeOccurred || rotate(Directions.EAST);
					break;
				case USE:
					break;
			}
		}
		if (changeOccurred) {
			screen.repaint();
		}	
	}
	
	private boolean rotate(Directions targetDir) {
		if (game.getPlayerOrientation() != targetDir) {
			game.setPlayerDirection(targetDir);
			return true;
		}
		return false;
	}
	
	public static void runGame() {
		timer.start();
	}
}