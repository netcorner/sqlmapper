package com.netcorner.sqlmapper.utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则字符处理
 * @author Stephen netcorner@qq.com
 *
 */
public  class Validator {
	
    // GUID
    public static final String GUID = "^[A-Za-z0-9]{8}\\-[A-Za-z0-9]{4}\\-[A-Za-z0-9]{4}\\-[A-Za-z0-9]{4}\\-[A-Za-z0-9]{12}$";
    
    // script标记
    public static final String Script="(<script[^>]*>)|(<\\/script>)";
    
    // 算术表达式
    public static final String ArithmeticExpression = "^[-+]?([0-9]+($|[-+*/]))*(((?<o>\\()[-+]?([0-9]+[-+*/])*)+[0-9]+((?<-o>\\))([-+*/][0-9]+)*)+($|[-+*/]))*(?(o)(?!))$";
    
    // 年+月
    public static final String YearMonth="^((1[6-9]|[2-9]\\d)\\d{2})-(([1-9]{1})|(0[1-9]{1})|(1[0-2]{1}))$";
    
    // 是年份
    public static final String Year = "^((1[6-9]|[2-9]\\d)\\d{2})$";
    
    //文件
    public static final String FILE="^([^\\/:*?\"<>|]*)$";
    
    // 特殊符号
    public static final String SPECIALCHAR = "^([^:*?<>,;|]*)$";
    
    // 非负数
    public static final String NONNEGATIVE = "^[1-9][0-9]*";
    
    // 序列数如1,2,3 或1-2-3
    public static final String SERIAL = "^(\\w{1,}[\\r\\n,，| \\\\/、\\-&^+=_~.`!@#$%*;:；：'‘’“”？?<>\\s]?){1,}";
    
    // 非法字符
    public static final String ILLEGALCHAR="^(.*)['\"‘“\\//\\<>](.*)$";
    
    // 双字节
    public static final String DoubleBit = "[^\\x00-\\xff]";
    
    // 数字
    public static final String DIGIT="^[0-9]{1,20}$";
    
    // EMAIL正则表达式
    public static final String EMAIL = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    
    // 日期正则表达式
    //public static final String DATE = "^(\\d{4})\\-(\\d{2})\\-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})$";
    //public static final String DATE = "^(\\d{4})\\-(\\d{2})\\-(\\d{2})$";
    public static final String DATE = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";
    
    // URL正则表达式
    public static final String URL = "(http[s]?|ftp):\\/\\/[^\\/\\.]+?\\..+\\w$";
    
    // 中国电话号码正则表达式
    public static final String CHNTEL = "((\\(\\d{3}\\)|\\d{3}-)|(\\(\\d{4}\\)|\\d{4}-))?(\\d{8}|\\d{7})";
    
    // 邮编
    public static final String ZIP = "\\d{6}";
    
    // 身份证
    public static final String ID = "\\d{17}[\\d|X]|\\d{15}";
    
    // 手机
    public static final String MOBILE = "^((\\(\\d{3}\\))|(\\d{3}\\-))?1\\d{10}$";
    
    // 中文字符
    public static final String CHNCHAR = "[\\u4e00-\\u9fa5]";
    
    // 双字节字符
    public static final String DOUBLECHAR = "[^\\x00-\\xff]";
    
    // 是否html标签
    public static final String HTMLLABLE = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";
    
    // 空白行
    public static final String BLANK = "\\n\\s*\\r";
    
    // 数字
    public static final String NUMBER = "^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?$";
    
    // 匹配首尾空白字符
    public static final String LRBLANK = "^\\s*|\\s*$";
    
    // 逗号序列
    public static final String COMMALIMIT = "^(\\w{1,}[,]?){1,}$";
    
    // 逗号序列且是数字
    public static final String NUMCOMMALIMIT = "^(\\d{1,}[,]?){1,}$";
    
    // 逗号序列且是字符串
    public static final String MONEYCOMMALIMIT = "^((0|[1-9]\\d*)(\\.\\d+)?[,]?){1,}$";
    
    // 匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
    public static final String ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    
    // 匹配ip地址
    public static final String IP = "^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
    
    // 匹配腾讯QQ号：  
    public static final String QQ = "[1-9][0-9]{4,}";
    
    // 匹配正整数
    public static final String PINT = "^[1-9]\\d*$";
    
