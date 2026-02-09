package Domain.interfaces;

import Domain.entities.Tariff;

public interface ITariffRepository {
    void printAllTariffs();
    Tariff getTariffBySpotType(String type);
    double getPriceById(int id);
}