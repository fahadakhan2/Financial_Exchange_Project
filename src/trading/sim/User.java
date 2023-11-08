package trading.sim;

import current.market.CurrentMarketObserver;
import current.market.CurrentMarketSide;
import product.book.DataValidationException;
import product.book.OrderDTO;
import java.util.HashMap;

public class User implements CurrentMarketObserver {
    private String userId;
    private static final HashMap<String, OrderDTO> orders = new HashMap<>();
    private static HashMap<String, CurrentMarketSide[]> currentMarkets = new HashMap<>();

    public User(String userId) throws DataValidationException {
        setUserId(userId);
    }

    public String getUserId() {
        return userId;
    }

    private void setUserId(String user) throws DataValidationException {
        if (user == null || user.length() != 3) {
            throw new DataValidationException("Invalid user passed in: " + user);
        }

        for (char c : user.toCharArray()) {
            if (!Character.isLetter(c) || Character.isLowerCase(c)) {
                throw new DataValidationException("Invalid user passed in: " + user);
            }
        }
        this.userId = user;
    }

    public void addOrder(OrderDTO o) throws DataValidationException {
        if (o == null) {
            throw new DataValidationException("Invalid order passed in: " + o);
        }
        orders.put(o.id, o);
    }

    public boolean hasOrderWithRemainingQty() {
        for (OrderDTO order : orders.values()) {
            if (order.remainingVolume > 0) {
                return true;
            }
        }
        return false;
    }

    public OrderDTO getOrderWithRemainingQty() {
        for (OrderDTO order : orders.values()) {
            if (order.remainingVolume > 0) {
                return order;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User Id: ").append(userId).append("\n");
        for (OrderDTO order : orders.values()) {
            sb.append("Product: ").append(order.product).append(", ");
            sb.append("Price: $").append(order.price).append(", ");
            sb.append("OriginalVolume: ").append(order.originalVolume).append(", ");
            sb.append("RemainingVolume: ").append(order.remainingVolume).append(", ");
            sb.append("CancelledVolume: ").append(order.cancelledVolume).append(", ");
            sb.append("FilledVolume: ").append(order.filledVolume).append(", ");
            sb.append("User: ").append(userId).append(", ");
            sb.append("Side: ").append(order.side).append(", ");
            sb.append("Id: ").append(order.id).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) throws DataValidationException {
        if (buySide == null || sellSide == null) {
            throw new DataValidationException("Invalid CurrentMarketSide passed in as an argument: " + null);
        }

        CurrentMarketSide[] currentMarketSidesArray = new CurrentMarketSide[2];
        currentMarketSidesArray[0] = buySide;
        currentMarketSidesArray[1] = sellSide;
        currentMarkets.put(symbol, currentMarketSidesArray);
    }

    public String getCurrentMarkets() {
        StringBuilder sb = new StringBuilder();

        for (String symbol : currentMarkets.keySet()) {
            CurrentMarketSide[] sides = currentMarkets.get(symbol);

            if (sides != null && sides.length == 2) {
                CurrentMarketSide buySide = sides[0];
                CurrentMarketSide sellSide = sides[1];

                sb.append(symbol).append("\t").append(buySide.toString()).append(" - ").append(sellSide.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
