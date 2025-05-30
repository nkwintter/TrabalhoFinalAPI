package org.serratec.trabalho.service;

import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

@Service
public class SendEmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Async
	public void SendEmailCadastrado(String remetente, String assunto, String nome) {
		
		try {
			MimeMessage email = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(email, true);
	        
	        helper.setTo(remetente);
	        helper.setSubject(assunto);
	        
	        String msg = """
	        		<html>
	        		<body style="text-align: center; font-family: Arial, sans-serif;">
					    <h2 style="color: #4CAF50;">Olá, %s! O mais novo Dev-verde!</h2>
					    <p>
					        Sua conta foi criada com sucesso no <strong style="color: #4CAF50;">Div das Plantas</strong> — agora você faz parte do nosso &lt;jardim&gt; cheio de código, clorofila e carinho. <br>
					        Aqui, cada &lt;div&gt; é uma folha, cada commit é uma flor, e cada bug... bem, a gente poda rapidinho. 😉 <br>
					        Prepare-se para cultivar conhecimento, compartilhar experiências e deixar sua plantinha interior dar um grow()! <br>
					        🌼 Seja bem-vindo(a) ao ecossistema! <br>
					        Com carinho, <br>
					        Equipe Div das Plantas
					    </p>
					    <img src="https://i.postimg.cc/7L4r9wpH/Imagem-do-Whats-App-de-2025-05-29-s-18-34-01-705eab7c.jpg" alt="Imagem do Cadastro" width="250" style="margin: 20px auto; display: block; border-radius: 15px;">
					    <p style="font-size: 12px; color: #888;">Obrigado por usar nosso sistema! <br> </p>
					</body>
					</html>
	        		""".formatted(nome);
	        
	        helper.setText(msg, true);

	        mailSender.send(email);
	        System.out.println("✅ Email enviado para " + remetente);
	    } 
		catch (Exception e) {
	        System.err.println("❌ Erro ao enviar e-mail: " + e.getMessage());
	    }
	}

	@Async
	public void SendEmailRemovido(String remetente, String assunto, String nome) {
		
		try {
			MimeMessage email = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(email, true);
	        
	        helper.setTo(remetente);
	        helper.setSubject(assunto);
	        
	        String msg = """
	        		<html>
					<body style="text-align: center; font-family: Arial, sans-serif;">
						<h2 style="color: #4CAF50;">Olá, ex-dev das folhas 🌱</h2>
						<p>
							Detectamos um deleteAccount() no nosso banco de dados e... confesso: o vasinho aqui ficou meio murcho. 😢 <br>
							Sabemos que às vezes é preciso fazer um refactor() na vida, e tudo bem! Mas se um dia quiser fazer um rollback, estaremos com a terra fresquinha e o código limpo esperando por você. <br>
							Enquanto isso, vamos regar a saudade com carinho e lembrar dos bons logs que deixamos no histórico. <br>
							🌿 Até breve, %s? <br>
							Com ternura,  <br>
							Equipe Div das Plantas 🌸
						</p>
						<img src="https://i.postimg.cc/ZKNtk4cf/Imagem-do-Whats-App-de-2025-05-29-s-18-34-03-833a7abc.jpg" alt="Imagem do Remoção de conta" width="300" style="margin: 20px auto; display: block; border-radius: 15px;">
						<p style="font-size: 12px; color: #888;">Esperamos que tenha aproveitado o nosso sistema! <br> </p>
					</body>
					</html>
	        		""".formatted(nome);
	        
	        helper.setText(msg, true);

	        mailSender.send(email);
	        System.out.println("✅ Email enviado para " + remetente);
	    } 
		catch (Exception e) {
	        System.err.println("❌ Erro ao enviar e-mail: " + e.getMessage());
	    }
	}
}
