package contexts;

public class PickupOrDeliveryContext {

    private boolean pickup = false;
    private boolean delivery = false;

    private final static PickupOrDeliveryContext instance = new PickupOrDeliveryContext();

    public static PickupOrDeliveryContext getInstance() {
        return instance;
    }


    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

}
