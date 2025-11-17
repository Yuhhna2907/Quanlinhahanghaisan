package Main;

import Model.Kitchen;
import Model.MenuItem;
import Model.OrderItem;
import Service.KitchenService;

public class MainKitchen {
    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen(1);
        KitchenService kitchenService = new KitchenService(kitchen);

        // Menu & OrderItem
        MenuItem shrimpDish = new MenuItem(1, "Tôm hấp", 250000, "kg");
        MenuItem crabDish = new MenuItem(2, "Cua rang me", 300000, "kg");

        OrderItem oi1 = new OrderItem(shrimpDish, 2);
        OrderItem oi2 = new OrderItem(crabDish, 1);

        // Bắt đầu chế biến
        kitchenService.startCooking(oi1);
        kitchenService.startCooking(oi2);

        // Kiểm tra tiến độ
        kitchenService.checkProgress();

        // Kết thúc chế biến
        kitchenService.finishCooking(oi1);
        kitchenService.finishCooking(oi2);

        // Kiểm tra lại tiến độ
        kitchenService.checkProgress();
    }
}
