package sell;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import main.MainScreen;

public class SellScreen extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panel_Top;
	private JPanel panel_Center;
	private JPanel panel_Left;
	private JPanel panel_Right;
	private JPanel panel_Buttons;
	
	private JLabel lblSetBCode;
	private JLabel lblCount1;
	private JLabel lblCount2;
	private JLabel lblAllPrice1;
	private JLabel lblAllPrice2;
	
	private JTextField tFieldBcode;
	
	private JButton btnBack;
	private JButton btnCash;
	private JButton btnCard;
	private JButton btnCoupon;
	
	private JOptionPane showMessage;
	
	private JScrollPane scrollpane;
	private JTable table;
	private DefaultTableModel model;
	private String header[] = {"바코드", "의류명", "가격", "사이즈", "색상", "카테고리", "개수"};
	private Object contents[][];

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private int count = 0;
	private int price = 0;

	private List<String> listBarcode = new ArrayList<String>();
	private List<String> listP_name = new ArrayList<String>();
	private List<String> listPrice = new ArrayList<String>();
	private List<String> listSize = new ArrayList<String>();
	private List<String> listColor = new ArrayList<String>();
	private List<String> listCategory = new ArrayList<String>();
	private JLabel lblCount2_1;
	
	private String userName;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellScreen frame = new SellScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SellScreen() {
		//JFrame 설정
		setTitle("상품 판매");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,700);
		setLocationRelativeTo(null); 
		setResizable(false);
		
		//컴포넌트 객체 선언
		contentPane = new JPanel();
		panel_Top = new JPanel();
		panel_Center = new JPanel();
		panel_Left = new JPanel();
		panel_Right = new JPanel();
		panel_Buttons = new JPanel();
		
		lblSetBCode = new JLabel("Scan the Barcode");
		lblSetBCode.setForeground(Color.WHITE);
		lblCount1 = new JLabel("0");
		lblCount1.setForeground(Color.WHITE);
		lblCount1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblCount2 = new JLabel("");
		lblCount2.setForeground(Color.WHITE);
		lblCount2.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblAllPrice1 = new JLabel("");
		lblAllPrice1.setForeground(Color.WHITE);
		lblAllPrice1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblAllPrice2 = new JLabel("원");
		lblAllPrice2.setForeground(Color.WHITE);
		lblAllPrice2.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblCount2_1 = new JLabel("개");
		lblCount2_1.setForeground(Color.WHITE);
		
		tFieldBcode = new JTextField();
		
		btnBack = new JButton("메인화면");
		btnBack.setForeground(Color.BLACK);
		btnCash = new JButton("현금");
		btnCash.setBackground(Color.ORANGE);
		btnCard = new JButton("카드");
		btnCard.setBackground(new Color(152, 194, 231));
		btnCoupon = new JButton("쿠폰");
		btnCoupon.setBackground(Color.PINK);
		
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_Top.setBackground(Color.GRAY);
		panel_Top.setLayout(new BoxLayout(panel_Top, BoxLayout.X_AXIS));
		panel_Center.setLayout(new BoxLayout(panel_Center, BoxLayout.X_AXIS));
		
		panel_Left.setPreferredSize(new Dimension(350,400));
		panel_Left.setLayout(null);
		panel_Left.setBackground(Color.DARK_GRAY);
		panel_Left.setBorder(new TitledBorder(new LineBorder(Color.gray,2),"상품 판매"));
		panel_Right.setBackground(Color.DARK_GRAY);
		panel_Right.setLayout(null);
		panel_Right.setBorder(new TitledBorder(new LineBorder(Color.gray,2),"상품 정보"));
		
		panel_Buttons.setBounds(30, 418, 600, 178);
		panel_Buttons.setLayout(new GridLayout(1, 0, 0, 0));
		panel_Buttons.setBackground(Color.LIGHT_GRAY);
		
		lblSetBCode.setBounds(117, 57, 108, 16);
		lblCount1.setBounds(173, 201, 50, 45);
		lblCount2.setBounds(120, 386, 126, 210);
		lblAllPrice1.setBounds(14, 299, 211, 54);
		lblAllPrice2.setBounds(237, 299, 35, 54);
		lblCount2_1.setFont(new Font("Lucida Grande", Font.PLAIN, 40));
		lblCount2.setIcon(new ImageIcon(getClass().getResource("/image/image4.png")));
		lblCount2_1.setBounds(236, 196, 35, 54);
		
		tFieldBcode.setBounds(43, 109, 239, 26);
		tFieldBcode.setColumns(10);
	
		addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		    	tFieldBcode.requestFocus();
		    }
		}); 
		tFieldBcode.addKeyListener(new KeyAdapter() {

		      public void keyPressed(KeyEvent e) {
		    	  tFieldBcode.requestFocus();
		      }
		      public void keyTyped(KeyEvent e) {
		    	  if(tFieldBcode.getText().length() == 8)
		    		  tableSelect();
		      }
		    });
		model= new DefaultTableModel(contents, header) {	// 	더블클릭으로 내용 수정 불가로 설정해줌
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.setFillsViewportHeight(true); //컨테이너의 전체 높이 테이블이 전부 사용하도록 설정한다.
		table.getTableHeader().setReorderingAllowed(false);	// 열 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 열 넓이 조절 불가 
		tableColumnSize();
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(30, 30, 600, 360);
		
		btnBack.addActionListener(this);
		btnCash.addActionListener(this);
		btnCash.setOpaque(true);
		btnCash.setBorderPainted(false);
		btnCard.addActionListener(this);
		btnCard.setOpaque(true);
		btnCard.setBorderPainted(false);
		btnCoupon.addActionListener(this);
		btnCoupon.setOpaque(true);
		btnCoupon.setBorderPainted(false);
		
		btnCash.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		btnCard.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		btnCoupon.setFont(new Font("Lucida Grande", Font.BOLD, 30));
		
		//add
		panel_Left.add(scrollpane);
		panel_Top.add(btnBack);
		panel_Center.add(panel_Left);
		panel_Left.add(panel_Buttons);
		panel_Buttons.add(btnCash);
		panel_Buttons.add(btnCard);
		panel_Buttons.add(btnCoupon);
		panel_Center.add(panel_Right);
		panel_Right.add(lblSetBCode);
		panel_Right.add(tFieldBcode);
		panel_Right.add(lblCount1);
		panel_Right.add(lblCount2);
		panel_Right.add(lblAllPrice1);
		panel_Right.add(lblAllPrice2);
		panel_Right.add(lblCount2_1);
		
		contentPane.add(panel_Top, BorderLayout.NORTH);
		contentPane.add(panel_Center, BorderLayout.CENTER);
		
		setContentPane(contentPane);
		
		MainScreen ms = new MainScreen();
		userName = ms.getUserName();
		System.out.println(userName);
	}
	
	public void tableColumnSize() {
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(20);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);
	}
	
	public void tableSelect() {
		try {
			String gt = tFieldBcode.getText();
			if(gt.equals("")) {
				showMessage.showMessageDialog(null, "바코드가 입력되지 않았습니다.", "알림", showMessage.PLAIN_MESSAGE);
				tFieldBcode.requestFocus();
				return;
			}
			int countPlus = 1;
			
			table.getColumnModel().setColumnMargin(count);
			String query = "select * from product where id = ?";
			dbConnect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gt);
			rs = pstmt.executeQuery();
			while(rs.next()) {	//각각 값을 가져와 테이블값들을 추가
				String gs1 = rs.getString("id");
				String gs2 = rs.getString("p_name");
				String gs3 = rs.getString("price");
				String gs4 = rs.getString("size");
				String gs5 = rs.getString("color");
				String gs6 = rs.getString("category");
				
				model.addRow(new Object[] {gs1, gs2, gs3, gs4, gs5, gs6, countPlus});
				
				listBarcode.add(gs1);
				listP_name.add(gs2);
				listPrice.add(gs3);
				listSize.add(gs4);
				listColor.add(gs5);
				listCategory.add(gs6);
				
				count++;
				price += rs.getInt("price");
				
			}
			lblCount1.setText(Integer.toString(count));
			lblAllPrice1.setText(Integer.toString(price));
			tFieldBcode.setText("");
			tFieldBcode.requestFocus();
			dbDis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getManagerPk() {
		dbConnect();

		String pk_id = "";
		String mQuery = "select id from manager where m_name = ?";
		System.out.println(userName);
		try {
			pstmt = conn.prepareStatement(mQuery);
			pstmt.setString(1, userName); 
			rs = pstmt.executeQuery();
			while(rs.next()) {
				pk_id = rs.getString("id");
				System.out.println(pk_id);
			}
			
		} catch (Exception e) {
			System.out.println("??");
			e.printStackTrace();
		}
		dbDis();
		return pk_id;
	}
	
	public void sellCash() {
		try {
			if(count == 0) {
				showMessage.showMessageDialog(null, "바코드를 찍어주세요.", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			//상품 등록
			String pk_id = getManagerPk();
			System.out.println("count : "+count);
			
			Random rand = new Random();
			int tNum = rand.nextInt(8999) + 1000; // 1000 ~ 9998 숫자 랜덤 생성
			
			dbConnect();
			String query = "INSERT INTO transaction(id, total_price, count_sale, payment, m_num) VALUES(?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, Integer.toString(tNum));
			pstmt.setString(2, Integer.toString(price));
			pstmt.setString(3, Integer.toString(count));
			pstmt.setString(4, "현금");
			pstmt.setString(5, pk_id);
			pstmt.executeUpdate();
			System.out.println("거래 항목이 저장되었습니다.");
			dbDis();
			
			//관계 테이블에 값 저장
			
			for (int i = 0; i < count; i++) {
				dbConnect();
				String query2 = "INSERT INTO relation(barcode, trans_id) VALUES(?,?)";
				pstmt = conn.prepareStatement(query2);
				pstmt.setString(1, listBarcode.get(i));
				pstmt.setString(2, Integer.toString(tNum));
				
				pstmt.executeUpdate();
				System.out.println("관계 테이블에 기록되었습니다.");
				dbDis();
			}
			model.setNumRows(0);
			count = 0;
			price = 0;
			lblCount1.setText("0");
			lblAllPrice1.setText("");
			showMessage.showMessageDialog(null, "현금 결제 완료", "알림", showMessage.PLAIN_MESSAGE);
			
		} catch (Exception e2) {
			System.out.println("여긴가?");
			e2.printStackTrace();
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
		if(e.getSource() == btnBack) {
			new MainScreen().setVisible(true);
			setVisible(false);
		}
		if(e.getSource() == btnCash) {
			sellCash();
		}
		if(e.getSource() == btnCard) {
			showMessage.showMessageDialog(null, "카드 결제 완료", "알림", showMessage.PLAIN_MESSAGE);
		}
		if(e.getSource() == btnCoupon) {
			showMessage.showMessageDialog(null, "쿠폰 사용 완료", "알림", showMessage.PLAIN_MESSAGE);
		}
	}
}
