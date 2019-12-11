// 68. Text Justification
// too many details
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int curWidth = 0;
        int start = 0;
        for (int i = 0; i < words.length; i++) {
            if (curWidth == 0) {
                curWidth += words[i].length();
            } else if (curWidth + words[i].length() + 1<= maxWidth) {
                curWidth += (words[i].length() + 1);  // extra space
            } else {
                // last word has already overbound, go back one word
                res.add(makeOneLineText(words, maxWidth, start, i, false));
                start = i;
                i -= 1;     // next loop i++
                curWidth = 0;
            }
        }
        // last line
        res.add(makeOneLineText(words, maxWidth, start, words.length, true));
        return res;
    }
    
    public String makeOneLineText(String[] words, int maxWidth, int start, int end, boolean isLast) {
        String res = "";
        if (isLast || end - start == 1) {   // last line or single word one line
            for (int i = start; i < end; i++) {
                if (i == start) {
                    res += words[i];
                } else {
                    res += " ";
                    res += words[i];
                }
            }
            while (res.length() < maxWidth) {
                res += " ";
            }
        } else {
            int wordsLength = 0;
            for (int i = start; i < end; i++) {
                wordsLength += words[i].length();
            }

            int spaceLength = maxWidth - wordsLength;
            int slots = spaceLength / (end - start - 1);
            int restSpace = spaceLength - slots * (end - start - 1);
            for (int i = start; i < end - 1; i++) {
                res += words[i];
                // rest space evenly divide
                if (restSpace > 0) {
                    restSpace--;
                    res += makeSpaces(slots + 1);
                } else {
                    res += makeSpaces(slots);
                }
            }
            res += words[end - 1];
        }
        
        return res;
    }
    
    public String makeSpaces(int n) {
        String res = "";
        while (n-- > 0) {
            res += " ";
        }
        return res;
    }
}

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> results = new ArrayList<>();

        int start = 0;
        int end = start;
        for(; end < words.length; start = end) {
            int len = -1; // a space + a word
            for(end = start; end < words.length && (len + words[end].length() + 1) <= maxWidth; end++) {
                len += words[end].length() + 1;
            }
            
            results.add(helper(words, start, end, maxWidth));
        }

        return results;
    }

    private String helper(String[] words, int start, int end, int maxWidth) {
        int len = -1;
        for(int i = start; i < end; i++) {
            len += words[i].length() + 1;
        }

        StringBuilder result = new StringBuilder(words[start]);

        int space = 1;
        int extra = 0;
        // not 1 char, not last line
        if (end != start + 1 && end != words.length) {
            space = (maxWidth - len) / (end - start - 1) + 1;
            extra = (maxWidth - len) % (end - start - 1);
        }
        for (int i = start + 1; i < end; i++) {
            for (int s = 0; s < space; s++) {
                result.append(' ');
            }
            if (extra > 0) {
                result.append(' ');
                extra--;
            }

            result.append(words[i]);
        }

        // last line, space = 1, extra = 0
        int lastSpace = maxWidth - result.length();
        while (lastSpace > 0) {
            result.append(' ');
            lastSpace--;
        }

        return result.toString();
    }
}