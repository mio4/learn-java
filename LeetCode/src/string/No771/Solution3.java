package string.No771;

class Solution3 {
    public int numJewelsInStones(String J, String S) {
        return S.replaceAll("[^" + J + "]","").length();
    }
}
