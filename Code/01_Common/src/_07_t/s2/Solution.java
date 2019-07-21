package _07_t.s2;

class Solution {
    public static int lengthOfLongestSubstring(String s) {
        if(s == null || s.length() == 0 || s.equals("")){
            return 0;
        }
        int[] mark = new int[256];

        for(int i=0; i < 256; i++){
            mark[i] = -1;
        }

        int max = 1;
        int start = 0;
        for(int i=0; i < s.length(); i++){
            int cur = s.charAt(i);
            if(mark[cur] != -1){
                mark[cur] = i;
                start = mark[cur] + 1;
            }
            else{
                max = Math.max(max,i - start + 1);
            }
            mark[cur] = i;
        }

        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
//        System.out.println(lengthOfLongestSubstring("abcabcbb"));
    }
}