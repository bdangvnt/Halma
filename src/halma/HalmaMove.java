/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

/**
 * HalmaMove.
 * */
public class HalmaMove implements Move {
	private int rating = 0;
	
	//these are coordinates
	private int fromXPosition = 0;
	private int fromYPosition = 0;
	private int toXPosition = 0;
	private int toYPosition = 0;
	
	public HalmaMove() {
	}
	
	public int getFromXPosition() {
		return fromXPosition;
	}

	public void setFromXPosition(int fromXPosition) {
		this.fromXPosition = fromXPosition;
	}

	public int getFromYPosition() {
		return fromYPosition;
	}

	public void setFromYPosition(int fromYPosition) {
		this.fromYPosition = fromYPosition;
	}

	public int getToXPosition() {
		return toXPosition;
	}

	public void setToXPosition(int toXPosition) {
		this.toXPosition = toXPosition;
	}

	public int getToYPosition() {
		return toYPosition;
	}

	public void setToYPosition(int toYPosition) {
		this.toYPosition = toYPosition;
	}
	
	public String toString() {
		return "" + getFromXPosition() + ", " + getFromYPosition() + " --> " + getToXPosition() + ", " + getToYPosition();
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}	
}
