package price;

import java.util.HashMap;

public abstract class PriceFactory {
    private static final HashMap<Integer, Price> priceStore = new HashMap<>();
    public static Price makePrice(int value) {
        if (priceStore.containsKey(value)) {
            return priceStore.get(value);
        } else {
            Price newPrice = new Price(value);
            priceStore.put(value, newPrice);
            return newPrice;
        }
    }

    public static Price makePrice(String stringValueIn) throws InvalidPriceOperationException{
        String cleanString = stringValueIn.replaceAll("[,$]", "");

        if (cleanString.contains(".")) {
            String removeDotString = cleanString.replace(".", "");
            try {
                int num = Integer.parseInt(removeDotString);

                if (priceStore.containsKey(num)) {
                    return priceStore.get(num);
                } else {
                    Price newPrice = new Price(num);
                    priceStore.put(num, newPrice);
                    return newPrice;
                }

            } catch (NumberFormatException nfe) {
                throw new InvalidPriceOperationException("NumberFormatException Occurred for the Following Input: " + stringValueIn);
            }

        } else {
            String addZeroesString = cleanString + "00";
            try {
                int num = Integer.parseInt(addZeroesString);

                if (priceStore.containsKey(num)) {
                    return priceStore.get(num);
                } else {
                    Price newPrice = new Price(num);
                    priceStore.put(num, newPrice);
                    return newPrice;
                }

            } catch (NumberFormatException nfe) {
                throw new InvalidPriceOperationException("NumberFormatException Occurred for the Following Input: " + stringValueIn);
            }
        }
    }
}
