package price;

public abstract class PriceFactory {
    public static Price makePrice(int value) {
        return new Price(value);
    }

    public static Price makePrice(String stringValueIn) {
        String cleanString = stringValueIn.replaceAll("[,$]", "");

        if (cleanString.contains(".")) {
            String removeDotString = cleanString.replace(".", "");
            int num = Integer.parseInt(removeDotString);
            return new Price(num);

        } else {
            String addZeroesString = cleanString + "00";
            int num = Integer.parseInt(addZeroesString);
            return new Price(num);
        }
    }
}
