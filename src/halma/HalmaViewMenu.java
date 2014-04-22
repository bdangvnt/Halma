/** 
 * @author Simon Bahl, Laura Neuser, Christian Stangier 
 * @date 24.01.2008
 * @version	0.9
 * **/
package halma;

import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
/**
 * Menu Bar for GUI
 * */
public class HalmaViewMenu {
	private JMenuBar menuBar;
	private JMenu menu;
	
	public void getNull() {
	}
	
	public HalmaViewMenu(JFrame frame, HalmaSettings.menu choice, HalmaBoard board) {
		menuBar = new JMenuBar();
		
		switch(choice) {
		case defaultMenu:
			menu = new JMenu("Spiel");
			//menuItem = new JMenuItem("Neues Spiel", KeyEvent.VK_T);
			JMenuItem close = new JMenuItem("Beenden", KeyEvent.VK_T);
			ButtonGroup group = new ButtonGroup();
			JRadioButtonMenuItem manVsMan = new JRadioButtonMenuItem("Mensch gegen Mensch", new ImageIcon("img/icon.png"));
			JRadioButtonMenuItem manVsMachine = new JRadioButtonMenuItem("Mensch gegen Maschine", new ImageIcon("img/icon.png"));
			JRadioButtonMenuItem machineVsMachine = new JRadioButtonMenuItem("Maschine gegen Maschine", new ImageIcon("img/icon.png"));
			manVsMachine.setSelected(true);
			
			group.add(manVsMan);
			group.add(manVsMachine);
			group.add(machineVsMachine);
			
			menu.add(manVsMan);
			menu.add(manVsMachine);
			menu.add(machineVsMachine);
			
			menu.add(close);
			
			menuBar.add(menu);
			
			menu = new JMenu("†ber");
			JMenuItem about = new JMenuItem("About");
			menu.add(about);
			
			menuBar.add(menu);
			
			about.setActionCommand("about");
			about.addActionListener(HalmaController.getInstance(board, this));
			
			close.setActionCommand("close");
			close.addActionListener(HalmaController.getInstance(board, this));
			
			manVsMachine.setActionCommand("manVsMachine");
			manVsMachine.addActionListener(HalmaController.getInstance(board, this));
			
			manVsMan.setActionCommand("manVsMan");
			manVsMan.addActionListener(HalmaController.getInstance(board, this));
			
			machineVsMachine.setActionCommand("machineVsMachine");
			machineVsMachine.addActionListener(HalmaController.getInstance(board, this));

			break;
		}
		
		frame.setJMenuBar(menuBar);
	}
	

}
