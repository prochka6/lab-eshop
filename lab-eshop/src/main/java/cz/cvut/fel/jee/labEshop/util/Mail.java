package cz.cvut.fel.jee.labEshop.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mail class serves for sending mails via gmail account.
 * @author Michal Horak
 *
 */
public class Mail {

	/**
	 * Method send mail via gmail SMTP host
	 * @param recipientEmial email address of recipient
	 * @param subject Subject of mail
	 * @param text Message of mail you want to send
	 */
	public static void sendMail(String recipientEmial, String subject,
			String text) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								LabEshopConstants.MAIL_USERNAME,
								LabEshopConstants.MAIL_PASSWORD);
					}
				});

		try {

			Message message = new MimeMessage(session);
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
