import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by mkarlsru on 10/1/17.
 *
 * TODO: how do you determine what the top product is? It says by popularity and price...
 *
John
iPhone6
625
Tim
GalaxyS7
685
Norman
iPhone6
618
Alex
GalaxyS7
700
Sam
iPhone6
610

 */
public class CodeAndSolve2 {
    private static class Purchaser implements Comparable<Purchaser>{
        private final String name;
        private final int amountPaid;

        private Purchaser(String name, int amountPaid) {
            this.name = name;
            this.amountPaid = amountPaid;
        }

        @Override
        public int compareTo(Purchaser o) {
            return this.amountPaid - o.amountPaid;
        }
    }

    public static void main(String[] args) {
        Map<String, Set<Purchaser>> purchaserMap = new HashMap<>();
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        while (!name.isEmpty()) {
            String product = scan.nextLine();
            int amount = Integer.parseInt(scan.nextLine());
            if (!purchaserMap.containsKey(product)) {
                purchaserMap.put(product, new TreeSet<>());
            }
            purchaserMap.get(product).add(new Purchaser(name, amount));
            name = scan.nextLine();
        }
        for (Map.Entry<String, Set<Purchaser>> entry : purchaserMap.entrySet()) {
            boolean printedProductName = false;
            Purchaser[] purchasers = entry.getValue().toArray(new Purchaser[entry.getValue().size()]);
            int cheapestAmount = purchasers[0].amountPaid;
            for (int i = 1; i < purchasers.length; i ++) {
                int difference = purchasers[i].amountPaid - cheapestAmount;
                if (difference > 0) {
                    if (!printedProductName) {
                        System.out.println(entry.getKey() + " " + cheapestAmount);
                        printedProductName = true;
                    }
                    System.out.println(purchasers[i].name + " - " + difference);
                }
            }

        }
    }
}
