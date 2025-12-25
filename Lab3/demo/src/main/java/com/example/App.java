package com.example;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Items item = new Items();
        item.addItem("Mango Juice");
        item.addItem("Mango MilkShake");
        Shop shop = new Shop();
        shop.setName("Jenny's Juice bar");
        shop.addItem(item);
        shop.displayItems();
    }
}
