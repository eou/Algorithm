// 640. Solve the Equation
class Solution {
    public String solveEquation(String equation) {
        String left = equation.split("=")[0];
        String right = equation.split("=")[1];

        // coefficent of x, constant: ax + b
        int[] leftRes = helper(left);
        int[] rightRes = helper(right);
        // ax + b = cx + d => (a - c)x = d - b => x = (d - b) / (a - c)
        int coefficent = leftRes[0] - rightRes[0];
        int constant = rightRes[1] - leftRes[1];

        if(coefficent == 0) {
            if(constant == 0) {
                return "Infinite solutions";
            } else {
                return "No solution";
            }
        }

        return "x=" + constant / coefficent;
    }

    public int[] helper(String expression) {
        int[] result = new int[2];

        // ?= means "positive lookahead". 
        String[] tokens = expression.split("(?=[-+])");
        for(String token : tokens) {
            if(token.equals("+x") || token.equals("x")) {
                result[0] += 1;
            } else if(token.equals("-x")) {
                result[0] -= 1;
            } else if(token.contains("x")) {
                result[0] += Integer.parseInt(token.substring(0, token.indexOf("x")));
            } else {
                result[1] += Integer.parseInt(token);
            }
        }
        
        return result;
    }
}