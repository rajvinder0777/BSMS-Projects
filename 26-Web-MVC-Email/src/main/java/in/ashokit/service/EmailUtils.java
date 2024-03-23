package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender sender;

	public boolean sendEmail(String to, String subject, String body) {

		try {
		SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(to);
			msg.setSubject(subject);
			msg.setText(body);
			
			sender.send(msg);
	/*		
			
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED,"UTF-8");
			helper.setText(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			sender.send(message);
			
			*/
			
			
			return true;
		}catch(Exception e) {
			
		}

		return false;
	}
}
