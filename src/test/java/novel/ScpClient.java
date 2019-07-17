package novel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SCPInputStream;
import ch.ethz.ssh2.SCPOutputStream;
import ch.ethz.ssh2.Session;

public class ScpClient {
	
	private static ScpClient instance;
    private String ip;
    private int port;
    private String name;
    private String password;
    
    /**
             * 私有化默认构造函数
             * 实例化对象只能通过getInstance
     */
    private ScpClient(){

    }
    
    /**
              * 单例模式
             * 懒汉式
             * 线程安全
     * @return
     */
    public static ScpClient getInstance(){
        if (null == instance) {
            synchronized (ScpClient.class){
                if (null == instance) {
                    instance = new ScpClient();
                }
            }
        }
        return instance;
    }

    public static ScpClient getInstance(String ip,int port,String name,String password){
        if (null == instance) {
            synchronized (ScpClient.class){
                if (null == instance) {
                    instance = new ScpClient(ip,port,name,password);
                }
            }
        }
        return instance;
    }
    
    public ScpClient(String IP, int port, String username, String passward) {
        this.ip = IP;
        this.port = port;
        this.name = username;
        this.password = passward;
    }
    
    /**
             *    文件下载
     * @param remoteFile 服务器上的文件名
     * @param remoteTargetDirectory 服务器上文件的所在路径
     * @param newPath 下载文件的路径
     */
    public boolean downloadFile(String remoteFile, String remoteTargetDirectory, String newPath){
        Connection connection = new Connection(ip, port);
        FileOutputStream fos = null;
        SCPInputStream sis = null;
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name, password);
            if (isAuthenticated) {
                SCPClient scpClient = connection.createSCPClient();
                sis = scpClient.get(remoteTargetDirectory + "/" + remoteFile);
                File f = new File(newPath);
                if (!f.exists()) {
                    f.mkdirs();
                }
                File newFile = new File(newPath + remoteFile);
                fos = new FileOutputStream(newFile);
                byte[] b = new byte[4096];
                int i;
                while ((i = sis.read(b)) != -1){
                    fos.write(b,0, i);
                }
                fos.flush();
                System.out.println("下载文件成功！");
            } else {
                System.out.println("连接建立失败");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (fos != null) {
        		try {
    				fos.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
        	if (sis != null) {
        		 try {
     				sis.close();
     			} catch (IOException e) {
     				e.printStackTrace();
     			}
        	}
            connection.close();
        }
        return true;
    }
    
    /**
             *    上传文件到服务器
     * @param f 文件对象
     * @param length 文件大小
     * @param remoteTargetDirectory 上传路径
     * @param mode 默认为null
     */
    public boolean uploadFile(File f, long length, String remoteTargetDirectory, String mode) {
        Connection connection = new Connection(ip, port);
        SCPOutputStream os = null; 
        FileInputStream fis = null;
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name,password);
            if (!isAuthenticated) {
                System.out.println("连接建立失败");
                return false;
            }
            SCPClient scpClient = new SCPClient(connection);
            os = scpClient.put(f.getName(),length,remoteTargetDirectory,mode);
            byte[] b = new byte[4096];
            fis = new FileInputStream(f);
            int i;
            while ((i = fis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
            System.out.println("上传成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (fis != null) {
        		try {
    				fis.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
        	if (os != null) {
        		try {
    				os.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
        	}
            connection.close();
        }
        return true;
    }
    
    /**
             * 远程连接linux，执行linux命令
     * @return
     */
    public boolean runs(){
        Connection connection = new Connection(ip, port);
        Session session = null;
        try {
            connection.connect();
            boolean isAuthenticated = connection.authenticateWithPassword(name, password);
            if (isAuthenticated) {
            	session = connection.openSession();
                session.execCommand("mkdir /srv/testssk/cc");
            } else {
                System.out.println("连接建立失败");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (session != null) {
        		session.close();
        	}
        	connection.close();
        }
        return true;
    }

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static void setInstance(ScpClient instance) {
		ScpClient.instance = instance;
	}


}
