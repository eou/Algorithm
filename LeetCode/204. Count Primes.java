// 204. Count Primes
// brute-force, TLE, O(N√N), n * squre root of n
class Solution {
    public int countPrimes(int n) {
        // unnecessary check
        if (n < 2) {
            return 0;
        }
    
        int res = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime(i)) {
                res++;                
            }
        }
        return res;
    }
    
    private boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

// Sieve of Eratosthenes, O(NloglogN)
class Solution {
    public int countPrimes(int n) {
        boolean[] primes = new boolean[n];
        Arrays.fill(primes, true);

        int res = 0;
        for (int i = 2; i < n; i++) {
            if (primes[i]) {
                res++;
                // mark 2*x, 3*x, ... as not prime number
                for (int j = 2; i * j < n; j++) {
                    primes[i * j] = false;
                }
            }
        }
        return res;
    }
}

class Solution {
    public int countPrimes(int n) {
        boolean[] primes = new boolean[n];
        Arrays.fill(primes, true);

        int res = 0;
        for (int i = 2; i < n; i++) {
            if (primes[i]) {
                res++;
                // we can start from i * i, but not 2 * i since 2 * i has been marked by 2's multiplication
                for (int j = i; (long) i * j < n; j++) {
                    primes[i * j] = false;
                }
            }
        }
        return res;
    }
}

// Euler Sieve, O(N), (since X = A * B * ... * Z could be marked by A, B, ... Z multiple times)
class Solution {
    public int countPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        boolean[] isPrime = new boolean[n];
        Arrays.fill(isPrime, true);

        for (int i = 2; i < n; ++i) {
            if (isPrime[i]) {
                primes.add(i);
            }
            for (int j = 0; j < primes.size() && i * primes.get(j) < n; ++j) {
                isPrime[i * primes.get(j)] = false;
                // if i % primes.get(j) == 0, then for i * primes.get(j + 1), it must be marked by the check from i / primes.get(j) * primes.get(j + 1)
                if (i % primes.get(j) == 0) {
                    break;
                }
            }
        }
        return primes.size();
    }
}