class LFUCache {
    private int cacheMaxCapacityAllowed;
    private int currentCacheElementCount;
    private int minimumObservedFrequency;

    private int[] storedItemValuesMap;
    private int[] itemAccessFrequenciesMap;

    private int[] dllPreviousNodeLink;
    private int[] dllFollowingNodeLink;

    private int[] frequencyDllHeadPointers;
    private int[] frequencyDllTailPointers;

    public LFUCache(int capacity) {
        this.cacheMaxCapacityAllowed = capacity;
        this.currentCacheElementCount = 0;
        this.minimumObservedFrequency = 0;

        this.storedItemValuesMap = new int[100005];
        this.itemAccessFrequenciesMap = new int[100005];
        this.dllPreviousNodeLink = new int[100005];
        this.dllFollowingNodeLink = new int[100005];

        this.frequencyDllHeadPointers = new int[200005];
        this.frequencyDllTailPointers = new int[200005];
    }

    public int get(int key) {
        int indexAdjustedKey = key + 1;

        if (itemAccessFrequenciesMap[indexAdjustedKey] == 0) {
            return -1;
        }

        elevateNodeFrequencyLevel(indexAdjustedKey);

        return storedItemValuesMap[indexAdjustedKey];
    }

    public void put(int key, int value) {
        int indexAdjustedKey = key + 1;

        if (itemAccessFrequenciesMap[indexAdjustedKey] > 0) {
            storedItemValuesMap[indexAdjustedKey] = value;
            elevateNodeFrequencyLevel(indexAdjustedKey);
            return;
        }

        if (currentCacheElementCount == cacheMaxCapacityAllowed) {
            purgeLeastFrequentlyUsedElement();
        }

        storedItemValuesMap[indexAdjustedKey] = value;
        itemAccessFrequenciesMap[indexAdjustedKey] = 1;

        attachNodeToFrequencyListHead(indexAdjustedKey, 1);

        minimumObservedFrequency = 1;
        currentCacheElementCount++;
    }

    private void elevateNodeFrequencyLevel(int targetKeyElement) {
        int presentFrequencyLevel = itemAccessFrequenciesMap[targetKeyElement];

        detachNodeFromFrequencyList(targetKeyElement, presentFrequencyLevel);

        if (minimumObservedFrequency == presentFrequencyLevel && frequencyDllHeadPointers[presentFrequencyLevel] == 0) {
            minimumObservedFrequency++;
        }

        itemAccessFrequenciesMap[targetKeyElement] = presentFrequencyLevel + 1;
        attachNodeToFrequencyListHead(targetKeyElement, presentFrequencyLevel + 1);
    }

    private void purgeLeastFrequentlyUsedElement() {
        int nodeDesignatedForEviction = frequencyDllTailPointers[minimumObservedFrequency];

        detachNodeFromFrequencyList(nodeDesignatedForEviction, minimumObservedFrequency);
        itemAccessFrequenciesMap[nodeDesignatedForEviction] = 0;

        currentCacheElementCount--;
    }

    private void attachNodeToFrequencyListHead(int elementToAttach, int targetFrequencyTier) {
        int currentHeadForTier = frequencyDllHeadPointers[targetFrequencyTier];

        if (currentHeadForTier == 0) {
            frequencyDllHeadPointers[targetFrequencyTier] = elementToAttach;
            frequencyDllTailPointers[targetFrequencyTier] = elementToAttach;
            dllPreviousNodeLink[elementToAttach] = 0;
            dllFollowingNodeLink[elementToAttach] = 0;
        } else {
            dllFollowingNodeLink[elementToAttach] = currentHeadForTier;
            dllPreviousNodeLink[currentHeadForTier] = elementToAttach;
            dllPreviousNodeLink[elementToAttach] = 0;
            frequencyDllHeadPointers[targetFrequencyTier] = elementToAttach;
        }
    }

    private void detachNodeFromFrequencyList(int elementToDetach, int sourceFrequencyTier) {
        int previousLinkNode = dllPreviousNodeLink[elementToDetach];
        int subsequentLinkNode = dllFollowingNodeLink[elementToDetach];

        if (previousLinkNode != 0) {
            dllFollowingNodeLink[previousLinkNode] = subsequentLinkNode;
        } else {
            frequencyDllHeadPointers[sourceFrequencyTier] = subsequentLinkNode;
        }

        if (subsequentLinkNode != 0) {
            dllPreviousNodeLink[subsequentLinkNode] = previousLinkNode;
        } else {
            frequencyDllTailPointers[sourceFrequencyTier] = previousLinkNode;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */