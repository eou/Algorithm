// https://leetcode.com/discuss/interview-question/460127/
import java.util.*;

class Solution {
    public static List<String> topNToys(int numToys, int topToys, List<String> toys, int numQuotes, List<String> quotes) {
        List<String> res = new ArrayList<>();
        Map<String, int[]> map = new HashMap<>();   // toys => number of quotes which it appears, number of frequency in all quotes
        Set<String> set = new HashSet<>(toys);      // faster search
        for (String quote : quotes) {
            Set<String> toyInCurQuote = new HashSet<>();    // toys in current quote
            String[] words = quote.split("[^(A-Za-z)]");    // filter all words  \\W+ = [A-Za-z_0-9]
            for (String word : words) {
                String w = word.toLowerCase();
                if (set.contains(w)) {
                    // find a toy
                    int[] freq = map.getOrDefault(w, new int[2]);
                    if (!toyInCurQuote.contains(w)) {
                        // first time appears in this quote, update freq[0]
                        toyInCurQuote.add(w);
                        freq[0]++;
                    }
                    // update freq[1]
                    freq[1]++;
                    map.put(w, freq);
                }
            }
        }

        // init capacity is not fixed size!!! size will grow automatically
        PriorityQueue<String> maxHeap = new PriorityQueue<>(new Comparator<String>() {
            public int compare(String a, String b) {
                int[] af = map.get(a);
                int[] bf = map.get(b);
                if (af[1] == bf[1]) {
                    if (af[0] == bf[0]) {
                        return a.compareTo(b);  // alphabetically
                    } else {
                        return bf[0] - af[0];
                    }
                } else {
                    return bf[1] - af[1];
                }
            }
        });

        for (String key : map.keySet()) {
            maxHeap.offer(key);
        }

        while (!maxHeap.isEmpty() && topToys > 0){
            String str = maxHeap.poll();
            res.add(str);
            topToys--;
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> toys = Arrays.asList("elmo", "elsa", "legos", "drone", "tablet", "warcraft");
        List<String> quotes = Arrays.asList(
            "Elmo is the hottest of the season! Elmo will be on every kid's wishlist!",
            "The new Elmo dolls are super high quality",
            "Expect the Elsa dolls to be very popular this year, Elsa!",
            "Elsa and Elmo are the toys I'll be buying for my kids, Elsa is good",
            "For parents of older kids, look into buying them a drone",
            "Warcraft is slowly rising in popularity ahead of the holiday season");
        List<String> res = topNToys(6, 2, toys, 6, quotes);
        for (String s : res) {
            System.out.println(s);
        }
    }
}