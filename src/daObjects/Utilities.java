package daObjects;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Utilities {

	static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static Calendar cal = Calendar.getInstance();
	static String dateToday = dateFormat.format(cal.getTime());
	private static final String alphaNumeric = "0123456789";

	public static boolean isNotNull(String txt) {
		// System.out.println("Inside isNotNull");
		return txt != null && txt.trim().length() >= 0 ? true : false;
	}

	public int randomNumber() {
		Random rnd = new Random();
		int x = 10000000 + rnd.nextInt(90000000);
		return x;
	}

	public String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int index = (int) (Math.random() * alphaNumeric.length());
			builder.append(alphaNumeric.charAt(index));
		}
		return builder.toString();
	}

	public static String MD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			// Now we need to zero pad it if you actually want the full 32
			// chars.
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}
	 /**
     * Utility method to save InputStream data to target location/file
     * @param inStream - InputStream to be saved
     * @param target - full path to destination file
     */
	public static void saveToFile(InputStream inStream, String target) throws IOException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];

		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
    
    /**
     * Creates a folder to desired location if it not already exists
     * @param dirName - full path to the folder
     * @throws SecurityException - in case you don't have permission to create the folder
     */
	public static void createFolderIfNotExists(String dirName) throws SecurityException {
    	File theDir = new File(dirName);
    	if (!theDir.exists()) {
    		theDir.mkdir();
    	}
    }

	
	
}
