package novel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class Test1 {
	
	public static void main(String[] args) {
//		 Runtime run = Runtime.getRuntime();
//		 if (run == null) {
//	            System.err.println("Create runtime false!");
//	     }
		 FileChannel inputChannel = null;    
	     FileChannel outputChannel = null;
         try {
             //Process process = run.exec("/usr/local/tomcat/apache-tomcat-7.0.92/bin/startup.sh");
             //Process process = run.exec("mkdir /srv/testssk/bb");
             //process.waitFor();
             //process.destroy();
        	
//        	ScpClient scpClient = ScpClient.getInstance("192.168.54.219", 22, "root","123456");
//        	scpClient.runs();
	    	
	    	File f = new File("D:/test/AAACopy.props");
	    	File a = new File("D:/test/CCC.props");
	    	inputChannel = new FileInputStream(f).getChannel();
	        outputChannel = new FileOutputStream(a).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
//	    	f.delete();
//	    	File l = new File("D:/AAACopy.props");
//	    	l.renameTo(f);
	    	//System.out.println("#aaa=111".indexOf("aaa=")); 
	        //ScpClient scpClient = ScpClient.getInstance("192.168.54.210", 22, "root","123456");
	        // 下载文件
//	        boolean flag = scpClient.downloadFile("AAA.txt", "/srv/testssk", "D:\\test\\");
//	        System.out.println(flag);
	        // 字符串写入文件
	        //writeFile("D:/test/AAA.txt"); 
	        // 上传文件
//	        File f = new File("D:/test/AAA.txt"); 
//	        System.out.println(f); 
//	        boolean flag = scpClient.uploadFile(f, f.length(), "/srv/testssk/", null); 
//	        System.out.println(flag); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
			if (inputChannel != null) {
				try {
					inputChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputChannel != null) {
				try {
					outputChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
//	  public static void writeFile(String filePath) {
//	    	 String str="\rhello world!";
//	         FileWriter writer = null;
//	         try {
//	            writer = new FileWriter(filePath, true);
//	            writer.write(str);
//	            writer.flush();
//	            System.out.println("写入成功！");   
//	         } catch (IOException e) {
//	            e.printStackTrace();
//	         } finally {
//	        	 try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//	         }
//	    }
	
	public static void writeFile(String filePath) {
        String line = null;
        try {
	         BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
	         while ((line = reader.readLine()) != null) {
	           //res.append(line + "\n");
	           System.out.println(line); 
	         }
	         reader.close();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }

	}

}
