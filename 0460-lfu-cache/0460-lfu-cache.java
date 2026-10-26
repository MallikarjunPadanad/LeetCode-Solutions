class LFUCache {
    private final int cap;
    private int minFreq;
    private final Map<Integer, Integer> keyVal = new HashMap<>();
    private final Map<Integer, Integer> keyFreq = new HashMap<>();
    private final Map<Integer, LinkedHashSet<Integer>> freqKeys = new HashMap<>();
 
    public LFUCache(int capacity) {
        this.cap = capacity;
    }
 
    private void touch(int key) {
        int f = keyFreq.get(key);
        LinkedHashSet<Integer> set = freqKeys.get(f);
        set.remove(key);
        if (set.isEmpty()) {
            freqKeys.remove(f);
            if (minFreq == f) minFreq++;
        }
        keyFreq.put(key, f + 1);
        freqKeys.computeIfAbsent(f + 1, k -> new LinkedHashSet<>()).add(key);
    }
 
    public int get(int key) {
        if (!keyVal.containsKey(key)) return -1;
        touch(key);
        return keyVal.get(key);
    }
 
    public void put(int key, int value) {
        if (cap == 0) return;
        if (keyVal.containsKey(key)) {
            keyVal.put(key, value);
            touch(key);
            return;
        }
        if (keyVal.size() >= cap) {
            // evict LFU key; ties broken by least recently used (first in set)
            LinkedHashSet<Integer> set = freqKeys.get(minFreq);
            int evict = set.iterator().next();
            set.remove(evict);
            if (set.isEmpty()) freqKeys.remove(minFreq);
            keyVal.remove(evict);
            keyFreq.remove(evict);
        }
        keyVal.put(key, value);
        keyFreq.put(key, 1);
        freqKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */