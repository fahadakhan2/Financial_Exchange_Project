package product.book;

import current.market.CurrentMarketTracker;
import price.InvalidPriceOperationException;
import price.Price;

public class ProductBook {
    private final String product;
    private ProductBookSide buySide;
    private ProductBookSide sellSide;

    public ProductBook(String product) throws DataValidationException {
        this.product = validateProduct(product);
        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
    }

    private void updateMarket() throws InvalidPriceOperationException, DataValidationException {
        Price buySideTopPrice = buySide.topOfBookPrice();
        int buySideTopVolume = buySide.topOfBookVolume();

        Price sellSideTopPrice = sellSide.topOfBookPrice();
        int sellSideTopVolume = sellSide.topOfBookVolume();

        CurrentMarketTracker.getInstance().updateMarket(product, buySideTopPrice, buySideTopVolume, sellSideTopPrice, sellSideTopVolume);
    }

    private String validateProduct(String product) throws DataValidationException {
        if (product == null || product.isEmpty() || product.length() > 5) {
            throw new DataValidationException("Invalid product passed in: " + product);
        }

        for (char c : product.toCharArray()) {
            if ((!Character.isLetterOrDigit(c) && c != '.') || Character.isLowerCase(c)) {
                throw new DataValidationException("Invalid product passed in: " + product);
            }
        }
        return product;
    }

    public OrderDTO add(Order o) throws DataValidationException, InvalidPriceOperationException{
        if (o.getSide().equals(BookSide.BUY)) {
            OrderDTO buySideOrderDTO = buySide.add(o);
            tryTrade();
            updateMarket();
            return buySideOrderDTO;
        } else {
            OrderDTO sellSideOrderDTO = sellSide.add(o);
            tryTrade();
            updateMarket();
            return sellSideOrderDTO;
        }
    }

    public OrderDTO cancel(BookSide side, String orderId) throws DataValidationException, InvalidPriceOperationException {
         if (side == BookSide.BUY) {
             OrderDTO bOrderDTO = buySide.cancel(orderId);
             updateMarket();
             return bOrderDTO;
         } else {
             OrderDTO sOrderDTO = sellSide.cancel(orderId);
             updateMarket();
             return sOrderDTO;
         }
    }

    public void tryTrade() throws DataValidationException,InvalidPriceOperationException {
        Price topBuyPrice = buySide.topOfBookPrice();
        Price topSellPrice = sellSide.topOfBookPrice();

        while (topBuyPrice != null && topSellPrice != null && topBuyPrice.greaterOrEqual(topSellPrice)) {
            int topBuyVolume = buySide.topOfBookVolume();
            int topSellVolume = sellSide.topOfBookVolume();
            int volumeToTrade = Math.min(topBuyVolume, topSellVolume);

            sellSide.tradeOut(topSellPrice, volumeToTrade);
            buySide.tradeOut(topBuyPrice, volumeToTrade);

            topSellPrice = sellSide.topOfBookPrice();
            topBuyPrice = buySide.topOfBookPrice();
        }
    }
    @Override
    public String toString(){
        return String.format("Product: %s\n%s", product, buySide.toString() + sellSide.toString());
    }
}
