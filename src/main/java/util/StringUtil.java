package util;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class StringUtil {
	public static final Logger logger = LoggerFactory.getLogger("StringUtil");

	private final static String DEFAULT_MASK = "*";
	private final static String EMPTY_STRING = "";
	
	public static boolean isNotNull(String s) {

		if (s != null && s.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Object s) {

		if (s == null) {
			return true;
		}
		String string = s.toString();
		if(string==null)return true;
		
		
		if (string.trim().length() == 0)
			return true;
		
		if(s instanceof String){
			if (string.trim().length() == 0)
				return true;
		}
		
		if(s instanceof Integer){
			Integer valueOf = Integer.valueOf(string);
			if(valueOf ==null||valueOf==0){
				return true;
			}
		}
		return false;
	}

	public static boolean isNotNull(Object s) {

		if (s == null)
			return false;

		if (s instanceof String) {
			if (s.toString().trim().length() > 0) {
				return true;
			}

		} else if (s instanceof Integer) {
			if (Integer.valueOf(s.toString()) > 0) {
				return true;
			}
		} else if (s instanceof Double) {
			if (Double.valueOf(s.toString()) > 0) {
				return true;
			}
		} else if (s instanceof BigDecimal) {
			if (s.toString().trim().length() > 0) {
				return true;
			}
		}

		return false;
	}

	public static Integer operationInteger(String s) {

		if (isNotNull(s)) {
			Integer valueOf = Integer.valueOf(s);
			return valueOf;
		} else {
			return null;
		}

	}

	public static Integer value(Object obj) {

		if (obj == null)
			return null;

		if (obj instanceof Integer) {
			return (Integer) obj;
		} else if (obj instanceof String) {
			String string = obj.toString();
			if (isNotNull(string)) {
				Integer integer = Integer.valueOf(string);
				return integer;
			}

		}
		return null;
	}

	  public static String listToString(List<?> list) {
	        StringBuffer sb1 = new StringBuffer();
	        if (list != null && list.size() > 0) {
	            for (int j = 0; j < list.size(); j++) {
	                sb1.append("'").append(list.get(j)).append("'").append(",");
	            }
	            sb1.deleteCharAt(sb1.length() - 1);
	        }
	        return sb1.toString();
	    }
	  
	  /**
	   * 
	   * @param splitStr 要分割的字符串
	   * @param splitType 分隔符 如 , | ... 
	   * @param list 判断是否存在要包含的集合
	   * @return
	   */
	  public static Boolean existInString(String splitStr,String splitType,List<?> list){
		  String[] temps = splitStr.split(splitType);
		  if(list != null && list.size() > 0){
			  for(String temp : temps){
				  for(Object obj : list){
					  if((obj+"").equals(temp)){
						  return true;
					  }
				  }
			  }
		  }
		  return false;
	  }
		
		public static String toScale(String value){
			if(value == null || "".equals(value) || "".equals(value.trim())){
				return "";
			}
			BigDecimal bd = new BigDecimal(value.trim());
			bd = bd.multiply(new BigDecimal(100));
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN);
			return bd+"%";
		}
		
		
		/**
	     * 字符串隐藏
	     * @param srcStr	   待隐藏字符串
	     * @param beginIndex 隐藏字符串起始下标
	     * @param len		   隐藏长度
	     * @return			   隐藏后字符串
	     */
	    public static String mask(String srcStr, int beginIndex, int len){
	    	
	    	return mask(srcStr, beginIndex, len, DEFAULT_MASK);
	    }
	    
	    /**
	     * 字符串隐藏
	     * @param srcStr	   待隐藏字符串
	     * @param beginIndex 隐藏字符串起始下标
	     * @param len		   隐藏长度
	     * @param maskstr    隐藏替换字符，可空，默认为“*”
	     * @return			   隐藏后字符串
	     */
	    public static String mask(String srcStr, int beginIndex, int len, String maskstr){
	    	
	    	if(StringUtil.isNull(srcStr)) {
	    		return srcStr;
	    	}
	    	//判断起始下标是否大于源字符串长度
	    	if(beginIndex > srcStr.length() - 1) {
	    		return srcStr;
	    	}
	    	if (beginIndex < 0) {
	    		beginIndex = 0;
	    	}
	    	if(len < 0) {
	    		len = 0;
	    	}
	    	if((beginIndex + len) > srcStr.length() - 1) {
	    		len = srcStr.length() - beginIndex;
	    	}
	    	
	    	if(StringUtil.isNull(maskstr)) {
	    		maskstr = DEFAULT_MASK;
	    	}
	    	StringBuffer maskedStr = new StringBuffer();
	    	
	    	maskedStr.append(srcStr.substring(0, beginIndex));
	    	
	    	for(int i = 0; i < len; i++) {
	    		maskedStr.append(maskstr);
	    	}
	    	
	    	maskedStr.append(srcStr.substring(beginIndex + len));
	    	
	    	return maskedStr.toString();
	    }
	    
	    /**
	     * 字符串倒序隐藏
	     * @param srcStr	   待隐藏字符串
	     * @param len		   隐藏长度
	     * @return			   隐藏后字符串
	     */
	    public static String maskEnd(String srcStr, int len){
	    	
	    	if(StringUtil.isNull(srcStr)) {
	    		return srcStr;
	    	}
	    	int beginIndex = srcStr.length()-len;
	    	return mask(srcStr,beginIndex,len,DEFAULT_MASK);
	    }
	  
	    /**
	     * 将object对象转换为字符串，如果obj参数为null，则返回""，否则返回obj.toString()
	     * @param obj  需要转换的对象
	     * @return     obj的字符串
	     */
	    public static String objToStr(Object obj) {
	    	return objToStr(obj, EMPTY_STRING);
	    }
	    
	    /**
	     * 将object对象转换为字符串，如果obj参数为null，则返回defaultVal，否则返回obj.toString()
	     * @param obj         需要转换的对象
	     * @param defaultVal  当obj为null时的默认值
	     * @return            obj的字符串
	     */
	    public static String objToStr(Object obj, String defaultVal) {
	    	return obj == null ? defaultVal : obj.toString();
	    }
	    
	    
	    /**
	     * 比较两个字符串（大小写敏感）。
	     * 
	     * <pre>
	     * StringUtil.equals(null, null)   = true
	     * StringUtil.equals(null, &quot;abc&quot;)  = false
	     * StringUtil.equals(&quot;abc&quot;, null)  = false
	     * StringUtil.equals(&quot;abc&quot;, &quot;abc&quot;) = true
	     * StringUtil.equals(&quot;abc&quot;, &quot;ABC&quot;) = false
	     * </pre>
	     * 
	     * @param str1 要比较的字符串1
	     * @param str2 要比较的字符串2
	     * 
	     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
	     */
	    public static boolean equals(String str1, String str2) {
	        if (str1 == null) {
	            return str2 == null;
	        }

	        return str1.equals(str2);
	    }
	    
	    /**
	     * 比较两个字符串（大小写不敏感）。
	     * 
	     * <pre>
	     * StringUtil.equalsIgnoreCase(null, null)   = true
	     * StringUtil.equalsIgnoreCase(null, &quot;abc&quot;)  = false
	     * StringUtil.equalsIgnoreCase(&quot;abc&quot;, null)  = false
	     * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
	     * StringUtil.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
	     * </pre>
	     * 
	     * @param str1 要比较的字符串1
	     * @param str2 要比较的字符串2
	     * 
	     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
	     */
	    public static boolean equalsIgnoreCase(String str1, String str2) {
	        if (str1 == null) {
	            return str2 == null;
	        }

	        return str1.equalsIgnoreCase(str2);
	    }
	    
}
