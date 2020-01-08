// # e-mail thread: 题目很长，说是有一个list，每个element都是一封字符串表示的邮件 ，格式是 “a@gmail.com, b@gmail.com, abc（内容）” 
// # 之后如果b对a回复了邮件，将会是“b@gmail.com, a@gmail.com, cde---abc"，邮件 是已经按发送顺序排序好的 
// # 主要是让你写字符串处理，让你把不同对话归类（thread）到一起并标序号，就像reddit 或者老贴吧的楼中楼一样。
import java.util.*;

class Solution {
    public static class Email {
        int threadId;
        int posId;
        String cur;
        String pre;
        public Email(int threadId, int posId, String cur, String pre) {
            this.threadId = threadId;
            this.posId = posId;
            this.cur = cur;
            this.pre = pre;
        }
    }

    public static int[][] emailThread(String[] emails) {
        int threadId = 1;
        List<int[]> resList = new ArrayList<>();
        Map<String, List<Email>> map = new HashMap<>(); // sender+receiver, threadId+posId+reply+previous
        for (String e : emails) {
            // xxx, xxx, xxx---xxx
            String[] tmp1 = e.split(", ");
            String sen = tmp1[0];
            String rec = tmp1[1];
            String[] tmp2 = tmp1[2].split("---");
            String cur = tmp2[0];
            String pre = tmp2.length == 2 ? tmp2[1] : ""; 
            
            String key = sen.compareTo(rec) > 0 ? sen + rec : rec + sen;
            Email em = new Email(-1, -1, cur, pre);

            if (!map.containsKey(key)) {
                em.threadId = threadId++; // thread id
                em.posId = 1; // position id
                List<Email> list = new ArrayList<>();
                list.add(em);
                map.put(key, list);
                int[] resArr = new int[] { em.threadId, em.posId };
                resList.add(resArr);
            } else {
                // compare previous thread
                List<Email> list = map.get(key);
                boolean connect = false;
                for (Email email : list) {
                    if (email.cur.equals(em.pre)) {
                        // find connection, rewrite latest thread content
                        connect = true;
                        email.cur = em.cur;
                        email.pre = em.pre;
                        email.posId++;
                        int[] resArr = new int[] { email.threadId, email.posId };
                        resList.add(resArr);
                        break;
                    }
                }
                if (connect == false) {
                    em.threadId = threadId++; // thread id
                    em.posId = 1; // position id
                    list.add(em);
                    map.put(key, list);
                    int[] resArr = new int[] { em.threadId, em.posId };
                    resList.add(resArr);
                } else {
                    map.put(key, list);
                }
            }
        }

        int[][] res = new int[resList.size()][2];
        for (int i = 0; i < resList.size(); i++) {
            res[i] = resList.get(i);
        }
        return res;
    }

    public static void main(String[] args) {
        String[] input = new String[5];
        input[0] = "a@gmail.com, b@gmail.com, first";
        input[1] = "b@gmail.com, c@gmail.com, first2";
        input[2] = "b@gmail.com, a@gmail.com, second---first";
        input[3] = "b@gmail.com, a@gmail.com, hi";
        input[4] = "b@gmail.com, a@gmail.com, third---second";

        int[][] result = emailThread(input);

        for (int[] res : result) {
            System.out.print(res[0] + " ");
            System.out.println(res[1]);
        }
    }
}