import current.market.CurrentMarketSide;
import current.market.CurrentMarketTracker;
import price.*;
import product.book.*;
import trading.sim.TradingSim;

public class Main {
    public static void main(String[] args) {
        try {
            Price buyPrice = PriceFactory.makePrice("98.10");
            Price sellPrice = PriceFactory.makePrice("99.10");
            int buyVolume = 100;
            int sellVolume = 50;
            String symbol = "AMZN";

            CurrentMarketTracker cmt = CurrentMarketTracker.getInstance();
            cmt.updateMarket(symbol, buyPrice, buyVolume, sellPrice, sellVolume);

        } catch(InvalidPriceOperationException e) {
            e.getMessage();
        }
    }
}
