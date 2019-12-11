// 65. Valid Number
// 此题点赞和点灭数目前是285:2236，因为test case对于 "Valid Number" 的定义很模糊，详情见LeetCode discuss
// 还有人用 DFA(Deterministic finite automaton) 自动机解决
class Solution {
    public boolean isNumber(String s) {
        // 注意去头尾空格
        s = s.trim();

        // 题意大概为：数字 + e + 数字，其中e前的数字可以有小数点，e后的数字不能有小数点；两边数字都可以有零个或一个正负号
        boolean hasPoint = false;
        boolean hasE = false;
        boolean hasNumber = false;
        
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                hasNumber = true;
            } else if(s.charAt(i) == '.') {
                if(hasE || hasPoint) {
                    return false;
                }
                hasPoint = true;
            } else if(s.charAt(i) == 'e') {
                if(hasE || !hasNumber) {
                    return false;
                }
                // 注意遇到e，此时如果直接返回应该是错误的，因为整个数字不完整
                hasNumber = false;
                hasE = true;
            } else if(s.charAt(i) == '-' || s.charAt(i) == '+') {
                if(i != 0 && s.charAt(i - 1) != 'e') {
                    return false;
                }
            } else {
                return false;
            }
        }
        
        return hasNumber;
    }
}

class Solution {
    // DFA，权当开阔眼界
    public boolean isNumber(String s) {
        Map<Integer, Map<Character, Integer>> state = new HashMap<>();

        for (int i = 0; i <= 10; i++) {
            state.put(i, new HashMap<>());
        }

        state.get(0).put('-', 1);
        state.get(0).put('+', 2);
        state.get(0).put('d', 3);
        state.get(0).put('.', 4);
        state.get(0).put('.', 10);
        state.get(1).put('d', 3);
        state.get(1).put('.', 10);
        state.get(2).put('d', 3);
        state.get(2).put('.', 10);
        state.get(3).put('d', 3);
        state.get(3).put('.', 4);
        state.get(3).put('e', 5);
        state.get(4).put('d', 6);
        state.get(4).put('e', 5);
        state.get(5).put('d', 8);
        state.get(5).put('+', 7);
        state.get(5).put('-', 9);
        state.get(6).put('d', 6);
        state.get(6).put('e', 5);
        state.get(7).put('d', 8);
        state.get(8).put('d', 8);
        state.get(9).put('d', 8);
        state.get(10).put('d', 6);

        int curState = 0, nextState = 0;
        s = s.trim();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                nextState = state.get(curState).getOrDefault('d', -1);
            } else {
                nextState = state.get(curState).getOrDefault(c, -1);
            }

            if (nextState == -1) {
                return false;
            }
            curState = nextState;
        }

        return (curState == 3 || curState == 4 || curState == 6 || curState == 8);
    }
}