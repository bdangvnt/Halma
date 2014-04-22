/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;

import javax.swing.JButton;


/**
 * Specific Halma Button
 * */
public class HalmaButton extends JButton{
	private static final long serialVersionUID = -1849313809170351841L;
	private Field field = new Field();
	
	public HalmaButton(){
		this.setBackground(HalmaSettings.BGCOLOR);
	}
	
	public HalmaButton(Field field){
		this.field = field;
		this.setToolTipText("" +field.getXPosition() + "/" + field.getYPosition());
		this.setBackground(HalmaSettings.BGCOLOR);
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
}
