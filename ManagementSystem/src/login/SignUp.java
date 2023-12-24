package login;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SignUp extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	private JLabel signStoreName;
	private JLabel signID;
	private JLabel signPassword;
	private JLabel signCheckPassword;
	private JLabel signName;
	private JLabel signBirth;
	private JLabel signGender;
	private JLabel signEMail;
	private JLabel signNumber;
	
	private JTextField tFieldStoreName;
	private JTextField tFieldSignID;
	private JTextField tFieldName;
	private JTextField tFieldNumber;
	private JTextField tFieldEMail;
	private JPasswordField tFieldSignPwd;
	private JPasswordField tFieldSignCPwd;
	
	private JComboBox comboBoxYear;
	private JComboBox comboBoxMonth;
	private JComboBox comboBoxDay;
	
	private JRadioButton rdbtnBoy;
	private JRadioButton rdbtnGirl;
	private ButtonGroup group;
	
	private JButton btnSameName;
	private JButton btnSignUp;
	private JButton btnBack;
	
	private JOptionPane showMessage;
	
	static Boolean idValid = false;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	//생성자 초기화
	public SignUp() {
		// JFrame 설정
		setTitle("사용자 추가");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(370,500);
		setLocationRelativeTo(null);	// 화면 중앙 위치
		setResizable(false);			// 사이즈 변경 불가 
		
		//컴포넌트 객체 생성
		contentPane = new JPanel();
		signStoreName= new JLabel("점포 이름");
		signStoreName.setForeground(Color.WHITE);
		signID = new JLabel("아이디");
		signID.setForeground(Color.WHITE);
		signPassword = new JLabel("비밀번호");
		signPassword.setForeground(Color.WHITE);
		signCheckPassword = new JLabel("비밀번호 확인");
		signCheckPassword.setForeground(Color.WHITE);
		signName = new JLabel("점주 성함");
		signName.setForeground(Color.WHITE);
		signBirth = new JLabel("생년월일");
		signBirth.setForeground(Color.WHITE);
		signGender = new JLabel("성별");
		signGender.setForeground(Color.WHITE);
		signEMail = new JLabel("E-mail");
		signEMail.setForeground(Color.WHITE);
		signNumber = new JLabel("휴대폰 번호");
		signNumber.setForeground(Color.WHITE);
		
		tFieldSignID = new JTextField();
		tFieldStoreName = new JTextField();
		tFieldName= new JTextField();
		tFieldEMail = new JTextField();
		tFieldNumber = new JTextField();
		tFieldSignCPwd = new JPasswordField();
		tFieldSignPwd = new JPasswordField();
		
		comboBoxYear = new JComboBox();
		comboBoxMonth = new JComboBox();
		comboBoxDay = new JComboBox();
		
		rdbtnBoy = new JRadioButton("남");
		rdbtnBoy.setForeground(Color.WHITE);
		rdbtnGirl = new JRadioButton("여");
		rdbtnGirl.setForeground(Color.WHITE);
		group = new ButtonGroup();
		
		btnSameName = new JButton("중복확인");
		btnSameName.setForeground(Color.BLACK);
		btnSignUp = new JButton("가입하기");
		btnSignUp.setForeground(Color.BLACK);
		btnBack = new JButton("뒤로 가기");
		btnBack.setForeground(Color.BLACK);
		
		// 컴포넌트 옵션 세팅
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);	
		
		signStoreName.setBounds(40, 40, 61, 16);
		signID.setBounds(40, 80, 61, 16);
		signPassword.setBounds(40, 120, 61, 16);
		signCheckPassword.setBounds(40, 160, 73, 16);
		signName.setBounds(40, 200, 61, 16);
		signBirth.setBounds(40, 240, 61, 16);
		signGender.setBounds(40, 280, 61, 16);
		signEMail.setBounds(40, 320, 61, 16);
		signNumber.setBounds(40, 360, 61, 16);
		
		tFieldStoreName.setColumns(10);
		tFieldStoreName.setBounds(125, 35, 130, 26);
		tFieldSignID.setColumns(10);
		tFieldSignID.setBounds(125, 75, 130, 26);
		tFieldSignPwd.setColumns(10);
		tFieldSignPwd.setBounds(125, 115, 130, 26);
		tFieldSignCPwd.setColumns(10);
		tFieldSignCPwd.setBounds(125, 155, 130, 26);
		tFieldName.setColumns(10);
		tFieldName.setBounds(125, 195, 130, 26);
		tFieldEMail.setColumns(10);
		tFieldEMail.setBounds(125, 315, 223, 26);
		tFieldNumber.setColumns(10);
		tFieldNumber.setBounds(125, 355, 130, 26);
	
		comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {"-", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990"}));
		comboBoxYear.setBounds(125, 236, 95, 27);
		comboBoxMonth.setModel(new DefaultComboBoxModel(new String[] {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBoxMonth.setBounds(216, 236, 69, 27);
		comboBoxDay.setModel(new DefaultComboBoxModel(new String[] {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		comboBoxDay.setBounds(280, 236, 69, 27);
		
		rdbtnBoy.setSelected(true);
		rdbtnBoy.setBounds(124, 276, 51, 23);
		rdbtnGirl.setBounds(177, 276, 51, 23);
		group.add(rdbtnBoy);
		group.add(rdbtnGirl);
		
		btnSameName.addActionListener(this);
		btnSameName.setBounds(253, 75, 107, 29);
		btnSignUp.addActionListener(this);
		btnSignUp.setBounds(189, 407, 130, 29);
		btnBack.addActionListener(this);
		btnBack.setBounds(57, 407, 130, 29);
		
		// 컨테이너에 컴포넌트 삽입
		contentPane.add(signStoreName);
		contentPane.add(signID);
		contentPane.add(signPassword);
		contentPane.add(signCheckPassword);
		contentPane.add(signName);
		contentPane.add(signBirth);
		contentPane.add(signGender);
		contentPane.add(signEMail);
		contentPane.add(signNumber);
		contentPane.add(tFieldStoreName);
		contentPane.add(tFieldSignID);
		contentPane.add(tFieldSignPwd);
		contentPane.add(tFieldSignCPwd);
		contentPane.add(tFieldName);
		contentPane.add(tFieldEMail);
		contentPane.add(tFieldNumber);
		contentPane.add(comboBoxYear);
		contentPane.add(comboBoxMonth);
		contentPane.add(comboBoxDay);
		contentPane.add(rdbtnBoy);
		contentPane.add(rdbtnGirl);
		contentPane.add(btnSameName);
		contentPane.add(btnSignUp);
		contentPane.add(btnBack);
	
		setContentPane(contentPane);
	}
	
	public void sameName() {
		try {
			String query = String.format("SELECT m_id FROM manager");
			dbConnect();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			String ID = tFieldSignID.getText();
			// 레코드가 존재하지 않을 경우를 대비해 설정
			if(!rs.isBeforeFirst()) { //중첩하는 것보다 return이 보기 편함
				showMessage.showMessageDialog(null, "회원 정보가 존재하지 않음", "알림", showMessage.PLAIN_MESSAGE);	
				return;
			}
			// 텍스트필드에 입력값이 없는 상태에서 버튼을 눌렀을 경우 메세지 출력과 강제종료
			if(ID.equals("")) {
				showMessage.showMessageDialog(null, "아이디를 입력하세요!", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			// 중복되는 아이디가 존재하는지 반복문을 통해 하나씩 확인
			while(rs.next()) {
				if(ID.equals(rs.getString("m_id"))) { 
					showMessage.showMessageDialog(null, "중복되는 아이디가 존재합니다.", "알림", showMessage.PLAIN_MESSAGE);	
					return;
				}
			}
			showMessage.showMessageDialog(null, "사용가능한 아이디입니다.", "알림", showMessage.PLAIN_MESSAGE);	
			idValid = true;
			dbDis();
			
		}catch (Exception ex) {
			System.out.println("뭐가 문제야");
			ex.printStackTrace();
		}
	}
	
	public void signUp() {
		try {
			//컴포넌트들로부터 값을 가져옴
			String tf1 = tFieldStoreName.getText();
			String tf2 = tFieldSignID.getText();
			String tf3 = tFieldSignPwd.getText();
			String tf4 = tFieldName.getText();
			String cb1 = comboBoxYear.getSelectedItem().toString();
			String cb2 = comboBoxMonth.getSelectedItem().toString();
			String cb3 = comboBoxDay.getSelectedItem().toString();
			String tf5 = tFieldEMail.getText();
			String tf6 = tFieldNumber.getText();
			String genderText;	//디폴트로 남으로 선택되어있음(위쪽 소스 참고). 남으로 선택되어있을 때 남, 아니면 여
			if(rdbtnBoy.isSelected()) {
				genderText = rdbtnBoy.getText();
			}else {
				genderText = rdbtnGirl.getText();
			}
			
			Boolean tf1B = tf1.equals("");	//공백일 경우 true
			Boolean tf2B = tf2.equals("");
			Boolean tf3B = tf3.equals("");
			Boolean tf4B = tf4.equals("");
			Boolean cb1B = cb1.equals("-");
			Boolean cb2B = cb2.equals("-");
			Boolean cb3B = cb3.equals("-");
			Boolean tf5B = tf5.equals("");
			Boolean tf6B = tf6.equals("");
			
			//공백이 하나라도 존재할 경우 가입못하게 만들기
			if(tf1B || tf2B || tf3B || tf4B || cb1B || cb2B || cb3B || tf5B || tf6B) {
				showMessage.showMessageDialog(null, "모든 정보를 입력해야 가입할 수 있습니다.", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			//아이디확인. 확인버튼을 클릭하지 않으면 가입 안됨(중복 안되게 만들기). idValid는 false값으로 시작하는데 사용가능한 아이디 창이 떠야 idValid가 true가 되면서 이 조건문은 넘어가게 됨.
			if(!idValid) {	
				showMessage.showMessageDialog(null, "아이디 중복을 확인하세요!", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			System.out.println(tFieldSignPwd.getText());
			System.out.println(tFieldSignCPwd.getText());

			if(!(tFieldSignPwd.getText()).equals(tFieldSignCPwd.getText())) { // String비교는 equals메소드로 하자...
				showMessage.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			String query = "INSERT INTO manager(store_name, m_id, m_pwd, m_name, m_birth, m_gender, m_email, m_phone)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			System.out.println(query);
			dbConnect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tf1);
			pstmt.setString(2, tf2);
			pstmt.setString(3, tf3);
			pstmt.setString(4, tf4);
			pstmt.setString(5, cb1+"-"+cb2+"-"+cb3);
			pstmt.setString(6, genderText);
			pstmt.setString(7, tf5);
			pstmt.setString(8, tf6);
			pstmt.executeUpdate();
		
			showMessage.showMessageDialog(null, "가입이 완료되었습니다.", "알림", showMessage.PLAIN_MESSAGE);
			new LoginPage().setVisible(true);
			setVisible(false);
			
			dbDis();
			
		} catch (Exception e1) {
			e1.printStackTrace();
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
		// 중복확인 버튼
		if(e.getSource() == btnSameName) {
			sameName();
		}
		// 회원가입 버튼
		if(e.getSource() == btnSignUp) {
			signUp();
		}
		//뒤로가기 버튼
		if(e.getSource() == btnBack) {
			new LoginPage().setVisible(true);
		}
	}
}
