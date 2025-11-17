package Service;

import Model.Kitchen;
import Model.OrderItem;

public class KitchenService {
    private Kitchen kitchen;

    public KitchenService(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public void startCooking(OrderItem item) {
        kitchen.startCooking(item);
    }

    public void finishCooking(OrderItem item) {
        kitchen.finishCooking(item);
    }

    public void checkProgress() {
        kitchen.checkProgress();
    }
}
