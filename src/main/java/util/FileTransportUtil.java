package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileTransportUtil {
	public static final Logger logger = LoggerFactory.getLogger("FileTransportUtil");
	
	public static String saveFile(String path, MultipartFile file){
		String flg = "";
		String fileName = new Date().getTime()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File targetFile = new File(path, fileName);
        File target = new File(path);
		if(!target.exists()){
			target.mkdirs();
		}
        //保存
        try {
            file.transferTo(targetFile);
            flg = fileName;
        } catch (Exception e) {
        	flg = "";
            logger.error("异常",e);
        }
		return flg;
	}
	
	/**
	 * 自定义文件夹
	 * @param path
	 * @param file
	 * @return
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static String saveFileBySelf(String path, File file,String folder){
		if(StringUtil.isNotNull(folder)){
			path = path + "/" + folder;
		}
		String fileName = new Date().getTime()+file.getName().substring(file.getName().lastIndexOf("."));
        File targetFile = new File(path, fileName);
        File target = new File(path);
		if(!target.exists()){
			target.mkdirs();
		}
		try {
			int bytesum = 0;
			int byteread = 0;
			if (file.exists()) {
				InputStream inStream = new FileInputStream(file);
				FileOutputStream fs = new FileOutputStream(targetFile);
				byte[] buffer = new byte[1024];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			logger.error("异常",e);
		}
		return folder+"/"+fileName;
	}
	
	/**
	 * 保存临时文件
	 * @param path
	 * @param file
	 * @return
	 */
	public static String saveTmpFile(String path, MultipartFile file,String uuid){
		String flg = "";
		String fileName = new Date().getTime()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		File target = new File(path+"/"+uuid);
		if(!target.exists()){
			target.mkdirs();
		}
        File targetFile = new File(target.getPath(), fileName);
        //保存
        try {
            file.transferTo(targetFile);
            flg = uuid+"/"+fileName;
        } catch (Exception e) {
        	flg = "";
            logger.error("异常",e);
        }
		return flg;
	}
	
	
	public static void deleteFile(File file){
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	}
	
}
