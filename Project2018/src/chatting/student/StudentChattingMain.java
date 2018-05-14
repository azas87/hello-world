package chatting.student;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import chatting.data.Data;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;

public class StudentChattingMain extends JFrame implements ActionListener, Runnable, MouseListener 
{

	private JPanel contentPane;
	private JScrollPane sp_chatOutput;
	private JTextArea ta_chatOutput;
	private JPanel p_south;
	private JTextField tf_chatInput;
	private JButton btn_send;
	private JButton btn_filelist;
	private JPanel p_east;
	private JLabel lbl_count;
	private JScrollPane sp_userList;
	private JList li_fileList;
	private String name;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Data data;
	private JPanel panel;
	private JButton b_download;
	private DefaultListModel content = new DefaultListModel();
	private ArrayList<String> as = new ArrayList<String>();
	private int count;
	private JButton b_upload;
	private String str = "D:\\IT_MASTER";
	private JTree tree;
	private JButton btn_create_folder;
	private JButton btn_delete_folder;
	private JButton btn_download;
	DefaultMutableTreeNode node;
	public static JProgressBar progressbar;
	public static JLabel lbl_per;
	private String SEVER_IP = "127.0.0.1";
//	private String SEVER_IP = "203.233.196.50";
//	private String SEVER_IP = "203.233.196.48";
	
	private FtpClientThread cst;
	private JButton btn_cancel;
	private boolean exit;
	private JButton btn_logs;
	private DataInputStream dis;
	private DataOutputStream dos;
	private FileInputStream fis;
	private FileOutputStream fos;

	
	/**
	private FtpClientThread cst;
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public StudentChattingMain(String name) {

		this.name = name;
		setTitle("SCIT(\uD559\uC0DD)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 533, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		sp_chatOutput = new JScrollPane();
		sp_chatOutput.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(sp_chatOutput, BorderLayout.CENTER);

		ta_chatOutput = new JTextArea();
		ta_chatOutput.setEditable(false);
		ta_chatOutput.setRows(10);
		ta_chatOutput.setColumns(40);
		sp_chatOutput.setViewportView(ta_chatOutput);

		p_south = new JPanel();
		contentPane.add(p_south, BorderLayout.SOUTH);
		p_south.setLayout(new BorderLayout(0, 0));

		tf_chatInput = new JTextField();
		tf_chatInput.addActionListener(this);
		p_south.add(tf_chatInput, BorderLayout.CENTER);
		tf_chatInput.setColumns(25);

		btn_send = new JButton("\uC804\uC1A1");
		btn_send.addActionListener(this);
		p_south.add(btn_send, BorderLayout.EAST);

		p_east = new JPanel();
		contentPane.add(p_east, BorderLayout.EAST);
		p_east.setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		p_east.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		lbl_count = new JLabel("\uD30C\uC77C\uBAA9\uB85D");
		lbl_count.setPreferredSize(new Dimension(110, 15));
		panel.add(lbl_count, BorderLayout.WEST);
		lbl_count.setHorizontalAlignment(SwingConstants.CENTER);
		
		b_upload = new JButton("\uC5C5\uB85C\uB4DC");
		b_upload.setActionCommand("");
		b_upload.setPreferredSize(new Dimension(81, 23));
		panel.add(b_upload, BorderLayout.CENTER);

		/*
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("���� ���");
		tree = new JTree(root);
		tree.disable();
		tree.addMouseListener(this);
		JScrollPane jsp = new JScrollPane(tree);
		p_east_left.add(jsp, BorderLayout.CENTER);
		b_download = new JButton("\uB2E4\uC6B4\uB85C\uB4DC");
		b_download.addActionListener(this);
		panel.add(b_download, BorderLayout.EAST);
		*/
		
		sp_userList = new JScrollPane();
		sp_userList.setAutoscrolls(true);
		sp_userList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp_userList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		p_east.add(sp_userList, BorderLayout.CENTER);

		li_fileList = new JList<String>();
		li_fileList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		li_fileList.addMouseListener(this);
		sp_userList.setViewportView(li_fileList);

		setVisible(true);
		connectServer();

