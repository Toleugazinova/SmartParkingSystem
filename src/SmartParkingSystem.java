import edu.aitu.oop3.db.DatabaseConnection;
import interfaces.IDatabase;
import repository.*;
import service.*;
import entity.ParkingSpot;
import exception.ReservationException;
import java.util.Scanner;

public class SmartParkingSystem {
    private final IDatabase db = DatabaseConnection.getInstance();
    private final ParkingSpotRepository spotRepo = new ParkingSpotRepository(db);
    private final TariffRepository tariffRepo = new TariffRepository(db);
    private final VehicleRepository vehicleRepo = new VehicleRepository(db);
    private final ReservationRepository resRepo = new ReservationRepository(db);
    private final ReservationService resService = new ReservationService(spotRepo, vehicleRepo, tariffRepo, resRepo);
    private final PricingService pricingService = new PricingService(resRepo, tariffRepo, spotRepo, vehicleRepo);
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n1. Show Spots\n2. Park\n3. Checkout\n4. Exit");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> spotRepo.getAll().stream().forEach(System.out::println);
                    case 2 -> {
                        System.out.print("Plate: "); String p = scanner.nextLine();
                        System.out.print("Type: "); String t = scanner.nextLine();
                        System.out.println(resService.parkVehicle(p, t));
                    }
                    case 3 -> {
                        System.out.print("Plate: "); String p = scanner.nextLine();
                        System.out.println(pricingService.calculateAndPay(p));
                    }
                    case 4 -> System.exit(0);
                }
            } catch (ReservationException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("System error occurred");
            }
        }
    }

    public static void main(String[] args) {
        new SmartParkingSystem().start();
    }
}