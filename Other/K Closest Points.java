// K Closest Points
// https://www.lintcode.com/problem/k-closest-points/description
class Solution {
    // 优先队列版本
    public Point originPoint;
    public Point[] kClosest(Point[] points, Point origin, int k) {
        Point[] result = new Point[k];
        originPoint = origin;
        PriorityQueue<Point> pq = new PriorityQueue<>(k, new Comparator<Point>() {
            public int compare(Point a, Point b) {
                int d1 = getDistance(a, originPoint);
                int d2 = getDistance(b, originPoint);
                if(d1 == d2) {
                    if(a.x == b.x) {
                        return b.y - a.y;
                    } else {
                        return b.x - a.x;
                    }
                } else {
                    return d2 - d1;
                }
            }
        });

        for(Point point : points) {
            pq.add(point);
            if(pq.size() > k) {
                pq.poll();
            }
        }

        while(!pq.isEmpty()) {
            result[k - 1] = pq.poll();
            k--;
        }

        return result;
    }

    private int getDistance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
}

class Solution {
    // quick select 版本，时间复杂度为 O(n*k)
    public Point originPoint;
    public Point[] kClosest(Point[] points, Point origin, int k) {
        originPoint = origin;
        Point[] result = new Point[k];
        for (int i = 0; i < k; i++) {
            int index = quickSelect(points, 0, points.length - 1, points.length - 1 - i);
            result[i] = points[index];
        }

        return result;
    }

    private int quickSelect(Point[] points, int start, int end, int k) {
        if (start >= end) {
            return k;
        }

        int left = start;
        int right = end;
        int pivot = left + (right - left) / 2;

        while (left <= right) {
            while (left <= right && comparePoint(points[left], points[pivot]) == -1) {
                left++;
            }
            while (left <= right && comparePoint(points[right], points[pivot]) == 1) {
                right--;
            }
            if (left <= right) {
                swap(points, left, right);
                left++;
                right--;
            }
        }

        if (k <= right) {
            return quickSelect(points, start, right, k);
        }
        if (k >= left) {
            return quickSelect(points, left, end, k);
        }
        return k;
    }

    private void swap(Point[] points, int i, int j) {
        Point tmp = points[i];
        points[i] = points[j];
        points[j] = tmp;
    }

    private int comparePoint(Point a, Point b) {
        int d1 = getDistance(a, originPoint);
        int d2 = getDistance(b, originPoint);
        if (d1 == d2) {
            if (a.x == b.x) {
                if (a.y == b.y) {
                    return 0;
                }
                return b.y - a.y > 0 ? 1 : -1;
            } else {
                return b.x - a.x > 0 ? 1 : -1;
            }
        } else {
            return d2 - d1 > 0 ? 1 : -1;
        }
    }

    private int getDistance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
}