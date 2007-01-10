package Interfaces;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Vector;

import javax.swing.*;

import com.toedter.calendar.*;

import Systeme.Client;
import bdd.*;
/**
 * 
 * @author Alexander Remen
 *
 */
public class Interface_Reservation {

	
	private final SimpleDateFormat formatjour =  new SimpleDateFormat("dd/MM/yyyy",new Locale("fr","FR"));
	private Client Classeclient;
	private Vector[] table;
	
	/* Elements de l'interface */
	private JFrame fenetre = new JFrame();
	private JDateChooser ChoixDate = new JDateChooser();
	private String[] duree = {"00:30","00:45","01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45","03:00","03:30","04:00","05:00","06:00"};
	private JComboBox Duree = new JComboBox(duree);
	private JTextField Heuredeb = new JTextField("08:00");
	private JComboBox ModifReservSalle;
	private JComboBox Salle;
	private JComboBox Matiere;
	private JComboBox Groupe;
	private JComboBox Enseignant;
	private JButton Reset = new JButton("Reset");
	private JButton Valider;
	private JButton Modifier;
	private DefaultComboBoxModel comboboxmodel = new DefaultComboBoxModel();
	
	/* Elements d'un cours */
	private Creneau nouveaucreneau;
	private Matiere nouvellematiere;
	private Groupe nouveaugroupe;
	private Salle nouvellesalle;
	private Enseignant nouvelenseignant;
	
	
	/*
	 *Methode qui reserve le cours a partir des données qu'il va chercher dans l'interface graphique 
	 *
	 */
	private void reserver_cours(){
		try {
			nouveaucreneau= new Creneau(formatjour.format(ChoixDate.getDate()),Heuredeb.getText(),Duree.getSelectedItem().toString());
			nouvellematiere = (Matiere)Matiere.getSelectedItem();
			nouveaugroupe = (Groupe)Groupe.getSelectedItem();
			nouvellesalle = (Salle)Salle.getSelectedItem(); 
			nouvelenseignant =  (Enseignant)Enseignant.getSelectedItem();
			boolean ok = false;
			if(nouvelenseignant != null && nouvellematiere != null && nouveaugroupe != null)
			{
				//Si l'enseignnt n'est pas celui du groupe pour cette matiere
				if(!nouvellematiere.getEnseignant(nouveaugroupe).egal(nouvelenseignant))
				{
					JOptionPane.showMessageDialog(fenetre,"Erreur l'enseignant n'est pas celui du groupe pour cette matière","Erreur",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//on demande la classe client d'envoyer un signal au serveur qui va ajouter le nouceau cours
					if (Classeclient.Ajouter_Cours(nouvellematiere,nouvellesalle,nouveaucreneau, nouveaugroupe, nouvelenseignant)==true)
						JOptionPane.showMessageDialog(fenetre,"Cours pris en compte");
					else 
						JOptionPane.showMessageDialog(fenetre,"Erreur dans l'enregistrement","Erreur",JOptionPane.ERROR_MESSAGE);
					//updateEDT
				}
			}
			else
			{
				//on demande la classe client d'envoyer un signal au serveur qui va ajouter le nouceau cours
				if (Classeclient.Ajouter_Cours(nouvellematiere,nouvellesalle,nouveaucreneau, nouveaugroupe, nouvelenseignant)==true)
					JOptionPane.showMessageDialog(fenetre,"Cours pris en compte");
				else 
					JOptionPane.showMessageDialog(fenetre,"Erreur dans l'enregistrement","Erreur",JOptionPane.ERROR_MESSAGE);
			}
			
			
		} catch (Exception e1) {
			//e1.printStackTrace();
			
			JOptionPane.showMessageDialog(fenetre,"Erreur de format d'entree","Erreur",JOptionPane.ERROR_MESSAGE);
		}
		finally{
			Reset.doClick();
			update_comboboxes();
		}
	}
	
	private void update_comboboxes(){
		table = null;
		try {
			table = Classeclient.Recup_Listes_Reservation();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Vector<Cours> liste_cours=table[4];
		comboboxmodel.removeAllElements();
		if (table[4]!= null)
			table[4].add(0,null);
		int i =0;
		while (i<table[4].size())
		{
			comboboxmodel.addElement(liste_cours.elementAt(i));
			i++;
		}
	}
	/**
     * Centre la fenetre au milieu de l'ecran
     * @param frame - la fenetre
     */
	private void LocationFrame(JFrame frame) {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   frame.setLocation((screenSize.width / 2) + (420-105), (screenSize.height / 2) - 300);
	}
	
	public void Affiche_Interface_Reservation(Client classeclient){
		Classeclient = classeclient;
		update_comboboxes();
		
		fenetre.setTitle("Reservation");
		Actions action = new Actions(Classeclient);
		fenetre.addWindowListener(action.getFermerWindows());
		fenetre.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		fenetre.setSize(210,600);
		LocationFrame(fenetre);
		JLabel LReservation = new JLabel("Reservation");
		LReservation.setFont(new Font("Verdana",Font.BOLD,15));
		fenetre.getContentPane().setLayout(new BorderLayout());
		fenetre.getContentPane().add(LReservation,BorderLayout.NORTH);
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new GridLayout(16,1));
		
		/* Reservations */
		JLabel LReservSalle = new JLabel("Modifier reservations");
		panelcenter.add(LReservSalle);
		/*if (table[4]!= null)
			table[4].add(0,null);*/
		ModifReservSalle = new JComboBox(comboboxmodel);
		panelcenter.add(ModifReservSalle);
		
		/* Date */
		JLabel Ldate = new JLabel("Date :");
		panelcenter.add(Ldate);
		ChoixDate.setLocale(new Locale("fr","FR"));
		ChoixDate.setDate(GregorianCalendar.getInstance().getTime());
		panelcenter.add(ChoixDate);
		
		/* Heure debut */
		JLabel Lheuredeb = new JLabel("Heure debut :");
		panelcenter.add(Lheuredeb);
		Heuredeb.setToolTipText("hh:mm");
		panelcenter.add(Heuredeb);
		
		/* Duree */
		JLabel Lduree = new JLabel("Duree :");
		panelcenter.add(Lduree);
		Duree.setSelectedItem("01:15");
		panelcenter.add(Duree);
		
		/* Groupe */
		JLabel LGroupe = new JLabel("Groupe");
		panelcenter.add(LGroupe);
		table[2].add(0,null);
		Groupe = new JComboBox(table[2]);
		ActionListener modif = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					Enseignant.setSelectedItem(((Matiere)Matiere.getSelectedItem()).getEnseignant((Groupe)Groupe.getSelectedItem()));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			}
		};
		Groupe.addActionListener(modif);
		panelcenter.add(Groupe);
		
