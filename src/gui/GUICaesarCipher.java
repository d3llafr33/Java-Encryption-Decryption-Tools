package gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import utility.Ciphers;
import utility.Constants;

/** Graphical User Interface Class
*
* @author Alessio Della Libera
*/
@SuppressWarnings("rawtypes")
public class GUICaesarCipher extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel lblDecryptedText;
	private JLabel lblEncryptedText;
	private JLabel lblText;
	private JLabel lblShiftCharacter;
	private JScrollPane scrollPane_Top;
	private JScrollPane scrollPane_Middle;
	private JScrollPane scrollPane_Bottom; 
	private JScrollPane scrollPane_Message;
	private JTextArea textArea_Top;
	private JTextArea textArea_Middle;
	private JTextArea textArea_Bottom;
	private JTextArea textArea_Message;
	private JButton buttonCripta;
	private JButton buttonDecripta; 
	private JButton buttonClear;
	private JButton buttonShowAll;
	private JButton buttonBruteForce;
	private Ciphers c;
	private JMenuBar menuBar;	
	private JComboBox shiftKey;
	
	/** 
	 * Class constructor's  that creates the GUI
	 * 
	*/
	
	@SuppressWarnings("unchecked")
	public GUICaesarCipher() {
		c = new Ciphers();		
		setTitle("Caesar Cipher");		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 600);
		
		menuBar = createMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		 
		textArea_Top = new JTextArea();
		textArea_Middle = new JTextArea();
		textArea_Bottom = new JTextArea();
		
		//---------------------------------------------------------JLABEL---------------------------------------------------------
		lblShiftCharacter = new JLabel("Shift character : ");
		lblShiftCharacter.setHorizontalAlignment(SwingConstants.RIGHT);
		lblShiftCharacter.setBounds(460, 164, 101, 28);
		contentPane.add(lblShiftCharacter);
		
		shiftKey = new JComboBox();
		for (int i = 1; i <=25; i++) {
			shiftKey.addItem(i);			
		}					
		shiftKey.setBounds(573, 164, 90, 28);
		contentPane.add(shiftKey);	
		
		lblText = new JLabel("Text");
		lblText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblText.setBounds(8, 18, 142, 28);
		contentPane.add(lblText);
		
		lblEncryptedText = new JLabel("Encrypted Text");
		lblEncryptedText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEncryptedText.setBounds(8, 202, 142, 28);
		contentPane.add(lblEncryptedText);
		
		lblDecryptedText = new JLabel("Decrypted Text");
		lblDecryptedText.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDecryptedText.setBounds(8, 393, 142, 28);
		contentPane.add(lblDecryptedText);
		
		//---------------------------------------------------------JBUTTON---------------------------------------------------------
		buttonShowAll = new JButton("Show All !");
		buttonShowAll.setBounds(10, 164, 115, 28);
		buttonShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(textArea_Top.getText().equals("")){
					JOptionPane.showMessageDialog(null, Constants.ERROR_TEXT, "Messaggio", JOptionPane.ERROR_MESSAGE);
				}
				else{					
					textArea_Message = new JTextArea(c.showAllMatches(textArea_Top.getText()));
					scrollPane_Message = new JScrollPane(textArea_Message); 
					textArea_Message.setEditable(false);
					scrollPane_Message.setPreferredSize( new Dimension( 450, 450 ) );
					JOptionPane.showMessageDialog(null, scrollPane_Message, "All Possible Combinations" , JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		contentPane.add(buttonShowAll);			
		
		
		
		buttonCripta = new JButton("Encrypt with Caesar Cipher");
		buttonCripta.setHorizontalAlignment(SwingConstants.CENTER);				
		buttonCripta.setBounds(238, 164, 198, 28);
		
		buttonCripta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textArea_Top.getText().equals("")){
					JOptionPane.showMessageDialog(null, Constants.ERROR_TEXT, "Messaggio", JOptionPane.ERROR_MESSAGE);
					textArea_Bottom.setText("");
					textArea_Middle.setText("");
					buttonDecripta.setEnabled(false);
				}
				else{
					textArea_Middle.setText(c.encryptOrDecryptWithCaesar(1, textArea_Top.getText(), (Integer)shiftKey.getSelectedItem()));
					buttonDecripta.setEnabled(true);
					buttonBruteForce.setEnabled(true);
					textArea_Bottom.setText("");
				}
			}
		});		
		contentPane.add(buttonCripta);	
		
		buttonBruteForce = new JButton("Force it!");
		buttonBruteForce.setEnabled(false);
		buttonBruteForce.setBounds(10, 355, 125, 28);
		buttonBruteForce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				textArea_Message = new JTextArea(c.bruteForcingCaesar(textArea_Middle.getText()));
				scrollPane_Message = new JScrollPane(textArea_Message); 
				textArea_Message.setEditable(false);
				scrollPane_Message.setPreferredSize( new Dimension( 450, 450 ) );
				JOptionPane.showMessageDialog(null, scrollPane_Message, "Brute Forcing Caesar Cipher" , JOptionPane.PLAIN_MESSAGE);
			}
		});		
		contentPane.add(buttonBruteForce);	
		
		
		buttonDecripta = new JButton("Decrypt with Caesar Cipher");
		buttonDecripta.setEnabled(false);
		buttonDecripta.setHorizontalAlignment(SwingConstants.CENTER);	
		buttonDecripta.setBounds(238, 355, 198, 28);
		buttonDecripta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_Bottom.setText(c.encryptOrDecryptWithCaesar(2, textArea_Middle.getText(), (Integer)shiftKey.getSelectedItem()));
			}
		});
		contentPane.add(buttonDecripta);
		
		buttonClear = new JButton("Clean");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			textArea_Top.setText("");
			textArea_Middle.setText("");
			textArea_Bottom.setText("");
			buttonBruteForce.setEnabled(false);
			buttonDecripta.setEnabled(false);
			}
		});
		buttonClear.setBounds(595, 26, 69, 20);
		contentPane.add(buttonClear);

		//---------------------------------------------------------JSCROLLPANE + JTEXTAREA---------------------------------------------------------
		scrollPane_Top = new JScrollPane();		
		scrollPane_Top.setBounds(8, 46, 666, 106);		
		contentPane.add(scrollPane_Top);	
		textArea_Top.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(192, 192, 192), null, null, null));
		textArea_Top.setFont(new Font("Monospaced", Font.PLAIN, 20));
		scrollPane_Top.setViewportView(textArea_Top);
		
		scrollPane_Middle = new JScrollPane();
		//scrollPane_Middle.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		scrollPane_Middle.setBounds(8, 237, 666, 106);
		contentPane.add(scrollPane_Middle);
		textArea_Middle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		textArea_Middle.setFont(new Font("Monospaced", Font.PLAIN, 20));			
		scrollPane_Middle.setViewportView(textArea_Middle);		
		
		scrollPane_Bottom = new JScrollPane();
		scrollPane_Bottom.setBounds(6, 421, 668, 97);
		contentPane.add(scrollPane_Bottom);
		textArea_Bottom.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		textArea_Bottom.setFont(new Font("Monospaced", Font.PLAIN, 20));			
		scrollPane_Bottom.setViewportView(textArea_Bottom);		
		
		textArea_Top.setLineWrap(true);
		textArea_Top.setAutoscrolls(true);
		
		textArea_Middle.setLineWrap(true);
		textArea_Middle.setAutoscrolls(true);
		
		textArea_Bottom.setLineWrap(true);
		textArea_Bottom.setAutoscrolls(true);
		
		textArea_Top.setText("a b c d e f g h i j k l m n o p q r s t u v w x y z ");
		
		textArea_Top.addKeyListener(new KeyAdapter() {				
			public void keyReleased(KeyEvent e) {
				checkCharacter(textArea_Top.getText());	
			}
			public void keyTyped(KeyEvent e) {
				checkCharacter(textArea_Top.getText());	
			}
			public void keyPressed(KeyEvent e) {
				checkCharacter(textArea_Top.getText());	
			}
		});
 
		textArea_Middle.setEditable(false);
		textArea_Bottom.setEditable(false);
					
	}
	
	/** 
	 * Check that in the text there aren't any characters different from those of the alphabet (Aa - Zz)
	 * 
	 * 	@param text text to check
	*/
	public void checkCharacter(String text){
		Pattern p = Pattern.compile("[^a-zA-Z\\s]");
		boolean hasSpecialChar = p.matcher(text).find();
		Document doc = textArea_Top.getDocument();

		if(hasSpecialChar){
			JOptionPane.showMessageDialog(null, "Insert only alphabet characters");
			try {
				doc.remove(doc.getLength() - 1, 1);
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(null, "Error ! The element to which it refers does not exist");
			}
		}			
	}
	

	/**
	 * Create the JMenubar 
	 * 
	 * @return JMenuBar JFrame's menu
	 */
	public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
        JMenu menuFile = new JMenu(Constants.HELP);
        JMenuItem guida = new JMenuItem(Constants.GUIDE);
        guida.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {        		
        		JOptionPane.showMessageDialog(null, Constants.CAESAR_CIPHER_HELP , "Help" , JOptionPane.INFORMATION_MESSAGE);
			}
		});                 
        menuFile.add(guida);
        menuBar.add(menuFile);
        return menuBar;
    }
}
