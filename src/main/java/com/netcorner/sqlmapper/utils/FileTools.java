package com.netcorner.sqlmapper.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.Random;

public  class FileTools {
	/**
	 * 得到资源文件内容
	 * @param resfilepath
	 * @return
	 */
	public static String getResFile(String resfilepath){
		FileTools f=new FileTools();
		InputStream is =f.getClass().getResourceAsStream(resfilepath);
		BufferedReader br;  
        StringBuilder strBlder = new StringBuilder("");  
        try {  
            br = new BufferedReader(new InputStreamReader(is));  
            String line = "";  
            while (null != (line = br.readLine())) {  
                strBlder.append(line + "\n");  
            }  
            br.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        return strBlder.toString();
	}


	public static void appendMethodB(String fileName, String content) {
		try {
//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static final Log log = LogFactory.getLog("FileTools");
	/**
	 * 
	 * @Title: getExtension
	 * @category 返回文件名后缀
	 * @param fileName
	 * @return
	 * @throws
	 */
	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * 使用时间戳和前缀的方式重命名上传文件的名称
	 * 
	 * @param prefix
	 */
	public static final String resetUploadFileNamePrefix(String prefix,
			String filename) {
		return prefix + System.currentTimeMillis() + getExtension(filename);
	}

	/**
	 * 读取路径文件的文本内容
	 * @param filepath
	 * @return
	 */
	public static String getPathFile(String filepath){
		File file = new File(filepath);

		BufferedReader br;
		StringBuilder strBlder = new StringBuilder("");
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while (null != (line = br.readLine())) {
				strBlder.append(line + "\n");
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strBlder.toString();
	}

	/**
	 * 使用时间戳和后缀的方式重命名上传文件的名称
	 * @param suffix
	 * @param filename
	 * @return
	 */
	public static final String resetUploadFileNameSuffix(String suffix,
			String filename) {
		return System.currentTimeMillis() + suffix + getExtension(filename);
	}
	
	public static final String resetUploadFileName(String filename) {
		Random ran = new Random();
		return "" + ran.nextInt(10000) + System.currentTimeMillis()
				+ getExtension(filename);
	}

	/**
	 * 文件名是否有效 为*.*格式，前一个*不能包含/\<>*?|，后一个*只能是数字或字母并且有一个以上
	 * 
	 * @param filename
	 * @return
	 */
	public static final boolean isEffectiveFileName(String filename) {
		return null != filename
				&& filename.matches("[^/\\\\<>*?|]+\\.[0-9a-zA-Z]{1,}$");
	}

	/**
	 * 是否是图片文件 jpg、gif、bmp、png、jpeg
	 * 
	 * @param filename
	 * @return
	 */
	public static final boolean isImageFile(String filename) {
		return filename.matches("[^/\\\\<>*?|]+\\.(?i)(jpg|png|gif|bmp|jpeg)$");
	}

	/**
	 * 是否是压缩文件
	 * 
	 * @param filename
	 * @return
	 */
	public static final boolean isArchiveFile(String filename) {
		return filename.matches("[^/\\\\<>*?|]+\\.(?i)(7z|rar|zip|gz)$");
	}

	/**
	 * 是否是文档
	 * 
	 * @param filename
	 * @return
	 */
	public static final boolean isDocumentFile(String filename) {
		return filename.matches("[^/\\\\<>*?|]+\\.(?i)(doc|docx|pdf|xps|wps)$");
	}




	/**
	 * 删除文件
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		if (!file.isDirectory()) {
			file.delete();
		} else{
			String[] fileList = file.list();
			for (int i = 0; i < fileList.length; i++) {
				File delFile = new File(filePath + "\\" + fileList[i]);
				if (!delFile.isDirectory()) {
					delFile.delete();
				} else{
					deleteFile(filePath + "\\" + fileList[i]);
				}
			}
			file.delete();
		}
		return true;
	}
	 public static boolean copyFile(String srcFileName, String destFileName, boolean overlay)
	  {
	    File srcFile = new File(srcFileName);
	    String MESSAGE = "";

	    if (!(srcFile.exists())) {
	      MESSAGE = "源文件：" + srcFileName + "不存在！";
	      System.out.print(MESSAGE);
	      return false; }
	    if (!(srcFile.isFile())) {
	      MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
	      System.out.print(MESSAGE);
	      return false;
	    }

	    File destFile = new File(destFileName);
	    if (destFile.exists())
	    {
	      if (overlay)
	      {
	        new File(destFileName).delete();
	      }

	    }
	    else if ((!(destFile.getParentFile().exists())) && 
	      (!(destFile.getParentFile().mkdirs())))
	    {
	      return false;
	    }

	    int byteread = 0;
	    InputStream in = null;
	    OutputStream out = null;
	    try
	    {
	      in = new FileInputStream(srcFile);
	      out = new FileOutputStream(destFile);
	      byte[] buffer = new byte[1024];

	      while ((byteread = in.read(buffer)) != -1) {
	        out.write(buffer, 0, byteread);
	      }
	      return true;
	    } catch (FileNotFoundException e) {
	      return false;
	    } catch (IOException e) {
	      return false;
	    } finally {
	      try {
	        if (out != null)
	          out.close();
	        if (in != null)
	          in.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }
}
