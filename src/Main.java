import price.*;
import product.book.*;
import trading.sim.TradingSim;

public class Main {
    public static void main(String[] args) {
        try {
            TradingSim.runSim();
        } catch(InvalidPriceOperationException | DataValidationException e) {
            e.getMessage();
        }
    }
}
