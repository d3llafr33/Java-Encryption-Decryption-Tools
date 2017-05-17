package gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;

import utility.Ciphers;
import utility.Constants;
import javax.swing.JButton;

/** Graphical User Interface Class
*
* @author Alessio Della Libera
*/

public class GUIDynamicEncryptionAndDecryption extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField txtKey;
	private JLabel labelKey;
	private JScrollPane scrollPane_Left;
	private JScrollPane scrollPane_Right;
	private JTextArea textArea_Left;
	private JTextArea textArea_Right;
	private JRadioButton rdbtnCripta;
	private JRadioButton rdbtnDecripta; 
	private JButton buttonDefault;
	private ButtonGroup bg;
	private Ciphers c;
	private int mode = 1;
	private JMenuBar menuBar;
	private boolean decripta = false;

	/** 
	 * Class constructor's  that creates the GUI
	 * 
	*/	
	public GUIDynamicEncryptionAndDecryption() {
		c = new Ciphers();
		
		setTitle("Dynamic Encryption and Decryption");		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 668, 513);
		
		menuBar = createMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textArea_Left = new JTextArea();
		textArea_Right = new JTextArea();		
		
		//--------------------------------------------------------------------------JRADIOBUTTON---------------------------------------------------
		rdbtnCripta = new JRadioButton("ENCRYPT");
		rdbtnCripta.setHorizontalTextPosition(SwingConstants.RIGHT);
		rdbtnCripta.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnCripta.setSelected(true);
		rdbtnCripta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				chooseEncryptOrDecrypt();
				if(isDecripta()){
					textArea_Right.setText(textArea_Left.getText());
					textArea_Left.setText("");
					txtKey.setText("");
					rdbtnCripta.setEnabled(false);
				}
				else{
					rdbtnCripta.setEnabled(true);
				}
			}
		});				
		rdbtnCripta.setBounds(0, 415, 99, 35);		
		contentPane.add(rdbtnCripta);	
		
		rdbtnDecripta = new JRadioButton("DECRYPT");
		rdbtnDecripta.setEnabled(false);
		rdbtnDecripta.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnDecripta.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {				
				chooseEncryptOrDecrypt();
				if(isDecripta()){
					textArea_Left.setText(textArea_Right.getText());
					textArea_Right.setText("");
					txtKey.setText("");		
					rdbtnCripta.setEnabled(false);
				}
				else{
					rdbtnCripta.setEnabled(true);
				}				
			}
		});		
		rdbtnDecripta.setBounds(102, 415, 99, 35);
		contentPane.add(rdbtnDecripta);
		
		bg = new ButtonGroup();
		bg.add(rdbtnCripta);
		bg.add(rdbtnDecripta);
		
		//-----------------------------------------------------JLABEL + JTEXTFIELD---------------------------------------------------
		labelKey = new JLabel("PASSWORD : ");
		labelKey.setBorder(null);
		labelKey.setHorizontalAlignment(SwingConstants.RIGHT);
		labelKey.setBounds(310, 415, 100, 35);
		contentPane.add(labelKey);
		
		txtKey = new JTextField();					
		txtKey.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyPressed(KeyEvent e) {
				doJob();
			}
			@Override
			public void keyReleased(KeyEvent e) {
				doJob();
			}
			@Override
			public void keyTyped(KeyEvent e) {
				doJob();
			}
		});
		
		txtKey.setBounds(410, 419, 99, 26);
		contentPane.add(txtKey);
		txtKey.setColumns(10);		
		
		//--------------------------------------------------JSCROLLPANE + JTEXTAREA-------------------------------------
		scrollPane_Left = new JScrollPane();		
		scrollPane_Left.setBounds(0, 0, 330, 416);		
		contentPane.add(scrollPane_Left);	
		textArea_Left.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, new Color(192, 192, 192), null, null, null));
		textArea_Left.setFont(new Font("Monospaced", Font.PLAIN, 20));
		scrollPane_Left.setViewportView(textArea_Left);
		
		scrollPane_Right = new JScrollPane();
		scrollPane_Right.setBounds(330, 0, 330, 416);
		contentPane.add(scrollPane_Right);
		textArea_Right.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, null, null));
		textArea_Right.setFont(new Font("Monospaced", Font.PLAIN, 20));			
		scrollPane_Right.setViewportView(textArea_Right);

		textArea_Left.setLineWrap(true);
		textArea_Left.setAutoscrolls(true);
		
		textArea_Right.setLineWrap(true);
		textArea_Right.setAutoscrolls(true);
		
		//if I write on the right side, it writes even on the left side
		textArea_Left.addKeyListener(new KeyAdapter() {			
			@Override
			public void keyTyped(KeyEvent e) {	 	
				doJob();		
			}
			@Override
			public void keyReleased(KeyEvent e) {
				doJob();
			}
			@Override
			public void keyPressed(KeyEvent e) {
				doJob();
			}
		});
		
		//disable the left side
		textArea_Right.setEditable(false);
		//--------------------------------------------------------------JBUTTON------------------------------------------------------------
		buttonDefault = new JButton("Default");
		buttonDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnCripta.setEnabled(true);
				rdbtnCripta.setSelected(true);
				rdbtnDecripta.setEnabled(false);
				textArea_Left.setText("");
				textArea_Right.setText("");
				txtKey.setText("");
			}
		});
		buttonDefault.setBounds(556, 418, 90, 28);
		contentPane.add(buttonDefault);
	}
	
	
	/** 
	 * Enable or disable the buttons (rdbtnCripta and rdbtnDeCripta) and set variables (mode and decripta) necessary for some control
	 * 
	 */
	public void chooseEncryptOrDecrypt(){
		//if the button encrypt  cripta is seleted, then encrypt the right content and show on the left
		if(rdbtnCripta.isSelected()){
			rdbtnDecripta.setEnabled(false);
			setMode(1);
			setDecripta(false);
		}
		//if the button decrypt is seleted, then decrypt the right content and show on the left
		if(rdbtnDecripta.isSelected()){
			setMode(2);
			setDecripta(true);
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
        		JOptionPane.showMessageDialog(null, Constants.DYNAMIC_TOOL_HELP , "Help" , JOptionPane.INFORMATION_MESSAGE);
			}
		});                 
        menuFile.add(guida);
        menuBar.add(menuFile);
        return menuBar;
    }
		
	/**
	 * Check and Manage the JFrame components' while the user is typing in the password and text area field, and when the radio buttons change status
	 */
	public void doJob(){
		if(!isDecripta()){ //if is set on encrypt mode
			if(!txtKey.getText().trim().equals("") && !textArea_Left.getText().trim().equals("")){
				rdbtnDecripta.setEnabled(true);	
				textArea_Right.setText(c.dynamicEncryptionOrDecryption(mode, textArea_Left.getText(), txtKey.getText()));
				}
			
			else{
				rdbtnDecripta.setEnabled(false);
				textArea_Right.setText("");
			}
		}
		else{//decrypt mode
			if(!txtKey.getText().trim().equals("") && !textArea_Left.getText().trim().equals("")){
				rdbtnCripta.setEnabled(true);
				textArea_Right.setText(c.dynamicEncryptionOrDecryption(mode, textArea_Left.getText(), txtKey.getText()));
			}
			else{
				rdbtnCripta.setEnabled(false);
				textArea_Right.setText("");
			}
		}
	}
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}	

	public boolean isDecripta() {
		return decripta;
	}

	public void setDecripta(boolean decripta) {
		this.decripta = decripta;
	}
}