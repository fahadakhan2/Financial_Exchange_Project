package product.book;

import price.Price;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ProductBookSide {
    private final BookSide side;
    private final HashMap<Price, ArrayList<Order>> bookEntries = new HashMap<>();

    public ProductBookSide(BookSide side) throws DataValidationException {
        this.side = setBookSide(side);
    }

    private BookSide setBookSide(BookSide side) throws DataValidationException {
        if (side == null) {
            throw new DataValidationException("Invalid book side passed in: " + side);
        }
        return side;
    }

    public OrderDTO add(Order o) {
        Price orderPrice = o.getPrice();

        if (!bookEntries.containsKey(orderPrice)) {
            ArrayList<Order> newOrderList = new ArrayList<>();
            newOrderList.add(o);
            bookEntries.put(orderPrice, newOrderList);
        } else {
            ArrayList<Order> listForPrice = bookEntries.get(orderPrice);
            listForPrice.add(o);
            bookEntries.put(orderPrice, listForPrice);

        }
        OrderDTO newOrderDTO = o.makeTradableDTO();
        System.out.println("ADD: " + side + ": " + newOrderDTO.toString());
        return newOrderDTO;
    }

    public OrderDTO cancel(String orderId) throws DataValidationException {
        for (ArrayList<Order> orderList : bookEntries.values()) {
            for (Order order : orderList) {
                if (order.getId().equals(orderId)) {
                    orderList.remove(order);
                    int remainingVolume = order.getRemainingVolume();
                    int cancelledVolume = order.getCancelledVolume();
                    order.setCancelledVolume(remainingVolume + cancelledVolume);
                    order.setRemainingVolume(0);

                    if (orderList.isEmpty()) {
                        Price orderPrice = order.getPrice();
                        bookEntries.remove(orderPrice);
                    }

                    OrderDTO cancelledOrderDTO = order.makeTradableDTO();
                    System.out.println("ADD: " + side + ": " + cancelledOrderDTO.toString());
                    return cancelledOrderDTO;
                }
            }
        }
        return null;
    }
    private ArrayList<Price> getSortedPrices() {
        ArrayList<Price> sorted = new ArrayList<>(bookEntries.keySet());
        Collections.sort(sorted);
        if (side == BookSide.BUY) {
            Collections.reverse(sorted);
        }
        return sorted;
    }

    public Price topOfBookPrice() {
        ArrayList<Price> topPrice = getSortedPrices();
        if (topPrice.isEmpty()) {
            return null;
        }
        return topPrice.get(0);
    }

    public int topOfBookVolume() {
        if (side == BookSide.BUY) {
            int highestPriceVolumeTotal = 0;
            ArrayList<Price> topPrice = getSortedPrices();

            if (!topPrice.isEmpty()) {
                Price highestPrice = topPrice.get(0);
                ArrayList<Order> ordersAtPrice = bookEntries.get(highestPrice);

                for (Order order : ordersAtPrice) {
                    highestPriceVolumeTotal += order.getRemainingVolume();
                }
                return highestPriceVolumeTotal;
            } else {
                return 0;
            }

        } else {
            int lowestPriceVolumeTotal = 0;
            ArrayList<Price> topPrice = getSortedPrices();

            if(!topPrice.isEmpty()) {
                Price lowestPrice = topPrice.get(0);
                ArrayList<Order> ordersAtPrice = bookEntries.get(lowestPrice);

                for (Order order : ordersAtPrice) {
                    lowestPriceVolumeTotal += order.getRemainingVolume();
                }
                return lowestPriceVolumeTotal;
            } else {
                return 0;
            }
        }
    }

    public void tradeOut(Price price, int vol) throws DataValidationException{
        int remainingVolume = vol;
        ArrayList<Order> ordersAtPrice = bookEntries.get(price);

        while (remainingVolume > 0) {
            Order firstOrder = ordersAtPrice.get(0);

            if (firstOrder.getRemainingVolume() <= remainingVolume) {
                ordersAtPrice.remove(0);
                int firstOrderRemainingVolume = firstOrder.getRemainingVolume();
                firstOrder.setFilledVolume(firstOrder.getFilledVolume() + firstOrderRemainingVolume);
                firstOrder.setRemainingVolume(0);
                remainingVolume -= firstOrderRemainingVolume;
                System.out.println("FILL: " + "(" + side + " " + firstOrderRemainingVolume + " " + ")" + " " + firstOrder.getUser() + " order: " + side + " " + firstOrder.getProduct() + " at " +
                        firstOrder.getPrice() + ", Orig Vol: " + firstOrder.getOriginalVolume() + ", Rem Vol: " + firstOrder.getRemainingVolume() +
                        ", Fill Vol: " + firstOrder.getFilledVolume() + ", CXL Vol: " + firstOrder.getCancelledVolume() + ", ID: " + firstOrder.getId());
            } else {
                firstOrder.setFilledVolume(firstOrder.getFilledVolume() + remainingVolume);
                firstOrder.setRemainingVolume(firstOrder.getRemainingVolume() - remainingVolume);
                System.out.println("PARTIAL FILL: " + "(" + side + " " + firstOrder.getFilledVolume() + ")" + " "  + firstOrder.getUser() + " order: " + side + " " + firstOrder.getProduct() + " at " +
                        firstOrder.getPrice() + ", Orig Vol: " + firstOrder.getOriginalVolume() + ", Rem Vol: " + firstOrder.getRemainingVolume() +
                        ", Fill Vol: " + firstOrder.getFilledVolume() + ", CXL Vol: " + firstOrder.getCancelledVolume() + ", ID: " + firstOrder.getId());
                remainingVolume = 0;
            }
        }
        if (ordersAtPrice.isEmpty()) {
            bookEntries.remove(price);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilderBuy = new StringBuilder();
        StringBuilder stringBuilderSell = new StringBuilder();

        if (side == BookSide.BUY) {
            stringBuilderBuy.append("Side: BUY\n");
            for (Price price : getSortedPrices()) {
                stringBuilderBuy.append("\tPrice: ").append(price.toString()).append("\n");
                ArrayList<Order> ordersAtPrice = bookEntries.get(price);
                for (Order order : ordersAtPrice) {
                    stringBuilderBuy.append("\t\t").append(order.toString()).append("\n");
                }
            }
        } else {
            stringBuilderSell.append("Side: SELL\n");
            for (Price price : getSortedPrices()) {
                stringBuilderSell.append("\tPrice: ").append(price.toString()).append("\n");
                ArrayList<Order> ordersAtPrice = bookEntries.get(price);
                for (Order order : ordersAtPrice) {
                    stringBuilderSell.append("\t\t").append(order.toString()).append("\n");
                }
            }
        }

        if (side == BookSide.BUY) {
            return stringBuilderBuy.toString();
        } else {
            return stringBuilderSell.toString();
        }
    }
}