    // 匹配负整数
    public static final String NINT = "^-[1-9]\\d*$";
    
    // 匹配整数
    public static final String INT = "^-?[1-9]\\d*$";
    
    // 匹配非负整数（正整数 + 0）
    public static final String PINTZERO = "^[1-9]\\d*|0$";
    
    // 匹配非正整数（负整数 + 0）
    public static final String NINTZERO = "^-[1-9]\\d*|0$";
    
    // 匹配正浮点数
    public static final String PFLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    
    // 匹配负浮点数
    public static final String NFLOAT = "^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$";
    
    // 匹配浮点数
    public static final String FLOAT = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    
    // 匹配非负浮点数（正浮点数 + 0）
    public static final String PFLOATZERO = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    
    // 匹配非正浮点数（负浮点数 + 0）
    public static final String NFLOATZERO = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    
    // 匹配由26个英文字母组成的字符串
    public static final String LETTER = "^[A-Za-z]+$";
    
    // 匹配由26个英文字母的大写组成的字符串
    public static final String CLETTER = "^[A-Z]+$";
    
    // 匹配由26个英文字母的小写组成的字符串
    public static final String LLETTER = "^[a-z]+$";
    
    // 匹配由数字和26个英文字母组成的字符串
    public static final String NUMLETTER = "^[A-Za-z0-9]+$";
     
    // 匹配由数字、26个英文字母或者下划线组成的字符串
    public static final String NUMLETTERGANG = "^\\w+$";
    
    // 密码规则
    public static final String PASSWORDRULE = "^(([a-z A-Z 0-9]*[a-z]+[a-z A-Z 0-9]*)|([a-z A-Z 0-9]*[A-Z]+[a-z A-Z 0-9]*)|([a-z A-Z 0-9]*[0-9]+[a-z A-Z 0-9]*))$";
    
    // 匹配帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线)
    public static final String USERNAME = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    
    // 金额
    public static final String MONEY = "^(0|[1-9]\\d*)(\\.\\d+)?$";
    
    // 非域名形式
    public static final String NotDomain="(^\\w+$)|(^\\w+:\\d)|(^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$)|(^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9]):\\d+$)";
	
    /**
     * 字符是否匹配
     * @param strInput
     * @param strPattern
     * @return
     */
    public static boolean isMatch(String strInput, String strPattern)
    {
        return isMatch(strInput, strPattern, false);
    }
    /**
     * 字符是否匹配
     * @param strInput
     * @param strPattern
     * @param isIgnoreCase
     * @return
     */
    public static boolean isMatch(String strInput, String strPattern,boolean isIgnoreCase)
    {
        if (strInput == null) return false;
        Pattern oPattern;
        if (isIgnoreCase == true)
        {
            oPattern = Pattern.compile(strPattern, Pattern.CASE_INSENSITIVE);
        }
        else {
            oPattern = Pattern.compile(strPattern);
        }
        Matcher m1=oPattern.matcher(strInput);
        return m1.find();
    }
    /**
     * 字符替换
     * @param inputValue
     * @param oldValue
     * @param newValue
     * @param isIgnoreCase
     * @return
     */
    public static String relpace(String inputValue, String oldValue, String newValue, boolean isIgnoreCase)
    {
        Pattern oPattern;
        if (isIgnoreCase)
        {
            oPattern = Pattern.compile(oldValue, Pattern.CASE_INSENSITIVE);
        }
        else
        {
            oPattern = Pattern.compile(oldValue);
        }
        StringBuffer sb = new StringBuffer(); 
        Matcher matcher = oPattern.matcher(inputValue); 
        
        while(matcher.find()) {
        	inputValue=inputValue.replace(matcher.group(), newValue);
        } 
        return inputValue;
    }
    /**
     * 字符替换
     * @param inputValue
     * @param oldValue
     * @param newValue
     * @return
     */
    public static String relpace(String inputValue, String oldValue, String newValue)
    {
        return relpace(inputValue, oldValue, newValue, true);
    }
	/**
	 * 
	 * @category 是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(Object str) {
		return isNum(str, null, null);
	}

	/**
	 * 
	 * @category 是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNum(Object str, Double min, Double max) {
		try {
			Double source = Double.parseDouble(str + "");
			if ((min == null && max == null) || (source > min && source < max)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}
