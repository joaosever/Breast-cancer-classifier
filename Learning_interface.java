package projeto;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



public class Learning_interface {
	private JFrame frame;
	private DataSet T;
	private Classifier c;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Learning_interface window = new Learning_interface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	
	public Learning_interface() {
		initialise();
	}

	/**
	 * Initialise the contents of the frame.
	 */

	private void initialise() {
		frame = new JFrame(); 										
		frame.setBounds(100, 100, 450, 300);							//Define o tamanho e localizacao da janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.getHSBColor((float) 290, (float) 0.1, (float) 0.9 ));     		//Define com HSB a cor do fundo
		
		JFileChooser fileChooser = new JFileChooser();
		
		
		
		JButton btnNewButton = new JButton("Read CSV File");								//nome do botao
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				fileChooser.showOpenDialog(btnNewButton);                					//Abre a janela dos ficheiros
				fileChooser.setDialogTitle("Choose CSV File to submit");					//Titulo da janela dos ficheiros
				
				T = new DataSet(fileChooser.getSelectedFile().getAbsolutePath()); 			//Define o CSV File que vamos usar no dataset
				
				JOptionPane.showMessageDialog(null,"CSV File has been submitted",null, JOptionPane.INFORMATION_MESSAGE);  //pop-up que aparece apos submeter o CSV File
				
			}
		});
		
		
		btnNewButton.setBounds(165, 90, 120, 29);           								//Define a localizacao e tamanho do botao
		btnNewButton.setFont(new Font("STPro", Font.PLAIN, 13));
		btnNewButton.setForeground(Color.getHSBColor((float) 200, (float) 0.6, 1));					//Estas duas linhas definem a font e a cor do texto do botao
		frame.getContentPane().add(btnNewButton);
		
		
		JLabel lblNewLabel = new JLabel("Click to generate a classifier from your CSV file.");             //Instrucao para o botao seguinte
		lblNewLabel.setBounds(75, 175, 250, 16);
		lblNewLabel.setFont(new Font("STPro", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.getHSBColor(200, (float) 0.5, 1));
		frame.getContentPane().add(lblNewLabel);
		
		
		JButton btnNewButton_1 = new JButton("Learn");							//botao "Learn"
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MachineLearning ML = new MachineLearning(T);  					//algoritmo usa o csv para gerar um classificador
				
				c = ML.Get_Classifier();
				
				fileChooser.setDialogTitle("Choose a file");
				int userSelection = fileChooser.showSaveDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					c.WriteToDisk(fileChooser.getSelectedFile().getAbsolutePath());     //write na classe Classifier vai permitir guardar o classificador no disco rigido
				}
				
				JOptionPane.showMessageDialog(null,"A classifier has been saved",null, JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		btnNewButton_1.setBounds(165, 200, 120, 29);
		btnNewButton_1.setFont(new Font("STPro", Font.PLAIN, 13));
		btnNewButton_1.setForeground(Color.getHSBColor((float) 290, (float) 0.6, 1));
		frame.getContentPane().add(btnNewButton_1);
		

	}
}
