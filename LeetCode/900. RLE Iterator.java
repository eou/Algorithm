// 900. RLE Iterator
class RLEIterator {
    private int pointer;
    private int[] A;
    public RLEIterator(int[] A) {
        this.A = A;
        this.pointer = 0;
    }
    
    public int next(int n) {
        // exhaust numbers as many as possible
        while(pointer < A.length && n > A[pointer]){
            n = n - A[pointer];
            pointer += 2;
        }
        
        if(pointer > A.length - 1){
            return -1;
        }
        
        A[pointer] -= n;
        return A[pointer + 1];
    }
}

// TLE
class RLEIterator {
    private int[] A;
    private int pointer;

    public RLEIterator(int[] A) {
        this.A = A;
        this.pointer = 0;
    }

    public int next(int n) {
        while (pointer < A.length - 1 && n > 0) {
            while (pointer < A.length && A[pointer] == 0) {
                pointer += 2;
            }

            if (pointer >= A.length) {
                break;
            }

            A[pointer]--;
            n--;

            if (n == 0) {
                return A[pointer + 1];
            }
        }

        return -1;
    }
}