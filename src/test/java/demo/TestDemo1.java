package demo;

import java.util.HashMap;
import java.util.Map;

public class TestDemo1 {
	
	public static void main(String[] args) {
		//System.out.println(romanToInt("MCMXCIV"));;
		long stime = System.currentTimeMillis();
		System.out.println(isPalindrome(1223222)); 
		long etime = System.currentTimeMillis();
		System.out.println(etime-stime);
		System.out.println(Integer.MAX_VALUE);
		int re = reverse(123);
		System.out.println(re);
	}
	
	/**
	   * 罗马数字转整数
	 * @param s
	 * @return
	 */
	public static int romanToInt(String s) {
		Map<String, Integer> map = new HashMap<>();
		map.put("", 4);
		if ("IV".equals(s)) {
			return 4;
		}
		if ("IX".equals(s)) {
			return 9;
		}
		if ("XL".equals(s)) {
			return 40;
		}
		if ("XC".equals(s)) {
			return 90;
		}
		if ("CD".equals(s)) {
			return 400;
		}
		if ("CM".equals(s)) {
			return 900;
		}
		String[] str = new String[s.length()];
		int sum = 0;
        for (int i = 0; i < s.length(); i++) {
        	str[i] = s.substring(i, i+1);
        	if ("I".equals(str[i])) {
        		sum += 1;
        	} else if ("V".equals(str[i])) {
        		sum += 5;
        	} else if ("X".equals(str[i])) {
        		sum += 10;
        	} else if ("L".equals(str[i])) {
        		sum += 50;
        	} else if ("C".equals(str[i])) {
        		sum += 100;
        	} else if ("D".equals(str[i])) {
        		sum += 500;
        	} else if ("M".equals(str[i])) {
        		sum += 1000;
        	}
        }
		return sum;
    }
	
	 /**
	  * 回文数  如：121->121 true,234->432 false,10->01 false
	  * @param x
	  * @return
	  */
	 public static boolean isPalindrome(int x) {
		 if (x < 0) return false;
		 int y = x;
		 int rev = 0;
		 while (x != 0) {
			 int pop = x % 10;
			 rev = rev * 10 + pop;
			 x /= 10;
		 }
		 if (y == rev) return true;
	     return false;
	 }
	
	 /**
	  * 整数反转 如：231->132，溢出反转为0
	  * @param x
	  * @return
	  */
	public static int reverse(int x) {
		int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE/10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) return 0;
            if (rev < Integer.MIN_VALUE/10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) return 0;
            rev = rev * 10 + pop;
        }
        return rev;
    }

}
