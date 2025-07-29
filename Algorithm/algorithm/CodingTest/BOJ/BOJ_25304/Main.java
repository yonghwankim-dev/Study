package BOJ.BOJ_25304;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {
    static class Product{
        private final int price;
        private final int count;

        private Product(int price, int count) {
            this.price = price;
            this.count = count;
        }

        public static Product create(String text){
            String[] info = text.trim().split(" ");
            int price = Integer.parseInt(info[0]);
            int count = Integer.parseInt(info[1]);
            return new Product(price, count);
        }

        public static boolean isTotalPriceMatchProducts(int totalPrice, List<Product> products){
            return totalPrice == getTotalPrice(products);
        }

        public static int getTotalPrice(List<Product> products){
            return products.stream().mapToInt(p->p.price*p.count).sum();
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int totalPrice = Integer.parseInt(br.readLine());
        int n          = Integer.parseInt(br.readLine());
        String[] items = new String[n];
        for(int i = 0; i < n; i++){
            items[i] = br.readLine();
        }
        System.out.println(Main.solution(totalPrice, n, items));
    }

    public static String solution(int totalPrice, int n, String[] items){
        List<Product> products = new ArrayList<>();
        Arrays.stream(items).forEach(item->products.add(Product.create(item)));
        return Product.isTotalPriceMatchProducts(totalPrice, products) ? "Yes" : "No";
    }
}
