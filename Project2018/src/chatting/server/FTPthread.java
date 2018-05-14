package chatting.server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPthread implements Runnable{
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private String uufName;
	private String FILE_SAVED_PATH;
	private String BASE_PATH = "D:\\IT_MASTER\\";
	
	public FTPthread(DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
	}
	
	@Override
	public void run() {
		try {
			String msg = dis.readUTF();
			FILE_SAVED_PATH = dis.readUTF();
			File file = new File(BASE_PATH+FILE_SAVED_PATH);
			System.out.println(file.getAbsolutePath());
			System.out.println(file.exists());
			
			if(msg.equals("STC"))
			{
				if(file.exists())
				{
					fis = new FileInputStream(file);
					dos.writeUTF("YES");
					dos.flush();
					
					dos.writeUTF(Long.toString(file.length()));
					dos.flush();
					
					msg = dis.readUTF();
					if(msg.equals("START"))
					{
						System.out.println("Sever to Client. Start");
						
						byte[] buf = new byte[4096];
						int read = 0;
						BufferedInputStream bis = new BufferedInputStream(fis);
						while((read = bis.read(buf)) != -1){
							dos.write(buf, 0, read);
						}
						dos.flush();
						System.out.println("���� ���� �Ϸ�");
					}
					else if(msg.equals("GIVE UP"))
					{
						System.out.println("Ŭ���̾�Ʈ���� ���� ��θ� �������� ����.");
					}
				}
				else
				{
					System.out.println("�������� �ʴ� ������ ��û��");
					dos.writeUTF("NO");
					dos.flush();
				}
			}
			else if(msg.equals("CTS"))
			{
				
				fos = new FileOutputStream(file);
				if(true)
				{
					dos.writeUTF("YES");
					
					byte[] buf = new byte[4096];
					int read = 0;
					
					while((read = dis.read(buf)) != -1)
					{
						fos.write(buf, 0, read);
					}
					fos.flush();
					System.out.println("���� ���� �Ϸ�");
				}
				else
				{
					//�뷮 �����̳� ���� �̸��̳� ��Ÿ ������ ���� ����
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			closeAll();
		}		
		System.out.println("ftp server ������ ����");
	} // run()
	
	public void closeAll(){
		System.out.println("��� �ڿ� ����");
		try { if(fos != null) {fos.close(); }} catch (IOException e) {}
		try { if(fis != null) {fis.close(); }} catch (IOException e) {}
		try { if(dis != null) {dis.close();}} catch (IOException e) {}
		try { if(dos != null) {dos.close(); }} catch (IOException e) {}
	}

}
