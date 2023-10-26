package trading.sim;

import price.InvalidPriceOperationException;
import price.Price;
import price.PriceFactory;
import product.book.BookSide;
import product.book.DataValidationException;
import product.book.Order;
import product.book.OrderDTO;

import java.util.HashMap;
import java.util.Random;

public class TradingSim {
    private static HashMap<String, Double> basePrices = new HashMap<>();

    private TradingSim() {

    }

    public static void runSim() throws DataValidationException, InvalidPriceOperationException {
        UserManager.getInstance().init(new String[]{"ANN", "BOB", "CAT", "DOG", "EGG"});

        ProductManager.getInstance().addProduct("WMT");
        ProductManager.getInstance().addProduct("TGT");
        ProductManager.getInstance().addProduct("AMZN");
        ProductManager.getInstance().addProduct("TSLA");

        basePrices.put("WMT", 140.98);
        basePrices.put("TGT", 174.76);
        basePrices.put("AMZN", 102.11);
        basePrices.put("TSLA", 196.81);

        for (int i = 0; i < 40; i++) {
            User user = UserManager.getInstance().getRandomUser();

            if (Math.random() < 0.9) {
                String productSymbol = ProductManager.getInstance().getRandomProduct();

                BookSide bookSide;
                Random random = new Random();
                int randomNumber = random.nextInt(2); // Generates either 0 or 1
                if (randomNumber == 0) {
                    bookSide = BookSide.BUY;
                } else {
                    bookSide = BookSide.SELL;
                }

                int qty = (int) (25 + (Math.random() * 300));
                int orderVolume = (int) Math.round(qty / 5.0) * 5;

                Price orderPrice = getPrice(productSymbol, bookSide);
                Order order = new Order(user.getUserId(), productSymbol, orderPrice, orderVolume, bookSide);
                OrderDTO dto = ProductManager.getInstance().addOrder(order);
                user.addOrder(dto);

            } else {
                if (user.hasOrderWithRemainingQty()) {
                    OrderDTO dto = user.getOrderWithRemainingQty();
                    OrderDTO dtoFromCancel = ProductManager.getInstance().cancel(dto);
                    if (dtoFromCancel != null) {
                        user.addOrder(dtoFromCancel);
                    }
                }
            }
        }
        ProductManager.getInstance().toString();
        UserManager.getInstance().toString();
    }

    private static Price getPrice(String symbol, BookSide side) {
        double basePrice = basePrices.get(symbol);
        double priceWidth = 0.02; double startPoint = 0.01; double tickSize = 0.1;
        double gapFromBase = basePrice * priceWidth;
        double priceVariance = gapFromBase * (Math.random());

        if (side == BookSide.BUY) {
            double priceToUse = basePrice * (1 - startPoint);
            priceToUse += priceVariance;
            double priceToTick = Math.round(priceToUse * 1/tickSize) / 10.0;
            return PriceFactory.makePrice((int) (priceToTick * 100));
        } else {
            double priceToUse = basePrice * (1 + startPoint);
            priceToUse -= priceVariance;
            double priceToTick = Math.round(priceToUse * 1/tickSize) / 10.0;
            return PriceFactory.makePrice((int) (priceToTick * 100));
        }
    }
}
