import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class Item {
    private final String name;
    private final BigDecimal price;
    private final boolean imported;
    private final boolean exempt;

    public Item(String name, BigDecimal price, boolean imported, boolean exempt) {
        this.name = name;
        this.price = price;
        this.imported = imported;
        this.exempt = exempt;
    }

    public BigDecimal calculateTax() {
        BigDecimal tax = BigDecimal.ZERO;
        if (!exempt) {
            tax = tax.add(price.multiply(BigDecimal.valueOf(0.10)));
        }
        if (imported) {
            tax = tax.add(price.multiply(BigDecimal.valueOf(0.05)));
        }
        return roundTax(tax);
    }

    private BigDecimal roundTax(BigDecimal value) {
        return new BigDecimal(Math.ceil(value.doubleValue() * 20.0) / 20.0)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPrice() {
        return price.add(calculateTax());
    }

    @Override
    public String toString() {
        return name + ": " + getTotalPrice();
    }
}

class Receipt {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void printReceipt() {
        BigDecimal totalTax = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Item item : items) {
            System.out.println(item);
            totalTax = totalTax.add(item.calculateTax());
            totalPrice = totalPrice.add(item.getTotalPrice());
        }

        System.out.println("Sales Taxes: " + totalTax);
        System.out.println("Total: " + totalPrice);
    }
}

public class SalesTaxApp {
    public static void main(String[] args) {
        Receipt receipt1 = new Receipt();
        receipt1.addItem(new Item("1 book", new BigDecimal("12.49"), false, true));
        receipt1.addItem(new Item("1 music CD", new BigDecimal("14.99"), false, false));
        receipt1.addItem(new Item("1 chocolate bar", new BigDecimal("0.85"), false, true));

        System.out.println("Output 1:");
        receipt1.printReceipt();
        System.out.println();

        Receipt receipt2 = new Receipt();
        receipt2.addItem(new Item("1 imported box of chocolates", new BigDecimal("10.00"), true, true));
        receipt2.addItem(new Item("1 imported bottle of perfume", new BigDecimal("47.50"), true, false));

        System.out.println("Output 2:");
        receipt2.printReceipt();
        System.out.println();

        Receipt receipt3 = new Receipt();
        receipt3.addItem(new Item("1 imported bottle of perfume", new BigDecimal("27.99"), true, false));
        receipt3.addItem(new Item("1 bottle of perfume", new BigDecimal("18.99"), false, false));
        receipt3.addItem(new Item("1 packet of headache pills", new BigDecimal("9.75"), false, true));
        receipt3.addItem(new Item("1 imported box of chocolates", new BigDecimal("11.25"), true, true));

        System.out.println("Output 3:");
        receipt3.printReceipt();
    }
}
