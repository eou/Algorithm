# Union Find

`union find`：[Union-Find Algorithms](https://www.cs.princeton.edu/~rs/AlgsDS07/01UnionFind.pdf). also named `Disjoint-set`.


```java
class unionFind {
        private int[] id;
        private int unions;
    	private int[] size;
    	private int[] rank;

        public unionFind(int n) {
            unions = n;
            id = new int[n];
            size = new int[n];
            // initialize the root of each node to itself
            // initialize the size of each union to 1
            for (int i = 0; i < n; i++) {
                id[i] = i;
                size[i] = 1;
                rank[i] = 0;
            }
        }

        public int root(int i) {
            while (i != id[i]) {
                id[i] = id[id[i]]; // path compression
                i = id[i];
            }
            return i;

            // query root recursively
            // if (i == id[i]) {
            //     return i;
            // }
            // return root(id[i]);
        }

        public void unite(int p, int q) {
            if(find(p, q)) {
                return;
            }
            
            int i = root(p);
            int j = root(q);
            
            // if-else: weighted quick-union
            // root points to other root
            // merge by size
            if (size[i] < size[j]) {
                id[i] = j;
                size[j] += size[i];
            } else {
                id[j] = i;
                size[i] += size[j];
            }
            
            // merge by rank
            if (rank[i] < rank[j]) {
                id[i] = j;
            } else if (rank[i] > rank[j]) {
                id[j] = i;
            } else {
                id[i] = j;
                rank[j]++;
            }

            unions--;
        }

        public boolean find(int p, int q) {
            return root(p) == root(q);
        }
    }
```

Union Find generally used checking connectivity.

- Init: root's parent is -1 if its status is unknown and root's parent is itself when it is a one element set.
- Query from root: query interatively or recursively. It includes path compression.
- Merge Two Sets: can be merged by rank(depth) or by size. But path compression might change sets' ranks, so it is more compatible with merge by size. If merging by rank, only merging two sets with same rank will effect rank. If a set with smaller rank connects with a set with larger rank, the former will disappear and the latter's rank won't change.

---

# Complexity

- Space Compexity is `O(n)`；
- Time Compexity is almost `O(n)`. Call m union function will cost `O(n + mlg*n)` if using path compression and merge by rank. And the single operation will cost `O(lg*n) = O(α(n)) ≈ O(1)` on average. (Ackermann function's Inverse function)
