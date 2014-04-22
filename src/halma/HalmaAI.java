/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * The artificial intelligence.
 * */
public class HalmaAI {
	
	/***************************************************************************
	 * Calculates hypotenuse to get the distance from one field to another using
	 * a fictive third triangle point (height in fields)
	 * 
	 * @return distance (in fields)
	 **************************************************************************/
	public int getDistance(HalmaMove move) {
		return (int)(Math.hypot(getAbs(move.getToXPosition(), move.getFromXPosition()), getAbs(move.getToYPosition(), move.getFromYPosition())));
	}
	
	/**
	 * Subtracts two integers and gets its absolute value
	 * 
	 * @return absolute value of result
	 * */
	public int getAbs(int x, int y) {
		int abs = x - y;
		if (abs < 0) abs = y - x;
		return(abs);
	}
	
	/**
	 * Determines whether an integer is even or uneven
	 * 
	 * @return absolute value of result
	 * */
	public boolean isEven(int integer) {
		return (integer/2) != Math.round(integer/2);
	}
	
	
	/***************************************************************************
	 * Gets all possible moves which a token can choose. It does so by iterating over all moves
	 * and evaluating isValid() for each move
	 * 
	 * @return list of moves
	 **************************************************************************/
	public ArrayList<HalmaMove> getPossibleMoves(HalmaBoard board, Player currentPlayer){
		Field [][] fields = board.getField();
		ArrayList<HalmaMove> moveList = new ArrayList<HalmaMove>();
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
					if(fields[i][j].getFieldType() == currentPlayer.getId() + 1) {
						for(int a = 0; a < fields.length; a++) {	
							for(int b=0; b<fields[a].length; b++) {
								HalmaMove move = new HalmaMove();
								move.setFromXPosition(fields[i][j].getXPosition());
								move.setFromYPosition(fields[i][j].getYPosition());
								move.setToXPosition(fields[a][b].getXPosition());
								move.setToYPosition(fields[a][b].getYPosition());
								if(board.isMoveValid(move, currentPlayer)) {
									moveList.add(move);
								}
							}
						}
					}
					
				}
			}
		return moveList;
	}
	
	/**
	 * Calculates the rating of a board for a defined player.
	 */
	public int getBoardRating(HalmaBoard board, Player currentPlayer) {
		HalmaMove move = new HalmaMove();		
		int rating[] = new int[HalmaSettings.NUMBER_OF_PLAYERS];
		//for every player sum difference of each figure to target
		//check for every possible move
		Field [][] fields = board.getField();
		for(int i = 0; i < fields.length; i++) {
			for(int j = 0; j < fields[i].length; j++) {
				if(fields[i][j].getFieldType() > 0) { //figure
					//get target-position for player at this position
					int targetX = 0;
					int targetY = 0;
					switch (fields[i][j].getFieldType()) {
					case 1:
						targetX = 24;
						targetY = 24;
						break;
					case 2:
						targetX = 24;
						targetY = 0;
						break;
						//...
					}
					move.setToXPosition(targetX);
					move.setToYPosition(targetY);
					move.setFromXPosition(i);
					move.setFromYPosition(j);
					rating[fields[i][j].getFieldType()-1] += getDistance(move);
				}
			}	
		}
		int ratingForCurrentPlayer = 0;
		switch (currentPlayer.getId() + 1) {
		case 1:
			ratingForCurrentPlayer = rating[1] - rating[0];
			
			break;
		case 2:
			ratingForCurrentPlayer = rating[0] - rating[1];
			break;
			//...
		}
		return ratingForCurrentPlayer;
	}
	
	/**
	 * Initiates ai algorithm
	 * 
	 * @return optimal Move
	 */
	public HalmaMove getAIMove(HalmaBoard board, Player[] player, int playerID, int depth) {
		Node test = new Node();
		test = buildSubtree(board, player, playerID, 3);
		HalmaMove bestMove = findBestMove(test);
		return bestMove;
	}
	 
	
    Vector<Node> basementNodes = new Vector<Node>();
    
	/**
	 * Determines which path leads to the best move from all possible moves without an anchestor.
	 * 
	 * @param node root Node
	 * @return optimal Move
	 */
	public HalmaMove findBestMove(Node node) {
		basementNodes.clear();
		traverse(node);
		Vector<Node> bestMoves = new Vector<Node>();
		int peakRating = -100;
		HalmaMove bestMove = new HalmaMove();
		//TODO not only inspect those "basement nodes"
		for(int i=0; i<basementNodes.size(); i++) {
			Node bNode = (Node)basementNodes.get(i);
			HalmaMove move = bNode.getContent();
			
			if (move.getRating() > peakRating) {
				bestMoves.clear();
				bestMoves.add(bNode);
				peakRating = move.getRating();
				//bestMoveNode = bNode;
			}
			
			if (move.getRating() == peakRating) {
				bestMoves.add(bNode);
			}
		}
		Double val = Math.random() * bestMoves.size();
		
		bestMove = bestMoves.get(val.intValue()).getParent().getParent().getContent();
		//bestMove = bestMoveNode.getParent().getParent().getContent();
		return bestMove;
	}
	
	/** 
	 * Traverses tree in post order 
	 * @param root Node*/
	public void traverse(Node root) {
		for(Node sub : root.getSubNodes()) {
			traverse(sub);
			Collection<Node> col = sub.getSubNodes();
			
			if(col.size() == 0) {
				basementNodes.add(sub);
			}
		}
	}
	
	/** 
	 * Recursively construct the tree with possible moves.
	 * @param Node
	 * */
	public Node buildSubtree(HalmaBoard board, Player[] player, int playerID, int depth) {
		int newPlayerID = 0;
		int peak = -100;
		Node rootNode = new Node();
		Collection<Node> nodeCollection = new Vector<Node>();
		if (depth>0) {
			ArrayList<HalmaMove> moves = getPossibleMoves(board, player[playerID]);
			for (HalmaMove move : moves) {
				if (playerID == 0) newPlayerID = 1; else newPlayerID = 0; //toggle players
				HalmaBoard newBoard = board.clone();
				newBoard.move(player[playerID], move);
				int boardRating = getBoardRating(newBoard, player[playerID]);
				if (boardRating >= peak) {
					Node n = buildSubtree(newBoard, player, newPlayerID, depth - 1 );
					n.setParent(rootNode);
					move.setRating(boardRating);
					n.setContent(move);
					nodeCollection.add(n);
					peak = boardRating;
				}
			}
		}
		rootNode.setSubNodes(nodeCollection);
		return rootNode;
	}
}
