package cz.cvut.fel.jee.labEshop.util;

import java.util.Random;

import org.jboss.security.auth.spi.Util;

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
	 */
	public static String getHash(String plainPassword) {

		String hashed = Util.createPasswordHash("MD5", Util.BASE64_ENCODING,
				null, null, plainPassword);

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
