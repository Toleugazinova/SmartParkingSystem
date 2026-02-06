package component;

import repository.TariffRepository;

public class ReportingComponent {
    private final TariffRepository tariffRepository;

    public ReportingComponent(TariffRepository tariffRepository) {
        this.tariffRepository = tariffRepository;
    }

    public void printTariffs() {
        tariffRepository.printAllTariffs();
    }
}