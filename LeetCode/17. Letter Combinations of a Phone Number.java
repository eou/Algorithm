// 17. Letter Combinations of a Phone Number
// queue, time O(3^n ~ 4^n)
class Solution {
    public List<String> letterCombinations(String digits) {
        // queue
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }

        Deque<String> queue = new ArrayDeque<>();
        // hard coding
        String[] mapping = new String[] { "", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
        queue.offer("");

        // "" => "a", "b", "c" => "ca", "cb", "cc", ...
        for (int i = 0; i < digits.length(); i++) {
            int num = digits.charAt(i) - '0';
            // notice that the '9' mapping 4 characters
            while (queue.peek().length() == i) {
                String t = queue.poll();
                for (char c : mapping[num].toCharArray()) {
                    queue.offer(t + c);
                }
            }
        }

        while (!queue.isEmpty()) {
            res.add(queue.poll());
        }
        return res;
    }
}

// dfs
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if (digits == null || digits.length() == 0) {
            return res;
        }

        String[] mapping = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        dfs(digits, 0, res, mapping, new StringBuilder());
        return res;
    }

    private void dfs(String digits, int pos, List<String> res, String[] mapping, StringBuilder str) {
        if (pos == digits.length()) {
            res.add(str.toString());
            return;
        }

        char c = digits.charAt(pos);
        String letter = mapping[c - '0'];
        for (int i = 0; i < letter.length(); i++) {
            str.append(letter.charAt(i));
            dfs(digits, pos + 1, res, mapping, str);
            str.deleteCharAt(str.length() - 1);
        }
    }
}