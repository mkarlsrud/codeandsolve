import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mkarlsru on 9/27/17.
 *
 *
20
A,5,2
D,9,3
B,3,1
C,7,4
E,1,0
F,9,3

 */
public class CodeAndSolve1 {
    private static class Cookie {
        private final String name;
        private final int price;
        private final int donationAmount;

        private Cookie(String name, int price, int donationAmount) {
            this.name = name;
            this.price = price;
            this.donationAmount = donationAmount;
        }

        int getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int maxMoney = scan.nextInt();
        scan.nextLine();
        Set<Cookie> cookieSet = new HashSet<>();
        String line = scan.nextLine();
        while (!line.isEmpty()) {
            cookieSet.add(parseCookie(line));
            line = scan.nextLine();
        }
        // Create list of all possible amounts of cookies.
        // For example, if the limit is $20 and one possible cookie is $1, we need to add that cookie 20 times
        List<Cookie> cookies = new ArrayList<>();
        for (Cookie cookie : cookieSet) {
            int totalPossible = maxMoney / cookie.price;
            for (int i = 0; i < totalPossible; i ++) {
                cookies.add(cookie);
            }
        }

        System.out.println(knapSack(maxMoney, cookies, cookies.size(), new CookieList(), new HashSet<>()));
    }

    private static Cookie parseCookie(String line) {
        String[] tokens = line.split(",");
        return new Cookie(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    private static CookieList knapSack(int moneyRemaining, List<Cookie> cookies, int n, CookieList cookieList,
                                       Set<CookieList> cache) {
        if (cache.contains(cookieList)) {
            return cookieList;
        }

        if (n == 0 || moneyRemaining == 0) {
            cache.add(cookieList);
            return cookieList;
        }

        Cookie nthCookie = cookies.get(n - 1);

        //Don't include if price of nth cookie is more than the remaining money
        if (nthCookie.price > moneyRemaining) {
            return knapSack(moneyRemaining, cookies, n - 1, cookieList, cache);
        }

        CookieList includeNthCookie = new CookieList(cookieList);
        includeNthCookie.add(nthCookie);

        return max(
                //include nth cookie
                knapSack(moneyRemaining - nthCookie.price, cookies, n - 1, includeNthCookie, cache),
                //don't include nth cookie
                knapSack(moneyRemaining, cookies, n - 1, cookieList, cache)
        );
    }

    private static CookieList max(CookieList cookieList1, CookieList cookieList2) {
        return cookieList1.donationAmount > cookieList2.donationAmount ? cookieList1 : cookieList2;
    }

    //Creating this class to keep track of the list of cookies to be outputted in the end
    private static class CookieList extends ArrayList<Cookie> {
        private int donationAmount;

        private CookieList() {
            super();
        }

        private CookieList(CookieList cookieList) {
            super(cookieList);
            this.donationAmount = cookieList.donationAmount;
        }

        @Override
        public boolean add(Cookie cookie) {
            boolean bool = super.add(cookie);
            this.donationAmount += cookie.donationAmount;
            return bool;
        }

        @Override
        public String toString() {
            return donationAmount + " (" + this.stream().map(Cookie::getPrice).map(i -> Integer.toString(i))
                    .collect(Collectors.joining(", ")) + ")";
        }
    }
}
