// 853. Car Fleet
// Sort then compare time to reach destination
class Solution {
    class Car {
        int position;
        double time;
        Car(int p, double t) {
            position = p;
            time = t;
        }
    }

    public int carFleet(int target, int[] position, int[] speed) {
        if (position.length == 0 || speed.length == 0) {
            return 0;
        }

        int n = position.length;
        Car[] cars = new Car[n];

        for (int i = 0; i < n; ++i) {
            cars[i] = new Car(position[i], (double) (target - position[i]) / speed[i]);
        }

        Arrays.sort(cars, (a, b) -> a.position - b.position);

        // we record tail of fleet, res starts with 1
        int res = 1, t = n - 1;
        while (t > 0) {
            // if cars[t] arrives sooner than cars[t - 1], it can't be caught
            // cars[t] is the tail of current fleet
            if (cars[t].time < cars[t - 1].time) {
                res++;
            } else {
                // cars[t] is slow, it's the head of new fleet, cars[t - 1] will arrive at same time as cars[t]
                cars[t - 1] = cars[t];
            }
            t--;
        }
        
        return res;
    }
}

class Solution {
    public int carFleet(int target, int[] pos, int[] speed) {
        int n = pos.length;
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; ++i) {
            cars[i] = new double[] { pos[i], (double) (target - pos[i]) / speed[i] };
        }

        Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));

        int res = 0;
        double curMaxTime = 0; // current largest time cost to reach destination
        for (int i = n - 1; i >= 0; i--) {
            double curTime = cars[i][1];

            // current is slower one, head of new fleet
            if (curTime > curMaxTime) {
                curMaxTime = curTime;
                res++;
            }
        }

        return res;
    }
}
