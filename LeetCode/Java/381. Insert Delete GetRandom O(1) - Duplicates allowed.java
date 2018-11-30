// 381. Insert Delete GetRandom O(1) - Duplicates allowed
// 380. Insert Delete GetRandom O(1) follow up
class RandomizedCollection {
    List<Integer> nums;
    Map<Integer, Set<Integer>> position;
    Random rand;

    public RandomizedCollection() {
        nums = new ArrayList<>();
        position = new HashMap<>();
        rand = new Random();
    }
    
    public boolean insert(int val) {
        nums.add(val);
        if (position.containsKey(val)) {
            position.get(val).add(nums.size() - 1);
            return false;
        } else {
            position.put(val, new HashSet<>());
            position.get(val).add(nums.size() - 1);
            return true;
        }
    }

    public boolean remove(int val) {
        if (!position.containsKey(val)) {
            return false;
        }

        int pos = position.get(val).iterator().next();
        position.get(val).remove(pos);
        if (position.get(val).isEmpty()) {
            position.remove(val);
        }
        // 注意要这里判断 pos 不是优化，而是防止后面调用最后一个元素覆盖时候越界
        if(pos < nums.size() - 1) {
            nums.set(pos, nums.get(nums.size() - 1));
            position.get(nums.get(nums.size() - 1)).remove(nums.size() - 1);
            position.get(nums.get(nums.size() - 1)).add(pos);
        }
        nums.remove(nums.size() - 1);
        return true;
    }

    public int getRandom() {
        int r = rand.nextInt(nums.size());
        return nums.get(r);
    }
}