package current.market;

public final class CurrentMarketPublisher {
    private static CurrentMarketPublisher instance;

    private CurrentMarketPublisher() {}

    public static CurrentMarketPublisher getInstance() {
        if (instance == null) {
            instance = new CurrentMarketPublisher();
        }
        return instance;
    }
}
