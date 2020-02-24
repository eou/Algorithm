// 140. Word Break II
// dfs, TLE
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return dfs(s, wordDict);
    }
    
    public List<String> dfs(String s, List<String> wordDict) {
        if (s == "") {
            return new ArrayList<>();
        }
        
        List<String> res = new ArrayList<>();
        for (int i = 1; i < s.length(); i++) {
            if (wordDict.contains(s.substring(0, i))) {
                List<String> list = dfs(s.substring(i), wordDict);
                if (list.size() != 0) {
                    for (String str : list) {
                        res.add(s.substring(0, i) + " " + str);
                    }
                }
            }
        }
        
        if (wordDict.contains(s)) {
            res.add(s);
        }
        
        return res;
    }
}

// dfs with memo
class Solution {
    public Map<String, List<String>> memo = new HashMap<>();
    public List<String> wordBreak(String s, List<String> wordDict) {
        return dfs(s, wordDict);
    }

    public List<String> dfs(String s, List<String> wordDict) {
        if (s == "") {
            return new ArrayList<>();
        }

        if (memo.containsKey(s)) {
            return memo.get(s);
        }

        List<String> res = new ArrayList<>();
        for (int i = 1; i < s.length(); i++) {
            if (wordDict.contains(s.substring(0, i))) {
                List<String> list = dfs(s.substring(i), wordDict);
                if (list.size() != 0) {
                    for (String str : list) {
                        res.add(s.substring(0, i) + " " + str);
                    }
                }
            }
        }

        if (wordDict.contains(s)) {
            res.add(s);
        }

        memo.put(s, res);
        return res;
    }
}

class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res  = new ArrayList<>();
        
        List<List<Integer>>[] memo = new ArrayList[s.length()];
        List<List<Integer>> paths = dfs(s, s.length() - 1, wordDict, memo);
        for(List<Integer> path: paths){
            addResult(wordDict, path, res);
        }
        return res;
    }
    
    private List<List<Integer>> dfs(String s, int pos, List<String> wordDict, List<List<Integer>>[] memo) {
        List<List<Integer>> res = new ArrayList();
        // exit
        if (pos < 0) {
            res.add(new ArrayList());
            return res;
        }
        
        if(memo[pos] != null) {
            return memo[pos];
        }
            
        for (int j = 0; j < wordDict.size(); j++) {
            // slower
            // if (pos >= wordDict.get(j).length() - 1 && wordDict.get(j).equals(s.substring(pos -  wordDict.get(j).length() + 1, pos + 1)))
            if (pos >= wordDict.get(j).length() - 1 && find(s, pos, wordDict.get(j))) {
                List<List<Integer>> list = dfs(s, pos - wordDict.get(j).length(), wordDict, memo);
                for(List<Integer> path: list) {
                    List<Integer> newPath = new ArrayList(path);
                    newPath.add(j);
                    res.add(newPath);
                }
            }
        }
        
        memo[pos] = res;
        return res;
    }
    
    public boolean find(String s, int tail, String tar) {
        for(int i = 0; i < tar.length(); i++) {
            if(tar.charAt(tar.length() - 1 - i) != s.charAt(tail - i)) {
                return false;
            }   
        }
        return true;
    }
                   
    private void addResult(List<String> words, List<Integer> path, List<String> res) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < path.size() - 1; i++){
            sb.append(words.get(path.get(i)));
            sb.append(" ");
        }
        sb.append(words.get(path.get(path.size()-1)));
        res.add(sb.toString());
    }
}