package Interface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Interface_EDT {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame fenetre = new JFrame();
		fenetre.setTitle("Emploi du temps");
		
		/* Labels et boutons NORD */
		JLabel LSemaine = new JLabel("Semaine 41");
		JButton SemainePrec = new JButton("Semaine Precedente");
		JButton SemaineSuiv = new JButton("Semaine Suivante");
		JPanel headerpane = new JPanel();
		
		headerpane.add(SemainePrec,BorderLayout.WEST);
		headerpane.add(SemainePrec,BorderLayout.CENTER);
		headerpane.add(SemainePrec,BorderLayout.EAST);
		
		
		/* Message et Sujet CENTER */
		JLabel LLundi = new JLabel("Lundi");
		JLabel LMardi = new JLabel("Mardi");
		JLabel LMercredi = new JLabel("Mercredi");
		JLabel LJeudi = new JLabel("Jeudi");
		JLabel LVendredi = new JLabel("Vendredi");
		JPanel JoursSemaine = new JPanel();
		JoursSemaine.setLayout(new GridLayout(1,5));
		
		JoursSemaine.add(LLundi);
		JoursSemaine.add(LMardi);
		JoursSemaine.add(LMercredi);
		JoursSemaine.add(LJeudi);
		JoursSemaine.add(LVendredi);
		
		fenetre.setLayout(new BorderLayout());
		fenetre.getContentPane().add(JoursSemaine,BorderLayout.NORTH);
		
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
		mfichier.add(quitter);
		menu.add(mfichier);
		
		
		fenetre.setSize(800,600);
		
		fenetre.setVisible(true);

	}

}
