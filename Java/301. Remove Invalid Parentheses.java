// 301. Remove Invalid Parentheses
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> results = new ArrayList<>();
        helper(s, 0, 0, '(', ')', results);
        return results;
    }
    
    private void helper(String s, int start, int delete, char open, char close, List<String> results) {
        int openNum = 0, closeNum = 0;
        for(int i = start; i < s.length(); i++) {
            if(s.charAt(i) == open) {
                openNum++;
            }
            if(s.charAt(i) == close) {
                closeNum++;
            }
            if(closeNum > openNum) {
                for(int j = delete; j <= i; j++) {
                    // 删除一个多余的左符号，并且要保证此删除操作不会重复，即之前没有同样的左符号
                    if(s.charAt(j) == close && (j == delete || s.charAt(j - 1) != close)) {
                        // 下一个从i开始，因为删除了一个字符导致下标前移
                        helper(s.substring(0, j) + s.substring(j + 1, s.length()), i, j, open, close, results);
                    }
                }
                // 注意在这里停止，只有当s调整完毕的时候不会进入这个if语句后才进行下一步
                return;
            }
        }
        
        String reversed = new StringBuilder(s).reverse().toString();
        if(open == '(') {
            helper(reversed, 0, 0, ')', '(', results);
        } else {
            results.add(reversed);
        }
    }
}