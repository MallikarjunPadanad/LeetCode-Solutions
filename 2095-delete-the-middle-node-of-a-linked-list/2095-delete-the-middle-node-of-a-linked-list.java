/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    private ListNode findMiddlePrev(ListNode head) {
        ListNode slow = head, fast = head.next.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private void deleteNode(ListNode prev) {
        prev.next = prev.next.next;
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null) return null;

        ListNode prev = findMiddlePrev(head);
        deleteNode(prev);
        return head;
    }
}