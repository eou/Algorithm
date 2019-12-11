// 158. Read N Characters Given Read4 II - Call multiple times
// 与 157 不同之处在于重复调用 read() 的时候会从上次读完的地方继续读
// 重点在于上一次从 read4() 读取的内容不一定已经全部用上，所以用方法外的变量存储已经读取的数据和上一次读取结束的位置
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    private int count = 0;
    private int pointer = 0;
    private char[] tmp = new char[4];

    public int read(char[] buf, int n) {
        int total = 0;
        
        while(total < n) {
            if(pointer == 0) {
                count = read4(tmp);
            }
            
            while(total < n && pointer < count) {
                buf[total++] = tmp[pointer++];
            }
            
            if(pointer == count) {
                pointer = 0;
            }
            
            if(count < 4) {
                break;
            }
        }
        
        return total;
    }
}

public class Solution extends Reader4 {
    // 另一个简洁版本
    private int count = 0; // numbers of characters we read from the file using read4()
    private int pointer = 0; // point to the current character in tmp[]
    private char[] tmp = new char[4];

    public int read(char[] buf, int n) {
        // Pointer to external buffer
        int total = 0;
        while (total < n) {
            // if pointer < count, which means there are still remaining datas in tmp since last read
            if (pointer == count) {
                pointer = 0; // Reset interval buffer's pointer to the beginning
                count = read4(tmp);
                if (count == 0) {
                    break;
                }  
            }
            buf[total++] = tmp[pointer++];
        }

        return total;
    }
}
