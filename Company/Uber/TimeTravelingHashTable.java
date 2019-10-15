/**
 * Implement TimeTravelingHashTable 的 get 和 insert 方法
 * TimeTravelingHashTable
 * insert(key, value, timestamp)
 * get(key, timestamp)
 * get(key) // returns value associated with key at latest time
 */
class TimeTravelingHashTable {
    // <key : <timestamp : value>> = <key: sorted values on timestamp> = <key : treemap>
    HashMap<String, TreeMap<Integer, String>> map = new HashMap<>();
    // Comparator 写法：
    // TreeMap<Student, String> tm = new TreeMap<Student, String>(
    //     new Comparator<Student>() {
    //         @Override
    //         public int compare(Student s1, Student s2) {
    //             int num = s2.getAge() - s1.getAge();
    //             int num2 = num == 0?s1.getName().compareTo(s2.getName()) : num;
    //             return num2;
    //         }
    //     }
    // );
    
    // insert <timestamp, value> into hashmap
    public void insert(String key, String value, int timestamp) {
        if(map.containsKey(key)) {
            Map<Integer, String> values = map.get(key);
            values.put(timestamp, value);
        } else {
            TreeMap<Integer, String> values = new TreeMap<>();
            values.put(timestamp, value);
            map.put(key, values);
        }
    }

    // get the floor value on timestamp
    public String get(String key, int timestamp) {
        if(!map.containsKey(key)) return null;
        return map.get(key).floorEntry(timestamp).getValue();         
    }

    // Return the latest value on timestamp
    public String get(String key) {
        return map.get(key).lastEntry().getValue();
    }

    public static void main(String[] args) {
        TimeTravelingHashTable hashtable = new TimeTravelingHashTable();
        hashtable.insert("k1", "v1", 10);
        hashtable.insert("k1", "v2", 20);
        hashtable.insert("k2", "v1", 5);
        hashtable.insert("k2", "v2", 15);
        System.out.println(hashtable.get("k1"));     //  "v1" 
        System.out.println(hashtable.get("k1", 11)); //  "v1"
        System.out.println(hashtable.get("k1", 15)); //  "v1" 
        System.out.println(hashtable.get("k1", 11)); //  "v1"
        System.out.println(hashtable.get("k1", 21)); //  "v2"
        System.out.println(hashtable.get("k2", 10)); //  "v1"
        System.out.println(hashtable.get("k2", 20)); //  "v2"
    }
}

