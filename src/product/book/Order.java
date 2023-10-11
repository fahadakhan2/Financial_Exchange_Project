package product.book;

import price.Price;

public class Order {
    private final String user;
    private final String product;
    private final Price price;
    private final BookSide side;
    private final String id;
    private final int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;
    private int filledVolume;


    public Order(String userCode, String stockSymbol, Price priceObject, int originalQuantity, BookSide sideOfOrder) throws DataValidationException {
        user = setUser(userCode);
        product = setProduct(stockSymbol);
        price = setPrice(priceObject);
        side = setSide(sideOfOrder);
        id = user + product + price + System.nanoTime();
        originalVolume = setOriginalVolume(originalQuantity);
        remainingVolume = originalVolume;
        cancelledVolume = 0;
        filledVolume = 0;
    }

    public OrderDTO makeTradableDTO() {
        return new OrderDTO(user, product, price, side, id, originalVolume, remainingVolume, cancelledVolume, filledVolume);
    }

    private int setOriginalVolume(int q) throws DataValidationException {
        if (q < 1 || q > 9999) {
            throw new DataValidationException("Invalid stock quantity passed in: " + q);
        }
        return q;
    }

    public String getUser() {
        return user;
    }

    private String setUser(String user) throws DataValidationException {
        // checks for null and incorrect length
        if (user == null || user.length() != 3) {
            throw new DataValidationException("Invalid user passed in: " + user);
        }

        // loop that checks each char in the user string. If any non-letter character is found throw exception
        for (char c : user.toCharArray()) {
            if (!Character.isLetter(c) || Character.isLowerCase(c)) {
                throw new DataValidationException("Invalid user passed in: " + user);
            }
        }
        return user;
    }

    public String getProduct() {
        return product;
    }

    private String setProduct(String product) throws DataValidationException {
        if (product == null || product.isEmpty() || product.length() > 5) {
            throw new DataValidationException("Invalid product passed in: " + product);
        }

        for (char c : product.toCharArray()) {
            if ((!Character.isLetterOrDigit(c) && c != '.') || Character.isLowerCase(c)) {
                throw new DataValidationException("Invalid product passed in: " + product);
            }
        }
        return product;
    }

    public Price getPrice() {
        return price;
    }

    private Price setPrice(Price p) throws DataValidationException {
        if (p == null) {
            throw new DataValidationException("Invalid price passed in: " + p);
        }
        return p;
    }

    public BookSide getSide() {
        return side;
    }
    
    private BookSide setSide(BookSide side) throws DataValidationException {
        if (side == null) {
            throw new DataValidationException("Invalid book side passed in: " + side);
        }
        return side;
    }

    public String getId() {
        return id;
    }

    public int getOriginalVolume() {
        return originalVolume;
    }

    public int getRemainingVolume() {
        return remainingVolume;
    }

    public void setRemainingVolume(int remainingVolume) {
        this.remainingVolume = remainingVolume;
    }

    public int getCancelledVolume() {
        return cancelledVolume;
    }

    public void setCancelledVolume(int cancelledVolume) {
        this.cancelledVolume = cancelledVolume;
    }

    public int getFilledVolume() {
        return filledVolume;
    }

    public void setFilledVolume(int filledVolume) {
        this.filledVolume = filledVolume;
    }

    @Override
    public String toString() {
        return String.format("%s order: %s %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s",
                user, side, product, price, originalVolume, remainingVolume, filledVolume, cancelledVolume, id);
    }
}
