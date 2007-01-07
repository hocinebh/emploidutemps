package Interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import Systeme.*;

public class Actions {

	private Client Classeclient;
	
	
	
	ActionListener fermer1 = new ActionListener()
	{public void actionPerformed(ActionEvent e){fermeture();}};
	
	WindowAdapter fermer2 = new WindowAdapter()
	{public void windowClosing(WindowEvent e){fermeture();}};
	
	private void fermeture()
	{
		int r = JOptionPane.showConfirmDialog(null,"Veux tu vraiment quitter? Tu vas regretter...","Fermeture",JOptionPane.YES_NO_OPTION);
		if (r == JOptionPane.YES_OPTION){
			Classeclient.FermerConnexion();
			System.exit(1);
		}
	}


	public Actions(Client classeclient) {
		Classeclient = classeclient;
	}



	public ActionListener getFermerButton() {
		return fermer1;
	}


	/**
	 * @return the fermer2
	 */
	public WindowAdapter getFermerWindows() {
		return fermer2;
	}

	
	
}
