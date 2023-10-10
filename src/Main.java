import price.*;
import product.book.*;

public class Main {
    public static void main(String[] args) {
        try {
            Price p1 = PriceFactory.makePrice(9638);
            Order newOrder = new Order("XRF", "AMZN", p1, BookSide.BUY, 50);
            OrderDTO dto = newOrder.makeOrderDTO();
            System.out.println(dto);
        } catch (DataValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}
