// 67. Add Binary
class Solution {
    public String addBinary(String a, String b) {
        if(a.length() < b.length()) {
            String tmp = a;
            a = b;
            b = tmp;
        }
        for(int i = b.length(); i < a.length(); i++) {
            b = '0' + b;
        }
        
        StringBuilder strBuilder = new StringBuilder();
        boolean isCarry = false;
        for(int i = a.length() - 1; i >= 0; i--) {
            char ac = a.charAt(i);
            char bc = b.charAt(i);
            char r = '0';
            if((ac == '0' && bc == '0')) {
                r = '0';
                if(isCarry == true) {
                    r = '1';
                    isCarry = false;
                }
            } else if((ac == '1' && bc == '0') || (ac == '0' && bc == '1')) {
                r = '1';
                if(isCarry == true) {
                    r = '0';
                }
            } else if(ac == '1' && bc == '1') {
                r = '0';
                if(isCarry == true) {
                    r = '1';
                }
                if(isCarry == false) {
                    isCarry = true;
                }
            }
            strBuilder.insert(0, r);
        }
        if(isCarry == true) {
            strBuilder.insert(0, "1");
        }
        
        return strBuilder.toString();
    }
}

class Solution {
    // 简洁版本，每一步换成int然后直接计算和
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;

            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }
                
            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
                
            sb.append(sum % 2);
            carry = sum / 2;
        }

        if (carry != 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }
}

class Solution {
    // 每次添加在字符串头部就不用最后反转
    public String addBinary(String a, String b) {
        String ans = "";

        int carry = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = carry;
            sum += (i >= 0) ? a.charAt(i) - '0' : 0;
            sum += (j >= 0) ? b.charAt(j) - '0' : 0;
            ans = (sum % 2) + ans;
            carry = sum / 2;
        }
        if (carry != 0) {
            ans = carry + ans;
        }
        return ans;
    }
}