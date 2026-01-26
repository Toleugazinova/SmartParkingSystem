import edu.aitu.oop3.db.DatabaseConnection;
import interfaces.IDatabase;
import repository.*;
import service.*;
import java.util.Scanner;

public class SmartParkingSystem {
    private final IDatabase db = new DatabaseConnection();
    private final ParkingSpotRepository spotRepo = new ParkingSpotRepository(db);
    private final TariffRepository tariffRepo = new TariffRepository(db);
    private final VehicleRepository vehicleRepo = new VehicleRepository(db);
    private final ReservationRepository resRepo = new ReservationRepository(db);
    private final ReservationService resService = new ReservationService(spotRepo, vehicleRepo, tariffRepo, resRepo);
    private final PricingService pricingService = new PricingService(resRepo, tariffRepo, spotRepo, vehicleRepo);
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println();
            System.out.println("Smart Parking System");
            System.out.println("1. Print all available spots");
            System.out.println("2. Print tariffs");
            System.out.println("3. Park vehicle");
            System.out.println("4. Parking fee");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt(); scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                        spotRepo.printFreeSpots();
                        break;
                    case 2:
                        tariffRepo.printAllTariffs();
                        break;
                    case 3:
                        System.out.print("Choose spot: ");
                        String s = scanner.nextLine();
                        System.out.print("Vehicle type: ");
                        String t = scanner.nextLine();
                        System.out.print("Plate number: ");
                        String p = scanner.nextLine();
                        System.out.println(resService.parkVehicleAtSpot(s, t, p));
                        break;
                    case 4:
                        System.out.print("Plate number: ");
                        p = scanner.nextLine();
                        System.out.print("Hours parked: ");
                        int hours = scanner.nextInt();
                        System.out.println(pricingService.calculateAndPay(p, hours));
                        break;
                    case 5:
                        return;
                }
            }
            catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
    }
}