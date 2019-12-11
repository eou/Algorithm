// 277. Find the Celebrity
public class Solution extends Relation {
    public int findCelebrity(int n) {
        int celebrity = 0;
        for(int i = 0; i < n; i++) {
            if(knows(celebrity, i)) {
                celebrity = i;
            }
        }
        
        // verify
        for(int i = 0; i < n; i++) {
            if(i < celebrity) {
                if(knows(celebrity, i) || !knows(i, celebrity)) {
                    return -1;
                }
            }
            if(i > celebrity) {
                if(!knows(i, celebrity)) {
                    return -1;
                }
            }
        }
        
        return celebrity;
    }
}