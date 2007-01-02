package Interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Interface_Connexion {

	/**
	 * Methode qui affiche la fenetre de connexion
	 *
	 */
	public static void main(String[] args) {
		JFrame fenetre = new JFrame("Connexion");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel Llogin = new JLabel("Login");
		JLabel Lmdp = new JLabel("Mot de passe");
		JTextField TFlogin = new JTextField(15);
		JPasswordField TFmdp = new JPasswordField(15);
		JButton Valider = new JButton("Connexion");
		GridBagLayout layout = new GridBagLayout();
		JPanel pconnexion = new JPanel(layout);
		
		
				
		JMenuBar menu = new JMenuBar();
		JMenu mfichier = new JMenu("Fichier");
		JMenuItem quitter = new JMenuItem("Quitter");
		
		
		ActionListener fermer = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int r = JOptionPane.showConfirmDialog(null,"Veux tu vraiment quitter? Tu vas regretter...","Fermeture",JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.YES_OPTION){
					System.exit(1);
				}
				
			}
		};
		
		ActionListener valider = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//TODO envoi un signal au serveur avec 2 parametres le nom et le mdp
				boolean test= false; //retour de envoi_signal();
				if (test == false){
					JOptionPane.showMessageDialog(null,"Connexion echou�","Connexion echou�",JOptionPane.ERROR_MESSAGE);
				}
				else{
					//TODO afficher son emploi du temps 
				}
					
			}
		};
		Valider.addActionListener(valider);
		quitter.addActionListener(fermer);
		mfichier.add(quitter);
		menu.add(mfichier);
		
		
		fenetre.setSize(800,600);
		//fenetre.getContentPane().setLayout(layout);
		
		/*====================INSERTIONS DES ELEMENTS===================*/
		
		//Jlabel llogin
		GridBagConstraints gcLlogin = new GridBagConstraints();
		gcLlogin.gridx = 1;
		gcLlogin.gridy = 1;
		gcLlogin.gridheight = 5;
		gcLlogin.gridwidth= 2;
		gcLlogin.weightx =50;
		gcLlogin.weighty =10;
		gcLlogin.insets = new Insets(10, 10, 20, 10);
		gcLlogin.anchor = GridBagConstraints.CENTER;
		
		pconnexion.add(Llogin, gcLlogin);
		
		//JTextField TFlogin
		GridBagConstraints gcTFlogin = new GridBagConstraints();
		gcTFlogin.gridx = 3;
		gcTFlogin.gridy = 1;
		gcTFlogin.gridheight = 5;
		gcTFlogin.gridwidth= 2;
		gcTFlogin.weightx =50;
		gcTFlogin.weighty =10;
		gcTFlogin.insets = new Insets(10, 10, 20, 10);
		gcTFlogin.anchor = GridBagConstraints.CENTER;
		
		pconnexion.add(TFlogin, gcTFlogin);
		
		//Jlabel lmdp
		GridBagConstraints gcLmdp = new GridBagConstraints();
		gcLmdp.gridx = 1;
		gcLmdp.gridy = 7;
		gcLmdp.gridheight = 5;
		gcLmdp.gridwidth= 2;
		gcLmdp.weightx =50;
		gcLmdp.weighty =10;
		gcLmdp.insets = new Insets(1, 10, 20, 10);
		gcLmdp.anchor = GridBagConstraints.CENTER;
		
		pconnexion.add(Lmdp, gcLmdp);
		
		//JTextField TFmdp
		GridBagConstraints gcTFmdp = new GridBagConstraints();
		gcTFmdp.gridx = 3;
		gcTFmdp.gridy = 7;
		gcTFmdp.gridheight = 5;
		gcTFmdp.gridwidth= 2;
		gcTFmdp.weightx =50;
		gcTFmdp.weighty =10;
		gcTFmdp.insets = new Insets(1, 10, 20, 10);
		gcTFmdp.anchor = GridBagConstraints.CENTER;
		
		pconnexion.add(TFmdp, gcTFmdp);
		
		//JButton Valider
		GridBagConstraints gcval = new GridBagConstraints();
		gcval.gridx = 3;
		gcval.gridy = 14;
		gcval.gridheight = 5;
		gcval.gridwidth = 2;
		gcval.weightx = 50;
		gcval.weighty =10;
		gcval.insets = new Insets(1, 10, 10, 10);
		gcval.anchor = GridBagConstraints.SOUTH;
		
		pconnexion.add(Valider, gcval);
		
		pconnexion.setSize(50, 50);
				
		fenetre.getContentPane().add(pconnexion, BorderLayout.CENTER);
		fenetre.setJMenuBar(menu);
		
		fenetre.pack();
		fenetre.setVisible(true);


	}

}
