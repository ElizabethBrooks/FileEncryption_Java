/*
Author: Elizabeth Brooks
File: ED_EB.java
Date Modified: 09/23/2016
*/

//Class imports
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

//Class for encrypting and decrypting specified java files
public class ED_EB{
    //Class fields
    private static Cipher ecipher;
    private static Cipher dcipher;
    // 8-byte initialization vector
    private static byte[] iv = {
	  (byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
	  (byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3
    };
    //The main method	
    public static void main(String[] args) {
        try {
            SecretKey key = KeyGenerator.getInstance("DES").generateKey();
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            ecipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
   			dcipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
   			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
   			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
   			encrypt(new FileInputStream("./ModEvo_DaphniaMelanica/src/GeneralModel.java"), 
               new FileOutputStream("./ModEvo_DaphniaMelanica/src/GeneralModel.java"));
			   encrypt(new FileInputStream("./ModEvo_DaphniaMelanica/src/IndividualFitness.java"), 
               new FileOutputStream("./ModEvo_DaphniaMelanica/src/IndividualFitness.java"));
            //Encrypt specified file
            encrypt(new FileInputStream("./ModEvo_DaphniaMelanica/src/IndividualTraitOne.java"), 
               new FileOutputStream("./ModEvo_DaphniaMelanica/src/IndividualTraitOne.java"));
            //Decrypt specified file
            decrypt(new FileInputStream("./ModEvo_ModelingEvolutionaryTrajectories/src/GaussianTraitDistribution_ED.java"), 
            	new FileOutputStream("./ModEvo_ModelingEvolutionaryTrajectories/src/GaussianTraitDistribution_DE.java"));
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found:" + e.getMessage());
            return;
        }
        catch (InvalidAlgorithmParameterException e) {
            System.out.println("Invalid Alogorithm Parameter:" + e.getMessage());
            return;
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm:" + e.getMessage());
            return;
        }
        catch (NoSuchPaddingException e) {
            System.out.println("No Such Padding:" + e.getMessage());
            return;
        }
        catch (InvalidKeyException e) {
            System.out.println("Invalid Key:" + e.getMessage());
            return;
        }
        System.out.println(" COMPLETED");
    }
    //Encrypting method
    private static void encrypt(InputStream is, OutputStream os) {
		try {
			byte[] buf = new byte[1024];
			// bytes at this stream are first encoded
			os = new CipherOutputStream(os, ecipher);
			// read in the clear text and write to out to encrypt
			int numRead = 0;
			while ((numRead = is.read(buf)) >= 0) {
				os.write(buf, 0, numRead);
			}
			// close all streams
			os.close();
		}
		catch (IOException e) {
			System.out.println("I/O Error:" + e.getMessage());
		}
      System.out.print("ENCRYPTION");
	}
    //Decrypting method	
    private static void decrypt(InputStream is, OutputStream os) {
		try {
			byte[] buf = new byte[1024];
			// bytes read from stream will be decrypted
			CipherInputStream cis = new CipherInputStream(is, dcipher);
			// read in the decrypted bytes and write the clear text to out
			int numRead = 0;
			while ((numRead = cis.read(buf)) >= 0) {
				os.write(buf, 0, numRead);
			}
			// close all streams
			cis.close();
			is.close();
			os.close();
		}
		catch (IOException e) {
			System.out.println("I/O Error:" + e.getMessage());
		}
      System.out.print("DECRYPTION");
    }
}
//End class