		// li_userList.setListData(new String [] {id});

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				tf_chatInput.requestFocus();
			}

		});

	}

	private void connectServer() {
		Socket client = null;
		try {
			client = new Socket(SEVER_IP, 7777);
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			data = new Data(name, "���� �����߽��ϴ�.", Data.CHAT_LOGIN);
			Thread t = new Thread(this);
			t.start();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("���� ����1");
		} catch (IOException e) {
			e.printStackTrace();
			// �߸� �� �ּ� ������ ��~~~~~���� �ִٰ� ���� ������. UnknownHostException�� ���� Ÿ��?
			// ������ ���������� �ʴ� ���.
			System.out.println("���� ����2");
		}

	}

	private void sendData(Data data) {
		try {
			oos.writeObject(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		
		
		BufferedWriter out = null;
		BufferedReader in = null;
		
		String message = ta_chatOutput.getText();
		//data = new Data(id, message, Data.CHAT_MESSAGE);
		//sendData(data);
		
		StringTokenizer st = new StringTokenizer(message, "\n");
		
		if(source == tf_chatInput)
		{	
			String message1 = tf_chatInput.getText();
			//ta_chatOutput.append("["+id+"]" + message1 +"\n");
			data = new Data(name, message1, Data.CHAT_MESSAGE);
			sendData(data);
			tf_chatInput.setText("");
		}
		else if(source == btn_send)
		{
			String message1 = tf_chatInput.getText();
			//ta_chatOutput.append("["+id+"]" + message1 +"\n");
			data = new Data(name, message1, Data.CHAT_MESSAGE);
			sendData(data);
			tf_chatInput.setText("");
		}
		else if(source == b_download)
		{
			
			str = "D:\\IT_MASTER";
			getList();
		
		}
		else if( source == btn_filelist)
		{
			//System.out.println(id);
			data = new Data(name, null, Data.CHAT_TREE);
			sendData(data);
		}
		/*
		else if( source == mi_exit )
		{
			int choice = JOptionPane.showConfirmDialog(this, "������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			
			if( choice == JOptionPane.YES_OPTION )
			{
				data = new Data(name, "���� ���� �߽��ϴ�.", Data.CHAT_LOGOUT);
				sendData(data);
				System.exit(0);
			}
		}
		*/
		else if( source == btn_create_folder )
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(node != null)
			{
				TreeNode[] tn = node.getPath();
				
				int index = tn.length;
				if(node.getChildCount() == 0)
				{
					index -= 2;
				}
				else
				{
					index--;
				}
				
				
				int choice = JOptionPane.showConfirmDialog(this, tn[index]+"������ ���ο� ������ �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION )
				{
					String path="";				
					for(int i = 1; i <= index; i++)
					{
						path += tn[i]+"\\";
					}
					
					System.out.println(path);
					
					data = new Data(name, path+"test", Data.FILE_CREATE);
					sendData(data);
					
					data = new Data(name, null, Data.CHAT_TREE);
					sendData(data);
				}
			}
			else
			{
				System.out.println("������ ������ ��θ� �����ϼ���.");
			}
		}
		else if( source == btn_delete_folder )
		{
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(node != null)
			{
				TreeNode[] tn = node.getPath();
				
				int index = tn.length;
				if(node.getChildCount() == 0)
				{
					index -= 2;
				}
				else
				{
					index--;
				}
				
				
				int choice = JOptionPane.showConfirmDialog(this, tn[index]+"������ ���� �Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
				if( choice == JOptionPane.YES_OPTION )
				{
					String path="";				
					// ���� ������ ������ ���;� �ؼ� ���̸�ŭ �̾�� ��.
					for(int i = 1; i < tn.length; i++)
					{
						path += tn[i]+"\\";
					}
					
					System.out.println(path);
					
					data = new Data(name, path, Data.FILE_DELETE);
					sendData(data);
					
					data = new Data(name, null, Data.CHAT_TREE);
					sendData(data);
				}
			}
			else
			{
				System.out.println("������ ������ ��θ� �����ϼ���.");
			}
			
		}
		else if( source == btn_download)
		{
			node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if (node != null && (node.getChildCount() == 0) ) 
			{
				String path="";
				TreeNode[] tn = node.getPath();			
				for(int i = 1; i < tn.length; i++)
				{
					path += tn[i]+"\\";
				}
				
				System.out.println(path.substring(0, path.length()-1));
	    		ftpconnect(path.substring(0, path.length()-1),0);
			}
		}
		else if( source == btn_cancel)
		{
			FtpClientThread.isCancel = true;
		}
		else if( source == btn_logs)
		{
			System.out.println("1111");
			data = new Data(name, null, Data.Log_ALL);
			sendData(data);
		}
	}
	
	
	public void fileName(File f, int count)
	{
		
		File [] ff = f.listFiles();
		
		
		for(File file : ff)
		{
			if(file.isDirectory())
			{
				for(int i = 0 ; i < count ; i++)
				{
					//as.add("\t");
				}	
				as.add(file.getName());
				fileName(file,count+1);
				
			}
			else
			{
				for(int i = 0 ; i < count ; i++)
				{
					//as.add("\t");
				}	
				as.add(file.getName());
			}
			
		}
		
	}
		
	


	/*
	 * else if(source == mi_exit) { int choice = JOptionPane.showConfirmDialog(this,
	 * "������ ���� �ϰڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION); if(choice ==
	 * JOptionPane.YES_OPTION)
	 * 
	 * data = new Data(id, "���� �����߽��ϴ�.", Data.CHAT_LOGOUT); sendData(data);
	 * 
	 * { System.exit(0); } } else if(source==mi_save) { JFileChooser save = new
	 * JFileChooser(); save.showSaveDialog(this); File file =
	 * save.getSelectedFile();
	 * 
	 * try { out = new BufferedWriter(new FileWriter(file));
	 * while(st.hasMoreTokens()) { String s = st.nextToken(); out.write(s);
	 * out.newLine(); } } catch (IOException e1) { e1.printStackTrace(); } finally {
	 * try { if(out!=null) { out.close(); } } catch (IOException e1) {
	 * e1.printStackTrace(); }
	 * 
	 * }
	 * 
	 * }
	 */
	/*
	 * else if(source==mi_text) { JFileChooser save = new JFileChooser();
	 * save.showOpenDialog(this); File file = save.getSelectedFile(); try { in = new
	 * BufferedReader(new FileReader(file)); } catch (FileNotFoundException e1) {
	 * e1.printStackTrace(); } }
	 * 
	 * else if(source == btn_send) {
	 * 
	 * String targetId = (String)li_fileList.getSelectedValue(); if(targetId ==
	 * null) { JOptionPane.showConfirmDialog(this, "�Ӹ��� ���� ����� �����Ͻÿ�","ERROR",
	 * JOptionPane.WARNING_MESSAGE);
	 * 
	 * } else { String message1 = tf_chatInput.getText(); data = new Data(name,
	 * message1, targetId, Data.CHAT_WHISPER); sendData(data); }
	 * tf_chatInput.requestFocus(); }
	 * 
	 * 
	 * }
	 */

	@Override
	public void run() {
		try {
			oos.writeObject(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while( !exit )
		{
			try 
			{
				data = (Data) ois.readObject();
				switch (data.getStatus()) {
				case Data.CHAT_LOGIN:
				case Data.CHAT_LOGOUT:
					ta_chatOutput.append("[" + data.getId() + "]" + data.getMessage() + "\n");
					break;

				case Data.CHAT_MESSAGE:
					ta_chatOutput.append("[" + data.getId() + "]" + data.getMessage() + "\n");
					tf_chatInput.setText("");

					break;
				case Data.CHAT_WHISPER:
					ta_chatOutput.append("[" + data.getId() + "](�Ӹ�)" + data.getMessage() + "\n");
					break;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2)
		{
			if(li_fileList.getSelectedValue().equals(".."))
			{
				
				str = new File(str).getParent();
			}
			else 
			{

				File file = new File(str+"\\"+li_fileList.getSelectedValue());
				if(file.isDirectory())
				{
					str += "\\"+li_fileList.getSelectedValue();
				}	
			}
			getList();
		}
		
	}
	public void getList()
	{
		File file = new File(str);
		if(file.isDirectory())
		{	
			
			String [] str = file.list();
			File [] f = file.listFiles();
			ArrayList<String> af = new ArrayList<String>();
			ArrayList<String> ad = new ArrayList<String>();
			
			for(int i = 0 ; i < f.length ; i++)
			{
				if(f[i].isFile())
				{
					af.add(f[i].getName());
				}
				else
				{
					ad.add(f[i].getName());
				}	
			}
			Collections.sort(af);
			Collections.sort(ad);
			
			content.clear();
			if(!file.getAbsolutePath().equals("D:\\IT_MASTER"))
			{
				String back = "..";
    			content.addElement(back);
			}
			
			for(String ss : ad)
			{
				content.addElement(ss);
			}
			
			for(String stt : af)
			{
				content.addElement(stt);
			}	
			li_fileList.setModel(content);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
	
		
	}
	
	public void ftpconnect(String path, int mode)
	{
		Socket ftpclient;
		
		try {
			ftpclient = new Socket(SEVER_IP, 8888);
			dos = new DataOutputStream(ftpclient.getOutputStream());
			dis = new DataInputStream(ftpclient.getInputStream());
			FtpClientThread cst = new FtpClientThread(dis, dos, mode, path);
			Thread t1 = new Thread(cst);
			t1.start();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("IOException 2222");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("������ ���� ���� ���� �� ������. IOException 1111");
		} 
	}	
	
	public void closeAll(){
		System.out.println("��� �ڿ� ����");
		try { if(ois != null) {oos.close(); }} catch (IOException e) {}
		try { if(oos != null) {ois.close();}} catch (IOException e) {}
		
	}


	/*private DefaultMutableTreeNode getNode(File file)
	{
		DefaultMutableTreeNode sub_root = new DefaultMutableTreeNode(file.getName());
		
		File list[] = file.listFiles();
		DefaultMutableTreeNode temp = null;

		// programfiles//windowsapps ���� �ֵ��� null ����.
		// ������ ������ ��� �� ���� ������...
		if(list == null || list.length == 0)	
		{
			temp = new DefaultMutableTreeNode("");
			sub_root.add(temp);
		}
		else
		{
			ArrayList<File> folderList = new ArrayList<File>();
			ArrayList<File> fileList = new ArrayList<File>();
			
			for(File f : list)
			{
				if(f.isDirectory())
				{
					folderList.add(f);
				}
				else if(f.isFile())
				{
					fileList.add(f);
				}
			}
			
			Collections.sort(folderList);
			Collections.sort(fileList);
			
			for (int i = 0; i < folderList.size(); ++i) 
			{
				temp = getNode(folderList.get(i));
				sub_root.add(temp);
			}
			
			File tempFile;
			for (int i = 0; i < fileList.size(); ++i) 
			{
				tempFile = fileList.get(i) ;
				temp = new DefaultMutableTreeNode(tempFile.getName());
				sub_root.add(temp);
			}
		}
		
		return sub_root;
	}*/

}
