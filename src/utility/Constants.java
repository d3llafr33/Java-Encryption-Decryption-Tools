package utility;

/** This class provides constants used in the application
*
* @author Alessio Della Libera
*/
public class Constants {
	
	public static final String ALGORITHM = "Blowfish";
    public static final String ENCRYPT_MODE = "Encrypt";
    public static final String DECRYPT_MODE = "Decrypt";
    public static final String EXTENSION = ".crypto";
    public static final String PASSWORD_ERROR_LENGTH = "The password must be no more than 16 characters";
    public static final String PASSWORD_ERROR_NULL = "Insert password";
    public static final String PASSWORD_ERROR = "Wrong password";
    public static final String FILE_NOT_FOUND = "File not found";
    public static final String DONE = "Operation completed";
    public static final String PATH_NULL = "Insert a file";
    public static final String DIRECTOY_NULL = "Insert a directory";
    public static final String ERROR_LOADING_LAYOUT = "Error during the layout loading";
    public static final String ERROR_TEXT = "Insert text";
    public static final String HELP = "Help";
    public static final String GUIDE = "Guide";
    public static final String WARNING = "Warning !";
    public static final String MAIN_PROGRAM_HELP = "" +
    		"How to use :\n"+
    		"- Click the button 'Choose File' and choose a file to encrypt/decrypt \n" +
    		"- Click the button 'Choose Path' and choose the output path \n" +
    		"- Digit a password (between 1 to 16 characters) into the password field \n" +
    		"- Click the 'Start' button to start the job \n" +
    		"\n " +
    		"Additional Tools \n"+
    		"Click 'Caesar Cipher' to use the Caesar Cipher;\n" +
    		"Click 'Dynamic Encryption or Decryption' to open a dymanic encryption \n" +
    		"and decryption tool";
    public static final String CAESAR_CIPHER_HELP = "" +
		    "How to use :\n"+
			"- Digit the text in the input area 'Text' \n" +
			"- Choose how many character want to shift \n" +
			"- Click 'Encrypt with Caesar Cipher' to encrypt text \n" +
			"- Click 'Decrypt with Caesar Cipher' to decrypt the encrypted text \n" +
			"\n" +
			"Other buttons : \n" +
			"'Show All' : shows all the possible combinations that could be generated \n" +
			"from the original text ; \n" +
			"'Force It!' : shows all the possible combinations that could be generated \n" +
			"from the encrypted in order to force the 'algorithm' and find the original text \n" +
			"'clear' button, cleans all the input text ";
    public static final String DYNAMIC_TOOL_HELP = "" +
    		"How to use :\n" +
    		"- Digit the text in the input left area text \n" +
    		"- Digit a password in the input password field \n" +
    		"- Change from 'Encrypt' to 'Decrypt' \n" +
    		"- Digit the same passoword in the input password field to \n" +
    		"see the original text or you'll have a surpise !\n" +
    		"\n" +
    		"HAVE FUN !";
    
}
