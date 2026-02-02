import edu.aitu.oop3.db.DatabaseConnection;
import interfaces.IDatabase;
import repository.*;
import service.*;
import entity.ParkingSpot;
import exception.ReservationException;
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
            printMenu();
            try {
                System.out.print("Enter choice: ");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        spotRepo.getAll().stream().filter(ParkingSpot::isAvailable).forEach(System.out::println);
                        break;
                    case 2:
                       tariffRepo.printAllTariffs();
                       break;
                    case 3:
                        parkVehicle();
                        break;
                    case 4:
                        calculateParkingFee();
                        break;
                    case 5:
                        return;
                }
            } catch (ReservationException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("System error occurred");
            }
        }
    }
    private void printMenu() {
        System.out.println("Smart Parking System");
        System.out.println("1. Print all available spots");
        System.out.println("2. Print tariffs");
        System.out.println("3. Park vehicle");
        System.out.println("4. Parking fee");
        System.out.println("5. Quit");
    }
    private void parkVehicle() throws ReservationException {
        System.out.print("Spot number: ");
        String s = scanner.nextLine();
        System.out.print("Plate number: ");
        String p = scanner.nextLine();
        System.out.print("Vehicle type: ");
        String t = scanner.nextLine();

        String result = resService.parkVehicle(s, p, t);
        System.out.println(result);
    }
    private void calculateParkingFee() throws Exception {
        System.out.print("Plate number: ");
        String p = scanner.nextLine();
        System.out.print("Hours: ");
        String hoursInput = scanner.nextLine();

        if (hoursInput.isEmpty()) {
            System.out.println("Error: Hours cannot be empty");
            return;
        }
        int h = Integer.parseInt(hoursInput);
        System.out.println(pricingService.calculateAndPay(p, h));
    }
}