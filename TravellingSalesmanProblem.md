# Travelling salesman problem

[Travelling salesman problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem)

Travelling Salesman Problem (TSP): Given a set of cities and distance between every pair of cities, the problem is to find the shortest possible route that visits every city exactly once and returns to the starting point.

[Hamiltonian Cycle](http://mathworld.wolfram.com/HamiltonianCycle.html)

The Hamiltonian cycle problem is to find if there exist a tour that visits every city exactly once. (Complete graph)

TSP problem is to find a minimum weight Hamiltonian cycle.

The problem is a famous NP hard problem. There is no polynomial time know solution for this problem.

https://www.geeksforgeeks.org/travelling-salesman-problem-set-1/
https://www.geeksforgeeks.org/travelling-salesman-problem-set-2-approximate-using-mst/

---

## Naive Solution:

1. Consider city 1 as the starting and ending point.
2. Generate all (n-1)! Permutations of cities.
3. Calculate cost of every permutation and keep track of minimum cost permutation.
4. Return the permutation with minimum cost.

Time Complexity: O(n!)

---

## Dynamic Programming

1. let C(S, i) be the cost of the minimum cost path visiting each vertex in set S exactly once, starting at 1 and ending at i. (1 must be present in every set S because finally the path must back to 1)
2. Start with all subsets of size 2 and calculate C(S, i) for all subsets where S is the subset, then we calculate C(S, i) for all subsets S of size 3 and so on.

```
if S.size() == 2, then S must be {1, i},
    C(S, i) = dist(1, i) 
else if S.size() > 2
    C(S, i) = min {C(S - {i}, j) + dis(j, i)} where j belongs to S, j != i and j != 1
```

Time Complexity: O(n^2 * 2^n), 2^n subsets, still exponential

---

## 2-Approximate MST

There is no polynomial time solution available for this problem as the problem is a known NP-Hard problem. There are approximate algorithms to solve the problem though. 

The approximate algorithms work only if the problem instance satisfies Triangle-Inequality.

**Triangle-Inequality**: The least distant path to reach a vertex j from i is always to reach j directly from i, rather than through some other vertex k (or vertices), i.e., dis(i, j) is always less than or equal to dis(i, k) + dist(k, j). The Triangle-Inequality holds in many practical situations.

1. Let 1 be the starting and ending point for salesman.
2. Construct MST from with 1 as root using Prim’s Algorithm.
3. List vertices visited in preorder walk of the constructed MST and add 1 at the end.
