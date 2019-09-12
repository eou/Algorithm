# Knuth–Morris–Pratt algorithm

[Knuth–Morris–Pratt algorithm](https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm)

Knuth–Morris–Pratt string-searching algorithm (or KMP algorithm) searches for occurrences of a "word" W within a main "text string" S by employing the observation that when a mismatch occurs, the word itself embodies sufficient information to determine where the next match could begin, thus bypassing re-examination of previously matched characters.

```java
class KMP {
    public static int NaiveMatch(String s, String p) {
        int i = 0, j = 0;
        while (i < s.length() && j < p.length()) {
            if (s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else {
                i = i - j + 1;  // last matching's start + 1
                j = 0;
            }
        }
        if (j == p.length()) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static int KMP(String s, String p) {
        int i = 0, j = 0;
        while (i < s.length() && j < p.length()) {
            if (j == -1 || s.charAt(i) == p.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == p.length()) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static int[] MakeNext(String p) {
        int[] next = new int[p.length()];
        next[0] = -1;
        int i = 0, j = -1;
        while (i < p.length() - 1) {
            if (j == -1 || p.charAt(i) == p.charAt(j)) {
                next[++i] = ++j;
                // optimization
                next[i] = (p.charAt(i) == p.charAt(j) ? next[j] : j);
            } else {
                j = next[j];
            }
        }
        return next;
    }
}
```
