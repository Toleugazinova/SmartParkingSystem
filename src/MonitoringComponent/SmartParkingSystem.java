package MonitoringComponent;

import DataAccessComponent.db.PostgresDB;
import Domain.entities.ParkingSpot;
import Domain.exceptions.InvalidVehiclePlateException;
import Domain.exceptions.ReservationException;
import java.util.Scanner;
import DataAccessComponent.repositories.ParkingSpotRepository;
import Domain.exceptions.NoFreeSpotsException;
import DataAccessComponent.repositories.ReservationRepository;
import DataAccessComponent.repositories.TariffRepository;
import DataAccessComponent.repositories.VehicleRepository;
import Business.ReservationComponent.ParkingLotManager;
import Business.PaymentComponent.service.PricingService;
import Business.ReservationComponent.ReservationService;

public class SmartParkingSystem {
    private final ParkingSpotRepository spotRepo = new ParkingSpotRepository(PostgresDB.getInstance());
    private final TariffRepository tariffRepo = new TariffRepository(PostgresDB.getInstance());
    private final VehicleRepository vehicleRepo = new VehicleRepository(PostgresDB.getInstance());
    private final ReservationRepository resRepo = new ReservationRepository(PostgresDB.getInstance());
    private final ReservationService resService = new ReservationService(spotRepo, vehicleRepo, tariffRepo, resRepo);
    private final PricingService pricingService = new PricingService(resRepo, tariffRepo, spotRepo, vehicleRepo);
    private final ParkingLotManager lotManager = ParkingLotManager.getInstance(spotRepo);
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
                        lotManager.getAvailableSpots().getItems().stream()
                                .filter(ParkingSpot::isAvailable)
                                .forEach(System.out::println);
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
            } catch (ReservationException | InvalidVehiclePlateException | NoFreeSpotsException e) {
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
    private void parkVehicle() throws ReservationException, InvalidVehiclePlateException, NoFreeSpotsException {
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