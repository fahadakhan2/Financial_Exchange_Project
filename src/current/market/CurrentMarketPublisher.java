package current.market;

import product.book.DataValidationException;

import java.util.ArrayList;
import java.util.HashMap;

public final class CurrentMarketPublisher {
    private static CurrentMarketPublisher instance;

    private static HashMap<String, ArrayList<CurrentMarketObserver>> filters = new HashMap<>();

    private CurrentMarketPublisher() {}

    public static CurrentMarketPublisher getInstance() {
        if (instance == null) {
            instance = new CurrentMarketPublisher();
        }
        return instance;
    }

    public void subscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) throws DataValidationException {
        if (cmo == null) {
            throw new DataValidationException("Invalid CurrentMarketObserver passed in as argument: " + cmo);
        }

        if (filters.containsKey(symbol)) {
            filters.get(symbol).add(cmo);
        } else {
            ArrayList<CurrentMarketObserver> newCurrentMarketObserverArray = new ArrayList<>();
            newCurrentMarketObserverArray.add(cmo);
            filters.put(symbol, newCurrentMarketObserverArray);
        }
    }

    public void unSubscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) throws DataValidationException {
        if (cmo == null) {
            throw new DataValidationException("Invalid CurrentMarketObserver passed in as an argument: " + cmo);
        }

        if (filters.containsKey(symbol)) {
            filters.get(symbol).remove(cmo);
        } else {
            return;
        }
    }

    public void acceptCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) throws DataValidationException {
        if (buySide == null || sellSide == null) {
            throw new DataValidationException("Invalid CurrentMarketSide passed in as an argument: " + null);
        }

        if (!filters.containsKey(symbol)) {
            return;
        }

        ArrayList<CurrentMarketObserver> cmo = filters.get(symbol);
        for (CurrentMarketObserver o : cmo) {
            o.updateCurrentMarket(symbol, buySide, sellSide);
        }
    }
}
