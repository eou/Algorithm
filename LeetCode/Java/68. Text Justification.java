// 68. Text Justification
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