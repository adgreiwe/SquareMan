package Main;

import java.util.ArrayList;

public class Board {
	private ArrayList<ArrayList<Pieces>> board;
	private Piece player;
	
	public Board(ArrayList<ArrayList<Pieces>> board, int playerRow, int playerCol, Directions playerDirection) {
		this(board, playerRow, playerCol, playerDirection, Usables.EMPTY);
	}
	public Board(ArrayList<ArrayList<Pieces>> board, int playerRow, int playerCol, Directions playerDirection, Usables playerItem) {
		this.board = board;
		this.player = new Piece(Pieces.PLAYER, playerDirection, playerRow, playerCol, playerItem);
		this.board.get(playerRow).set(playerCol, Pieces.PLAYER);
	}
	
	public int getPlayerRow() {
		return this.player.rowNum;
	}
	public int getPlayerCol() {
		return this.player.colNum;
	}
	public Usables getItem() {
		return this.player.item;
	}
	public void movePlayer(int newRow, int newCol, Directions direction) {
		int currRow = getPlayerRow();
		int currCol = getPlayerCol();
		this.board.get(currRow).set(currCol, Pieces.EMPTY);
		this.player.setLoc(newRow, newCol);
		this.board.get(newRow).set(newCol, Pieces.PLAYER);
		this.setPlayerDirection(direction);
	}
	public ArrayList<ArrayList<Pieces>> getBoard() {
		return board;
	}

	public Directions getPlayerOrientation() {
		return this.player.orientation;
	}

	public void setPlayerDirection(Directions direction) {
		this.player.setOrientation(direction);
	}
	
	public boolean checkMovable(Pieces type, int targetRow, int targetCol) {
		return this.board.get(targetRow).get(targetCol) == Pieces.EMPTY;
	}
	
	private class Piece {
		private final Pieces type;
		private Directions orientation;
		private int rowNum;
		private int colNum;
		private Usables item;
		
		public Piece(Pieces type, Directions orientation, int row, int col, Usables item) {
			this.type = type;
			this.orientation = orientation;
			this.rowNum = row;
			this.colNum = col;
			this.item = item;
		}
		
		public Piece(Pieces type, Directions orientation, int row, int col) {
			this(type, orientation, row, col, Usables.EMPTY);
		}

		public void setOrientation(Directions orientation) {
			this.orientation = orientation;
		}

		public void setLoc(int rowNum, int colNum) {
			this.rowNum = rowNum;
			this.colNum = colNum;
		}
	}
}