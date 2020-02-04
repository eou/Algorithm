// 422. Valid Word Square
class Solution {
    public boolean validWordSquare(List<String> words) {
        boolean res = true;
        // horizontal, ith row
        for (int i = 0; i < words.size(); i++) {
            // faster than string
            StringBuilder strBuilder = new StringBuilder();
            // vertical, jth column
            for (int j = 0; j < words.size(); j++) {
                strBuilder.append(words.get(j).length() <= i ? " " : words.get(j).charAt(i));
            }
            
            // trim
            res = res && strBuilder.toString().trim().equals(words.get(i));
        }
        
        return res;
    }
}

class Solution {
    public boolean validWordSquare(List<String> words) {
        if (words.size() == 0 || words == null) {
            return true;
        }

        for (int i = 0; i < words.size(); i++) {
            String tmp = words.get(i);
            for (int j = 0; j < tmp.length(); j++) {
                // too long
                if (j >= words.size()) {
                    return false;
                }

                // too short to form a correct vertical string
                if (words.get(j).length() <= i) {
                    return false;
                }

                // letter not equal
                if (tmp.charAt(j) != words.get(j).charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }
}