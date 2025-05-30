package org.serratec.trabalho.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;


@Service
public class SendEmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Async
	public void SendEmail(String remetente, String assunto, String msg) {
		
		try {
	        SimpleMailMessage email = new SimpleMailMessage();
	        email.setTo(remetente);
	        email.setSubject(assunto);
	        email.setText(msg);

	        mailSender.send(email);
	        System.out.println("✅ Email enviado para " + remetente);
	    } 
		catch (Exception e) {
	        System.err.println("❌ Erro ao enviar e-mail: " + e.getMessage());
	    }
	
		
	}
}
