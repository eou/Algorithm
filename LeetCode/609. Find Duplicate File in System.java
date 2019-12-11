// 609. Find Duplicate File in System
// 处理字符串，使用map，此题follow up很多
// Imagine you are given a real file system, how will you search files? DFS or BFS?
// If the file content is very large (GB level), how will you modify your solution?
// If you can only read the file by 1kb each time, how will you modify your solution?
// What is the time complexity of your modified solution? What is the most time-consuming part and memory consuming part of it? How to optimize?
// How to make sure the duplicated files you find are not false positive?
class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        List<List<String>> res = new ArrayList<>();
        Map<String, Set<String>> map = new HashMap<>();

        for (String str : paths) {
            String[] files = str.split(" ");
            String path = files[0];
            for (int i = 1; i < files.length; ++i) {
                // 注意右括号的转义
                String[] tmp = files[i].split("\\(");
                StringBuilder strBuilder = new StringBuilder(path);
                // 如果路径只是root没有斜杠，还要加上
                if (!path.endsWith("/")) {
                    strBuilder.append("/");
                }
                strBuilder.append(tmp[0]);

                // tmp[1] is the content of file with a right parenthese
                if (!map.containsKey(tmp[1])) {
                    Set<String> set = new HashSet<>();
                    set.add(strBuilder.toString());
                    map.put(tmp[1], set);
                } else {
                    map.get(tmp[1]).add(strBuilder.toString());
                }
            }
        }

        for (String str : map.keySet()) {
            if (map.get(str).size() > 1) {
                List<String> list = new ArrayList<>();
                for (String s : map.get(str)) {
                    list.add(s);
                }
                res.add(list);
            }
        }

        return res;
    }
}