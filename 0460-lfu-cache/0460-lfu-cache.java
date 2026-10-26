class LFUCache {
    private static final class Node {
        int key, val, freq = 1;
        Node prev, next;
        Node() {}
        Node(int k, int v) { key = k; val = v; }
    }
 
    private static final class DLL {
        final Node head = new Node(), tail = new Node();
        int size;
        DLL() { head.next = tail; tail.prev = head; }
 
        void addFirst(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next.prev = n;
            head.next = n;
            size++;
        }
 
        void remove(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
            size--;
        }
 
        Node removeLast() {
            Node n = tail.prev;
            remove(n);
            return n;
        }
    }
 
    private final int cap;
    private int minFreq;
    private final Map<Integer, Node> nodes;
    private final Map<Integer, DLL> lists = new HashMap<>();
 
    public LFUCache(int capacity) {
        cap = capacity;
        nodes = new HashMap<>(Math.max(16, capacity * 2));
    }
 
    private void bump(Node n) {
        DLL old = lists.get(n.freq);
        old.remove(n);
        if (old.size == 0) {
            lists.remove(n.freq);
            if (minFreq == n.freq) minFreq++;
        }
        n.freq++;
        lists.computeIfAbsent(n.freq, k -> new DLL()).addFirst(n);
    }
 
    public int get(int key) {
        Node n = nodes.get(key);
        if (n == null) return -1;
        bump(n);
        return n.val;
    }
 
    public void put(int key, int value) {
        if (cap == 0) return;
        Node n = nodes.get(key);
        if (n != null) {
            n.val = value;
            bump(n);
            return;
        }
        if (nodes.size() == cap) {
            DLL l = lists.get(minFreq);
            Node ev = l.removeLast();          // LFU, ties -> LRU
            if (l.size == 0) lists.remove(minFreq);
            nodes.remove(ev.key);
        }
        n = new Node(key, value);
        nodes.put(key, n);
        lists.computeIfAbsent(1, k -> new DLL()).addFirst(n);
        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */