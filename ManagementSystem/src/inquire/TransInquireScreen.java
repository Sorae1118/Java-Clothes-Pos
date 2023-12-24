package inquire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import main.MainScreen;
import java.awt.Font;

public class TransInquireScreen extends JFrame implements ActionListener, MouseListener{

	private JPanel contentPane;
	private JPanel panel_Top;
	private JPanel panel_Center;
	private JPanel panel_Left;
	private JPanel panel_Right;
	
	private JLabel lblCount;
	private JLabel lblCount1;
	private JLabel lblTotalPrice;
	private JLabel lblTotalPrice1;
	
	private JButton btnBack;
	private JButton btnTestSaerch;
	
	private JScrollPane scrollpane;
	private JTable table;
	private DefaultTableModel model;
	private String header[] = {"번호", "거래번호", "총 금액", "개수", "결제방식", "거래 매니저, 거래시간"};
	private Object contents[][];
	
	private JScrollPane scrollpane2;
	private JTable table2;
	private DefaultTableModel model2;
	private String header2[] = {"바코드", "의류명", "가격", "사이즈", "색상", "카테고리"};
	private Object contents2[][];
	private int getRow;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransInquireScreen frame = new TransInquireScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TransInquireScreen() {
		setTitle("판매 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		// 생성자 호출
		contentPane = new JPanel();
		panel_Top = new JPanel();
		panel_Top.setBackground(Color.GRAY);
		panel_Center = new JPanel();
		panel_Left = new JPanel();
		panel_Left.setBackground(Color.DARK_GRAY);
		panel_Right = new JPanel();
		panel_Right.setForeground(Color.WHITE);
		panel_Right.setBackground(Color.DARK_GRAY);
		
		lblCount = new JLabel("판매 개수 :");
		lblCount.setForeground(Color.WHITE);
		lblCount.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblCount1 = new JLabel("_");
		lblCount1.setForeground(Color.WHITE);
		lblTotalPrice = new JLabel("판매 총액 : ");
		lblTotalPrice.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblTotalPrice.setForeground(Color.WHITE);
		lblTotalPrice1 = new JLabel("_");
		lblTotalPrice1.setForeground(Color.WHITE);
		
		btnBack = new JButton("메인 화면");
		btnTestSaerch = new JButton("검색");
		
		// 컴포넌트 설정
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel_Top, BorderLayout.NORTH);
		contentPane.add(panel_Center, BorderLayout.CENTER);
		
		panel_Top.setLayout(new BoxLayout(panel_Top, BoxLayout.X_AXIS));
		panel_Center.setLayout(new BoxLayout(panel_Center, BoxLayout.X_AXIS));
		panel_Left.setBorder(new TitledBorder(new LineBorder(Color.gray, 2), "판매 내역"));
		panel_Right.setBorder(new TitledBorder(new LineBorder(Color.gray, 2), "상세 정보"));
		
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
		table.addMouseListener(this);
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(20, 50, 455, 536);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		
		lblCount.setBounds(52, 395, 99, 44);
		lblCount1.setBounds(163, 398, 85, 44);
		lblTotalPrice.setBounds(52, 451, 99, 44);
		lblTotalPrice1.setBounds(163, 454, 220, 44);
		
		btnTestSaerch.setBounds(358, 16, 117, 29);
		btnTestSaerch.addActionListener(this);
		btnBack.addActionListener(this);
		
		model2= new DefaultTableModel(contents2, header2) {	// 	더블클릭으로 내용 수정 불가로 설정해줌
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table2 = new JTable(model2);
		table2.setFillsViewportHeight(true); //컨테이너의 전체 높이 테이블이 전부 사용하도록 설정한다.
		table2.getTableHeader().setReorderingAllowed(false);	// 열 이동 불가
		table2.getTableHeader().setResizingAllowed(false); // 열 넓이 조절 불가 
		scrollpane2 = new JScrollPane(table2);
		scrollpane2.setBounds(20, 50, 455, 300);
		
		//add
		panel_Top.add(btnBack);
		panel_Center.add(panel_Left);
		panel_Center.add(panel_Right);
		panel_Left.setLayout(null);
		panel_Left.add(scrollpane);
		panel_Left.add(btnTestSaerch);
		panel_Right.setLayout(null);
		panel_Right.add(scrollpane2);
		panel_Right.add(lblCount);
		panel_Right.add(lblCount1);
		panel_Right.add(lblTotalPrice);
		panel_Right.add(lblTotalPrice1);
		
		setContentPane(contentPane);
	}
	
	public void transaction() {
		try {
			model.setNumRows(0);
			dbConnect();
			String query2 = "select t.id, t.total_price, t.count_sale, t.payment, m.m_name, t.created_at "
					+ "from transaction t inner join manager m on t.m_num = m.id order by t.created_at;";
			pstmt = conn.prepareStatement(query2);
			rs = pstmt.executeQuery();
			int count = 1;
			while(rs.next()) {	//각각 값을 가져와 테이블값들을 추가
				model.addRow(new Object[] {
						count++,
						rs.getString("t.id"),
						rs.getString("t.total_price"),
						rs.getString("t.count_sale"),
						rs.getString("t.payment"),
						rs.getString("m.m_name"),
						rs.getString("t.created_at")
				   });
			}
			dbDis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void searchTrans() {
		model2.setNumRows(0);
		getRow = table.getSelectedRow();
		String value = table.getModel().getValueAt(getRow, 1).toString();
		System.out.println(getRow);
		System.out.println(value);
		String query = "select * from product where id in "
				+"( select barcode from relation where trans_id = ?)";
		try {
			int price = 0;
			lblCount1.setText(Integer.toString(0));
			lblTotalPrice1.setText(Integer.toString(price));
			dbConnect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				model2.addRow(new Object[] {
						rs.getString("id"),
						rs.getString("p_name"),
						rs.getString("price"),
						rs.getString("size"),
						rs.getString("color"),
						rs.getString("category")
				   });
				price += rs.getInt("price");
			}
			lblCount1.setText(Integer.toString(model2.getRowCount()));
			lblTotalPrice1.setText(Integer.toString(price));
			dbDis();
		} catch (Exception e) {
			e.printStackTrace();
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
		if(e.getSource() == btnTestSaerch) {
			transaction();
		}
		if(e.getSource() == btnBack) {
			new MainScreen().setVisible(true);
			setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getClickCount() == 2) {
			searchTrans();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
