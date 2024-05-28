package ecommerce.shop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ecommerce.src.Exception.NotFoundException;
import ecommerce.src.model.Reserva;
import ecommerce.src.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EmailService emailService;

    // Otros métodos y dependencias

    public Reserva confirmarReserva(String reservaId, String salonid) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();

            // Resto del código de validación y confirmación...

            // Cambiar el estado a true

            // Guardar la reserva
            Reserva reservaGuardada = reservaRepository.save(reserva);

            // Enviar el correo solo si el estado es true
            if (reservaGuardada.getEstado()==true) {
                emailService.enviarCorreoReservaExitosa(reservaGuardada.getEmail(), reservaGuardada);
            }

            return reservaGuardada;
        } else {
            throw new NotFoundException("Reserva con ID " + reservaId + " no encontrada");
        }
    }
}