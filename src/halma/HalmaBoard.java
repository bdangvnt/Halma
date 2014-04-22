/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;


import java.util.ArrayList;
import java.util.Observable;

/**
 * Halma board.
 */
public class HalmaBoard extends Observable implements Board {

	private int fieldWidth = 0;
	private int fieldHeight = 0;
	private int rating;
	public Field field[][];
	private Field startField = new Field();
	private int boardType;

	/** all fields visited during a multi jump */
	private ArrayList<Field> path = new ArrayList<Field>();

	/** marks end of a jump sequence */
	private static final int SEPARATOR = -10;

	/** jump sequences -> parts of a multi jump */
	private ArrayList<ArrayList<Field>> subPaths = new ArrayList<ArrayList<Field>>();

	// Constructors
	public HalmaBoard() {
		boardType = HalmaSettings.BOARD_STARLIKE;
		fieldHeight = 36;
		fieldWidth = 25;
		field = new Field[fieldHeight][fieldWidth];
	}

	/**
	 * Returns the clone of itself. 
	 * 
	 * @return HalmaBoard
	 */
	public HalmaBoard clone() {
		HalmaBoard board = new HalmaBoard();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				Field tmpField = this.field[i][j];
				Field field = new Field(tmpField.getFieldType(), tmpField.getHomeBaseFromPlayer(), tmpField.getEndBaseFromPlayer(), tmpField.getXPosition(),
						tmpField.getYPosition(), tmpField.isAsleep());
				board.setField(i, j, field);
			}
		}
		return (board);
	}

	public HalmaBoard(int boardType) {
		this.boardType = boardType;
		fieldHeight = 36;
		fieldWidth = 25;
		field = new Field[fieldHeight][fieldWidth];
	}

	// Getters and Setters
	public int getWidth() {
		return fieldWidth;
	}

	public int getHeight() {
		return fieldHeight;
	}

	public Field getField(int x, int y) {
		return field[x][y];
	}

	public void setField(int x, int y, Field field) {
		this.field[x][y] = field;
	}

	public Field[][] getField() {
		return field;
	}

	/**
	 * Defines the board as a two-dimensional array (n * n). Field types are declared in HalmaSettings.
	 * 
	 */
	public void createInitialField() {
		switch (this.boardType) {
		case HalmaSettings.BOARD_QUADRATICALLY:
			break;

		case HalmaSettings.BOARD_STARLIKE:
			// black out everything
			for (int i = 0; i < fieldHeight; i++) {
				for (int j = 0; j < fieldWidth; j++) {
					// field[i][j]=-1;
					field[i][j] = new Field(-1, 0, 0, i, j, false);
				}
			}
			// field
			int center = 13;
			for (int i = 10; i < 18; i++) {
				for (int j = center - ((i - 4) / 2); j < center + ((i - 2) / 2); j++) {
					field[i][j - 1] = new Field(0, 0, 0);
					field[34 - i][j - 1] = new Field(0, 0, 0);
					j++;
				}
				i++;
			}

			// players
			center = 13;
			for (int i = 0; i < 12; i++) {
				for (int j = center - (i / 2); j < center + (i / 2); j++) {
					// check for start- and end base
					switch (HalmaSettings.NUMBER_OF_PLAYERS) {
					case 2:
						field[i - 2][j] = new Field(0, 0, 0);
						field[36 - i][j] = new Field(0, 0, 0);
						j++;
						break;
					case 3:
						field[i - 2][j] = new Field(1, 1, 0);
						field[36 - i][j] = new Field(0, 0, 1);
						j++;
						break;
					case 4:
						field[i - 2][j] = new Field(0, 0, 0);
						field[36 - i][j] = new Field(0, 0, 0);
						j++;
						break;
					case 6:
						field[i - 2][j] = new Field(1, 1, 4);
						field[36 - i][j] = new Field(4, 4, 1);
						j++;
						break;
					}

				}
				i++;
			}
			center = 5;
			for (int i = 8; i < 20; i++) {
				for (int j = center - ((i - 8) / 2); j < center + ((i - 8) / 2); j++) {

					switch (HalmaSettings.NUMBER_OF_PLAYERS) {
					case 2:
						field[26 - i][j + 16] = new Field(2, 2, 0);
						field[i + 8][j] = new Field(0, 0, 2);
						field[26 - i][j] = new Field(1, 1, 0);
						field[i + 8][j + 16] = new Field(0, 0, 1);
						j++;
						break;
					case 3:
						field[26 - i][j + 16] = new Field(3, 3, 0);
						field[i + 8][j] = new Field(0, 0, 3);
						field[26 - i][j] = new Field(2, 2, 0);
						field[i + 8][j + 16] = new Field(0, 0, 2);
						j++;
						break;
					case 4:
						field[26 - i][j + 16] = new Field(2, 2, 3);
						field[i + 8][j] = new Field(3, 3, 2);
						field[26 - i][j] = new Field(1, 1, 4);
						field[i + 8][j + 16] = new Field(4, 4, 1);
						j++;
						break;
					case 6:
						field[26 - i][j + 16] = new Field(2, 2, 5);
						field[i + 8][j] = new Field(5, 5, 2);
						field[26 - i][j] = new Field(6, 6, 3);
						field[i + 8][j + 16] = new Field(3, 3, 6);
						j++;
						break;
					}
				}
				i++;
			}

			for (int i = 0; i < 26; i++) {
				for (int j = 8; j < 18; j++) {
					field[8][j] = new Field(0, 0, 0);
					field[26][j] = new Field(0, 0, 0);
					j++;
				}
				i++;
			}
			field[10][7] = new Field(0, 0, 0);
			field[10][17] = new Field(0, 0, 0);
			field[12][6] = new Field(0, 0, 0);
			field[12][18] = new Field(0, 0, 0);
			field[14][5] = new Field(0, 0, 0);
			field[14][19] = new Field(0, 0, 0);
			field[16][4] = new Field(0, 0, 0);
			field[16][20] = new Field(0, 0, 0);
			field[18][4] = new Field(0, 0, 0);
			field[18][20] = new Field(0, 0, 0);
			field[24][7] = new Field(0, 0, 0);
			field[24][17] = new Field(0, 0, 0);
			field[22][6] = new Field(0, 0, 0);
			field[22][18] = new Field(0, 0, 0);
			field[20][5] = new Field(0, 0, 0);
			field[20][19] = new Field(0, 0, 0);
			for (int i = 18; i < 36; i++) {
				for (int j = 0; j < 25; j++) {
					field[i - 2][j] = field[i][j];
				}
				i++;
			}
			field[34][12] = new Field(-1, 0, 0);
			break;
		}
		// set field positions
		for (int i = 0; i < this.field.length; i++) {
			for (int j = 0; j < this.field[i].length; j++) {
				field[i][j].setXPosition(i);
				field[i][j].setYPosition(j);
			}
		}
		// now, notify observer
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Performs a move (there is no validation in this method)
	 * 
	 */
	public void move(Player currentPlayer, Move move) {
		HalmaMove halmaMove = (HalmaMove) move;
		Field tmp = this.getField(halmaMove.getFromXPosition(), halmaMove.getFromYPosition());
		tmp.setFieldType(0);
		this.setField(halmaMove.getFromXPosition(), halmaMove.getFromYPosition(), tmp);
		tmp = this.getField(halmaMove.getToXPosition(), halmaMove.getToYPosition());
		tmp.setFieldType(currentPlayer.getId() + 1);
		this.setField(halmaMove.getToXPosition(), halmaMove.getToYPosition(),tmp);

		setChanged();
		notifyObservers(this);
	}
	
	public void forceUpdate() {
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Determines if the game has been won. If true, currentPlayer is victorious.
	 * 
	 */
	protected boolean isGameOver() {
		// count figures
		int[] inEndBase = new int[3];
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				for (int k = 1; k < 3; k++) {
					if (k == getField(i, j).getEndBaseFromPlayer() && getField(i, j).getEndBaseFromPlayer() == getField(i, j).getFieldType())
						inEndBase[k]++;
				}
			}
		}
		if (inEndBase[1] == 10)
			return (true);
		if (inEndBase[2] == 10)
			return (true);
		return (false);
	}

	/**
	 * Debug method which prints the current board on the console (ASCII art style)
	 * 
	 */
	public void showField() {
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j].getFieldType() != -1)
					System.out.print(" " + field[i][j].getFieldType());
				else
					System.out.print("  ");
			}
			System.out.println();
		}
	}

	/**
	 * Checks recursively if a move is compliant to Halma rules.
	 */
	public boolean isMoveValid(Move move, Player player) {

		boolean valid = false;

		HalmaMove halmaMove = (HalmaMove) move;

		// global -> used by checkJumpMulti
		startField = field[halmaMove.getFromXPosition()][halmaMove
				.getFromYPosition()];
		Field targetField = field[halmaMove.getToXPosition()][halmaMove
				.getToYPosition()];

		// target field free?
		if (targetField.getFieldType() != HalmaSettings.EMPTY_FIELD)
			return false;

		// was own token moved? FieldInfo == PlayerId +1 (playerIds are
		// starting width 0)
		if (player.getId() + 1 != startField.getFieldType())
			return false;

		// get the neighbors of start field
		ArrayList<Field> neighbors = getNeighborList(startField);

		// single move to neighbor field?
		for (int i = 0; i < neighbors.size(); i++) {
			Field field = neighbors.get(i);

			// Target found -> Single move to neighbor field
			if (field.getXPosition() == targetField.getXPosition()
					&& field.getYPosition() == targetField.getYPosition()) {
				return true;
			}
		}

		// single jump?
		if (!valid) {
			valid = checkSingleJump(halmaMove);
		}

		/*
		 * multi jump? just search recursively, if threre are no single move or
		 * single jump conditions
		 */
		if (!valid) {
			// filter all occupied fields and check each
			ArrayList<Field> occFields = getOccupiedFields(neighbors);
			for (int i = 0; i < occFields.size(); i++) {
				Field field = occFields.get(i);
				valid = checkMultiJump(field.getXPosition(), field
						.getYPosition(), targetField, field.getXPosition(),
						field.getYPosition());

				// don't reuse jump path at next search
				path.clear();
				if (valid) {
					return valid;
				} else // check other neighbors
				{
					continue;
				}

			}
			// don't reuse path at next players move
			path.clear();
		}

		return valid;

	}

	/***************************************************************************
	 * Searches for single jump conditions (jump over one occupied field) The job
	 * of checkSingleJump could also be done by checkMultiJump, but there is no
	 * need to browse the hole board if target field is near by.
	 * 
	 * @return is Move a single jump valid according to Halma rules
	 **************************************************************************/
	private boolean checkSingleJump(HalmaMove move) {

		Field startField = field[move.getFromXPosition()][move
				.getFromYPosition()];
		Field targetField = field[move.getToXPosition()][move.getToYPosition()];

		// getNeighbors
		ArrayList<Field> neighbors = getNeighborList(startField);
		// Filter for occupiedFields
		ArrayList<Field> occFields = getOccupiedFields(neighbors);

		for (int i = 0; i < occFields.size(); i++) {
			Field occ = occFields.get(i);

			// get neighbors of occupied fields
			ArrayList<Field> neighbor2 = getNeighborList(occ);
			// Filter free fields
			ArrayList<Field> frees = getFreeFields(neighbor2);

			// Search for target
			for (int j = 0; j < frees.size(); j++) {
				Field free = frees.get(j);

				/*
				 * Target found? check direction, if not valid, whole validation
				 * returns false
				 */
				if (free.getXPosition() == targetField.getXPosition()
						&& free.getYPosition() == targetField.getYPosition()) {

					if (!isDirectionValid(startField, free)) {
						return false;
					} else {
						return true;
					}
				}
			}

		}
		return false;
	}

	/***************************************************************************
	 * Searches for multi jump conditions (jump over more than one occupied
	 * fields)
	 * 
	 * @return is move a multi jump which is valid according to Halma rules
	 **************************************************************************/
	private boolean checkMultiJump(int x, int y, Field targetField,
			int previousX, int previousY) {
		Field currentField = field[x][y];
		String status = getFieldState(currentField);

		// field visited and no separator field? -> loop condition?
		if (isFieldVisited(path, currentField)
				&& currentField.getXPosition() != SEPARATOR) {

			if (checkLoop()) {
				return false;
			}
		}

		// Target found! -> Move is valid :))
		if (x == targetField.getXPosition() && y == targetField.getYPosition()) {
			// Flush old coordinates
			previousX = 0;
			previousY = 0;
			// check last free field to check correct direction
			if (getLastFreeField(path) != null) {
				if (isDirectionValid(getLastFreeField(path), currentField)) {
					return true;
				}
			} else // if there is no last free field, direction is always valid
			{
				return true;
			}

		}

		// horizontal search forward ignoring last checked field
		if (y < this.getWidth() - 2 && !(y + 2 == previousY)
				&& !getFieldState(field[x][y + 2]).equals("invalid"))

			// ignore startfield
			if (!(startField.getYPosition() == y + 2 && startField
					.getXPosition() == x))

				// Alternating field state?
				if (!getFieldState(field[x][y + 2]).equals(status)) {
					Field field2 = field[x][y];

					// Valid Direction? Validation just works with free fields
					if (getFieldState(field[x][y + 2]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if ((isDirectionValid(getLastFreeField(path),
									field[x][y + 2]))) {
								path.add(field2);
								if (checkMultiJump(x, y + 2, targetField, x, y))
									return true;
							}

						}// no last free field? then it's the beginning of a
							// multi jump
						else if ((isDirectionValid(startField, field[x][y + 2]))) {
							path.add(field2);
							if (checkMultiJump(x, y + 2, targetField, x, y))
								return true;
						}

					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x, y + 2, targetField, x, y))
							return true;
					}
				}

		// horizontal search backward
		if (y > 2 && !(y - 2 == previousY)
				&& !(getFieldState(field[x][y - 2]).equals("invalid")))
			// ignore startfield
			if (!(startField.getYPosition() == y - 2 && startField
					.getXPosition() == x))
				// Alternating field state?
				if (!getFieldState(field[x][y - 2]).equals(status)
						&& !getFieldState(field[x][y - 2]).equals("invalid")) {
					Field field2 = field[x][y];

					// Valid Direction? Validation just works with free fields
					if (getFieldState(field[x][y - 2]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if ((isDirectionValid(getLastFreeField(path),
									field[x][y - 2]))) {
								path.add(field2);
								if (checkMultiJump(x, y - 2, targetField, x, y))
									return true;
							}
						}// no last free field? then it's the beginning of a
							// multi jump
						else if (isDirectionValid(startField, field[x][y - 2])) {
							path.add(field2);
							if (checkMultiJump(x, y - 2, targetField, x, y))
								return true;
						}
					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x, y - 2, targetField, x, y))
							return true;
					}

				}

		// positive diagonal search forward
		if (x < this.getHeight() - 2 && x > 0 && y > 0
				&& y < this.getWidth() - 1
				&& (!(x - 2 == previousX) || !(y + 1 == previousY)))
			// ignore startfield
			if (!(startField.getYPosition() == y + 1 && startField
					.getXPosition() == x - 2))
				// Alternating field state?
				if (!getFieldState(field[x - 2][y + 1]).equals(status)
						&& !getFieldState(field[x - 2][y + 1])
								.equals("invalid")) {
					Field field2 = field[x][y];

					// Valid Direction? Validation just works with free fields
					if (getFieldState(field[x - 2][y + 1]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if ((isDirectionValid(getLastFreeField(path),
									field[x - 2][y + 1]))) {
								path.add(field2);
								if (checkMultiJump(x - 2, y + 1, targetField,
										x, y)) {
									return true;
								}
							}
						}// no last free field? then it's the beginning of a
							// multi jump
						else if ((isDirectionValid(startField,
								field[x - 2][y + 1]))) {
							path.add(field2);
							if (checkMultiJump(x - 2, y + 1, targetField, x, y)) {
								return true;
							}
						}

					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x - 2, y + 1, targetField, x, y)) {
							return true;
						}
					}
				}

		// positive diagonal search backward
		if (x < this.getHeight() - 2 && x > 0 && y < this.getWidth() - 1
				&& y > 0 && (!(x + 2 == previousX) || !(y - 1 == previousY)))
			// ignore startfield
			if (!(startField.getYPosition() == y - 1 && startField
					.getXPosition() == x + 2))

				// Alternating field state?
				if (!getFieldState(field[x + 2][y - 1]).equals(status)
						&& !getFieldState(field[x + 2][y - 1])
								.equals("invalid")) {
					Field field2 = field[x][y];

					// Valid Direction? Validation just works with free fields
					if (getFieldState(field[x + 2][y - 1]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if (isDirectionValid(getLastFreeField(path),
									field[x + 2][y - 1])) {
								path.add(field2);
								if (checkMultiJump(x + 2, y - 1, targetField,
										x, y)) {
									return true;
								}
							}
						}// no last free field? then it's the beginning of a
						// multi jump
						else if (isDirectionValid(startField,
								field[x + 2][y - 1])) {
							path.add(field2);
							if (checkMultiJump(x + 2, y - 1, targetField, x, y)) {
								return true;
							}
						}

					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x + 2, y - 1, targetField, x, y)) {
							return true;
						}
					}
				}

		// negative diagonal search forward
		if (x < this.getHeight() - 2 && x > 0 && y > 0
				&& y < this.getWidth() - 1
				&& (!(x - 2 == previousX) || !(y - 1 == previousY)))
			// ignore startfield
			if (!(startField.getYPosition() == y - 1 && startField
					.getXPosition() == x - 2))

				// Alternating field state?
				if (!getFieldState(field[x - 2][y - 1]).equals(status)
						&& !getFieldState(field[x - 2][y - 1])
								.equals("invalid")) {
					Field field2 = field[x][y];

					// Valid Direction? Validation just works with free fields
					if (getFieldState(field[x - 2][y - 1]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if (isDirectionValid(getLastFreeField(path),
									field[x - 2][y - 1])) {
								path.add(field2);
								if (checkMultiJump(x - 2, y - 1, targetField,
										x, y)) {
									return true;
								}
							}

						}// no last free field? then it's the beginning of a
						// multi jump
						else if (isDirectionValid(startField,
								field[x - 2][y - 1])) {
							path.add(field2);
							if (checkMultiJump(x - 2, y - 1, targetField, x, y)) {
								return true;
							}
						}
					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x - 2, y - 1, targetField, x, y)) {
							return true;
						}
					}

				}

		// negativ diagonal search backward
		if (x < this.getHeight() - 2 && x > 0 && y < this.getWidth() - 1
				&& y > 0 && (!(x + 2 == previousX) || !(y + 1 == previousY)))
			// ignore startfield
			if (!(startField.getYPosition() == y + 1 && startField
					.getXPosition() == x + 2))

				// Alternating field state and valid field?
				if (!getFieldState(field[x + 2][y + 1]).equals(status)
						&& !getFieldState(field[x + 2][y + 1])
								.equals("invalid")) {
					Field field2 = field[x][y];

					// Valid direction?
					// validation just works from one free field to another
					if (getFieldState(field[x + 2][y + 1]).equals("free")) {
						if (path.size() > 0 && getLastFreeField(path) != null) {
							if (isDirectionValid(getLastFreeField(path),
									field[x + 2][y + 1])) {
								path.add(field2);
								if (checkMultiJump(x + 2, y + 1, targetField,
										x, y)) {
									return true;
								}
							}

						}// no last free field? then it's a beginning of a
						// multi jump
						else if (isDirectionValid(startField,
								field[x + 2][y + 1])) {
							path.add(field2);
							if (checkMultiJump(x + 2, y + 1, targetField, x, y)) {
								return true;
							}
						}

					}// free field? look futher, situation unclear
					else {
						path.add(field2);
						if (checkMultiJump(x + 2, y + 1, targetField, x, y)) {
							return true;
						}
					}
				}

		// Add dummy separator field to mark end of jump sequence
		path.add(new Field(SEPARATOR, SEPARATOR, SEPARATOR, SEPARATOR,
				SEPARATOR, false));
		return false;
	}

	/**
	 * Searches for last visited free field within jump path
	 * 
	 */
	private Field getLastFreeField(ArrayList<Field> path) {
		Field field = new Field();
		if (path.size() > 0) {
			for (int i = path.size() - 1; i >= 0; i--) {
				field = path.get(i);
				if (getFieldState(field).equals("free")) {
					return field;
				}
			}

		} else {
			return null;
		}
		return null;
	}

	/**
	 * Copies an ArrayList of fields
	 * 
	 * @return new ArrayList containing copied content
	 */
	private ArrayList<Field> clone(ArrayList<Field> list) {
		ArrayList<Field> newList = new ArrayList<Field>();
		for (int i = 0; i < list.size(); i++) {
			newList.add(list.get(i));
		}
		return newList;
	}

	/***************************************************************************
	 * Searches for twofold jump sequences
	 * 
	 * @return true if jump path contains the same sequence of fields more than
	 *         once
	 **************************************************************************/
	public boolean findDoubleSubPath(ArrayList<Field> sample) {
		for (int i = 0; i < subPaths.size(); i++) {
			ArrayList<Field> sub = subPaths.get(i);
			if (sub.size() == sample.size()) {
				for (int j = 0; j < sub.size(); j++) {
					for (int k = 0; k < sample.size(); k++) {
						if (sub.get(i).getXPosition() != sample.get(k)
								.getXPosition()) {
							return false;
						}
					}
				}
			}

		}
		return true;
	}

	/***************************************************************************
	 * Searches for loop conditions within multi jump. There are no Halma rules
	 * that avoid senseless looping, so jump path has to be analyzed.
	 * 
	 * @return true if there are twofold jump sequences
	 **************************************************************************/
	private boolean checkLoop() {
		// Copy path
		ArrayList<Field> tempPath = clone(path);

		// get last jump sequence from last separator to next backward
		ArrayList<Field> sample = extractSubPathBackward(tempPath);

		// remove all up to last separator Field
		for (int i = tempPath.size() - 1; i >= 0; i--) {

			if (tempPath.get(i).getXPosition() != SEPARATOR) {
				tempPath.remove(i);
			}
		}

		while (tempPath.size() < 0) {
			ArrayList<Field> subPath = extractSubPathBackward(tempPath);
			subPaths.add(subPath);
			// remove all up to last seperator Field
			for (int i = tempPath.size() - 1; i >= 0; i--) {

				if (tempPath.get(i).getXPosition() != SEPARATOR) {
					tempPath.remove(i);
				}
			}
		}

		return findDoubleSubPath(sample);
	}

	/** Gets subsequence from one separator Field (x=-10) to next backward */
	/**
	 * Gets subsequence from one seperator Field (x=-10) to next backward
	 */
	private static ArrayList<Field> extractSubPathBackward(ArrayList<Field> path) {

		ArrayList<Field> subPath = new ArrayList<Field>();

		// get subpath
		// search for identical number sequences
		for (int i = path.size() - 1; i >= 0; i--) {
			// Path section from last separator to next separator, to get jump
			// sequence
			if (path.get(i).getXPosition() == SEPARATOR) {
				for (int j = i - 1; j >= 0; j--) {
					if (path.get(j).getXPosition() != SEPARATOR && j >= 1) {
						subPath.add(path.get(j));
					} else {
						return revertPath(subPath);
					}
				}

			}
		}

		return revertPath(subPath);
	}

	// Reverting ArrayList of fields
	public static ArrayList<Field> revertPath(ArrayList<Field> path) {

		ArrayList<Field> revertedPath = new ArrayList<Field>();

		for (int i = path.size() - 1; i >= 0; i--) {
			revertedPath.add(path.get(i));
		}

		return revertedPath;
	}

	/***************************************************************************
	 * Checks if a field is part of the jump path
	 **************************************************************************/
	private boolean isFieldVisited(ArrayList<Field> fieldList, Field field) {
		for (int i = 0; i < fieldList.size(); i++) {
			Field oldField = fieldList.get(i);
			if (oldField.getXPosition() == field.getXPosition()
					&& oldField.getYPosition() == field.getYPosition()) {
				return true;
			}
		}
		return false;
	}

	// TODO -> Enums
	private static String getFieldState(Field field) {
		String status = "";
		if (field.getFieldType() > 0) {
			status = "occupied";
		} else if (field.getFieldType() == 0) {
			status = "free";
		} else {
			status = "invalid";
		}
		return status;
	}

	/** Filter all occupied fields from an list of fields */
	private ArrayList<Field> getOccupiedFields(ArrayList<Field> fieldList) {
		ArrayList<Field> occupiedFields = new ArrayList<Field>();

		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);

			if (field.getFieldType() > 0) {
				occupiedFields.add(field);
			}
		}
		return occupiedFields;
	}

	/** Filter all free fields from an list of fields */
	private ArrayList<Field> getFreeFields(ArrayList<Field> fieldList) {
		ArrayList<Field> freeFields = new ArrayList<Field>();

		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);

			if (field.getFieldType() == HalmaSettings.EMPTY_FIELD) {
				freeFields.add(field);
			}
		}
		return freeFields;
	}

	/**
	 * Gets the surrounding fields of a defined field excluding the field itself,
	 * just valid fields are added (no dummy fields).
	 * 
	 * Just in case of a field at the edge of board, dummy fields are created to
	 * avoid errors
	 * 
	 * @return ArrayList surrounding Fields of a defined Field
	 */
	private ArrayList<Field> getNeighborList(Field field) {
		ArrayList<Field> neighborList = new ArrayList<Field>();

		// Get the Board
		Field[][] fields = getField();

		int x = field.getXPosition();
		int y = field.getYPosition();

		for (int i = x - 2; i <= x + 2; i++) {
			for (int j = y - 2; j <= y + 2; j++) {
				Field neighborField = new Field();

				// Field within board range?
				if (i >= 0 && j >= 0 && i < (fields.length)
						&& j < (fields[i].length)) {
					Field arrField = fields[i][j];

					// no dummy field? (fieldType != -1)
					if (arrField.getFieldType() != HalmaSettings.NO_FIELD) {

						// The targetField itself is not part of the
						// neighborhood

						if ((arrField.getXPosition() != field.getXPosition())
								|| (arrField.getYPosition() != field
										.getYPosition())) {

							neighborField = arrField;
							neighborList.add(arrField);
						}

					}
				} else {// create dummy fields (Fields out of board range)
					neighborField.setFieldType(HalmaSettings.NO_FIELD);
					neighborField.setHomeBaseFromPlayer(0);
					neighborField.setEndBaseFromPlayer(0);
					neighborField.setXPosition(HalmaSettings.DUMMY_POSITION);
					neighborField.setYPosition(HalmaSettings.DUMMY_POSITION);
				}
			}
		}

		return neighborList;
	}

	/***************************************************************************
	 * Checks if the moving direction is valid according to the halma rules: a correct
	 * move has to be on a line without changes of direction whithin one single
	 * move.
	 * 
	 * it validates just the relation between a target field and the last free
	 * field
	 * 
	 * 
	 * @param startfield, targetfield
	 *            pairs of start field and target field
	 * @return boolean returns true, if direction is valid
	 * 
	 **************************************************************************/
	private boolean isDirectionValid(Field startField, Field targetField) {

		int startX = startField.getXPosition();
		int startY = startField.getYPosition();
		int targetX = targetField.getXPosition();
		int targetY = targetField.getYPosition();

		if ((startX == targetX - 4) && (startY == targetY - 2)) {
			return true;
		} else if ((startX == targetX - 4) && (startY == targetY + 2)) {
			return true;
		} else if ((startX == targetX) && (startY == targetY + 4)) {
			return true;
		} else if ((startX == targetX) && (startY == targetY - 4)) {
			return true;
		} else if ((startX == targetX + 4) && (startY == targetY + 2)) {
			return true;
		} else if ((startX == targetX + 4) && (startY == targetY - 2)) {
			return true;
		} else {
			return false;
		}
	}


	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
