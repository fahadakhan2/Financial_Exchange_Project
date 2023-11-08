package current.market;

import product.book.DataValidationException;

public interface CurrentMarketObserver {
    void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) throws DataValidationException;
}
