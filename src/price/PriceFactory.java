package price;

public abstract class PriceFactory {
    public static Price makePrice(int value) {
        return new Price(value);
    }

    public static Price makePrice(String stringValueIn) throws InvalidPriceOperationException{
        String cleanString = stringValueIn.replaceAll("[,$]", "");

        if (cleanString.contains(".")) {
            String removeDotString = cleanString.replace(".", "");
            try {
                int num = Integer.parseInt(removeDotString);
                return new Price(num);
            } catch (NumberFormatException nfe) {
                throw new InvalidPriceOperationException("NumberFormatException Occurred for the Following Input: " + stringValueIn);
            }

        } else {
            String addZeroesString = cleanString + "00";
            try {
                int num = Integer.parseInt(addZeroesString);
                return new Price(num);
            } catch (NumberFormatException nfe) {
                throw new InvalidPriceOperationException("NumberFormatException Occurred for the Following Input: " + stringValueIn);
            }

        }
    }
}
