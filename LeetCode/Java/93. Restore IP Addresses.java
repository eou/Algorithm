// 93. Restore IP Addresses
class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> results = new ArrayList<>();
        helper(s, results, "", 0);
        return results;
    }

    public void helper(String s, List<String> results, String cur, int count) {
        if (count == 4) {
            if (s.length() == 0) {
                // delete last '.'
                results.add(cur.substring(0, cur.length() - 1));
            }
            return;
        }

        for (int i = 1; i < 4 && i <= s.length(); i++) {
            if (!isValid(s.substring(0, i))) {
                continue;
            }

            cur += s.substring(0, i) + ".";
            count++;
            helper(s.substring(i), results, cur, count);
            count--;
            cur = cur.substring(0, cur.length() - i - 1);
        }
    }

    public boolean isValid(String s) {
        if ((s.charAt(0) == '0' && s.length() > 1) || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }
}

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> results = new ArrayList<>();
        helper(s, results, "", 0);
        return results;
    }
    
    public void helper(String s, List<String> results, String cur, int count) {
        if(count < 4) {
            // 注意等于号 i <= s.length()
            for(int i = 1; i < 4 && i <= s.length(); i++) {
                if(isValid(s.substring(0, i))) {
                    String str = new String(s.substring(i));
                    if(count + 1 == 4) {
                        if(str.length() == 0) {
                            results.add(cur + s.substring(0, i));
                        }
                    } else {
                        helper(str, results, cur + s.substring(0, i) + ".", count + 1);
                    }
                }
            }
        }
    }
    
    public boolean isValid(String s){
        if((s.charAt(0) == '0' && s.length() > 1) || Integer.parseInt(s) > 255) {
            return false;
        }
        return true;
    }
}

class Solution {
    // Backtracking
    public List<String> restoreIpAddresses(String s) {
        List<String> results = new ArrayList<>();
        StringBuilder ip = new StringBuilder();
        for (int a = 1; a < 4; a++) {
            for (int b = 1; b < 4; b++) {
                for (int c = 1; c < 4; c++) {
                    for (int d = 1; d < 4; d++) {
                        if (a + b + c + d == s.length()) {
                            int ip1 = Integer.parseInt(s.substring(0, a));
                            int ip2 = Integer.parseInt(s.substring(a, a + b));
                            int ip3 = Integer.parseInt(s.substring(a + b, a + b + c));
                            int ip4 = Integer.parseInt(s.substring(a + b + c, a + b + c + d));
                            if (ip1 <= 255 && ip2 <= 255 && ip3 <= 255 && ip4 <= 255) {
                                ip.append(ip1).append('.').append(ip2).append('.').append(ip3).append('.').append(ip4);
                                if (ip.length() == s.length() + 3) {
                                    results.add(ip.toString());
                                }
                                ip = new StringBuilder();
                            }
                        }
                    }
                }
            }
        }

        return results;
    }
}
