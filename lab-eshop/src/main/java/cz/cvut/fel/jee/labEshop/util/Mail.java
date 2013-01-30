package cz.cvut.fel.jee.labEshop.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Mail class serves for sending mails via gmail account.
 * 
 * @author Michal Horak
 * 
 */
public class Mail {

	private static Session mailSession;

	/**
	 * Method send mail via gmail SMTP host
	 * 
	 * @param recipientEmial
	 *            email address of recipient
	 * @param subject
	 *            Subject of mail
	 * @param text
	 *            Message of mail you want to send
	 * @throws NamingException
	 */
	public static void sendMail(String recipientEmial, String subject,
			String text) throws NamingException {

		try {

			InitialContext ctx = new InitialContext();
			mailSession = (Session) ctx
					.lookup("java:jboss/mail/Default");

			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(LabEshopConstants.MAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipientEmial));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
