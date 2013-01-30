package cz.cvut.fel.jee.labEshop.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Password class contains methods for encrypting passwords and for generating
 * random passwords
 * 
 * @author Michal Horak
 * 
 */
public class Password {

	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random rnd = new Random();

	/**
	 * Method generates hashed password with MD5 algorithm and Base64 encoding
	 * 
	 * @param string
	 *            to be hashed
	 * @return secured password
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String getHash(String plainPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest md = MessageDigest.getInstance("MD5");

		md.update(plainPassword.getBytes("UTF-8"));

		byte[] hashedByteArray = md.digest();

		String hashed = javax.xml.bind.DatatypeConverter
				.printBase64Binary(hashedByteArray);

		return hashed;
	}

	/**
	 * Method generates random password with length 8
	 * 
	 * @return random password
	 */
	public static String getRandomPlainPassword() {
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}
