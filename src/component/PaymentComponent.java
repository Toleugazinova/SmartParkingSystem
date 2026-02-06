package component;

import service.PricingService;

public class PaymentComponent {
    private final PricingService pricingService;

    public PaymentComponent(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public String calculateAndPay(String plateNumber, int hours) throws Exception {
        return pricingService.calculateAndPay(plateNumber, hours);
    }
}
