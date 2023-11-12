package current.market;

import price.Price;

public class CurrentMarketSide {
    private final Price price;
    private final int volume;

    public CurrentMarketSide(Price p, int v) {
        price = p;
        volume = v;
    }

    @Override
    public String toString() {
        return price + "x" + volume;
    }
}
