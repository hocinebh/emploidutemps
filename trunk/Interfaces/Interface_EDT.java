package Interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Systeme.Jours;
import javax.swing.*;

import java.util.*;

public class Interface_EDT {
	
	public static Calendar maintenant = Calendar.getInstance();
	public static JFrame fenetre = new JFrame();
	private static JLabel LLundi = new JLabel();
	private static JLabel LMardi = new JLabel();
	private static JLabel LMercredi = new JLabel();
	private static JLabel LJeudi = new JLabel();
	private static JLabel LVendredi = new JLabel();
	private static JButton LSemaine = new JButton();
	private static JButton SemainePrec = new JButton();
	private static JButton SemaineSuiv = new JButton();
	/**
	 * @param args
	 */
	public static void afficher_contenu(Jours Semaine){
		LLundi.setText(Semaine.getStringJour1());
		LMardi.setText(Semaine.getStringJour2());
		LMercredi.setText(Semaine.getStringJour3());
		LJeudi.setText(Semaine.getStringJour4());
		LVendredi.setText(Semaine.getStringJour5());
		LSemaine.setText(" Semaine: "+ Semaine.getStringSemaine());
		SemainePrec.setText("Semaine " + Semaine.getStringSemaineprec());
		SemaineSuiv.setText("Semaine " + Semaine.getStringSemaineproch());
	}
	public static void main(String[] args) {
		
		fenetre.setTitle("Emploi du temps");
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		Jours Semaine = new Jours(maintenant);
		/* Labels et boutons NORD */
		
		JPanel headerpane = new JPanel();
		headerpane.setLayout(new BorderLayout());
		headerpane.add(SemainePrec,BorderLayout.WEST);
		headerpane.add(LSemaine,BorderLayout.CENTER);
		headerpane.add(SemaineSuiv,BorderLayout.EAST);
		
		
		/* Message et Sujet CENTER */
		afficher_contenu(Semaine);

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
				maintenant.add(Calendar.WEEK_OF_YEAR,+1);
				Jours Semaine = new Jours(maintenant);
				afficher_contenu(Semaine);
			}
		};
		SemaineSuiv.addActionListener(SemaineSuivante);
		
		ActionListener SemainePrecedente = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				maintenant.add(Calendar.WEEK_OF_YEAR,-1);
				Jours Semaine = new Jours(maintenant);
				afficher_contenu(Semaine);
				
			}
		};
		SemainePrec.addActionListener(SemainePrecedente);
		
		mfichier.add(quitter);
		menu.add(mfichier);
		
		
		fenetre.setSize(800,600);
		
		fenetre.setVisible(true);
		
		
	}
	
}

