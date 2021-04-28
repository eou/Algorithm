// 843. Guess the Word
// !!! Guess: choose a word (use Strategy to find a word) => calculate the match number => use matches to remove impossible words!
// !!! The strategy is to minimize the number of remaing possible words after each remove, to remove impossible words as much as possible.
class Solution {
    public void findSecretWord(String[] wordlist, Master master) {
        distanceGuess(wordlist, master);
    }

    private int match(String a, String b) {
        int matches = 0;
        for (int i = 0; i < a.length(); ++i) {
            if (a.charAt(i) == b.charAt(i)) {
                matches++;
            }
        }
        return matches;
    }

    private void shuffleGuess(String[] wordlist, Master master) {
        List<String> list = Arrays.asList(wordlist);

        for (int i = 0; i < 10; i++) {
            Collections.shuffle(list);
            String guess = list.get(0);
            int x = master.guess(guess);
            List<String> list2 = new ArrayList<>();

            for (String w : list) {
                if (match(guess, w) == x) {
                    list2.add(w);
                }
            }

            list = list2;
        }
    }

    // For distance between every words in wordlist, we can have 0 distance (same word), 1 distance,..., 6 distances (totally different)
    // this solution tries to find an available number which has minimum number of neighbors with 6 distances (0 matches)
    // thus from this number we can increase the possiblity to reach the target number
    // e.g. Starting from word A, there are lots of words which have 0 matches with A, we should ignore guessing word A 
    // since it is highly possible that we got 0 matches from 1 master guess, then we get nothing useful information from 1 guess
    private void minimizeZeroMatchGuess(String[] wordlist, Master master) {
        for (int i = 0; i < 10; ++i) {
            HashMap<String, Integer> count = new HashMap<>();
            for (String w1 : wordlist) {
                for (String w2 : wordlist) {
                    if (match(w1, w2) == 0) {
                        count.put(w1, count.getOrDefault(w1, 0) + 1);
                    }
                }
            }

            // we select a candidate who has a biggest "family",
            // (that is, the fewest 0 matched with other words.)
            // We want to guess a word that can minimum our worst outcome.
            String guess = "";
            int min = wordlist.length + 1;
            for (String w : wordlist) {
                if (count.getOrDefault(w, 0) < min) {
                    guess = w;
                    min = count.getOrDefault(w, 0);
                }
            }

            int x = master.guess(guess);
            List<String> wordlist2 = new ArrayList<>();
            for (String w : wordlist) {
                if (match(guess, w) == x) {
                    wordlist2.add(w);
                }
            }

            wordlist = wordlist2.toArray(new String[0]);
        }
    }

    private void scoreGuess(String[] wordlist, Master master) {
        for (int i = 0; i < 10; i++) {
            int freq[][] = new int[6][26];
            for (String w : wordlist) {
                for (int j = 0; j < 6; j++)
                    freq[j][w.charAt(j) - 'a']++;
            }

            String guess = wordlist[0];
            int bestScore = 0;
            for (String w : wordlist) {
                int score = 0;
                for (int j = 0; j < 6; j++) {
                    score += freq[j][w.charAt(j) - 'a'];
                }
                if (score > bestScore) {
                    guess = w;
                    bestScore = score;
                }
            }

            int x = master.guess(guess);
            List<String> wordlist2 = new ArrayList<String>();
            for (String w : wordlist) {
                if (match(guess, w) == x) {
                    wordlist2.add(w);
                }
            }

            wordlist = wordlist2.toArray(new String[0]);
        }
    }

    // For distance between every words in wordlist, we can have 0 distance (same word), 1 distance,..., 6 distances (totally different)
    // this solution tries to find an available number which has minimum number of maximum number in distances array [0,1,2,3,4,5,6]
    // thus we will reduce the future guess steps, also min-max
    private void distanceGuess(String[] wordlist, Master master) {
        int n = wordlist.length;
        int[][] dis = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                dis[j][i] = match(wordlist[i], wordlist[j]);
                dis[i][j] = dis[j][i];
            }
        }

        Set<Integer> removed = new HashSet<>();
        for (int t = 0; t < 10; t++) {
            int maxDis = Integer.MAX_VALUE;
            int guess = 0;

            for (int i = 0; i < n; i++) {
                if (!removed.contains(i)) {
                    // nums[i] means the number of words which is i distances from current word
                    int[] nums = new int[7];
                    for (int j = 0; j < n; j++) {
                        if (i != j && !removed.contains(j)) {
                            nums[dis[i][j]] += 1;
                        }
                    }

                    int curMaxDis = nums[0];
                    for (int num : nums) {
                        curMaxDis = Math.max(curMaxDis, num);
                    }
                    if (curMaxDis < maxDis) {
                        maxDis = curMaxDis;
                        guess = i;
                    }
                }
            }

            int x = master.guess(wordlist[guess]);
            removed.add(guess);
            for (int i = 0; i < wordlist.length; i++) {
                if (!removed.contains(i) && match(wordlist[guess], wordlist[i]) != x) {
                    removed.add(i);
                }
            }
        }
    }
}