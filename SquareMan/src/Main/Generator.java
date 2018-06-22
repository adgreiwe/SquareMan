package Main;

import java.util.ArrayList;

public class Generator {
	private static LevelGenerator[] levels = new LevelGenerator[] {
			// 0: empty
			new LevelGenerator() { 
				public Board generateLevel(int numRows, int numCols) {
					ArrayList<ArrayList<Pieces>> board = new ArrayList<>(numRows);
					for (int i = 0; i < numRows; i++) {
						ArrayList<Pieces> row = new ArrayList<>(numCols);
						for (int j = 0; j < numCols; j++) {
							row.add(Pieces.EMPTY);
						}
						board.add(row);
					}
					return new Board(board, 0, 0, Directions.EAST);
				} 
			},
			// 1: empty with walls
			new LevelGenerator() { 
				public Board generateLevel(int numRows, int numCols) {
					ArrayList<ArrayList<Pieces>> board = new ArrayList<>(numRows);
					for (int i = 0; i < numRows; i++) {
						ArrayList<Pieces> row = new ArrayList<>(numCols);
						for (int j = 0; j < numCols; j++) {
							if (i == 0 || j == 0 || i == numRows - 1 || j == numCols - 1) {
								row.add(Pieces.WALL);
							} else {
								row.add(Pieces.EMPTY);
							}
						}
						board.add(row);
					}
					return new Board(board, 1, 1, Directions.EAST, Usables.LASER);
				} 
			},
			
	};
	
	private interface LevelGenerator {
		Board generateLevel(int numRows, int numCols);
	}
	
	public static Board getLevel(int boardNum, int numRows, int numCols) {
		return levels[boardNum].generateLevel(numRows, numCols);
	}
}