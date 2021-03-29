// 726. Number of Atoms
// Recursion, O(n^2)
class Solution {
    public String countOfAtoms(String formula) {
        if (formula == null || formula.length() <= 1) {
            return formula;
        }

        Map<String, Integer> map = dfs(formula);

        StringBuilder sb = new StringBuilder();
        for (String atom : map.keySet()) {
            sb.append(atom);
            sb.append(map.get(atom) == 1 ? "" : map.get(atom));
        }

        return sb.toString();
    }
    
    private Map<String, Integer> dfs(String formula) {
        Map<String, Integer> res = new TreeMap<>();

        int i = 0;
        while (i < formula.length()) {
            char c = formula.charAt(i);
            if (c == '(') {
                // find the right parenthesis, then extract the inner formula
                int r = 0, j = i;
                for (; j < formula.length(); j++) {
                    if (formula.charAt(j) == '(') {
                        r++;
                    } else if (formula.charAt(j) == ')') {
                        r--;
                    }
                    if (r == 0) {
                        break;
                    }
                }

                Map<String, Integer> innerMap = dfs(formula.substring(i + 1, j));
                
                j++;
                int cnt = 1, k = j;
                while (k < formula.length() && Character.isDigit(formula.charAt(k))) {
                    k++;
                }
                if (k > j) {
                    cnt = Integer.parseInt(formula.substring(j, k));
                }

                for (String name : innerMap.keySet()) {
                    res.put(name, innerMap.get(name) * cnt + res.getOrDefault(name, 0));
                }

                i = k;
            } else {
                int j = i + 1;
                while (j < formula.length() && Character.isLowerCase(formula.charAt(j))) {
                    j++;
                }

                int cnt = 1, k = j;
                while (k < formula.length() && Character.isDigit(formula.charAt(k))) {
                    k++;
                }
                if (k > j) {
                    cnt = Integer.parseInt(formula.substring(j, k));
                }

                String name = formula.substring(i, j);
                res.put(name, cnt + res.getOrDefault(name, 0));

                i = k;
            }
        }
        
        return res;
    }
}

// Stack
class Solution {
    public String countOfAtoms(String formula) {

        TreeMap<String, Integer> map = new TreeMap<>();
        Deque<TreeMap> stack = new ArrayDeque<>();

        int i = 0;
        while (i < formula.length()) {
            if (formula.charAt(i) == '(') {
                stack.push(map);
                map = new TreeMap<>();
                i++;
            } else if (formula.charAt(i) == ')') {
                int val = 0;
                i++;
                while (i < formula.length() && Character.isDigit(formula.charAt(i))) {
                    val = val * 10 + formula.charAt(i++) - '0';
                }
                val = val == 0 ? 1 : val;

                if (!stack.isEmpty()) {
                    TreeMap<String, Integer> curAtom = map;
                    map = stack.pop();

                    for (String atom : curAtom.keySet()) {
                        map.put(atom, curAtom.get(atom) * val + map.getOrDefault(atom, 0));
                    }
                }
            } else {
                // current atom
                int j = i + 1;
                while (j < formula.length() && Character.isLowerCase(formula.charAt(j))) {
                    j++;
                }

                String atom = formula.substring(i, j);

                int val = 0;
                while (j < formula.length() && Character.isDigit(formula.charAt(j))) {
                    val = val * 10 + (formula.charAt(j++) - '0');
                }
                val = val == 0 ? 1 : val;
                map.put(atom, map.getOrDefault(atom, 0) + val);
                i = j;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (String atom : map.keySet()) {
            sb.append(atom);
            sb.append(map.get(atom) == 1 ? "" : map.get(atom));
        }
        return sb.toString();
    }
}