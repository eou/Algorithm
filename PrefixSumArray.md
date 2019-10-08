# Prefix Sum Array

[Prefix Sum](https://en.wikipedia.org/wiki/Prefix_sum)

---

Given input array `nums[]`.

```java
// prefix[0]         prefix[1]                  prefix[2]       
// nums[0],      nums[0] + nums[1],     nums[0] + nums[1] + nums[2],  ...

// nums[i] + nums[i + 1] + ... + nums[j] = prefix[j] - prefix[i - 1] (i > 0)
// nums[0] + nums[1] + ... + nums[j] = prefix[j] - **nums[-1] ?** = prefix[j]
int[] prefix = new int[nums.length];
prefix[0] = nums[0];
for (int i = 1; i < nums.length; i++) {
    prefix[i] = prefix[i - 1] + nums[i];
}
```

Use 1 more space, don't need check border:
```java
// prefix[0]    prefix[1]           prefix[2]                  prefix[3]
//    0,         nums[0],       nums[0] + nums[1],     nums[0] + nums[1] + nums[2],  ...

// nums[i] + nums[i + 1] + ... + nums[j] = prefix[j + 1] - prefix[i] (j <= nums.length)
// nums[0] + nums[1] + ... + nums[j] = prefix[j + 1] - prefix[0] = prefix[j + 1]
int[] prefix = new int[nums.length + 1];
for (int i = 1; i <= nums.length; i++) {
    prefix[i] = prefix[i - 1] + nums[i - 1];
}
```