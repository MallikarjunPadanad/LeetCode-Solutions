class Solution {
    private String s;
    private int pos;

    public List<String> braceExpansionII(String expression) {
        this.s = expression;
        this.pos = 0;
        Set<String> result = parseExpr();
        List<String> list = new ArrayList<>(result);
        Collections.sort(list);
        return list;
    }

    // expr := term (',' term)*
    private Set<String> parseExpr() {
        Set<String> result = new TreeSet<>(parseTerm());
        while (pos < s.length() && s.charAt(pos) == ',') {
            pos++; // skip ','
            result.addAll(parseTerm());
        }
        return result;
    }

    // term := factor+   (Cartesian-product concatenation)
    private Set<String> parseTerm() {
        List<Set<String>> factors = new ArrayList<>();
        while (pos < s.length() && s.charAt(pos) != ',' && s.charAt(pos) != '}') {
            factors.add(parseFactor());
        }
        Set<String> result = new HashSet<>();
        result.add("");
        for (Set<String> factor : factors) {
            Set<String> next = new HashSet<>();
            for (String prefix : result) {
                for (String suffix : factor) {
                    next.add(prefix + suffix);
                }
            }
            result = next;
        }
        return result;
    }

    // factor := letters | '{' expr '}'
    private Set<String> parseFactor() {
        Set<String> result = new HashSet<>();
        if (s.charAt(pos) == '{') {
            pos++; // skip '{'
            result = parseExpr();
            pos++; // skip '}'
        } else {
            int start = pos;
            while (pos < s.length() && Character.isLetter(s.charAt(pos))) {
                pos++;
            }
            result.add(s.substring(start, pos));
        }
        return result;
    }
}