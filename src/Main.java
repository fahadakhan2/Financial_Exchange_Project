import price.InvalidPriceOperationException;
import price.Price;
import price.PriceFactory;

public class Main {
    public static void main(String[] args) {
        // int input tests
        System.out.println("int input tests below:");
        try {
            Price p1 = PriceFactory.makePrice("hello");
            System.out.println(p1);
        } catch (InvalidPriceOperationException e) {
            System.out.println("In the catch block");
        }

        try {
            Price p2 = PriceFactory.makePrice("1000");
            Price p2a = PriceFactory.makePrice("1000");
            Price newPrice = p2.add(p2a);
            System.out.println(p2);
            System.out.println(newPrice);
        } catch (InvalidPriceOperationException e) {
            System.out.println("In the catch block");
        }

        Price p3 = PriceFactory.makePrice(-1000);
        System.out.println(p3);
        System.out.println(p3.isNegative());

//        // String input tests
//        System.out.println("String input tests below:");
//        Price p4 = PriceFactory.makePrice("12.85");
//        Price p5 = PriceFactory.makePrice("-12.85");
//        Price p6 = PriceFactory.makePrice("$12.85");
//        Price p7 = PriceFactory.makePrice("$-12.85");
//        Price p8 = PriceFactory.makePrice("12");
//        System.out.println("p4: " + p4);
//        System.out.println("p5: " + p5);
//        System.out.println("p6: " + p6);
//        System.out.println("p7: " + p7);
//        System.out.println("p8: " + p8);
    }
}
