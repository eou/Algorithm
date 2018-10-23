// 17. Letter Combinations of a Phone Number
class Solution {
    public List<String> letterCombinations(String digits) {
        LinkedList<String> results = new LinkedList<>();
		if(digits.isEmpty()) {
            return results;
        }
        
        // hard coding
        String[] mapping = new String[] {"", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		results.add("");
        
		for(int i = 0; i < digits.length(); i++) {
			int num = digits.charAt(i) - '0';
            // notice that the '9' mapping 4 characters
			while(results.peek().length() == i){
				String t = results.remove();
				for(char c : mapping[num].toCharArray()) {
                    results.add(t + c);
                }
			}
		}
        
		return results;
    }
}