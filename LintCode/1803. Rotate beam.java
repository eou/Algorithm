// 1803. Rotate beam
// Mathematical problem
public class Solution {
    // Use arctan to get angle/radian
    private double theta(double x, double y) {
        // return Math.atan(y/x);  // this is not monotonic in 4 quadrants
        // !!! In this way we can get a monotonic increasing radian sequence in the range (-180.0, 180.0] anti-clockwise
        // [1,0] => 0.0, [1,1] => 45.0, [-1,1] => 135.0, [-1,0] => 180.0, [-1,-1] => -135.0, [1,-1] => -45.0
        // So starting from -179.999... in 3rd quadrant, the degree is always increasing through 4th, 1st until 2nd quadrant
        return Math.atan2(y, x);
    }

    public int AngleBeam(int[][] points, int angle) {
        int n = points.length;
        double[] B = new double[n];
        for (int i = 0; i < n; i++) {
            B[i] = theta(points[i][0], points[i][1]);
        }
        Arrays.sort(B);

        double[] A = new double[2 * n];
        System.arraycopy(B, 0, A, 0, n);
        // Cycle check, we might start from a point at the end of array and then check some points at the begining of the array
        System.arraycopy(B, 0, A, n, n);

        int res = 0;
        // degree measure <=> radian measure
        double rad = (double)angle / 180.0 * Math.PI;
        for (int i = 0; i < n; i++) {
            if (i > 0 && A[i] == A[i - 1]) {
                continue;  // small pruning, avoid duplicate calculation
            }

            double start = A[i];
            int j = i + 1;
            for (; j < 2 * n && j - i < n; j++) {
                double diff = Math.abs((A[j] + (j < n ? 0 : 2 * Math.PI)) - start);
                if (diff > rad) {
                    break;
                }
            }
            res = Math.max(res, j - i);
        }
        return res;
    }
}