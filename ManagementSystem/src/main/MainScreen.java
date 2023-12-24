package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.tools.javac.Main;

import inquire.SelectInquire;
import login.LoginPage;
import management.ManagementScreen;
import sell.SellScreen;

public class MainScreen extends JFrame implements Runnable, ActionListener{

	private JPanel contentPane;
	private JPanel panel_Intro;
	private JPanel panel_Main;
	private JPanel panel_Buttons;
	
	private JLabel lblIntroUser;
	private JLabel lblTime;
	private JLabel lblWelcome;
	
	private JButton btnLogOut;
	private JButton btnSell;
	private JButton btnInquire;
	private JButton btnManagement;
	
	private Thread thread;
	
	private JOptionPane showMessage;
	
	public static String userName;
	
	public MainScreen() {
		//JFrame 설정
		setTitle("메인 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,700);
		setLocationRelativeTo(null); 
		setResizable(false);
		
		//컴포넌트 객체 생성
		contentPane = new JPanel();
		panel_Intro = new JPanel();
		panel_Main = new JPanel();
		panel_Main.setBackground(Color.DARK_GRAY);
		panel_Buttons = new JPanel();
		panel_Buttons.setBackground(Color.DARK_GRAY);
		
		LoginPage lp = new LoginPage();
		userName = lp.getUserName();
		lblIntroUser = new JLabel("환영합니다. "+userName+" 씨");
		lblIntroUser.setForeground(Color.WHITE);
		lblTime = new JLabel("2022.12.22 00:00:00");
		lblTime.setForeground(Color.PINK);
		lblWelcome = new JLabel("Welcome!");
		lblWelcome.setForeground(Color.PINK);
		
		btnLogOut = new JButton("로그아웃");
		btnLogOut.setForeground(Color.BLACK);
		btnSell = new JButton("<html>상품<br>판매</html>");
		btnSell.setBackground(new Color(255, 85, 119));
		btnSell.setFont(new Font("Lucida Grande", Font.BOLD, 50));
		btnInquire = new JButton("<html>판매<br>조회</html>");
		btnInquire.setBackground(new Color(235, 217, 114));
		btnInquire.setFont(new Font("Lucida Grande", Font.BOLD, 50));
		btnManagement = new JButton("<html>상품<br>관리</html>");
		btnManagement.setBackground(new Color(152, 194, 231));
		btnManagement.setFont(new Font("Lucida Grande", Font.BOLD, 50));
		
		//컴포넌트 옵션
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_Intro.setBackground(Color.GRAY);
		panel_Main.setLayout(null);
		panel_Buttons.setBounds(140, 323, 728, 253);
		panel_Buttons.setLayout(new GridLayout(0, 3, 0, 0));
		
		lblWelcome.setFont(new Font("Nanum Pen Script", Font.PLAIN, 82));
		lblWelcome.setBounds(380, 104, 264, 60);
		lblTime.setFont(new Font("Anonymous Pro for Powerline", Font.PLAIN, 32));
		lblTime.setBounds(645, 33, 323, 31);
		
		btnLogOut.setBounds(19, 19, 97, 29);
		btnLogOut.addActionListener(this);
		btnSell.addActionListener(this);
		btnSell.setOpaque(true);
		btnSell.setBorderPainted(false);
		btnInquire.addActionListener(this);
		btnInquire.setOpaque(true);
		btnInquire.setBorderPainted(false);
		btnManagement.addActionListener(this);
		btnManagement.setOpaque(true);
		btnManagement.setBorderPainted(false);
		
		panel_Intro.add(lblIntroUser);
		panel_Main.add(lblTime);
		panel_Main.add(lblWelcome);
		panel_Main.add(panel_Buttons);
		panel_Main.add(btnLogOut);
		panel_Buttons.add(btnSell);
		panel_Buttons.add(btnInquire);
		panel_Buttons.add(btnManagement);
		
		contentPane.add(panel_Intro, BorderLayout.NORTH);
		contentPane.add(panel_Main, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(458, 221, 97, 90);
		lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/image/image1.png")));
		panel_Main.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(getClass().getResource("/image/image3.png")));
		lblNewLabel_1.setBounds(702, 209, 97, 102);
		panel_Main.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(211, 221, 107, 90);
		lblNewLabel_1_1.setIcon(new ImageIcon(getClass().getResource("/image/image4.png")));
		panel_Main.add(lblNewLabel_1_1);

		setContentPane(contentPane);
		
		if(thread == null){
			//this의 의미는 Runnable이 구현된 객체를 뜻함(DigitalClock)
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public static String getUserName() {
		return userName;
	}
	
	public void run(){
		while(true){
			Calendar cal = Calendar.getInstance();	//현재 날짜정보를 담는다
			String now = cal.get(Calendar.YEAR)+"."+
					(cal.get(Calendar.MONTH)+1)+"."+
					cal.get(Calendar.DATE)+" "+
					cal.get(Calendar.HOUR)+":"+
					cal.get(Calendar.MINUTE)+":"+
					cal.get(Calendar.SECOND);
			lblTime.setText(now);
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public void logOut() {
		int m = showMessage.showConfirmDialog(null, "로그아웃하시겠습니까?", "알림", showMessage.YES_NO_OPTION, showMessage.PLAIN_MESSAGE);
		if(m == showMessage.CLOSED_OPTION)
			return;
		else if(m == showMessage.YES_OPTION) {
			new LoginPage().setVisible(true);
			setVisible(false);
			showMessage.showMessageDialog(null, "로그아웃! 다시 로그인하세요", "알림", showMessage.PLAIN_MESSAGE);
		} else
			return;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSell) {
			new SellScreen().setVisible(true);
			setVisible(false);
		}
		if(e.getSource() == btnInquire) {
			new SelectInquire().setVisible(true);
		}
		if(e.getSource() == btnManagement) {
			new ManagementScreen().setVisible(true);
			setVisible(false);
		}
		if(e.getSource() == btnLogOut) {
			logOut();
		}
		
	}
}
