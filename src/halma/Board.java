/**
 * @author Simon Bahl, Christian Stangier, Laura Neuser 
 * @date 24.01.2008
 * @version	0.9
 * */

package halma;

/**
 * basic game functionality
 * */
public interface Board {
	public void createInitialField();
	
	/**
	 * Moves a player's token to a defined field. Start and destination fields
	 * are specified in the Move object
	 */
	public void move(Player currentPlayer, Move move);
	public boolean isMoveValid(Move move, Player player);
}