package price;

import java.util.Objects;

public class Price implements Comparable<Price> {
    private final int cents;
    
     Price(int centsIn) {
         cents = centsIn;
     }

    public boolean isNegative() {
        return cents < 0;
    }

    public Price add(Price p) throws InvalidPriceOperationException {
        // null check
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);
        }

        int sum = cents + p.cents;
        return PriceFactory.makePrice(sum);
    }

    public Price subtract(Price p) throws InvalidPriceOperationException {
        // null check
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);        }

        int diff = cents - p.cents;
        return PriceFactory.makePrice(diff);
    }

    public Price multiply(int p) {
        int product = cents * p;
        return PriceFactory.makePrice(product);
    }

    public boolean greaterOrEqual(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);        }

        return cents >= p.cents;
    }

    public boolean lessOrEqual(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);        }

        return cents <= p.cents;
    }

    public boolean greaterThan(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);        }

        return cents > p.cents;
    }

    public boolean lessThan(Price p) throws InvalidPriceOperationException {
        if (p == null) {
            throw new InvalidPriceOperationException("Invalid price passed in: " + p);        }

        return cents < p.cents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return cents == price.cents;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cents);
    }

    @Override
    public int compareTo(Price p) {
        return cents - p.cents;
    }

    @Override
    public String toString() {
        boolean isNegative = cents < 0;
        int absoluteCents = Math.abs(cents);
        int dollars = absoluteCents / 100;
        int centsPart = absoluteCents % 100;

        return String.format("%s$%d.%02d", isNegative ? "-" : "", dollars, centsPart);
    }
}
