package com.wushuai.contest;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>contest178</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-18 09:14
 */
public class contest178 {

    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] ans = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    ans[i]++;
                }
            }
        }
        return ans;
    }


    /**
     * 权重因子
     * Map排序（按键，按值）
     *
     * @param votes
     * @return
     */
    public String rankTeams(String[] votes) {
        // 41.4mb 24ms  BigInteger效率还是不错的
        if (votes.length == 1) {
            return votes[0];
        }
        int len = votes[0].length();
        // 用Map<Character, StringBuilder> 也可以 StringBuilder每次都append
        Map<Character, BigInteger> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(votes[0].charAt(i), new BigInteger("0"));
        }
        for (int i = 0; i < len; i++) {
            // 乘以权重，一般为10，但是需要注意，以免有错
            for (Map.Entry<Character, BigInteger> e : map.entrySet()) {
                e.setValue(e.getValue().multiply(BigInteger.valueOf(50)));
            }
            for (String vote : votes) {
                char c = vote.charAt(i);

                /*
                 *  需要注意，一下加1，votes很长时，当某个c超过十时，就会发生错误，这时需要把权重调大一点
                 */

                map.put(c, map.get(c).add(BigInteger.ONE));
            }
        }
        List<Map.Entry<Character, BigInteger>> list = new ArrayList<>(map.entrySet());
        list.sort(new Comparator<Map.Entry<Character, BigInteger>>() {
            @Override
            public int compare(Map.Entry<Character, BigInteger> o1, Map.Entry<Character, BigInteger> o2) {
                if (o2.getValue().equals(o1.getValue())) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Character, BigInteger> aList : list) {
            sb.append(aList.getKey());
        }
        return sb.toString();


        /*  42mb 26ms
        //key是参赛团队，value是该团队每个排位获得的票数
        Map<Character, int[]> teamRankMap = new HashMap<>();

        for (String vote : votes) {
            for (int i = 0; i < vote.length(); i++) {
                int[] rankArr = teamRankMap.getOrDefault(vote.charAt(i), new int[26]);
                rankArr[i]++;
                teamRankMap.put(vote.charAt(i), rankArr);
            }
        }

        List<Map.Entry<Character, int[]>> teamRankList = new ArrayList<>(teamRankMap.entrySet());
        Collections.sort(teamRankList, (team1, team2) -> {
            int[] ranks1 = team1.getValue();
            int[] ranks2 = team2.getValue();
            //根据投票排序
            for (int i = 0; i < 26; i++) {
                if (ranks1[i] != ranks2[i]) {
                    return ranks2[i] - ranks1[i];
                }
            }
            //字母顺序排序
            return team1.getKey() - team2.getKey();
        });

        //转换为字符串输出
        return teamRankList.stream().map(entry -> String.valueOf(entry.getKey())).collect(Collectors.joining());
        */
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
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

    List<Integer> listnode = new ArrayList<>();

    boolean Judge(ListNode head, TreeNode root) {
        // 后续子匹配
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val != root.val) {
            return false;
        }
        return Judge(head.next, root.left) || Judge(head.next, root.right);
    }

    /**
     * 路径匹配的递归，有两种情况需要考虑
     *
     * 1.root.val != head.val 这个时候，head不变，root进入左右子树，判断左右子树是否和head匹配
     * 2.root.val == head.val 这个时候递归进入左右子树，head变为head.next。如果左右子树匹配过程中，出现了匹配不上的情况，
     * 这个时候，需要回到root和head的情况，重新进入到root.val != head.val这种不匹配的情况。
     * @param head
     * @param root
     * @return
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) {
            return false;
        }
        // 匹配成功
        if (root.val == head.val) {
            boolean isSub = Judge(head.next, root.left) || Judge(head.next, root.right);
            //不能直接return Judge...   若是匹配不成功，不能直接返回false，要进行后续匹配
            if (isSub) {
                return true;
            }
        }
        // 若后续匹配不成功，则还是从当重新前匹配
        return isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    /* 问题在于，当一个不匹配，不能在当前节点重新开始匹配，而一个重新回到上一个匹配失败的地方
        List<Integer> listnode = new ArrayList<>();

        boolean Judge(int index, TreeNode root) {
            if (root != null) {
               if (root.val != listnode.get(index)) {
                    // 从头开始比较
                    if (root.val == listnode.get(0)) {
                        return Judge(1, root.left) || Judge(1, root.right);
                    } else {
                        return Judge(0, root.left) || Judge(0, root.right);
                    }
                } else if (index == listnode.size() - 1) {
                    // 比完了
                    return true;
                } else {
                    // 相等，下一个继续比较
                    return Judge(index + 1, root.left) || Judge(index + 1, root.right);
                }
            }
            return false;
        }

        public boolean isSubPath(ListNode head, TreeNode root) {
            while (head != null) {
                listnode.add(head.val);
                head = head.next;
            }
            return Judge(0, root);
        }
       */


    @Test
    public void test1() {
        contest178 obk = new contest178();
//        int[] e = new int[]{6, 5, 4, 8};
//        System.out.println(Arrays.toString(obk.smallerNumbersThanCurrent(e)));
//        String[] v = new String[]{"PGAOFXIKYJVSLTUHCZQRE", "LSIAPTHYCGURKEZXJOFQV", "UGSARZICOXYPHTQFJKVEL", "XIAOKSVGRJCHQZUETLYPF", "OHRXPGEVUYCSKZILQATFJ", "UHFSCZVKIELGOQJYRXPAT", "QECXVZKJTLIYHUFRGOSAP", "RJGAZIKPEVUSTOXFQYCHL", "XRFGPAQSJOETHVCZIKLUY", "ZCTQOVRFKXJGEALSHIYUP", "QGTEHXCJRIPSUZALOYFVK", "GIHXVJPQYOSZFRKLEUTAC", "LURXHFECKZSYPTQGAOJVI", "TFUKZELVARQCXIYPJHGSO", "FIPYTACSXZKLRGOHJVEQU", "ZKJQOGHVURAISXFLPYECT", "XKOJAHVQUCETFPRYZGLSI", "TUKZAVIFXEGQCSOLJRPHY", "OSIPCFVRLAYKEHZTJGUQX", "OYAQTCEKLGPRZVISUXFHJ", "SKHCXEZLTFVQOIUPRAJGY", "CRZPAEILFTOKUJVGXHQYS", "OQVLPEYZFIGJRKUSXAHTC", "VQTXURZKAPGHEIFCOLSJY", "YVRZKXSOAEIPUQJHGCFLT", "YEJTGFQPOZLCARSUIKVHX", "XKASIECRHVQFPULZTOGJY", "KAQVYSZLTFXCGEJIRUHOP", "IYJXCUTOAPQKHEZFGRVSL", "OIEPRLZGUVQHSYJAFTXCK", "CIGVAZLKORXQYPHTEUSFJ", "UVTCJFQOGXIZHLKYRASEP", "FCVOYSJHZLKATXRIUPQGE", "TJLQFSRKIUGPCVZHOEAXY", "FUHLSRGTCOXAEZVQIYKPJ", "CAGRHEUQZISYKXLFVPJOT", "LASKUHEPJQXRCTZOVIYFG", "IGKJSYLVFCUXETRAHZQPO", "UZGXPIAFVOSLKRYQJTCHE", "VKELXAFPOGSRIZCUQYTHJ", "GCTJEUFRVLKAIOYSHZXQP", "PHUOAXLYFTCRSEQIKGJZV", "XLQYSIJHTREFPOKCUGVAZ", "LUZXHQKIATOCGJYPFSVRE", "XILVQZAFUKECYJGTPHSOR", "GCQFHJKTAERVPIYUSZOLX", "ZPRILGHYXTFVSQCAUKEOJ", "SLFTYQPZKUAIVGRHEOCXJ", "FCUVEOKHGZQSYJRXPAILT", "AKZJRQCPVHYGSXLFITUOE", "OKVEHCXPYRUGIAZJQFSLT", "YZVHPIGSLREJQCXOKFATU", "QTCILUZFVYHSAPGJEKXRO", "YXKJOPHIVGARUTZEFLSCQ", "OZPXURLHEKFQTSIVJYGCA", "YIVEGRJLTPOAZFCQSUXHK", "VPQUXLYGACSITFKEJHORZ", "AOEQLYPFZUXCKHSTVIJRG", "IFYTULEXKZRCOHASQPJGV", "TASULVYKZPOCGIHJERXFQ", "RGXYZCHVEAPTJISQUFKOL", "XKVRTHJSLOZPIAYUEQGFC", "EYIRXQTCUPHSVJFZLAKOG", "ZRJSTQIGPELKUCHAOYVFX", "EIXJPTZGRKVYCUFAOHQLS", "LRSGUJZYXCIVQOAHTFPEK", "ZHLOESVGIARXCUQYKPFJT", "ZKQLVGOIFASTPUHYJRCXE", "ZLPJQYTXEGAFHOSRCIUVK", "ZVHYQJXCGTAFKLEISOPUR", "KZGLYEQAURFHSXIVPTJCO", "TIXPAYEKJLVGCQRUHOSZF", "ESORYCVXLPKZFQJAUGIHT", "FAJTCHORZGLIKXYSVUPQE", "AOXHJEZFYLIQCPRVTSGKU", "IZVAPOTEHCJQLKRGFUSXY", "RTVYZHSIPGCEKJQUAXFOL", "GSHOKEXAZCIQYTRJFVLPU", "SXZLKIUGEVCPFHTQJARYO", "QPCOTHISGLKYJFUAZXVRE", "XPUEYATJHCOLVGSQRIZKF", "GIKSZFHEQLTCVXJAYOPUR", "RFSOEJPUAGKYHZIQCTLXV", "UFHXJLITQPEGCKOSZYRAV", "ZVQAFGXEIRSTPJYLHCOKU", "ZITVJLSOAUGPCFYXHRQKE", "RCZOIPEXFJAQGSYVLKHUT", "TUHVJROCXIGEPLQAZYKSF", "ATQJGKUIPLZXEYCRSOVFH", "TVHYLSRAKIXJFGEPOZUCQ", "CAPHZLGXQSIRTJYFOKEVU", "FAYOKHEUZIRJXQTVGCSPL", "IJCRUHVAPFYKGXLTSOQEZ", "ZKIJLQEAUVHFTYRXCSGOP", "ALFTJSRXUQPIOVZCYHEKG", "ZASIVJQYUCRPEKGLTXFHO", "OICQXZPEFUSTVALKGYRJH", "ZOKVPHUXGRQIJCSEYALFT", "FKACPHOSIREGUTYLJXZVQ", "IZXOREFGSLPKUHAYCVTJQ", "UVXZTCFAQEYLORSKHPJIG", "PKVQCYSTLHRZIFAUEXJGO", "IRFZSKGEHQVOLXCTJUPAY", "XYHPVOTILEAUZJGSKQCRF", "ERXJLFZQIGHPCSTYKUVOA", "SZTIFCGVEKUJYPAOQXHRL", "JPTGQOEYFIKURCVHXALZS", "URJGTXZKICAOQSLVEYHFP", "ZERGKOLTSUIXJFACVQHPY", "GQCVAJYIKRZLTPSFHOUEX", "OKRCTSGLZXQJEHPFVYUIA", "GUKXARTQFYSCVEZIOJHLP", "UTCIRLFHGJOSYZVPQKEAX", "JPIOVSFZHUTQKCERXAGLY", "LKJEGOHRTYQFXUSCVIPZA", "LSETPUAHZYOICVRGJFXKQ", "OPGHRVUXQEYLZFISTJKCA", "VOKCSGFTXZEIRYALHJPQU", "POTXFYCQIVZHLGJERAUSK", "FAUIECXOYPLRHJKTSZQVG", "XFAETYRCJUGVPQZOKSLHI", "LGCVYIAZRFJSHQXTKUOPE", "YKATQFIURCHPLSVGJEXOZ", "IRUHTAXVYZKFPSEOJCLGQ", "TAVEXIHLPOGSZRCJFQKUY", "GFACLJSETKIXRZUYHVPOQ", "XTJKSAZIQYRCLPGOHUFEV", "GSFTZLJCVKRYUOXAQIEPH", "GIOLPRXHCSUFKAVTJZEQY", "SFCRPVGYKOUZJALQEXHIT", "EUTOKQGFCLRAVZPJYSXHI", "UOYSHGLTPZXRICFJEKAVQ", "OTZIGVXSQHCPRYUKELAJF", "SGZOEHLKYUPXCJRFTAVQI", "JOCVZLGSUIAHPFKYTQERX", "ZFSRJULQGKIOXEPYHTACV", "FGSJCIYXHEAZTOLUKRVQP", "YXHICZVFTSQJKLPOURGAE", "XALRUOHJZYVEGQPKCSFIT", "KCIJLRXYHAESGFQUOPVZT", "AUTSXLECOGPZIHRVYQKFJ", "GIZRAKEVOXLTCJSUHQPFY", "ZKUPCORTFYEIAGJQXVLHS", "JALHXZQIYSUROPCEGKVFT", "KUIGTSQAJEOZYPCFHLXRV", "LXICRFZJEUVYASPKQHTOG", "EFUPZKRXLCAVJGQITHOSY", "EUYGQKARZJVHLIPFCOSTX", "AOXTKHRYLJZQEUCSPGVIF", "SHUTLZEGRJYPCVAFXQOKI", "GUZTAXQLKIRFYEOHJSPVC", "RGOZPAXLYVJSHFICKTQUE", "ASCHFQVTRLEKYUZIXGJPO", "CQFRUSIXLOZPVJTGYAHEK", "VGYURSHEXIKCFOAPTQLJZ", "VFQCGPEAYISTXKORLUJHZ", "UZJOYGVRETFSPLQAIKHCX", "JKQRZFXICESTHPGOUYVAL", "XGFYRJKCVTESZPHLQUIOA", "YEPGFRALQOKJTXSZCIUVH", "CXHPFAYKVZQORLJUTEGSI", "PXHACSGJUILTYVQRFKEOZ", "RSILHFAYCQJKVUGZTPXOE", "IKAEOXFVPHSLRCQGZYJTU", "GVKRJFLXZUOSAEIYPTCQH", "TEXZOUVPICHGFLRYSQKAJ", "JCEHLTRZQVFOPUSIKGYXA", "GFLJHYXEAQTOPSKIUZVRC", "ZGKJFVEAUHIXRTCLSPYQO", "GXOTELHIQKUAFSVRPZYCJ", "XRZEUGOIVAKSYPHJQCFLT", "PYQVXGHSAKLRFTEZOUICJ", "QGHOCVKFEPUTSRAIJYZLX", "YRQVOLKJGFIXHEPCTUAZS", "CTXYFQVZASEHPIOUJGRLK", "GIZHRVQLJEYAKTSUCOPXF", "XIGKSCLTRYJHZFUOQAPVE", "RYCXPSEGIFAULKZTQJVHO", "PXEQTGYSHICLAVRUZKJOF", "AERXGTIHLUZSFKQYVPCJO", "SKTPVFXILUZHYCEJAQROG", "OCQUZKSJYLIVARFEHTPGX", "ISZXFQUKHJOTCREPGYALV", "LOSTQCIRZFVGYKPEAJHUX", "QPFTRGUKOIVXESYCJZLAH", "PXSUEIVOHZCKJFQLTGRAY", "QYLRCVJOPEXGUTZKSAIFH", "RJQPOFKXIHUZYVETCSLAG", "HTFJEAISYQZUXLCGVPORK", "ECGFLAHQJPORITXYUKVZS", "FJGAIOSPTVXUZYHLQKERC", "GEQLKYPICTUAFRSOVXZHJ", "CGJHEZKSTIUQOFVRYXLAP", "XYJSGTIKOVUFERLHCPAQZ", "LQFVECITUGXHOYJKASRZP", "HYGJQFUPZVORSKICLETXA", "ACTXZRUYPOJLIEFSKQVGH", "PHOLYCFUKRASEGTVQZXIJ", "RTUOEZHPSKAFXLQYICVGJ", "RAPTGQIJOSEFZUKVXCLHY", "EUAKTQLJFOHCPSYVGRZXI", "TYUPHSXVEZKIFQACRLJGO", "ZHOEAIULSVJQKGRYFTXPC", "CPEUIGVRFSOZAKYHQLTJX", "ZCOEYRHVQFLSJIXUATGKP", "FGSCJZKUTRVOLEYHQIPAX", "SPKIGAUVQJXZYEHROCFTL", "ITQVPZLEOGJUHKXRYFASC", "GSAFTOHJQCYLUVPZEXRKI", "ZPFKXLUJSRCAHOGYTVIEQ", "QIFYPTAJELCSOXUKRZGVH", "JFGZCQAELXKOIUHYRSTVP", "TIGPACUOYZJHXKSVQEFRL", "EATKLFGOCZVQHXRYSJIPU", "SPOJYHVQXGZELUACKFITR", "YTVIQXEZUJRFGCOLKSAPH", "SPGHEQZJVXKIOAYLRUCFT", "RFXCSUZIJAQTELHKVPOGY", "PHXJESZRGFLKOAVIQUTYC", "GUYCOKILATEJZXHFPRSVQ", "GQTUFLAKCPVRYIOHXEZJS", "AYVCGFTIQKLZSXEPHRJUO", "EAXRUTPZVHCGLISYQFKOJ", "SUVFHQGRLXEZAYJTCIPKO", "PXKTOGRLHQAJFZCIYUSVE", "ZGXYPLOSRCTAVFEUIQKHJ", "OKEHXFQSIZGCRJLPATYUV", "YXQILGVKPJOUTAZRCSEHF", "TYQAGXSCZVKLJEOIHPURF", "ELAJZPXRIFQVSGKTOCUHY", "YIPXUJHGACTVQEKSOZRLF", "ZPAGUYLVSRCIKOJXFTEQH", "LYKIJURAEGVZSFPXHCOTQ", "EULYFIOJTGXZQCPARHKSV"};
//        System.out.println(obk.rankTeams(v));

    }
}

