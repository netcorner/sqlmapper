package com.netcorner.sqlmapper.utils;

import com.netcorner.sqlmapper.DALException;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class StringTools {

	/**
     * 得到ascii的长度
     * @param str
     * @return
     */
	public static int getAscIILength(String str){
        String regEx = "[\\u4e00-\\u9fa5]";
        String term = str.replaceAll(regEx, "aa");
        return term.length();
    }
	/**
	 * 得到ascii的截取的字符串
	 * @param str
	 * @param length
	 * @return
	 */
	public static String getAscIIString(String str,int length){
		if(StringTools.getAscIILength(str)>length){
			if(str.length()>length){
				str=str.substring(0, length);
			}else{
				int t=0,curLoc=str.length();
				for(int i=0;i<str.length();i++){
					if(t==length){ 
						curLoc=i; 
						break;
					}else if(t>length){ 
						curLoc=i-1;
						break;
					}
					String tmp=str.substring(i,i+1);
					if(StringTools.isChinese(tmp)) t+=2;
					else t+=1;
				}
				str=str.substring(0, curLoc);
			}
			return str;
		}else{
			return str;
		}
	}
	/**
	 * 产生随机数
	 * @param numberFlag
	 * @param length
	 * @return
	 */
	public static String generateNumString(boolean numberFlag, int length) {
		String retStr = "";
		String strTable = numberFlag ? "1234567890"
				: "1234567890abcdefghijklmnopqrstuvwxyz";
		int len = strTable.length();
		boolean bDone = true;
		do {
			retStr = "";
			int count = 0;
			for (int i = 0; i < length; i++) {
				double dblR = Math.random() * len;
				int intR = (int) Math.floor(dblR);
				char c = strTable.charAt(intR);
				if (('0' <= c) && (c <= '9')) {
					count++;
				}
				retStr += strTable.charAt(intR);
			}
			if (count >= 2) {
				bDone = false;
			}
		} while (bDone);
		return retStr;
	}
	/**
	 * 通过数字得到容量字符串
	 * @param filesize
	 * @return
	 */
    public static String formatFileSize(long filesize)
    {
        if (filesize < 0)
        {
            throw new DALException("文件大小错误!");
        }
        else if (filesize >= 1024 * 1024 * 1024)  //文件大小大于或等于1024MB    
        {
            return String.format("%.2f GB", (double)filesize / (1024 * 1024 * 1024));
        }
        else if (filesize >= 1024 * 1024)  //文件大小大于或等于1024KB    
        {
            return String.format("%.2f MB", (double)filesize / (1024 * 1024));
        }
        else if (filesize >= 1024)  //文件大小大于等于1024bytes    
        {
            return String.format("%.2f KB", (double)filesize / 1024);
        }
        else
        {
            return String.format("%.2f bytes", filesize);
        }
    }
	/**
	 * 通过点的方式取hash数据，如Application.FileType.Value ，就会取得Application下面FileType下面属性为Value的值
	 * @param map
	 * @param path
	 * @return
	 */
	public static Object getMapByKey(Map<String,Object> map,String path){
		String[] arr=path.split("\\.");
		Map<String,Object> hash=map;
		for(String key:arr){
			//System.out.println("key:"+key);
			if(hash.containsKey(key)){
				if(hash.get(key) instanceof Map){
					hash=(Map<String,Object>)hash.get(key);
					//System.out.println("hash:"+hash);
				}else{
					//System.out.println("hash1:"+hash);
					return hash.get(key);
				}
			}else{
				return null;
			}
		}
		return hash;
	}
	
	
	public static final String empty="";
	
	private static VelocityEngine v;
	/**
	 * 得到模板引擎，这里不要用同步线程（synchronized）不然会出现访问一多会卡顿，但不加的话可能会造成线程不安全
	 * @return
	 */
	public static VelocityEngine getVelocityEngine(){
		if(v==null){
	        v=new VelocityEngine();
	        v.setProperty("output.encoding", "UTF-8");
	        v.setProperty("input.encoding", "UTF-8");
	        v.setProperty("velocimacro.permissions.allow.inline.local.scope", true);//在当前模板的宏有效，不是全局的
		}
		return v;
	}
	public static List<String> split(String inputValue,String regex){
		return split(inputValue,regex,true);
	}
	public static List<String> split(String inputValue,String regex,boolean isIgnoreCase){
        Pattern oPattern;
        if (isIgnoreCase)
        {
            oPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        }
        else
        {
            oPattern = Pattern.compile(regex);
        }
        StringBuffer sb = new StringBuffer(); 
        Matcher matcher = oPattern.matcher(inputValue); 
        List<String> list=new ArrayList<String>();
        while(matcher.find()) {
        	list.add(matcher.group());
        } 
        if(list.size()==0) list.add(inputValue);
        return list;
	}
	/**
	 * 得到模板字串
	 * @param template
	 * @param context
	 * @return
	 */
	public static String evaluate(String template,Map<String,Object> context){
        VelocityContext vcontext = new VelocityContext();
        vcontext.put("map", context);
        StringWriter w = new StringWriter(); 
        
        VelocityEngine v=getVelocityEngine();
        v.evaluate(vcontext, w, "sqlmap", template);
        return w.toString();
	}
	


	/**
	 * 字符串是空还是null
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str){
		if(str==null) return true;
		if(str.equals(""))return true;
		return false;
	}
	/**
	 * 字符串是空还是null
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(Object str){
		if(str==null) return true;
		if((str+"").equals(""))return true;
		return false;
	}

	/**
	 * md加密
	 * @param message
	 * @return
	 */
	public static String getMD5(String message) {
		String md5 = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(message.getBytes("utf-8"));
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
			md5 = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5;
	}
	/**
	 * 删除字符串中的html格式
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String clearHtml(String input, int length) {
		if (input == null || input.trim().equals("")) {
			return "";
		}
		// 去掉所有html元素,
		String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
				"<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "......";
		}
		return str;
	}
	/**
	 * HTML标签转义方法 ?? java代码库
	 * @param content
	 * @return
	 */
	public static String htmlEncode(String content) {
	    if(content==null) return "";       
	    String html = content;
	    html = html.replaceAll("<", "&lt;");
	    html = html.replaceAll(">", "&gt;");
	    return html;
	}

	
	/**
	 * 
	 * @Title: 保留位数自动补零
	 * @category 左补0
	 * @param str
	 * @param strLength
	 * @return
	 * @throws
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		while (strLen < strLength) {
			StringBuffer sb = new StringBuffer();
			sb.append("0").append(str);// 左补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}
	/**
	 * 
	 * @category: 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * @param length
	 *            随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return new SimpleDateFormat("HHmmss").format(new Date())
				+ sb.toString();
	}
	/**
	 * 
	 * @Title: generateNumString
	 * @category 返回指定长度的数字字符串
	 * @param length
	 * @return
	 * @throws
	 */
	public static String generateNumString(int length){
		final String allChar = "0123456789";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 将某种编码(如GB2312)转换成另外一种编码(如UTF-8)
	 * @param str
	 * @param convertedCodeName
	 * @param convertCodeName
	 * @return
	 */
	public static String codeConvert(String str,String convertedCodeName,String convertCodeName) {
		try {
			if (str != null)
				str = new String(str.getBytes(convertedCodeName), convertCodeName);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 将某种编码转成utf-8
	 * @param str
	 * @param code
	 * @return
	 */
	public static String convertUTF8(String str, String code) {
		return codeConvert(str,code,"UTF-8");
	}
	/**
	 * url编码
	 * @param source
	 * @param code
	 * @return
	 */
	public static String urlEncode(String source, String code) {
		try {
			return URLEncoder.encode(source, code);
		} catch (UnsupportedEncodingException e) {
			return source;
		}
	}
	// 根据Unicode编码完美的判断中文汉字和符号
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	// 只能判断部分CJK字符（CJK统一汉字）
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含 
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}

    

    /**
     * 特殊字符过滤
     * @param val
     * @return
     */
    public static String queryFilter(String val)
    {
    	
        if (val == null) return StringTools.empty;
        val=val.trim();
        if (!Validator.isMatch(val, Validator.NUMBER))
        {
            //非数字处理
            val = val.replaceAll("'", "''");
            //防止模糊查询
            val = val.replaceAll("[%]", "&#37;");
            //val = val.replaceAll("[?]", "&#63;");

			val = val.replaceAll("[{]", "｛");
			val = val.replaceAll("[}]", "｝");
        }
        
        return val;
    }
    /**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String toSBC(String input) {
             char c[] = input.toCharArray();
             for (int i = 0; i < c.length; i++) {
               if (c[i] == ' ') {
                 c[i] = '\u3000';
               } else if (c[i] < '\177') {
                 c[i] = (char) (c[i] + 65248);

               }
             }
             return new String(c);
    }

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        

             char c[] = input.toCharArray();
             for (int i = 0; i < c.length; i++) {
               if (c[i] == '\u3000') {
                 c[i] = ' ';
               } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                 c[i] = (char) (c[i] - 65248);

               }
             }
        String returnString = new String(c);
        
             return returnString;
    }
//    /**
//     * 字符转Map对象
//     * @param json
//     * @return
//     */
//	public static Map<String, Object> convertJson2Map(String json) {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			// json = Tools.decryptBASE64(json);
//			Map<String, Object> map = mapper.readValue(json, HashMap.class);
//			return map;
//		} catch (Exception e) {
//			return null;
//		}
//	}
}
