package utility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/** This class provides methods used for encryption and decryption
*
* @author Alessio Della Libera
*/

public class Ciphers {	
	
	//------------------------------------------START METHOD ENCRYPT OR DECRYPT FOR FUNNY------------------------------------------	
	/**
	 * Encrypt and decrypt text dynamically
	 * 
	 * @param mode the mode that could be ENCRYPT_MODE (1) or DECRYPT_MODE (2)
	 * @param text text to be encrypted or decrypted
	 * @param password password used for encryption or decryption 
	 * 
	 * @return the text encrypted or decrypted
	 */
	public String dynamicEncryptionOrDecryption(int mode, String text, String password) {		
		password = password.replaceAll("\\s", "");
		Random random = new Random(password.hashCode());
		char c;
		String result = "";
		if(!password.equals("")){
		if(mode==Cipher.ENCRYPT_MODE){
			for (int i = 0; i < text.length(); i++) {
					c = (char)(text.charAt(i) + (random.nextInt(687-192)+192) - (random.nextInt(687-192)+192));
				result += c; 
			}
		}
		
		else if(mode==Cipher.DECRYPT_MODE){
			for (int i = 0; i < text.length(); i++) {			
					c = (char)(text.charAt(i) - (random.nextInt(687-192)+192) + (random.nextInt(687-192)+192));	
				result += c; 			
			}			
		}
		}
		return result;	
    }

    //------------------------------------------START METHOD ENCRYPT OR DECRYPT------------------------------------------
	/**
	 * Encryption or Decryption a file 
	 * 
	 * @param in the path of the input file 
	 * @param out the path of the output file
	 * @param password password used for encryption or decryption 
	 * @param mode mode in which the cipher is set (ENCRYPT_MODE or DECRYPT_MODE)
	 * 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws IOException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * 
	 */
	public void encryptOrDecryptFile(String in, String out,  String password, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException{
			File inputFile = new File(in);
			Key secretKey = new SecretKeySpec(password.getBytes(), Constants.ALGORITHM);
			Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
            cipher.init(mode, secretKey);
            
            FileInputStream fis = new FileInputStream(inputFile);
            FileOutputStream fos = null;
            
            byte[] inputBytes = new byte[(int) inputFile.length()];
            
            fis.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);
            
            if(mode==1){
            	fos = new FileOutputStream(out+"\\"+inputFile.getName().concat(Constants.EXTENSION));
            }
            else if(mode==2){
               	fos = new FileOutputStream(out+"\\"+inputFile.getName().replaceAll(Constants.EXTENSION, ""));   
            }
            
            fos.write(outputBytes);             
            fis.close();
            fos.close();
			
	}
	

	//------------------------------------------START METHOD ENCRYPT OR DECRYPT WITH CAESAR CIPHER------------------------------------------
	//ASCII a = 97 z = 122
	//ASCII a = 65 z = 90
	/**
	 * Choose if encrypt or decrypt using Caesar Cipher
	 * 
	 * @param mode the mode that could be ENCRYPT (1) or DECRYPT (2)
	 * @param text text to be encrypted or decrypted
	 * @param shift number of characters to shift
	 * 
	 * @return the text encrypted or decrypted
	 * 
	 */
	public String encryptOrDecryptWithCaesar(int mode, String text ,int shift){
		if(mode == 1)return encryptWithCaesar(text, shift);
		else if(mode == 2)return decryptWithCaesar(text, shift);		
		return null;
	}
	
	/**
	 * Encrypt text using the Caesar Cipher
	 * 
	 * @param text text to be encrypted
	 * @param shift number of characters to shift
	 * 
	 * @return the text encrypted
	 */
	public String encryptWithCaesar(String text, int shift){
		char c, temp;
		String result = "";		
		for (int i = 0; i < text.length(); i++) {					
			temp = text.charAt(i);
			text = text.replaceAll("\\s+", " ");
			if (((Character.isUpperCase(temp)  && (temp + shift)>'Z'  ))|| (temp + shift)>'z'  ) {
				c = (char) ((temp + shift) - 26);				
			}
			else if(temp == ' ' || temp == '\n' || temp == '\t') {
				c = ' ';
			}	
			else{
				c = (char) (temp + shift);
			}
			result += c;			
		}		
		return result;
	}
	
	/**
	 * Decrypt text using the Caesar Cipher
	 * 
	 * @param text text to be decrypted
	 * @param shift number of characters to shift
	 * 
	 * @return the text decrypted
	 */
	public String decryptWithCaesar(String text, int shift){
		char c, temp;
		String result = "";		
		for (int i = 0; i < text.length(); i++) {
			temp = text.charAt(i);
			if (  ((Character.isUpperCase(temp)  && (temp - shift) >= 'A'  )) || (temp - shift) >= 'a'  ) {
				c = (char) ((temp - shift));				
			}
			else if(temp == ' ' || temp == '\n' || temp == '\t') {
				c = ' ';
			}
			else {
				c = (char) ((temp - shift)+ 26);
			}			
			result += c;	
		}
		return result;
	}
		
	/**
	 * Compute all possible combinations generated by a string using Caesar Cipher
	 * 
	 * @param text text from which compute all possible combinations
	 * 
	 * @return the text with all possibilities combinations 
	 */
	public String showAllMatches(String text){		
		char temp,c;
		String result = "";
		for (int i = 0; i < 26; i++) {
			result += "Shift "+i+" : ";			
			for (int j = 0; j < text.length(); j++) {
				temp = text.charAt(j);
				text = text.replaceAll("\\s+", " ");
				if (((Character.isUpperCase(temp)  && (temp + i)>'Z'  ))|| (temp + i)>'z'  ) {
					c = (char) ((temp + i) - 26);				
				}
				else if(temp == ' ' || temp == '\n' || temp == '\t') {
					c = ' ';
				}	
				else{
					c = (char) (temp + i);
				}
				result += c;				
			}
			result += "\n";
		}
		return result;
	}
		
	/** 
	 * Compute all possible combinations (including the original string ) generated by a encrypted text using Caesar Cipher 
	 * 
	 * @param text encrypted from which compute all possible combinations , including the original string
	 * 
	 * @return text with all possibilities combinations 
	 */
	public String bruteForcingCaesar(String text){
		char temp,c;
		String result ="";
		for (int i = 0; i < 26; i++) {
			result += "Shift "+i+" : ";			
			for (int j = 0; j < text.length(); j++) {
				temp = text.charAt(j);
				text = text.replaceAll("\\s+", " ");
				if (  ((Character.isUpperCase(temp)  && (temp - i) >= 'A'  )) || (temp - i) >= 'a'  ) {
					c = (char) ((temp - i));					
				}
				else if(temp == ' ' || temp == '\n' || temp == '\t') {
					c = ' ';
				}	
				else{
					c = (char) ((temp - i)+ 26);
				}
				result += c;				
			}
			result += "\n";
		}
		return result;
	}

}
