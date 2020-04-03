package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer3</p>
 * <p>
 * Collections.reverse
 * Deque
 * Queue	                        Deque
 * 添加元素到队尾	    add(E e) / offer(E e)	        addLast(E e) / offerLast(E e)
 * 取队首元素并删除	    E remove() / E poll()	        E removeFirst() / E pollFirst()
 * 取队首元素但不删除	E element() / E peek()	        E getFirst() / E peekFirst()
 * 添加元素到队首	    无	                            addFirst(E e) / offerFirst(E e)
 * 取队尾元素并删除	    无	                            E removeLast() / E pollLast()
 * 取队尾元素但不删除	无	                            E getLast() / E peekLast()
 * </p>
 * <p>
 * 面试题32 - II. 从上到下打印二叉树 II <层序遍历>
 * 面试题34. 二叉树中和为某一值的路径  -> Path  <根到叶子节点的路径集合>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-31 09:21
 */
public class offer3 {

    /**
     * 面试题31. 栈的压入、弹出序列
     *
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int lp = pushed.length;
        int i = 0;
        int j = 0;
        if (lp == 0) {
            return true;
        }
        Stack<Integer> stack = new Stack<>();
        // pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
        while (true) {
            if (stack.empty() || stack.peek() != popped[j]) {
                while (stack.empty() || stack.peek() != popped[j]) {
                    if (i == lp) {
                        return false;
                    }
                    stack.push(pushed[i++]);
                }
                stack.pop();
                j++;
            } else if (stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
            if (j == lp && stack.empty()) {
                return true;
            }
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 面试题32 - I. 从上到下打印二叉树
     *
     * @param root
     * @return
     */
    public int[] levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int[] a = new int[1000];
        int idx = 0;
        if (root == null) {
            return new int[]{};
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            a[idx++] = root.val;
            if (root.left != null) {
                queue.add(root.left);
            }
            if (root.right != null) {
                queue.add(root.right);
            }
        }
        return Arrays.copyOf(a, idx);
    }

    /**
     * 面试题32 - II. 从上到下打印二叉树 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder_v2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (; size > 0; size--) {
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
            }
            res.add(list);
            list = new ArrayList<>();
        }
        return res;
    }

    /**
     * 面试题32 - III. 从上到下打印二叉树 III
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder_v3(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        int i = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (; size > 0; size--) {
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
            }
            if (i % 2 == 1) {
                Collections.reverse(list);
            }
            i++;
            res.add(list);
            list = new ArrayList<>();
        }
        return res;
    }

    /**
     * 面试题33. 二叉搜索树的后序遍历序列
     * <p>
     * 后序遍历的逆序是 root->right->left
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        // 单调栈使用，单调递增的单调栈
        Deque<Integer> queue = new LinkedList<>();
        int pervElem = Integer.MAX_VALUE;
        // 逆向遍历，就是翻转的先序遍历
        for (int i = postorder.length - 1; i >= 0; i--) {
            // 左子树元素必须要小于递增栈被peek访问的元素，否则就不是二叉搜索树
            if (postorder[i] > pervElem) {
                return false;
            }
            while (!queue.isEmpty() && postorder[i] < queue.getLast()) {
                // 数组元素小于单调栈的元素了，表示往左子树走了，记录下上个根节点
                // 找到这个左子树对应的根节点，之前右子树全部弹出，不再记录，因为不可能在往根节点的右子树走了
                pervElem = queue.pollLast();
            }
            // 这个新元素入栈
            queue.addLast(postorder[i]);
        }
        return true;
    }

    List<List<Integer>> res = new ArrayList<>();
    int sum = 0;

    void Judge(TreeNode root, List<Integer> list, int sum) {
        if (root != null) {
            List<Integer> tmp = new ArrayList<>();
            list.add(root.val);
            if (root.left == null && root.right == null) {
                if (sum + root.val == this.sum) {
                    res.add(list);
                }
            }
            tmp.addAll(list);
            Judge(root.left, tmp, sum + root.val);
            tmp = new ArrayList<>();
            tmp.addAll(list);
            Judge(root.right, tmp, sum + root.val);
        }
    }

    void Path(TreeNode root, List<Integer> list) {
        if (root != null) {
            List<Integer> tmp = new ArrayList<>();
            list.add(root.val);
            if (root.left == null && root.right == null) {
                res.add(list);
            }
            tmp.addAll(list);
            Path(root.left, tmp);
            tmp = new ArrayList<>();
            tmp.addAll(list);
            Path(root.right, tmp);
        }
    }

    /**
     * 面试题34. 二叉树中和为某一值的路径
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        this.sum = sum;
        Judge(root, new ArrayList<>(), 0);
        return this.res;
    }


    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 面试题35. 复杂链表的复制
     * 复制一个新的节点在原有节点之后，如 1 -> 2 -> 3 -> null 复制完就是 1 -> 1 -> 2 -> 2 -> 3 - > 3 -> null
     * 从头开始遍历链表，通过 cur.next.random = cur.random.next 可以将复制节点的随机指针串起来，当然需要判断 cur.random 是否存在
     * 将复制完的链表一分为二 根据以上信息，我们不难写出以下代码
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node tmp = head;
        while (head != null) {
            Node copy = new Node(head.val);
            copy.next = head.next;
            head.next = copy;
            head = head.next.next;
        }
        head = tmp;
        int i = 0;
        while (head != null) {
            if (head.random != null) {
                head.next.random = head.random.next;
            }
            head = head.next.next;
        }
        Node copyHead = tmp.next;
        head = tmp;
        Node curCopy = head.next;
        while (head != null) {
            head.next = head.next.next;
            head = head.next;
            if (curCopy.next != null) {
                curCopy.next = curCopy.next.next;
                curCopy = curCopy.next;
            }
        }
        return copyHead;
    }

    class NNode {
        public int val;
        public NNode left;
        public NNode right;

        public NNode() {
        }

        public NNode(int _val) {
            val = _val;
        }

        public NNode(int _val, NNode _left, NNode _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    NNode head, pre, tail;

    void inOrder(NNode root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        if (pre == null) {
            head = root;
        } else {
            pre.right = root;
        }
        root.left = pre;
        pre = root;
        tail = root;
        inOrder(root.right);
    }

    /**
     * 面试题36. 二叉搜索树与双向链表
     *
     * @param root
     * @return
     */
    public NNode treeToDoublyList(NNode root) {
        if (root == null) {
            return null;
        }
        inOrder(root);
        head.left = tail;
        tail.right = head;
        return head;
    }

    /**
     * 面试题37. 序列化二叉树
     */
    class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "[]";
            }
            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                root = queue.poll();
                if (root.left != null) {
                    queue.add(root.left);
                    list.add(root.left.val);
                } else {
                    list.add(null);
                }
                if (root.right != null) {
                    queue.add(root.right);
                    list.add(root.right.val);
                } else {
                    list.add(null);
                }
            }
            StringBuilder sb = new StringBuilder();
            int idx = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                if (list.get(i) != null) {
                    idx = i + 1;
                    break;
                }
            }
            sb.append('[');
            for (int i = 0; i < idx; i++) {
                if (i != 0) {
                    sb.append(',');
                }
                sb.append(list.get(i));
            }
            sb.append(']');
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String s = data.substring(1, data.length() - 1);
            String[] split = s.split(",");
            int idx = 0;
            if ("".equals(split[0])) {
                return null;
            }
            TreeNode head = new TreeNode(Integer.parseInt(split[idx++]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                TreeNode root = queue.poll();
                if (idx < split.length) {
                    String left = split[idx++];
                    TreeNode leftNode = null;
                    if (!"null".equals(left)) {
                        leftNode = new TreeNode(Integer.parseInt(left));
                        queue.add(leftNode);
                        root.left = leftNode;
                    }
                }
                if (idx < split.length) {
                    String right = split[idx++];
                    TreeNode rightNode = null;
                    if (!"null".equals(right)) {
                        rightNode = new TreeNode(Integer.parseInt(right));
                        queue.add(rightNode);
                        root.right = rightNode;
                    }
                }
            }
            return head;
        }
    }

    List<String> list = new ArrayList<>();
    char[] c = null;

    void swap(int a, int b) {
        char ch = c[a];
        c[a] = c[b];
        c[b] = ch;
    }

    void next_permutation(int x) {
        if (x == c.length - 1) {
            list.add(new String(c));
            return;
        }
        Set<Character> set = new HashSet<>();
        for (int i = x; i < c.length; i++) {
            if (set.contains(c[i])) {
                continue;
            }
            set.add(c[i]);
            swap(i, x);  // 交换
            next_permutation(x + 1);  // 求[i..-1]字串的排列
            swap(i, x);  // 恢复
        }
    }

    /**
     * 面试题38. 字符串的排列
     * <p>
     * 交换字符 i 于其他字符的位置， 然后求 [i,-1]字串的排列， 然后恢复与 i 交换字符的位置，换到下一个字符进行交换
     *
     * @param s
     * @return
     */
    public String[] permutation(String s) {
        c = s.toCharArray();
        next_permutation(0);
        return list.toArray(new String[list.size()]);
    }


    /**
     * 面试题41. 数据流中的中位数
     * <p>
     * Your MedianFinder object will be instantiated and called as such:
     * MedianFinder obj = new MedianFinder();
     * obj.addNum(num);
     * double param_2 = obj.findMedian();
     * <p>
     * 小顶堆 + 大顶堆 = 全部数据流
     * 大顶堆 - 小顶堆 <= 1
     * 中位数 = （odd）小顶堆头 + 大顶堆头 / 2； （even） 大顶堆头
     */
    class MedianFinder {
        Queue<Integer> up = new PriorityQueue<>(Collections.reverseOrder());
        Queue<Integer> lo = new PriorityQueue<>();

        /**
         * initialize your data structure here.
         */
        public MedianFinder() {

        }

        public void addNum(int num) {
            lo.add(num);
            up.add(lo.poll());
            // 若大顶堆比小顶堆多2个了
            if (lo.size() + 1 < up.size()) {
                lo.add(up.poll());
            }
        }

        public double findMedian() {
            if ((up.size() + lo.size()) % 2 == 1) {
                return up.peek();
            } else {
                return (double) (lo.peek() + up.peek()) / 2.0;
            }
        }
    }

    /**
     * 面试题42. 连续子数组的最大和
     * <p>
     * 1.义一个函数f(n) => 到第n个数为止的子数列的最大和，存在一个递推关系f(n) = max(f(n-1) + A[n], A[n])
     * => 到第n个点为止的子数列最大和，即为，到第n-1个点为止的子数列最大和加上第n个点的值 与 第n个点的值 的最大值;
     * 2.将这些最大和保存下来后，取最大的那个就是，最大子数组和。因为最大连续子数组 等价于 最大的以n个数为结束点的子数列和
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int res = Integer.MIN_VALUE;
        int[] dp = new int[nums.length + 1];
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i - 1], nums[i - 1]);
            res = Math.max(dp[i], res);
        }
        return res;
    }


    int dfs_countDigitOne(int n) {
        if (n <= 0) {
            return 0;
        }
        String s = String.valueOf(n);
        int high = s.charAt(0) - '0';
        int pow = (int) Math.pow(10, s.length() - 1);
        int last = n - pow * high;
        if (high == 1) {
            // 最高位是1，如1234, 此时pow = 1000,那么结果由以下三部分构成：
            // (1) dfs(pow - 1)代表[0,999]中1的个数;
            // (2) dfs(last)代表234中1出现的个数;
            // (3) last+1代表固定高位1有多少种情况。
            return dfs_countDigitOne(pow - 1) + dfs_countDigitOne(last) + last + 1;
        } else {
            // 最高位不为1，如2234，那么结果也分成以下三部分构成：
            // (1) pow代表固定高位1，有多少种情况;
            // (2) high * dfs(pow - 1)代表999以内和1999以内低三位1出现的个数;
            // (3) dfs(last)同上。
            return high * dfs_countDigitOne(pow - 1) + dfs_countDigitOne(last) + pow;
        }
    }

    /**
     * 面试题43. 1～n整数中1出现的次数
     * <p>
     * https://leetcode-cn.com/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof/solution/java-di-gui-qiu-jie-by-npe_tle-2/
     *
     * @param n
     * @return
     */
    public int countDigitOne(int n) {
        return dfs_countDigitOne(n);
    }


    /** 1的个数
     * 《编程之美》上这样说:
     *
     *     设N = abcde ,其中abcde分别为十进制中各位上的数字。
     *     如果要计算百位上1出现的次数，它要受到3方面的影响：百位上的数字，百位以下（低位）的数字，百位以上（高位）的数字。
     *     如果百位上数字为0，百位上可能出现1的次数由更高位决定。比如：12013，则可以知道百位出现1的情况可能是：100~199，1100~1199,2100~2199，，...，11100~11199，一共1200个。可以看出是由更高位数字（12）决定，并且等于更高位数字（12）乘以 当前位数（100）。注意：高位数字不包括当前位
     *     如果百位上数字为1，百位上可能出现1的次数不仅受更高位影响还受低位影响。比如：12113，则可以知道百位受高位影响出现的情况是：100~199，1100~1199,2100~2199，，....，11100~11199，一共1200个。和上面情况一样，并且等于更高位数字（12）乘以 当前位数（100）。但同时它还受低位影响，百位出现1的情况是：12100~12113,一共14个，等于低位数字（13）+1。 注意：低位数字不包括当前数字
     *     如果百位上数字大于1（2~9），则百位上出现1的情况仅由更高位决定，比如12213，则百位出现1的情况是：100~199,1100~1199，2100~2199，...，11100~11199,12100~12199,一共有1300个，并且等于更高位数字+1（12+1）乘以当前位数（100）

     int num=n,i=1,s=0;

     while(num)              //分别计算个、十、百......千位上1出现的次数，再求和。
     {
     if(num%10==0)
     s=s+(num/10)*i;

     if(num%10==1)
     s=s+(num/10)*i+(n%i)+1;

     if(num%10>1)
     s=s+ceil(num/10.0)*i;

     num=num/10;
     i=i*10;
     }
     return s;
     */

    /**
     * 面试题44. 数字序列中某一位的数字
     * 0 ->
     * 1~9 -> 9*1 = 9
     * 10~99 -> 90*2 = 180
     * 100~999 -> 900*3 = 2700
     * <p>
     * 比如输入的 n 是 365：
     * <p>
     * 经过第一步计算我们可以得到第 365 个数字表示的数是三位数，n=365−9−90×2=176，digtis = 3。
     * 这时 n=1176 表示目标数字是三位数中的第 176176 个数字。
     * 我们设目标数字所在的数为 number，计算得到 number=100+176/3=158，
     * idx 是目标数字在 number 中的索引，如果 idx = 0，表示目标数字是 number 中的最后一个数字。
     * 根据步骤2，我们可以计算得到 idx = n % digits = 176 % 3 = 2，
     * 说明目标数字应该是 number = 158 中的第二个数字，即输出为 5。
     *
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        List<Long> list = new ArrayList<>(Collections.nCopies(11, 0L));
        for (int i = 1; i <= 10; i++) {
            list.set(i, (long) (Math.pow(10, i - 1) * 9 * i));
        }
        Long p = 0L;
        for (int i = 1; i <= 10; i++) {
            p += list.get(i);
            if (p >= n) {
                n -= (int) (p - list.get(i));
                int number = (int) Math.pow(10, i - 1) + n / i;
                if (n % i == 0) {
                    number--;
                }
                if (i == 1) {
                    number = n;
                }
                return String.valueOf(number).charAt(((n % i) + i - 1) % i) - '0';
            }
        }
        return 0;
    }

    /**
     * 面试题45. 把数组排成最小的数
     *
     * @param nums
     * @return
     */
    public String minNumber(int[] nums) {
        List<String> list = new ArrayList<>();
        for (int num : nums) {
            list.add(String.valueOf(num));
        }
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return (s1 + s2).compareTo(s2 + s1);
            }
        });
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Test
    public void test1() {
        offer3 o = new offer3();
        Codec codec = new Codec();
        System.out.println(codec.serialize(codec.deserialize("[1,2,3,null,null,4,5]")));
//        System.out.println(o.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(o.findNthDigit(9));
        System.out.println(o.findNthDigit(11));
        System.out.println(o.findNthDigit(2723));
        System.out.println(o.findNthDigit(365));
    }
}

