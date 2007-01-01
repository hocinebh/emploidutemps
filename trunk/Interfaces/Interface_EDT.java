package Interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Systeme.Jours;
import javax.swing.*;

import java.util.*;
import java.text.*;

public class Interface_EDT {
	
	public static Calendar maintenant = Calendar.getInstance();
	public static JFrame fenetre = new JFrame();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		fenetre.setTitle("Emploi du temps");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Jours Semaine = new Jours(maintenant);
		/* Labels et boutons NORD */
		JButton LSemaine = new JButton(" Semaine: "+ Semaine.getStringSemaine());
		JButton SemainePrec = new JButton("Semaine " + Semaine.getStringSemaineprec());
		JButton SemaineSuiv = new JButton("Semaine " + Semaine.getStringSemaineproch());
		JPanel headerpane = new JPanel();
		headerpane.setLayout(new BorderLayout());
		headerpane.add(SemainePrec,BorderLayout.WEST);
		headerpane.add(LSemaine,BorderLayout.CENTER);
		headerpane.add(SemaineSuiv,BorderLayout.EAST);
		
		
		/* Message et Sujet CENTER */
		JLabel LLundi = new JLabel(Semaine.getStringJour1());
		JLabel LMardi = new JLabel(Semaine.getStringJour2());
		JLabel LMercredi = new JLabel(Semaine.getStringJour3());
		JLabel LJeudi = new JLabel(Semaine.getStringJour4());
		JLabel LVendredi = new JLabel(Semaine.getStringJour5());
		JPanel JoursSemaine = new JPanel();
		JoursSemaine.setLayout(new GridLayout(1,5));
		JoursSemaine.add(LLundi);
		JoursSemaine.add(LMardi);
		JoursSemaine.add(LMercredi);
		JoursSemaine.add(LJeudi);
		JoursSemaine.add(LVendredi);
		
		JPanel toppanel = new JPanel();
		toppanel.setLayout(new BorderLayout());
		toppanel.add(headerpane,BorderLayout.NORTH);
		toppanel.add(JoursSemaine,BorderLayout.SOUTH);
		
		fenetre.setLayout(new BorderLayout());
		fenetre.getContentPane().add(toppanel,BorderLayout.NORTH);
		
		JMenuBar menu = new JMenuBar();
		JMenu mfichier = new JMenu("Fichier");
		JMenuItem quitter = new JMenuItem("Quitter");
		
		
		ActionListener fermer = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int r = JOptionPane.showConfirmDialog(null,"Veux tu vraiment quitter?","Fermeture",JOptionPane.YES_NO_OPTION);
				if (r == JOptionPane.YES_OPTION){
					System.exit(1);
				}
				
			}
		};
		quitter.addActionListener(fermer);
		
		ActionListener SemaineSuivante = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				maintenant.add(Calendar.DAY_OF_YEAR,+7);
				Jours Semaine = new Jours(maintenant);
				/** TODO how do I repaint the panel ? */
			}
		};
		SemaineSuiv.addActionListener(SemaineSuivante);
		
		ActionListener SemainePrecedente = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				maintenant.add(Calendar.DAY_OF_YEAR,+7);
				Jours Semaine = new Jours(maintenant);
				
			}
		};
		SemainePrec.addActionListener(SemainePrecedente);
		
		mfichier.add(quitter);
		menu.add(mfichier);
		
		
		fenetre.setSize(800,600);
		
		fenetre.setVisible(true);

	}

}
