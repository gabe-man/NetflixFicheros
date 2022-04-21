package email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Clase que controla el envio de emails
 * @author Gabriel
 *
 */
public class Email {
	
	public Email() {
		
	}
	
	/**
	 * Envia un correo con el codigo generado
	 * @param correoUsuario correo al que enviar el código
	 * @param codigo codigo a enviar
	 */
	public void Send(String correoUsuario, String codigo) {
		final String username = "ratatasalvaje1dam@gmail.com";
	    final String password = "Pollofrit0!";

	    Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
	    prop.put("mail.smtp.port", "587");
	    prop.put("mail.smtp.auth", "true");
	    prop.put("mail.smtp.ssl.trust", "*");
	    prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
	    prop.put("mail.smtp.starttls.required", "true");
	    
	    Session session = Session.getInstance(prop,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("netflix@gmail.com"));
	        message.setRecipients(
	                Message.RecipientType.TO,
	                InternetAddress.parse(correoUsuario)
	        );
	        message.setSubject("Testing Gmail TLS");
	        message.setText("Este es su codigo de registro:"
	                + "\n\n "+codigo);

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        e.printStackTrace();
	    }
	}
}
