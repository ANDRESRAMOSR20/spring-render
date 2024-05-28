package ecommerce.shop.service;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import ecommerce.src.model.Elementos;
import ecommerce.src.model.Reserva;
import ecommerce.src.model.Salon;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PdfGeneratorService {

    public byte[] generatePdf(List<Salon> salones, Authentication authentication) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        String username = "N/A";
        if (authentication != null) {
            username = authentication.getName();
        }

        // Crear un estilo personalizado para el título
        PdfFont tituloFont = PdfFontFactory.createFont();
        PdfFont titulodFont = PdfFontFactory.createFont();
        PdfFont titulotFont = PdfFontFactory.createFont();
        PdfFont tituloaFont = PdfFontFactory.createFont();
        document.add(new Paragraph("Lista de Salones:").setFont(tituloFont));
        document.add(new Paragraph("Administrador: ").setFont(tituloaFont));
        document.add(new Paragraph(username));

        for (Salon salon : salones) {
            document.add(new Paragraph(salon.getNombre()).setFont(titulodFont));
            document.add(new Paragraph("Precio: " + salon.getPrecio()));
            document.add(new Paragraph("Disponibilidad: " + salon.getDisponibilidad()));
            document.add(new Paragraph("Capacidad: " + salon.getCapacidad()));
            document.add(new Paragraph("Ciudad: " + salon.getUbicacion()));
            document.add(new Paragraph("Localidad:  " + salon.getBarrio()));
            document.add(new Paragraph("Direccion: " + salon.getDireccion()));

            document.add(new Paragraph("Servicios: ").setFont(titulotFont).setTextAlignment(TextAlignment.LEFT));
            for (Elementos elemento : salon.getElementos()) {
                document.add(new Paragraph(elemento.getNombre()));
            }

            if (salon.getImagen() != null && salon.getImagen().length > 0) {
                Image imagen = new Image(ImageDataFactory.create(salon.getImagen())).scaleAbsolute(120, 100);
				document.add(imagen);
            }

            document.add(new Paragraph("\n\n"));
        }

        document.close();

        return baos.toByteArray();
    }

    public byte[] generaterPdf(List<Reserva> reservas, Authentication authentication) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd MMM yyyy");
        String userEmail = "N/A";

        if (authentication != null) {
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
                userEmail = oauthToken.getPrincipal().getAttribute("email");
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
                userEmail = auth.getName();
            }
        }

        PdfFont tituloFont = PdfFontFactory.createFont();
        PdfFont titulodFont = PdfFontFactory.createFont();
        PdfFont titulotFont = PdfFontFactory.createFont();
        PdfFont tituloaFont = PdfFontFactory.createFont();
        document.add(new Paragraph("Informe de Reservas:").setFont(tituloFont));
        document.add(new Paragraph("Administrador: ").setFont(tituloaFont));
        document.add(new Paragraph(userEmail));

        for (Reserva reserva : reservas) {
            document.add(new Paragraph("Codigo de reserva: " + reserva.getId()).setFont(titulodFont));
            document.add(new Paragraph("Nombre de quien reserva: " + reserva.getNombre()));
            document.add(new Paragraph("Apellidos: " + reserva.getApellido()));
            document.add(new Paragraph("Numero celular: " + reserva.getCelular()));
            document.add(new Paragraph("Email de la persona quien reserva: " + reserva.getEmail()));
            document.add(new Paragraph("Fecha de reserva:  " + formatoFecha.format(reserva.getFecha())));
            document.add(new Paragraph("Nombre salon de reserva:  " + reserva.getSalon().getNombre()));
            document.add(new Paragraph("Codigo salon de reserva:  " + reserva.getSalon().getId()));

            document.add(new Paragraph("\n\n"));
        }

        document.close();

        return baos.toByteArray();
    }
}
