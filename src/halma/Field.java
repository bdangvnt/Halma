/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/

package halma;

/**
 * Specific Halma Field
 * */
public class Field {
	private int fieldType = 0;

	private int homeBaseFromPlayer = 0;
	
	private int endBaseFromPlayer = 0;
	
	private int xPosition = 0;
	
	private int yPosition = 0;
	
	private boolean isAsleep = false;

	public boolean isAsleep() {
		return isAsleep;
	}

	public void setAsleep(boolean isAsleep) {
		this.isAsleep = isAsleep;
	}

	public Field(){}
	
	public Field(int x, int y){
		this.setXPosition(x);
		this.setYPosition(y);
	}

	public Field(int fieldType, int homeBase, int endBase) {
		this.fieldType = fieldType;
		this.homeBaseFromPlayer = homeBase;
		this.endBaseFromPlayer = endBase;
	}
	
	public Field(int fieldType, int homeBase, int endBase,int x, int y, boolean isAsleep) {
		this.fieldType = fieldType;
		this.homeBaseFromPlayer = homeBase;
		this.endBaseFromPlayer = endBase;
		this.setXPosition(x);
		this.setYPosition(y);
		this.setAsleep(isAsleep);
	}
	
	public int getFieldType() {
		return fieldType;
	}

	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}

	public int getHomeBaseFromPlayer() {
		return homeBaseFromPlayer;
	}

	public void setHomeBaseFromPlayer(int homeBaseFromPlayer) {
		this.homeBaseFromPlayer = homeBaseFromPlayer;
	}

	public int getEndBaseFromPlayer() {
		return endBaseFromPlayer;
	}

	public void setEndBaseFromPlayer(int endBaseFromPlayer) {
		this.endBaseFromPlayer = endBaseFromPlayer;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int position) {
		xPosition = position;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int position) {
		yPosition = position;
	}
}
