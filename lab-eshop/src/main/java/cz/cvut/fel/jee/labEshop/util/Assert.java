package cz.cvut.fel.jee.labEshop.util;

/**
 * Utility class containing methods which asserts program invariants.
 * 
 * @author Kamil Prochazka (<a href="mailto:prochka6@fel.cvut.cz">prochka6</a>)
 */
public final class Assert {

	private Assert() {
	}

	/**
	 * Assert that the given reference to object is <code>not</code> null. If is
	 * null throws IllegalArgumentException.
	 * 
	 * @param reference
	 *            the referenced object
	 * @throws IllegalArgumentException
	 *             if given reference is null.
	 */
	public static void notNull(Object reference) {
		notNull(reference, null);
	}

	/**
	 * Assert that the given reference to object is <code>not</code> null. If is
	 * null throws IllegalArgumentException with given errorMessage.
	 * 
	 * @param reference
	 *            the referenced object
	 * @param errorMessage
	 *            the error message used in IllegalArgumentException.
	 * @throws IllegalArgumentException
	 *             if given reference is null.
	 */
	public static void notNull(Object reference, String errorMessage) {
		if (reference == null) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * Assert that the given string is <code>not null</code> and
	 * <code>not empty</code>.
	 * 
	 * @see #notEmpty(String, String)
	 * @param string
	 *            the string to check
	 * @throws IllegalArgumentException
	 *             if the given string is empty or null.
	 */
	public static void notEmpty(String string) {
		notEmpty(string, null);
	}

	/**
	 * Assert that the given string is <code>not null</code> and
	 * <code>not empty</code>.
	 * 
	 * <p>
	 * Empty strings: String string = null; String string = ""; String string =
	 * "  ";
	 * 
	 * @param string
	 *            the string to check
	 * @throws IllegalArgumentException
	 *             if the given string is empty or null.
	 */
	public static void notEmpty(String string, String errorMessage) {
		if (string == null) {
			throw new IllegalArgumentException(errorMessage);
		}
		string = string.trim();
		if (string.length() == 0) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

}
