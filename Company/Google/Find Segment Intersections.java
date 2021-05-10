// https://www.hackerearth.com/practice/math/geometry/line-intersection-using-bentley-ottmann-algorithm/tutorial
// https://segmentfault.com/a/1190000004457595
/**
 * For infinite straight line, 
    for Li, i = 1, 2, ... n-1
        for Lj, j = i+1, ... n
        if Li parallel to Lj, output <i, j, PARALLEL> 
        else output <i, j, intersection(Li, Lj)>
 */

// Given N horizontal and vertical line segments, we need to find all the intersections of horizontal and vertical line segments.
// O(NlogN + K^2), K is the number of intersections
import java.util.*;
import javafx.geometry.Point2D;

class test {
    static class Line {
        Point2D x;
        Point2D y;
        Line (Point2D x, Point2D y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Point {
        Point2D p;
        Line l;
        // !!! 0 for start point of horizontal line, 1 for vertical line, 2 for end point of horizontal line
        // Since the start point and end point are inclusive, 
        // when reaching an end of line with a vertical line, we firstly calculate intersection using vertical line and then remove 1 line based on the end of that line
        // when reaching a start of line with a vertical line, we firstly add the line into line set and then calculate the intersection using vertical line
        int type;
        Point (Point2D p, Line l, int type) {
            this.p = p;
            this.l = l;
            this.type = type;
        }
    }

    private static int intersect(List<Line> lines) {
        List<Point> points = new ArrayList<>();
        for (Line l : lines) {
            if (l.x.getX() == l.y.getX()) {
                // vertical, only add 1 point
                points.add(new Point(l.x, l, 1));
            } else {
                // horizontal, add both left point and right point
                points.add(new Point(l.x, l, 0));
                points.add(new Point(l.y, l, 2));
            }
        }

        points.sort((p1, p2) -> p1.p.getX() == p2.p.getX() ? p1.type - p2.type : Double.compare(p1.p.getX(), p2.p.getX()));

        // the y-coordinate of those horizontal lines, sorted by BST
        TreeSet<Line> ySet = new TreeSet<>((l1, l2) -> Double.compare(l1.x.getY(), l2.x.getY()));

        int res = 0;
        for (Point p : points) {
            if (p.type == 0) {
                ySet.add(p.l);
            } else if (p.type == 2) {
                ySet.remove(p.l);
            } else {
                // vertical line
                Line upper = new Line(new Point2D(-1000, p.l.y.getY()), new Point2D(1000, p.l.y.getY()));
                Line lower = new Line(new Point2D(-1000, p.l.x.getY()), new Point2D(1000, p.l.x.getY()));

                if (ySet.higher(lower) != null && ySet.lower(upper) != null) {
                    res += ySet.subSet(ySet.higher(lower), true, ySet.lower(upper), true).size();
                } else if (ySet.higher(lower) != null) {
                    res += ySet.headSet(ySet.higher(lower), true).size();
                } else if (ySet.lower(upper) != null) {
                    res += ySet.tailSet(ySet.lower(upper), true).size();
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        
        Line l1 = new Line(new Point2D(1, 1), new Point2D(2, 1));
        Line l2 = new Line(new Point2D(1, 2), new Point2D(4, 2));
        Line l3 = new Line(new Point2D(3.5, 1.5), new Point2D(4, 1.5));
        Line l4 = new Line(new Point2D(2, 1.5), new Point2D(2, 3));
        Line l5 = new Line(new Point2D(3.5, 1), new Point2D(3.5, 1.5));
        Line l6 = new Line(new Point2D(4, -1), new Point2D(4, 4));
        List<Line> lines = Arrays.asList(l1, l2, l3, l4, l5, l6);

        System.out.println(test.intersect(lines));
    }
}

// https://segmentfault.com/a/1190000004457595
// Given multiple infinite straight lines
class Solution {
    private int findIntersections(int[][][] lines) {
        int res = 0;
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                if (intersect(lines[i][0], lines[i][1], lines[j][0], lines[j][1])) {
                    res += 1;
                }
            }
        }
        return res;
    }

    // if (p1, p2) intersect with (p3, p4)
    private boolean intersect(int[] p1, int[] p2, int[] p3, int[] p4) {
        int o1 = orientation(p1, p2, p3), o2 = orientation(p1, p2, p4), o3 = orientation(p3, p4, p1), o4 = orientation(p3, p4, p2);
        // p3 and p4 is different orientation to p1 and p3 and p4 is different orientation to p2
        // p1 and p2 is different orientation to p3 and p1 and p1 is different orientation to p4
        return (o1 != o2 && o3 != o4) || 
                (o1 == 0 && on(p1, p2, p3)) || 
                (o2 == 0 && on(p1, p2, p4)) || 
                (o3 == 0 && on(p3, p4, p1)) ||
                (o4 == 0 && on(p3, p4, p2));
    }

    // (p3, p2) is counter clock wise or clock wise of (p1, p2)
    private int orientation(int[] p1, int[] p2, int[] p3) {
        // slope1 = p2[1] - p1[1] / p2[0] - p1[0]
        // slope2 = p3[1] - p2[1] / p3[0] - p2[0]

        int diff = (p2[1] - p1[1]) * (p3[0] - p2[0]) - (p3[1] - p2[1]) * (p2[0] - p1[0]);
        // 2 slope equal in the same line
        if (diff == 0) {
            return 0;
        }
            
        // slope1 > slope2 -> p2p3 is counter clock wise of p1p2
        if (diff > 0) {
            return 1;
        }

        // slope1 > slope2 -> p2p3 is clock wise of p1p2
        return 2;
    }

    // p3 is on (p1, p2)
    private boolean on(int[] p1, int[] p2, int[] p3) {
        int maxx = Math.max(p1[0], p2[0]), maxy = Math.max(p1[1], p2[1]), minx = Math.min(p1[0], p2[0]), miny = Math.min(p1[1], p2[1]);
        return minx <= p3[0] && p3[0] <= maxx && miny <= p3[1] && p3[1] <= maxy;
    }
}

/**
 * Calculate intersection points.
 * 
 * 1. a1x + b1y + c1 = 0 
 * 2. a2x + b2y + c2 = 0
 * 
 * a2 * a1x + a2 * b1y + a2 * c1 = 0 
 * a1 * a2x + a1 * b2y + a1 * c2 = 0
 * 
 * x = (c2 * b1 - c1 * b2) / (a1 * b2 - a2 * b1)
 * y = (c1 * a2 - c2 * a1) / (a1 * b2 - a2 * b1)
 * 
 * If (x, y) satisfies 1 and 2 equations, then (x, y) should be intersection points.
 */