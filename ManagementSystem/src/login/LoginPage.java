package login;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.MainScreen;

public class LoginPage extends JFrame implements ActionListener {
	//컴포넌트
	private JPanel contentPane;
	private JLabel lblTitle;
	private JLabel lblID;
	private JLabel lblPassword;
	private JLabel lblSignUp;
	
	private JTextField tFieldID;
	private JPasswordField tFieldPassword;
	
	private JButton btnLogin;
	
	private JOptionPane showMessage;
	
	private String id;
	private String password;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public static String userName;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//생성자 선언 
	public LoginPage() {
		//JFrame 설정
		setTitle("로그인 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(370,500);
		setLocationRelativeTo(null); //화면 중앙 위치
		setResizable(false);		 //화면 사이즈 변경
		
		//객체 생성
		contentPane = new JPanel();
		lblTitle = new JLabel("Management");
		lblTitle.setForeground(Color.PINK);
		lblID = new JLabel("아이디");
		lblID.setForeground(Color.WHITE);
		lblPassword = new JLabel("비밀번호");
		lblPassword.setForeground(Color.WHITE);
		lblSignUp = new JLabel("회원가입");
		lblSignUp.setForeground(Color.WHITE);
		tFieldID = new JTextField();
		tFieldPassword = new JPasswordField();
		btnLogin = new JButton("로그인");
		btnLogin.setForeground(Color.BLACK);
		
		//옵션
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		lblTitle.setFont(new Font("Hannotate TC", Font.BOLD, 28));
		lblTitle.setBounds(113, 70, 151, 40);
		lblID.setBounds(60, 157, 61, 16);
		lblPassword.setBounds(60, 223, 69, 16);
		lblSignUp.setBounds(165, 401, 47, 16);
		lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblSignUp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new SignUp().setVisible(true);
				setVisible(false);
		    }
		});
		
		tFieldID.setColumns(10);
		tFieldID.setBounds(60, 176, 250, 35);
		tFieldPassword.setColumns(10);
		tFieldPassword.setBounds(60, 242, 250, 35);
		
		btnLogin.addActionListener(this);
		btnLogin.setBounds(123, 317, 130, 29);
		
		// 컨테이너에 컴포넌트 삽입
		contentPane.add(lblTitle);	
		contentPane.add(lblID);
		contentPane.add(lblPassword);
		contentPane.add(tFieldID);
		contentPane.add(tFieldPassword);	
		contentPane.add(btnLogin);
		contentPane.add(lblSignUp);
		
		setContentPane(contentPane);
	}

	public static String getUserName() {
		return userName;
	}
	
	public void logIn() {
		id = tFieldID.getText();
		password = tFieldPassword.getText();
		try {
			String query = "SELECT * FROM manager";
			dbConnect();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			if (!rs.isBeforeFirst() ) {	// 커서가 첫 번째 행 앞에 있으면 true, 커서가 그 외의 위치에 있거나 결과 집합에 행이 들어 있지 않으면 false.
				showMessage.showMessageDialog(null, "로그인 실패2", "알림", showMessage.PLAIN_MESSAGE);	//알림창
				return;
			}
			while(rs.next()) {	//getString 메소드는 next() 호출 이후에 사용가능함. next()메소드는 호출될 때마다 커서가 이동함
				if(id.equals(rs.getString("m_id")) && password.equals(rs.getString("m_pwd"))) {
					showMessage.showMessageDialog(null, "로그인 성공", "알림", showMessage.PLAIN_MESSAGE);
					userName = rs.getString("m_name");
					MainScreen m = new MainScreen();
					m.setVisible(true);
					setVisible(false);
					return;
				}
			}
			showMessage.showMessageDialog(null, "아이디나 비밀번호를 확인하세요!", "알림", showMessage.PLAIN_MESSAGE);
			dbDis();
		} catch (Exception ex) {
			System.out.println("뭐가 문제야");
			ex.printStackTrace();
		}
	}
	
	public void dbConnect() {
		//드라이버 검색
		try{
			Class.forName("com.mysql.jdbc.Driver");
	        System.out.println("드라이버 검색 성공!");     
	    }catch(ClassNotFoundException e) {
	        System.err.println("error = " + e);
	    }
		try {
			String url = "jdbc:mysql://localhost/management_db";
			conn = DriverManager.getConnection(url,"root","1234"); 	
            System.out.println("데이터베이스 연결 성공!");
        }
        catch(Exception e) {
        	System.out.println("데이터베이스 연결 실패!");
        }
	}
	
	public void dbDis(){
		try {
			if (conn != null)
				conn.close();
			if (pstmt != null)
				pstmt.close();
			System.out.println("데이터베이스 연결해제!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 로그인 버튼
		if(e.getSource() == btnLogin) {
			logIn();
		}
	}
	
}
