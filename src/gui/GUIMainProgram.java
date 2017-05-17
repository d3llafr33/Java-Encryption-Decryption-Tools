package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import utility.Ciphers;
import utility.Constants;

/** 
* Graphical User Interface Class
*
* @author Alessio Della Libera
*/

public class GUIMainProgram extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JLabel label;
	private JLabel labelOutput;
	private JLabel labelMode;
	private JLabel labelMessage;
	private JLabel labelAdditionalTools;
	private JTextField textFilePathInputField;
	private JTextField textPasswordField;	
	private JTextField textFilePathOutputField;
	private JButton buttonChooseFile;
	private JButton buttonChoosePath;
	private JButton buttonAction;
	private JButton buttonCaesarCipher;	
	private JButton buttonDynamic;
	private JMenuBar menuBar;
	private Font font;
	private Ciphers c;
	private boolean isEncrypt;	
	
	/** 
	 * Program's start point
	 * 
	 * @param args Commands line options don't used at the moment
	*/
	public static void main(String[] args) {
		try {
			GUIMainProgram frame = new GUIMainProgram();
			frame.setVisible(true);
			frame.setResizable(false);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}			
	}

	/** 
	 * Class constructor's  that creates the GUI
	 * 
	*/
	public GUIMainProgram() {
		setTitle("Encryption / Decryption Tool ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 395, 434);
		
		menuBar = createMenuBar();
		setJMenuBar(menuBar);		
		
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//style/layout of the JFrame "Nimbus"
		try {
			setFrameStyle("Nimbus");
		} catch (ClassNotFoundException e2) {
			JOptionPane.showMessageDialog(null, Constants.ERROR_LOADING_LAYOUT);
		} catch (InstantiationException e2) {
			JOptionPane.showMessageDialog(null, Constants.ERROR_LOADING_LAYOUT);
		} catch (IllegalAccessException e2) {
			JOptionPane.showMessageDialog(null, Constants.ERROR_LOADING_LAYOUT);
		} catch (UnsupportedLookAndFeelException e2) {
			JOptionPane.showMessageDialog(null, Constants.ERROR_LOADING_LAYOUT);
		}
		
		c = new Ciphers();
		font = new Font("Tahoma", Font.PLAIN, 13);
		
		//------------------------------------------------------------JLABEL------------------------------------------------------------
		label = new JLabel("File Input :");
		label.setFont(font);
		label.setBounds(20, 10, 70, 14);
		contentPane.add(label);
		
		labelOutput = new JLabel("Output :");
		labelOutput.setFont(font);
		labelOutput.setBounds(20, 60, 89, 14);
		contentPane.add(labelOutput);
		
		label = new JLabel("Password");
		label.setFont(font);
		label.setBounds(20, 106, 55, 14);
		contentPane.add(label);
		
		label = new JLabel("Mode : ");
		label.setFont(font);
		label.setBounds(20, 157, 44, 14);
		contentPane.add(label);
		
		labelMode = new JLabel("");
		labelMode.setBounds(76, 157, 82, 14);
		contentPane.add(labelMode);
		
		labelMessage = new JLabel("");
		labelMessage.setFont(font);
		labelMessage.setBounds(20, 183, 229, 26);
		contentPane.add(labelMessage);
		
		labelAdditionalTools = new JLabel("Additional Tools");
		labelAdditionalTools.setHorizontalAlignment(SwingConstants.CENTER);
		labelAdditionalTools.setFont(new Font("Gisha", Font.BOLD | Font.ITALIC, 14));
		labelAdditionalTools.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		labelAdditionalTools.setBounds(0, 270, 390, 26);
		contentPane.add(labelAdditionalTools);
		
		//------------------------------------------------------------JTEXTFIELD------------------------------------------------------------
		textFilePathInputField = new JTextField();
		textFilePathInputField.setBounds(17, 24, 246, 26);
		contentPane.add(textFilePathInputField);		
		textFilePathInputField.setEditable(false);
		
		textFilePathOutputField = new JTextField();		
		textFilePathOutputField.setBounds(17, 74, 246, 26);
		contentPane.add(textFilePathOutputField);	
		textFilePathOutputField.setEditable(false);
		
		textPasswordField = new JTextField();
		textPasswordField.setBounds(17, 119, 246, 26);
		contentPane.add(textPasswordField);
		
				
		//------------------------------------------------------------JBUTTON------------------------------------------------------------
		buttonChooseFile = new JButton("Choose File");
		buttonChooseFile.setFont(font);
		//JFileChooser
		buttonChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("File Chooser");
				//set the directory
				File userDirectory = new File(System.getProperty("user.home"));
				jFileChooser.setCurrentDirectory(userDirectory);
				jFileChooser.showOpenDialog(null);	
				//delete the content of different elements
				labelMode.setText("");
				labelMessage.setText("");
				textPasswordField.setText("");
				
				File file = jFileChooser.getSelectedFile();
				if(file != null){
					textFilePathInputField.setText(file.getAbsolutePath().toString());
					setMode();
				}		
				
			}
		});
		buttonChooseFile.setBounds(264, 26, 109, 23);
		contentPane.add(buttonChooseFile);
		
		buttonChoosePath = new JButton("Choose Path");
		buttonChoosePath.setFont(font);
		//JFileChooser
		buttonChoosePath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jFileChooser = new JFileChooser();
				jFileChooser.setDialogTitle("File Chooser");
				File userDirectory = new File(System.getProperty("user.home"));
				jFileChooser.setCurrentDirectory(userDirectory);
				jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = jFileChooser.showOpenDialog(null);
				labelMessage.setText("");
				textPasswordField.setText("");
				File file = null;
			
				if(result == JFileChooser.APPROVE_OPTION) file = jFileChooser.getSelectedFile();
				if(file != null){
					textFilePathOutputField.setText(file.getAbsoluteFile().toString());
				}			
			}
		});
		buttonChoosePath.setBounds(264, 76, 109, 23);
		contentPane.add(buttonChoosePath);
	
		buttonAction = new JButton("Start");
		buttonAction.setBackground(Color.GRAY);
		buttonAction.setFont(new Font("SansSerif", Font.BOLD, 12));
		buttonAction.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {					
				try {
					if(!isError()){
						if(isEncrypt){
							c.encryptOrDecryptFile(textFilePathInputField.getText(), textFilePathOutputField.getText(), textPasswordField.getText(), 2);
						}
						else{
							c.encryptOrDecryptFile(textFilePathInputField.getText(), textFilePathOutputField.getText(), textPasswordField.getText(), 1);
						}
						labelMessage.setForeground(Color.BLUE);
						labelMessage.setText(Constants.DONE);
					}
					 
				} catch (InvalidKeyException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText("Invalid Key (Password non valida)");
				} catch (NoSuchAlgorithmException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText("No Such Algorithm Exception");
				} catch (NoSuchPaddingException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText("No Such Padding Exception");
				} catch (IllegalBlockSizeException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText("Dimensioni della password non valide");
				} catch (BadPaddingException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText(Constants.PASSWORD_ERROR);
				} catch (IOException e1) {
					labelMessage.setForeground(Color.RED);
					labelMessage.setText(Constants.FILE_NOT_FOUND);
				}			
			}
		});
		buttonAction.setBounds(134, 223, 112, 35);
		contentPane.add(buttonAction);
		
		buttonDynamic = new JButton("Dynamic Encryption & Decription !");
		buttonDynamic.setBackground(Color.LIGHT_GRAY);
		buttonDynamic.setFont(new Font("SansSerif", Font.BOLD, 12));
		buttonDynamic.setForeground(Color.BLACK);
		
		buttonDynamic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUIDynamicEncryptionAndDecryption dynamic = new GUIDynamicEncryptionAndDecryption();
				dynamic.setVisible(true);
				dynamic.setResizable(false);
			}
		});
		buttonDynamic.setBounds(145, 308, 228, 46);
		contentPane.add(buttonDynamic);	
		
		
		buttonCaesarCipher = new JButton("Caesar Cipher");		
		buttonCaesarCipher.setBackground(Color.LIGHT_GRAY);
		buttonCaesarCipher.setFont(new Font("SansSerif", Font.BOLD, 12));
		buttonCaesarCipher.setForeground(Color.BLACK);
		
		buttonCaesarCipher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GUICaesarCipher caesar = new GUICaesarCipher();
				caesar.setVisible(true);
				caesar.setResizable(false);
			}
		});
		buttonCaesarCipher.setBounds(11, 308, 122, 46);
		contentPane.add(buttonCaesarCipher);	
	
	}
	
	
	/** 
	 * Set the mode (ENCRYPT_MODE o DECRYPT_MODE)
	 * 
	 */
	private void setMode() {
        if (textFilePathInputField.getText().substring(textFilePathInputField.getText().length() - 6, textFilePathInputField.getText().length()).equalsIgnoreCase(Constants.EXTENSION)) {
        	labelMode.setText(Constants.DECRYPT_MODE);
            isEncrypt = true;
        } else {
        	labelMode.setText(Constants.ENCRYPT_MODE);
        	textPasswordField.setText("");
            isEncrypt = false;
        }	
    }
	
	/** 
	 * Check if the password field and file field are empty end if the password has length grater than 16 characters
	 * 
	 * @return boolean 
	 */
	private boolean isError() {
        boolean trovato = false;
        
        if(textFilePathInputField.getText().equals("")){
        	JOptionPane.showMessageDialog(null, Constants.PATH_NULL , Constants.WARNING , JOptionPane.ERROR_MESSAGE);
            return trovato=true;
        }
        if(textFilePathOutputField.getText().equals("")){
        	JOptionPane.showMessageDialog(null, Constants.PATH_NULL , Constants.WARNING , JOptionPane.ERROR_MESSAGE);
            return trovato=true;
        }
        if (textPasswordField.getText().equals("")) {
        	JOptionPane.showMessageDialog(null, Constants.PASSWORD_ERROR_NULL , Constants.WARNING , JOptionPane.ERROR_MESSAGE);
            return trovato=true;
        }
        else if(textPasswordField.getText().length() > 16) {
        	JOptionPane.showMessageDialog(null, Constants.PASSWORD_ERROR_LENGTH , Constants.WARNING , JOptionPane.ERROR_MESSAGE);
            return trovato=true;
		}        
        
        return trovato;
    }
	
	/** Set the JFrame's style 
	 * 
	 * @param style the chosen style 
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws UnsupportedLookAndFeelException
	 */
	public void setFrameStyle(String style) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
	    LookAndFeelInfo[] plafs = UIManager.getInstalledLookAndFeels();
	    for (LookAndFeelInfo info : plafs) {
	        if (info.getName().contains(style)) {
	        	UIManager.setLookAndFeel(info.getClassName());	        	
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
        		JOptionPane.showMessageDialog(null, Constants.MAIN_PROGRAM_HELP , "Help" , JOptionPane.INFORMATION_MESSAGE);
			}
		});     
                       
        menuFile.add(guida);
        menuBar.add(menuFile);        
        return menuBar;
    }
}
