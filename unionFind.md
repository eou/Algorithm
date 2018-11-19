# 1 并查集

很早在[Algorithms, 4th Edition](https://algs4.cs.princeton.edu/home/)上第一章看到`union find`：[Union-Find Algorithms](https://www.cs.princeton.edu/~rs/AlgsDS07/01UnionFind.pdf). 也称`Disjoint-set`.

当时并不在意，也没深究，不曾想到其如此重要。

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

            // 也可以递归查找根
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
            // 注意不是把当前结点的根指向另一个结点，而是当前结点所在的集合的根的根指向另一个结点所在的集合的根
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

并查集的想法来源于“合并集合”和“查找集合”操作，一般用于判断连通性。

并查集一般有三个基本操作：

- 初始化：在不知道结点状态的时候，结点的父亲设为`-1`（因为数组下标从`0`开始），当结点为独立集合的时候，父亲设定为自己；

- 查询根：根据父亲下标寻找祖先，可以循环查找，也可以递归查找。此方法顺便包含“路径压缩”的优化，使得集合中的结点直接连在该集合的根上；

- 合并集合：将两个集合合并，可以根据集合的等级/秩`rank`合并，也可以根据集合的大小`size`合并（启发式合并）。两者效率并无不同，但是“路径压缩”可能会改变秩（树的深度），更加兼容按大小合并。

  例如合并树就可以根据树的深度或者树的结点个数。不过需要注意如果按秩合并，只有当两个同秩的集合合并，秩才会增加（比如深度小的树连接在深度大的树上并不会增加深度大的树的深度）。

---

# 复杂度

- 空间复杂度为元素的总个数为`O(n)`；
- 时间复杂度近似为`O(n)`，在同时使用路径压缩，按秩/大小合并优化的情况下，从一个完全不相连通的`n`个对象的集合开始，调用任意顺序的`m`次合并操作所需的时间为`O(n + mlg*n)`，其中单次操作的平均复杂度为`O(lg*n) = O(α(n)) ≈ O(1)`. （此函数为[阿克曼函数](https://zh.wikipedia.org/wiki/%E9%98%BF%E5%85%8B%E6%9B%BC%E5%87%BD%E6%95%B8)的反函数，原函数增长速度极快）

---





