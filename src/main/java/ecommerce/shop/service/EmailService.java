package ecommerce.shop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import ecommerce.src.model.Reserva;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void enviarCorreoReservaExitosa(String destino, Reserva reserva) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            Context context = new Context();
            context.setVariable("reserva", reserva);

            // Procesar la plantilla Thymeleaf
            String html = templateEngine.process("correo-reserva-exitosa", context);

            helper.setTo(destino);
            helper.setText(html, true);
            helper.setSubject("Reserva Exitosa");

            // Enviar el correo
            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Manejar excepción de envío de correo (puedes loggearla, lanzarla de nuevo, etc.)
            e.printStackTrace();
        }
    }
}