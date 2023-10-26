package trading.sim;

import price.InvalidPriceOperationException;
import product.book.DataValidationException;
import product.book.Order;
import product.book.OrderDTO;
import product.book.ProductBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public final class ProductManager {
    private static ProductManager instance;
    private static final HashMap<String, ProductBook> productBooks = new HashMap<>();

    private ProductManager() {

    }
    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public void addProduct(String symbol) throws DataValidationException {
        ProductBook pb = new ProductBook(symbol);
        productBooks.put(symbol, pb);
    }

    public String getRandomProduct() {
        if (productBooks.isEmpty()) {
            return null;
        }
        ArrayList<String> productSymbols = new ArrayList<>(productBooks.keySet());

        Random random = new Random();
        int randomIndex = random.nextInt(productSymbols.size());

        return productSymbols.get(randomIndex);
    }

    public OrderDTO addOrder(Order o) throws DataValidationException, InvalidPriceOperationException {
        ProductBook pb = productBooks.get(o.getProduct());
        return pb.add(o);
    }

    public OrderDTO cancel(OrderDTO o) {
        ProductBook pb = productBooks.get(o.product);
        try {
            return pb.cancel(o.side, o.id);
        } catch (DataValidationException dve) {
            System.out.println("Failed to cancel order: " + dve.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (ProductBook pb : productBooks.values()) {
            sb.append(pb.toString());
        }
        return sb.toString();
    }
}
