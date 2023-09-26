import price.InvalidPriceOperationException;
import price.Price;
import price.PriceFactory;

public class Main {
    public static void main(String[] args) {
        // int input tests
        System.out.println("int input tests below:");
        Price p1 = PriceFactory.makePrice(1000);
        System.out.println(p1);
        Price p2 = PriceFactory.makePrice(1000);
        Price p3 = PriceFactory.makePrice(-1000);
        System.out.println(p3);
        System.out.println(p3.isNegative());
        try {
            Price newPrice = p1.add(p2);
            System.out.println(newPrice);
        } catch (InvalidPriceOperationException e) {
            e.getMessage();
        }

        // String input tests
        System.out.println("String input tests below:");
        Price p4 = PriceFactory.makePrice("12.85");
        Price p5 = PriceFactory.makePrice("-12.85");
        Price p6 = PriceFactory.makePrice("$12.85");
        Price p7 = PriceFactory.makePrice("$-12.85");
        Price p8 = PriceFactory.makePrice("12");
        System.out.println("p4: " + p4);
        System.out.println("p5: " + p5);
        System.out.println("p6: " + p6);
        System.out.println("p7: " + p7);
        System.out.println("p8: " + p8);

        // NumberFormatException Test
        System.out.println("NumberFormatException test below:");
        try {
            Price p9 = PriceFactory.makePrice("fahfasdkjads");
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException occurred: " + nfe.getMessage());
        }
    }
}
