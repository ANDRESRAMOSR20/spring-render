package ecommerce.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ecommerce.src.model.Tarjeta;
import ecommerce.src.repository.TarjetaRepository;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public boolean validarTarjeta(String nombre, Long numero, int codigo) {
        // Lógica de validación de tarjeta
        // Podría verificar en una base de datos si existe una tarjeta con los datos proporcionados
        return tarjetaRepository.existsByNombreAndNumeroAndCodigo(nombre, numero, codigo);
    }

    public boolean validarMontoSuficiente(Long numeroTarjeta, int monto) {
        // Lógica para validar si el monto en la tarjeta es suficiente
        Tarjeta tarjeta = tarjetaRepository.findByNumero(numeroTarjeta);
        return tarjeta != null && tarjeta.getMonto() >= monto;
    }

    public Tarjeta obtenerTarjetaPorNumero(Long numeroTarjeta) {
        // Obtener una tarjeta por su número
        return tarjetaRepository.findByNumero(numeroTarjeta);
    }

    // Otros métodos relacionados con la gestión de tarjetas
}