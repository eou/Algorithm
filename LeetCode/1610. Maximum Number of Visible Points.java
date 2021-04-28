// 1610. Maximum Number of Visible Points
// Similar with LintCode 1803. Rotate beam
// double pointer sweep
class Solution {
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int res = 0, origin = 0;  // ignore original points
        int n = points.size();
        int x = location.get(0), y = location.get(1);
        for (List<Integer> point : points) {
            point.set(0, point.get(0) - x);
            point.set(1, point.get(1) - y);
            if (point.get(0) == 0 && point.get(1) == 0) {
                origin++;
                n--;
            }
        }

        double[] B = new double[n];
        for (int i = 0, j = 0; i < points.size(); i++) {
            if (points.get(i).get(0) == 0 && points.get(i).get(1) == 0) {
                continue;
            }
            B[j++] = theta(points.get(i).get(0), points.get(i).get(1));
        }
        Arrays.sort(B);

        double[] A = new double[2 * n];
        System.arraycopy(B, 0, A, 0, n);
        // Cycle check, we might start from a point at the end of array and then check some points at the begining of the array
        System.arraycopy(B, 0, A, n, n);

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

        return res + origin;
    }

    private double theta(double x, double y) {
        return Math.atan2(y, x);
    }
}

class Solution {
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        int x = location.get(0), y = location.get(1);
        int origin = 0;

        List<Double> v = new ArrayList<>();
        for (List<Integer> p : points) {
            int px = p.get(0), py = p.get(1);
            if (px == x && py == y) {
                origin++;
            } else {
                v.add(Math.atan2(py - y, px - x) * 180 / Math.PI);
            }
        }

        Collections.sort(v);

        int n = v.size();
        for (int i = 0; i < n; ++i) {
            v.add(v.get(i) + 360);
        }
            
        int res = 0, r = 0;
        for (int l = 0; l < n; ++l) {
            while (r + 1 < v.size() && v.get(r + 1) - v.get(l) <= (double)angle) {
                r++;
            }
            res = Math.max(res, r - l + 1);
        }

        return res + origin;
    }
}