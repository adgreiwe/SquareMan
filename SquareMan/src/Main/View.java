package Main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Rectangle;

public class View extends Canvas {
	private static final Color BACKGROUND_COLOR = new Color(220, 220, 220);
	
	private static final double PIECE_WIDTH_MULTIPLIER = 0.96;
	private static int pieceWidth;
	private static int pieceBezelWidth;
	
	private static final Color PLAYER_EYE_COLOR = new Color(0x1F,0xBE,0xD6);
	private static final double EYE_SEPARATION_DIST_MULTIPLIER = 0.34;
	private static final double EYE_WIDTH_MULTIPLIER = 0.08;
	private static final double EYE_LENGTH_MULTIPLIER = 0.25;
	private static final double EYE_DIST_FROM_EDGE_MULTIPLIER = 0.05;
	private static int eyeLength;
	private static int eyeWidth;
	private static int eyeDistFromFront;
	private static int eyeSeparationDist;
	private static int eyeDistFromSide;
	
	private static int squareWidth;
	private static int bufferTop;
	private static int bufferLeft;
	private static Board game;
	
	private static final long serialVersionUID = 1L;

	public View(Board game, int squareWidth) {
		View.game = game;
		ArrayList<ArrayList<Pieces>> board = game.getBoard();
		int numRows = board.size();
		int numCols = board.get(0).size();
		View.squareWidth = squareWidth;
		setSize(numCols * (squareWidth + 1) + 1, numRows * (squareWidth + 1) + 1);
		setBackground(BACKGROUND_COLOR);
		
		pieceWidth = (int) (squareWidth * PIECE_WIDTH_MULTIPLIER);
		pieceBezelWidth = (squareWidth - pieceWidth) / 2;
		
		eyeLength = (int) (squareWidth * EYE_LENGTH_MULTIPLIER);
		eyeWidth = (int) (squareWidth * EYE_WIDTH_MULTIPLIER);
		eyeDistFromFront = (int) (squareWidth * EYE_DIST_FROM_EDGE_MULTIPLIER);
		eyeSeparationDist = (int) (squareWidth * EYE_SEPARATION_DIST_MULTIPLIER);
		eyeDistFromSide = (squareWidth - 2 * eyeWidth - eyeSeparationDist) / 2;
	}
	
	public void paint(Graphics g) {
		drawGrid(g);
		drawPieces(g);
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.BLACK);
		Rectangle bounds = this.getBounds();
		int leftoverPixelsHeight = (bounds.height - 1) % (squareWidth + 1);
		int leftoverPixelsWidth = (bounds.width - 1) % (squareWidth + 1);
		bufferTop = leftoverPixelsHeight / 2;
		int bufferBottom = 1 + (leftoverPixelsHeight % 2 == 0 ? bufferTop : bufferTop + 1);
		bufferLeft = leftoverPixelsWidth / 2;
		int bufferRight = 1 + (leftoverPixelsWidth % 2 == 0 ? bufferLeft : bufferLeft + 1);
		
		ArrayList<ArrayList<Pieces>> board = game.getBoard();
		int numRows = board.size();
		int numCols = board.get(0).size();
		
		for (int i = 0; i <= numCols; i++) {
			g.drawLine(bufferLeft + i * (squareWidth + 1), bufferTop, bufferLeft + i * (squareWidth + 1), bufferTop + (squareWidth + 1) * numRows);
		}
		for (int i = 0; i <= numRows; i++) {
			g.drawLine(bufferLeft, bufferTop + i * (squareWidth + 1), bufferLeft + (squareWidth + 1) * numCols, bufferTop + i * (squareWidth + 1));
		}
	}
	private void drawPieces(Graphics g) {
		ArrayList<ArrayList<Pieces>> board = game.getBoard();
		int numRows = board.size();
		int numCols = board.get(0).size();
		for (int i = 0; i < numRows; i++) {
			ArrayList<Pieces> currRow = board.get(i);
			for (int j = 0; j < numCols; j++) {
				switch (currRow.get(j)) {
					case EMPTY:
						drawPieceBody(g, BACKGROUND_COLOR, i, j);
						break;
					case WALL:
						drawPieceBody(g, Color.BLACK, i, j);
						break;
					case PLAYER:
						drawPieceBody(g, Color.WHITE, i, j);
						drawEyes(g, PLAYER_EYE_COLOR, i, j, game.getPlayerOrientation());
						break;
				}
			}
		}
	}
	private void drawPieceBody(Graphics g, Color c, int rowNum, int colNum) {
		g.setColor(c);
		g.fillRect(bufferLeft + pieceBezelWidth + colNum * (1 + squareWidth), bufferTop + pieceBezelWidth + rowNum * (1 + squareWidth), pieceWidth, pieceWidth);
	}
	private void drawEyes(Graphics g, Color eyeColor, int rowNum, int colNum, Directions orientation) {
		// initialize and then calculate
		int x1 = 0;
		int x2 = 0;
		int y1 = 0; 
		int y2 = 0;
		int width = 0;
		int height = 0;
		
		if (orientation == Directions.NORTH || orientation == Directions.SOUTH) {
			x1 = bufferLeft + pieceBezelWidth + colNum * (1 + squareWidth) + eyeDistFromSide;
			x2 = bufferLeft + pieceBezelWidth + colNum * (1 + squareWidth) + eyeDistFromSide + eyeWidth + eyeSeparationDist;
			if (orientation == Directions.NORTH) {
				y1 = y2 = bufferTop + pieceBezelWidth + rowNum * (1 + squareWidth) + eyeDistFromFront;
			} else {
				y1 = y2 = bufferTop + pieceBezelWidth + rowNum * (1 + squareWidth) + squareWidth - eyeDistFromFront - eyeLength;
			}
			width = eyeWidth;
			height = eyeLength;
		} else {
			y1 = bufferTop + pieceBezelWidth + rowNum * (1 + squareWidth) + eyeDistFromSide;
			y2 = bufferTop + pieceBezelWidth + rowNum * (1 + squareWidth) + eyeDistFromSide + eyeWidth + eyeSeparationDist;
			if (orientation == Directions.EAST) {
				x1 = x2 = bufferLeft + pieceBezelWidth + colNum * (1 + squareWidth) + squareWidth - eyeDistFromFront - eyeLength;
			} else {
				x1 = x2 = bufferLeft + pieceBezelWidth + colNum * (1 + squareWidth) + eyeDistFromFront;
			}
			width = eyeLength;
			height = eyeWidth;
		}
		g.setColor(eyeColor);
		g.fillRect(x1, y1, width, height);
		g.fillRect(x2, y2, width, height);
	}
}