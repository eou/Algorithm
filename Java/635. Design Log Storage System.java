public class LogSystem {
    // 处理时间是一个难点
    // TreeMap<Long, Integer> map 不能写成Map<Long, Integer> map
    TreeMap<Long, Integer> map;
    public LogSystem() {
        map = new TreeMap<Long, Integer>();
    }

    public void put(int id, String timestamp) {
        // 这样处理字符串变成数组很常用
        int[] time = Arrays.stream(timestamp.split(":")).mapToInt(Integer::parseInt).toArray();
        map.put(convert(time), id);
    }
    
    // 时间数组转换成秒数
    public long convert(int[] time) {
        // 注意此处：年份范围为[2000,2017]，年份和月份如果非0要减去1
        time[1] = time[1] - (time[1] == 0 ? 0 : 1);
        time[2] = time[2] - (time[2] == 0 ? 0 : 1);
        
        return (time[0] - 1999L) * (31 * 12) * 24 * 60 * 60 + time[1] * 31 * 24 * 60 * 60 + time[2] * 24 * 60 * 60 + time[3] * 60 * 60 + time[4] * 60 + time[5];
    }
    
    public List<Integer> retrieve(String s, String e, String gra) {
        List<Integer> res = new ArrayList<>();
        
        long start = helper(s, gra, false);
        long end = helper(e, gra, true);

        // tailMap是从TreeMap尾部开始遍历，因为TreeMap内部是红黑树，按key自然排序的，这样可以减少搜索时间
        for (long key: map.tailMap(start).keySet()) {
            if (key >= start && key < end)
                res.add(map.get(key));
        }
        return res;
    }

    public long helper(String s, String gra, boolean end) {
        Map<String, Integer> h = new HashMap<>();
        h.put("Year", 0);
        h.put("Month", 1);
        h.put("Day", 2);
        h.put("Hour", 3);
        h.put("Minute", 4);
        h.put("Second", 5);

        String[] res = new String[] {"1999", "00", "00", "00", "00", "00"};
        String[] time = s.split(":");
        
        // 只需处理到所要求的粒度
        for (int i = 0; i <= h.get(gra); i++) {
            res[i] = time[i];
        }
        
        int[] t = Arrays.stream(res).mapToInt(Integer::parseInt).toArray();
    
        if (end) {
            t[h.get(gra)]++;
        }
        
        return convert(t);
    }
}