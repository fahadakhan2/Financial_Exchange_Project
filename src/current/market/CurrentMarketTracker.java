package current.market;

import price.InvalidPriceOperationException;
import price.Price;
import price.PriceFactory;
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

        Price sPrice = sellPrice;
        int sVolume = sellVolume;

        Price bPrice = buyPrice;
        int bVolume = buyVolume;

        if (sellPrice == null) {
            marketWidth = "[$0.00]";
            sPrice = PriceFactory.makePrice(0);
            sVolume = 0;
        } else if (buyPrice == null) {
            marketWidth = "[$0.00]";
            bPrice = PriceFactory.makePrice(0);
            bVolume = 0;
        } else{
            marketWidth = "[" + sellPrice.subtract(buyPrice).toString() + "]";
        }

        CurrentMarketSide buySide = new CurrentMarketSide(bPrice, bVolume);
        CurrentMarketSide sellSide = new CurrentMarketSide(sPrice, sVolume);

        String currentMarket = String.format("* %s %s %s - %s %s", symbol, "\t", buySide,
                sellSide, marketWidth);

        System.out.println("*********** Current Market ***********");
        System.out.println(currentMarket);
        System.out.println("**************************************");

        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol, buySide, sellSide);
    }
}
