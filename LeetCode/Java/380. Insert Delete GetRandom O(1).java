// 380. Insert Delete GetRandom O(1)
// List insert(add) / getRandom O(1); HashMap insert / get / remove is O(1)  
class RandomizedSet {
    List<Integer> nums;
    HashMap<Integer, Integer> position; // number, position of nums array
    Random rand;

    public RandomizedSet() {
        nums = new ArrayList<>();
        position = new HashMap<>();
        rand = new Random();
    }

    public boolean insert(int val) {
        if(position.containsKey(val)) {
            return false; // already in the set
        }

        nums.add(val);
        position.put(val, nums.size() - 1); // add the val in the end of the nums
        return true;
    }

    public boolean remove(int val) {
        if(!position.containsKey(val)) {
            return false; // cannot find the val
        }

        int pos = position.get(val);
        // 不能这样赋值
        // nums.get(pos) = nums.get(nums.size() - 1);
        nums.set(pos, nums.get(nums.size() - 1));
        position.put(nums.get(nums.size() - 1), pos);
        
        nums.remove(nums.size() - 1);
        position.remove(val);
        
        return true;
    }

    public int getRandom() {
        int r = rand.nextInt(nums.size());
        return nums.get(r);
    }
}
