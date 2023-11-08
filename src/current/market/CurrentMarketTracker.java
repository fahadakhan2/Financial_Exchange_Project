package current.market;

import price.InvalidPriceOperationException;
import price.Price;
import product.book.DataValidationException;

public final class CurrentMarketTracker {
    private static CurrentMarketTracker instance;
    private CurrentMarketTracker(){}

    public static CurrentMarketTracker getInstance() {
        if (instance == null) {
            instance = new CurrentMarketTracker();
        }
        return instance;
    }

    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) throws InvalidPriceOperationException, DataValidationException {
        String marketWidth;

        if (sellPrice == null || buyPrice == null) {
            marketWidth = "$0.00";
        } else {
            Price marketWidthPriceObject = sellPrice.subtract(buyPrice);
            marketWidth = marketWidthPriceObject.toString();
        }

        CurrentMarketSide buySide = new CurrentMarketSide(buyPrice, buyVolume);
        CurrentMarketSide sellSide = new CurrentMarketSide(sellPrice, sellVolume);

        String marketWidthStr = "[" + marketWidth + "]";
        String currentMarket = String.format("* %s %s - %s %s", symbol, buySide.toString(), sellSide.toString(), marketWidthStr);

        // Printing the current market information
        System.out.println("*********** Current Market ***********");
        System.out.println(currentMarket);
        System.out.println("**************************************");

        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
    }
}
