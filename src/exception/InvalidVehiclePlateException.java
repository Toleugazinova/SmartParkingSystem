package exception;

public class InvalidVehiclePlateException extends RuntimeException {
    public InvalidVehiclePlateException(String m) {
        super(m);
    }
}