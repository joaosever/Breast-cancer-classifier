package projeto;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Classifiying_interface {
	
	private JFrame frame;
	private Classifier c;
	static int result;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Classifiying_interface window = new Classifiying_interface();
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
	
	public Classifiying_interface(){
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);						//Define o tamanho e localizacao da janela
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.getHSBColor((float) 290, (float) 0.1, (float) 0.9));
		
		JFileChooser fileChooser = new JFileChooser();
		JButton btnNewButton = new JButton("Load Classifier");			//botao para submeter o classificador gerado
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser.showOpenDialog(btnNewButton);
				fileChooser.setDialogTitle("Input classifier saved previously");
				File f1 = fileChooser.getSelectedFile();    
				
				c = Classifier.ReadFromDisk(f1.getAbsolutePath());  //"c" passa a ser o classificador submetido
				
				JOptionPane.showMessageDialog(null,"The classifier was successfully submitted",null, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnNewButton.setBounds(165, 20, 120, 29);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.setFont(new Font("STPro", Font.PLAIN, 13));
		btnNewButton.setForeground(Color.getHSBColor((float) 200, (float) 0.6, 1));		//font, cor, tamanho e localizacao
		
		
		JLabel lblNewLabel = new JLabel("Insert Data:");						
		lblNewLabel.setBounds(10, 60, 100, 16);
		frame.getContentPane().add(lblNewLabel);
		lblNewLabel.setFont(new Font("STPro", Font.PLAIN, 13));
		lblNewLabel.setForeground(Color.getHSBColor(200, (float) 0.5, 1));	
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 80, 430, 20);
		frame.getContentPane().add(textArea_1);
		textArea_1.setBackground(Color.white);
		
		
		JLabel lblNewLabel2 = new JLabel("Results:");
		lblNewLabel2.setBounds(10, 170, 100, 16);
		frame.getContentPane().add(lblNewLabel2);
		lblNewLabel2.setFont(new Font("STPro", Font.PLAIN, 13));
		lblNewLabel2.setForeground(Color.getHSBColor(200, (float) 0.5, 1));	
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBounds(10, 190, 430, 20);
		frame.getContentPane().add(textArea_2);
		textArea_2.setBackground(Color.white);
		System.out.println(result);			      				//da output do resultado obtido na operação classify
		textArea_2.setBackground(Color.white);
		
		
		JButton btnNewButton_1 = new JButton("Classify");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amostra = textArea_1.getText();  
			    int[] a = DataSet.convert(amostra);     //converte a linha usando "convert" do DataSet de forma a aplicar depois o classify
			    result = c.Classify(a);
				textArea_2.setText(String.valueOf(result));    ////da output do resultado obtido na operacao classify na textArea_2
			}
		});
		btnNewButton_1.setBounds(180, 130, 90, 29);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("STPro", Font.PLAIN, 13));
		btnNewButton_1.setForeground(Color.getHSBColor((float) 200, (float) 0.6, 1));	

		
		
		
		
	}
}
