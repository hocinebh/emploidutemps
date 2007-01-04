package Interfaces;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.*;

import com.toedter.calendar.*;
import bdd.*;

public class Interface_Reservation {

	
	private final SimpleDateFormat formatjour =  new SimpleDateFormat("dd/MM/yyyy",new Locale("fr","FR"));
	
	
	/* Elements de l'interface */
	private JFrame fenetre = new JFrame();
	private JDateChooser ChoixDate = new JDateChooser();
	private String[] duree = {"00:30","00:45","01:00","01:15","01:30","01:45","02:00","02:15","02:30","02:45","03:00","03:30","04:00","05:00","06:00"};
	private JComboBox Duree = new JComboBox(duree);
	private JTextField Heuredeb = new JTextField("08:00");
	JComboBox Salle = new JComboBox();
	JTextField Matiere = new JTextField();
	JComboBox Groupe = new JComboBox();
	JComboBox Enseignant = new JComboBox();
	JButton Reset = new JButton("Reset");
	
	
	/* Elements d'un cours */
	private Cours nouveaucours;
	private Creneau nouveaucreneau;
	private Matiere nouvellematiere;
	private Groupe nouveaugroupe;
	private Salle nouvellesalle;
	
	/**
     * Centre la fenetre au milieu de l'ecran
     * @param frame - la fenetre
     */
	private void LocationFrame(JFrame frame) {
	   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	   frame.setLocation((screenSize.width / 2) + (420), (screenSize.height / 2) - 300);
	}
	
	public void Affiche_Interface_Reservation(){
		
		fenetre.setTitle("Reservation");
		fenetre.setSize(210,600);
		LocationFrame(fenetre);
		JLabel LReservation = new JLabel("Reservation");
		LReservation.setFont(new Font("Verdana",Font.BOLD,15));
		fenetre.getContentPane().setLayout(new BorderLayout());
		fenetre.getContentPane().add(LReservation,BorderLayout.NORTH);
		JPanel panelcenter = new JPanel();
		panelcenter.setLayout(new GridLayout(14,1));
		
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
		panelcenter.add(Groupe);
		
		/* Enseignant */
		JLabel LEnseignant = new JLabel("Enseignant");
		panelcenter.add(LEnseignant);
		panelcenter.add(Enseignant);
		
		/* Matiere */
		JLabel LMatiere = new JLabel("Matiere");
		panelcenter.add(LMatiere);
		panelcenter.add(Matiere);
		
		/* Salle */
		JLabel LSalle = new JLabel("Salle");
		LSalle.setHorizontalTextPosition(JLabel.CENTER);
		panelcenter.add(LSalle);
		panelcenter.add(Salle);
		fenetre.getContentPane().add(panelcenter,BorderLayout.CENTER);
		
		/* Bouttons valider et effacer */
		JPanel ButtonPanel = new JPanel();
		JButton Valider = new JButton("Valider");
		ButtonPanel.add(Reset);
		ButtonPanel.add(Valider);
		fenetre.getContentPane().add(ButtonPanel,BorderLayout.SOUTH);
		
		/* Action Valider */
		ActionListener valider = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/* on cree un creneau */
				try {
					nouveaucreneau= new Creneau(formatjour.format(ChoixDate.getDate()),Heuredeb.getText(),Duree.getSelectedItem().toString());
					
					/*matiere = new Matiere()
					groupe = new Groupe();	
					enseignant = new Enseignant();*/
					//nouvellesalle = new Salle(); il faut des nouvelles methodes de selection de salle, cours, 
					//nouveaucours= new Cours(nouveaucreneau,nouvellesalle,nouveaugroupe,nouvellematiere);
					//addcourstolistedescours
					//updateEDT
					JOptionPane.showMessageDialog(fenetre,"Cours pris en compte");
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(fenetre,"Erreur de format d'entree","Erreur",JOptionPane.ERROR_MESSAGE);
				}
				finally{
					
					Reset.doClick();
				}
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
				//Groupe.setSelectedIndex(0);
				//Enseignant.setSelectedIndex(0);
				Matiere.setText(null);
				//Salle.setSelectedIndex(0);
				
				
				
			}
		};
		Reset.addActionListener(reset);
		
		fenetre.setVisible(true);
	}
}
