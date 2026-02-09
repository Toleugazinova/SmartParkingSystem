package Domain.exceptions;

public class InvalidVehiclePlateException extends RuntimeException {
    public InvalidVehiclePlateException(String m) {
        super(m);
    }
}