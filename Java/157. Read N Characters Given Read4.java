// 157. Read N Characters Given Read4
// 此题意义模糊，无特别算法，不知考察点在哪
// 注意：char *buf is destination not source, similar to that of scanf("%s", buf), OJ outputs this buf value.
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    public int read(char[] buf, int n) {
        int total = 0;
        char[] tmp = new char[4];

        // 当 total == n 的时候停止
        while (total < n) {
            int count = read4(tmp);

            // 把读入的字符存储进buf中，注意不是所有tmp中的字符都能存储，还要考虑最大读取上限 total < n
            for (int i = 0; i < count && total < n; i++) {
                buf[total++] = tmp[i];
            }

            // 如果此次读入已经不足4个字符，说明 read4() 已经读完
            if(count < 4) {
                break;
            }
        }

        return total;
    }
}