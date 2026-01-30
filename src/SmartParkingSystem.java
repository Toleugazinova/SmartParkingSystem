import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.db.IDatabase;
import repository.*;
import service.*;
import exception.*; // Подключаем исключения
import java.util.Scanner;

public class SmartParkingSystem {
    // SINGLETON: Получаем экземпляр базы
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
            System.out.println("\n--- Milestone 2 System ---");
            System.out.println("1. Show free spots (Lambda)");
            System.out.println("2. Show tariffs");
            System.out.println("3. Park vehicle (Builder & Factory)");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");

            int choice = scanner.nextInt(); scanner.nextLine();
            try {
                switch (choice) {
                    case 1 -> spotRepo.getAll().stream()
                            .filter(s -> s.isAvailable())
                            .forEach(System.out::println); // Lambda method reference
                    case 2 -> tariffRepo.printAllTariffs();
                    case 3 -> {
                        System.out.print("Plate: "); String p = scanner.nextLine();
                        System.out.print("Type: "); String t = scanner.nextLine();
                        System.out.println(resService.parkVehicle(p, t));
                    }
                    case 4 -> {
                        System.out.print("Plate: "); String p = scanner.nextLine();
                        System.out.println(pricingService.calculateAndPay(p));
                    }
                    case 5 -> System.exit(0);
                }
            } catch (ReservationException re) {
                System.out.println("Reservation Error: " + re.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) { new SmartParkingSystem().start(); }
}