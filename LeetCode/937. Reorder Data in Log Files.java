// 937. Reorder Data in Log Files
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        List<String> letterLogs = new ArrayList<>();
        List<String> digitLogs = new ArrayList<>();
        for (String log : logs) {
            String[] id = log.split(" ");
            if (Character.isDigit(id[1].charAt(0))) {
                digitLogs.add(log);
            } else {
                letterLogs.add(log);
            }
        }
        Collections.sort(letterLogs, new Comparator<String>(){
            public int compare(String s1, String s2) {
                int i = 0;
                for (; i < s1.length(); i++) {
                    if (s1.charAt(i) == ' ') {
                        break;
                    }
                }
                String _s1 = s1.substring(i + 1);
                String id1 = s1.substring(0, i);
                i = 0;
                for (; i < s2.length(); i++) {
                    if (s2.charAt(i) == ' ') {
                        break;
                    }
                }
                String _s2 = s2.substring(i + 1);
                String id2 = s2.substring(0, i);
                
                if (_s1.equals(_s2)) {
                    return id1.compareTo(id2);
                }
                return _s1.compareTo(_s2);
            }
        });
        
        String[] res = new String[logs.length];
        for (int i = 0; i < letterLogs.size(); i++) {
            res[i] = letterLogs.get(i);
        }
        for (int i = 0; i < digitLogs.size(); i++) {
            res[i + letterLogs.size()] = digitLogs.get(i);
        }
        return res;
    }
}

// use 1 comparator
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] split1 = s1.split(" ", 2);
                String[] split2 = s2.split(" ", 2);
                String id1 = split1[0];
                String id2 = split2[0];
                String log1 = split1[1];
                String log2 = split2[1];

                boolean isDigit1 = Character.isDigit(log1.charAt(0));
                boolean isDigit2 = Character.isDigit(log2.charAt(0));
                // letter log
                if (!isDigit1 && !isDigit2) {
                    int comp = log1.compareTo(log2);
                    if (comp != 0) {
                        return comp;
                    }
                    return id1.compareTo(id2);
                }
                // digit log compare with letter log or digit log
                return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
            }
        });
        return logs;
    }
}