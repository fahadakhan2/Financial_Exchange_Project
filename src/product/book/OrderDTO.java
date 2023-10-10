package product.book;

import price.Price;

public class OrderDTO {
    public String user;
    public String product;
    public Price price;
    public BookSide side;
    public String id;
    public int originalVolume;
    public int remainingVolume;
    public int cancelledVolume;
    public int filledVolume;

    public OrderDTO(String user, String product, Price price, BookSide side, String id, int originalVolume, int remainingVolume, int cancelledVolume, int filledVolume) {
        this.user = user;
        this.product = product;
        this.price = price;
        this.side = side;
        this.id = id;
        this.originalVolume = originalVolume;
        this.remainingVolume = remainingVolume;
        this.cancelledVolume = cancelledVolume;
        this.filledVolume = filledVolume;
    }

    @Override
    public String toString() {
        return String.format("%s order: %s %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s",
                user, side, product, price, originalVolume, remainingVolume, filledVolume, cancelledVolume, id);
    }
}