		/* Enseignant */
		JLabel LEnseignant = new JLabel("Enseignant");
		panelcenter.add(LEnseignant);
		table[3].add(0, null);
		Enseignant = new JComboBox(table[3]);
		panelcenter.add(Enseignant);
		
		/* Matiere */
		JLabel LMatiere = new JLabel("Matiere");
		panelcenter.add(LMatiere);
		table[1].add(0,null);
		Matiere = new JComboBox(table[1]);
		Matiere.addActionListener(modif);
		panelcenter.add(Matiere);
		
		/* Salle */
		JLabel LSalle = new JLabel("Salle");
		LSalle.setHorizontalTextPosition(JLabel.CENTER);
		panelcenter.add(LSalle);
		Salle = new JComboBox(table[0]);
		panelcenter.add(Salle);
		fenetre.getContentPane().add(panelcenter,BorderLayout.CENTER);
		
				/* Bouttons valider et effacer */
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(new BorderLayout());
		
		Valider = new JButton("Valider");
		ButtonPanel.add(Reset,BorderLayout.SOUTH);
		ButtonPanel.add(Valider,BorderLayout.EAST);
		fenetre.getContentPane().add(ButtonPanel,BorderLayout.SOUTH);
		Modifier = new JButton("Modifier");
		Modifier.setEnabled(false);
		ButtonPanel.add(Modifier,BorderLayout.WEST);
		
		/* Action Valider */
		ActionListener valider = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				reserver_cours();
			}
		};
		Valider.addActionListener(valider);
		
		/* Action Reset */
		ActionListener reset = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ChoixDate.setDate(GregorianCalendar.getInstance().getTime());
				Heuredeb.setText("08:00");
				Duree.setSelectedItem("01:15");
				Groupe.setSelectedIndex(0);
				Enseignant.setSelectedIndex(0);
				Matiere.setSelectedIndex(0);
				Salle.setSelectedIndex(0);
				if (comboboxmodel.getSize()!=0)
				ModifReservSalle.setSelectedIndex(0);				
			}
		};
		Reset.addActionListener(reset);
		
		/* Action Charger Modification*/
		ActionListener changerComboBoxmodification = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Cours coursamodifier = (Cours) ModifReservSalle.getSelectedItem();
				if (coursamodifier!= null)
				{
					Valider.setEnabled(false);
					Modifier.setEnabled(true);
					ChoixDate.setDate(coursamodifier.getCreneau().getDate().getTime());
					Heuredeb.setText(new SimpleDateFormat("HH:mm",new Locale("fr","FR")).format(coursamodifier.getCreneau().getDatedebut().getTime()));
					Duree.setSelectedItem(coursamodifier.getCreneau().getDuree().toString());
					Groupe.setSelectedItem(coursamodifier.getGroupe());
					Enseignant.setSelectedItem(coursamodifier.getEnseignant());
					Matiere.setSelectedItem(coursamodifier.getMatiere());
					Salle.setSelectedItem(coursamodifier.getSalle());
				}
				else{
					Valider.setEnabled(true);
					Modifier.setEnabled(false);
					Reset.doClick();
				}
			}
		};
		ModifReservSalle.addActionListener(changerComboBoxmodification);
		
		
		/* Action modifier */
		ActionListener modifier = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//on enleve le cours a modifier et on rajoute un nouveau
				Cours coursaenlever = (Cours) ModifReservSalle.getSelectedItem();
				//on reserve le cours demandé
				reserver_cours();
				
				
			}
		};
		Modifier.addActionListener(modifier);
		
		fenetre.setVisible(true);
	}
}
