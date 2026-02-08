package PaymentComponent.interfaces;

import PaymentComponent.entity.Tariff;

public interface ITariffRepository {
    void printAllTariffs();
    Tariff getTariffBySpotType(String type);
    double getPriceById(int id);
}