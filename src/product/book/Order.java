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


    public Order(String userCode, String stockSymbol, Price priceObject, BookSide sideOfOrder, int originalQuantity) throws DataValidationException {
        user = validateUser(userCode);
        product = validateProduct(stockSymbol);
        price = validatePrice(priceObject);
        side = validateBookSide(sideOfOrder);
        id = user + product + price + System.nanoTime();
        originalVolume = validateStockQuantity(originalQuantity);
        remainingVolume = originalVolume;
        cancelledVolume = 0;
        filledVolume = 0;
    }

    private int validateStockQuantity(int q) throws DataValidationException {
        if (q < 1 || q > 9999) {
            throw new DataValidationException("Invalid stock quantity passed in: " + q);
        }
        return q;
    }

    private String validateUser(String user) throws DataValidationException {
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

    private String validateProduct(String product) throws DataValidationException {
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

    private Price validatePrice(Price p) throws DataValidationException {
        if (p == null) {
            throw new DataValidationException("Invalid price passed in: " + p);
        }
        return p;
    }

    private BookSide validateBookSide(BookSide side) throws DataValidationException {
        if (side == null) {
            throw new DataValidationException("Invalid book side passed in: " + side);
        }
        return side;
    }
    public String getUser() {
        return user;
    }

    public String getProduct() {
        return product;
    }

    public Price getPrice() {
        return price;
    }

    public BookSide getSide() {
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
