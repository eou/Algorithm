// 71. Simplify Path
class Solution {
    public String simplifyPath(String path) {
        int i = path.length() - 1, lastSlash = path.length(), up = 0;

        String dir = "", res = "";
        while (i >= 0) {
            if (path.charAt(i) == '/') {
                dir = path.substring(i + 1, lastSlash);
                if (dir.length() > 0) {
                    if (dir.equals("..")) {
                        up++;
                    } else if (!dir.equals(".")) {
                        if (up > 0) {
                            up--;
                        } else {
                            res = "/" + dir + res;       
                        }
                    }
                }
                lastSlash = i;
            }
            i--;
        }
        
        return res.length() == 0 ? "/" : res;
    }
}

class Solution {
    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();

        for (String s : path.split("/")) {
            if (s.equals("..")) {
                // use stack.poll() here to avoid check empty stack
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (!s.equals("") && !s.equals(".")) {
                stack.push(s);
            }
        }

        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()) {
            return "/";
        }

        while (!stack.isEmpty()) {
            sb.append("/").append(stack.pollLast());
        }

        return sb.toString();
    }
}