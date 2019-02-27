import java.util.*;
import java.io.*;

class Record {
    public int id;
    public int score;

    public Record(int id, int score) {
        this.id = id;
        this.score = score;
    }
}

class highFive {
    // O(nlogn)
    public static Map<Integer, Double> solution(Record[] results) {
        Map<Integer, Double> map = new HashMap<>();
        Map<Integer, ArrayList<Integer>> person = new HashMap<>();

        // add records
        for (Record result : results) {
            int id = result.id;
            ArrayList<Integer> list = null;
            if (person.containsKey(id)) {
                list = person.get(id);
            } else {
                list = new ArrayList<>();
            }
            list.add(result.score);
            person.put(id, list);
        }

        // sort the scores
        for (Integer id : person.keySet()) {
            Collections.sort(person.get(id), (a, b) -> a > b ? 1 : 0);
            double val = 0;
            for (int i = 0; i < 5; ++i) {
                val += person.get(id).get(i);
            }
            val /= 5.0;
            map.put(id, val);
        }

        return map;
    }

    // O(nlog5)
    public static Map<Integer, Double> solution2(Record[] results) {
        Map<Integer, Double> result = new HashMap<Integer, Double>();
        HashMap<Integer, PriorityQueue<Record>> map = new HashMap<Integer, PriorityQueue<Record>>();

        int k = 5;
        for (Record r : results) {
            if (!map.containsKey(r.id)) {
                // PriorityQueue<Record> pq = new PriorityQueue<Record>(k, new Comparator<Record>() {
                //     public int compare(Record a, Record b) {
                //         return a.score - b.score; // min-heap
                //     }
                // });
                PriorityQueue<Record> pq = new PriorityQueue<Record>(k, (a, b) -> {return a.score - b.score;});
                map.put(r.id, pq);
            }

            map.get(r.id).add(r);
            if (map.get(r.id).size() > k) {
                map.get(r.id).poll();
            }
        }

        for (Integer id : map.keySet()) {
            double val = 0;
            PriorityQueue<Record> pq = map.get(id);
            while (!pq.isEmpty()) {
                val += pq.poll().score;
            }
            val /= k;
            result.put(id, val);
        }

        // for (Map.Entry<Integer, PriorityQueue<Record>> entry : map.entrySet()) {
        //     int id = entry.getKey();
        //     PriorityQueue<Record> pq = entry.getValue();
        //     double average = 0;
        //     int num = pq.size();
        //     while (!pq.isEmpty()) {
        //         average += pq.poll().score;
        //     }
        //     average /= num;
        //     result.put(id, average);
        // }

        return result;
    }

    public static void main(String[] args) {
        Record r1 = new Record(1, 91);
        Record r2 = new Record(1, 92);
        Record r3 = new Record(1, 90);
        Record r4 = new Record(1, 90);
        Record r5 = new Record(1, 90);
        Record r6 = new Record(1, 80);
        Record r7 = new Record(2, 1);
        Record r8 = new Record(2, 1);
        Record r9 = new Record(2, 1);
        Record r10 = new Record(2, 1);
        Record r11 = new Record(2, 6);
        Record[] arr = { r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11 };
        Map<Integer, Double> res = solution2(arr);
        System.out.println(res.get(1) + " " + res.get(2));
    }
}