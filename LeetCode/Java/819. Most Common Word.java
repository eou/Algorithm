// 819. Most Common Word
// Amazon OA2
class Solution {
    public String mostCommonWord(String paragraph, String[] banned) {
        String result = "";
        String lowerCaseText = paragraph.toLowerCase();
        String[] splitText = lowerCaseText.split("[^a-z]");
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < splitText.length; i++) {
            if (splitText[i].length() > 0) {
                map.put(splitText[i], map.getOrDefault(splitText[i], 0) + 1);
            }
        }
        for (int i = 0; i < banned.length; i++) {
            banned[i] = banned[i].toLowerCase();
        }

        int mostFrequency = 0;
        for(String key : map.keySet()) {
            if (!Arrays.asList(banned).contains(key)) {
                mostFrequency = mostFrequency < map.get(key) ? map.get(key) : mostFrequency;
            }
        }
        for (String key : map.keySet()) {
            if (map.get(key) == mostFrequency && !Arrays.asList(banned).contains(key)){
                result = key;
            }
        }

        return result;
    }
}