package ecommerce.src.config;

public class Execptionst extends RuntimeException{
	

    public Execptionst(String mensaje) {
        super(mensaje);
    }

    
    
    public static class TarjetaInvalidaException extends Execptionst {
        public TarjetaInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    public static class SaldoInsuficienteException extends Execptionst {
        public SaldoInsuficienteException(String mensaje) {
            super(mensaje);
        }
    }

	
}