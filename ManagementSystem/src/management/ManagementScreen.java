package management;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class ManagementScreen extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	private JPanel panel_Top;
	private JPanel panel_Center;
	private JPanel panel_Left;
	private JPanel panel_Right;
	private JPanel panel_Edit;
	private JPanel panel_Remove;
	
	private JLabel lblBarcode;
	private JLabel lblPName;
	private JLabel lblPrice;
	private JLabel lblInven;
	private JLabel lblSize;
	private JLabel lblColor;
	private JLabel lblCategory;
	private JLabel lblExplain;
	private JLabel lblEdit;
	private JLabel lblRemove;
	
	private JTextField tFieldBarcode;
	private JTextField tFieldPName;
	private JTextField tFieldPrice;
	private JTextField tFieldInven;
	private JTextField tFieldSize;
	private JTextField tFieldColor;
	private JTextField tFieldCategory;
	private JTextField tFieldSearch;
	private JTextField tFieldEdit;
	
	private JButton btnBack;
	private JButton btnRegister;
	private JButton btnSearch;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnSearchAll;
	
	private JComboBox cbEdit;

	private JScrollPane scrollpane;
	private JTable table;
	private DefaultTableModel model;
	private String header[] = { "바코드", "의류명", "가격", "재고량", "사이즈", "색상", "카테고리" };
	private Object contents[][];

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private JOptionPane showMessage;
	

	private List<String> testList = new ArrayList<String>(); // 계속 추가할 수 있는 리스트 선언
	private int arrayIndex;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagementScreen frame = new ManagementScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ManagementScreen() {
		// JFrame 설정
		setTitle("상품 관리");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);

		// 컴포넌트 객체 생성
		contentPane = new JPanel();
		panel_Top = new JPanel();
		panel_Center = new JPanel();
		panel_Left = new JPanel();
		panel_Right = new JPanel();
		panel_Edit = new JPanel();
		panel_Remove = new JPanel();
		
		lblExplain = new JLabel("<html>설명 : 바코드 형식은 8자입니다.<br>모든 정보를 입력해야 상품을 등록할 수 있고,<br>수정, 삭제는 우측 페이지를 이용하세요.</html>");
		lblBarcode = new JLabel("바코드");
		lblPName = new JLabel("상품명");
		lblPrice = new JLabel("가격");
		lblInven = new JLabel("재고량");
		lblSize = new JLabel("사이즈");
		lblColor = new JLabel("색상");
		lblCategory = new JLabel("카테고리");
		lblEdit = new JLabel("항목 선택 ");
		lblRemove = new JLabel("* 검색된 정보를 삭제할 수 있습니다.");
		
		tFieldBarcode = new JTextField();
		tFieldPName = new JTextField();
		tFieldPrice = new JTextField();
		tFieldInven = new JTextField();
		tFieldSize = new JTextField();
		tFieldColor = new JTextField();
		tFieldCategory = new JTextField();
		tFieldEdit = new JTextField();
		tFieldSearch = new JTextField();
		
		btnBack = new JButton("메인화면");
		btnRegister = new JButton("상품 등록");
		btnEdit = new JButton("수정");
		btnSearch = new JButton("검색");
		btnRemove = new JButton("삭제");
		btnSearchAll = new JButton("모든 상품 검색");
		
		cbEdit = new JComboBox();
		// 컴포넌트 설정
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.DARK_GRAY);
		
		panel_Top.setBackground(Color.LIGHT_GRAY);
		panel_Top.setLayout(new BoxLayout(panel_Top, BoxLayout.X_AXIS));
		panel_Center.setBackground(new Color(177, 180, 233));
		panel_Center.setLayout(new BoxLayout(panel_Center, BoxLayout.X_AXIS));
		panel_Left.setBackground(Color.GRAY);
		panel_Left.setLayout(null);
		panel_Left.setBorder(new TitledBorder(new LineBorder(Color.gray, 2), "상품 추가"));
		panel_Right.setBackground(Color.GRAY);
		panel_Right.setLayout(null);
		panel_Right.setPreferredSize(new Dimension(300, 300));
		panel_Right.setBorder(new TitledBorder(new LineBorder(Color.gray, 2), "등록된 상품 조회"));
		panel_Edit.setBackground(Color.LIGHT_GRAY);
		panel_Edit.setBounds(30, 396, 585, 87);
		panel_Edit.setLayout(null);
		panel_Remove.setLayout(null);
		panel_Remove.setBackground(Color.LIGHT_GRAY);
		panel_Remove.setBounds(30, 506, 585, 87);
		
		lblBarcode.setBounds(60, 200, 61, 16);
		lblPName.setBounds(60, 250, 61, 16);
		lblPrice.setBounds(60, 300, 61, 16);
		lblInven.setBounds(60, 350, 61, 16);
		lblSize.setBounds(60, 400, 61, 16);
		lblColor.setBounds(60, 450, 61, 16);
		lblCategory.setBounds(60, 500, 61, 16);
		lblExplain.setBounds(60, 66, 239, 87);
		lblEdit.setBounds(33, 37, 52, 16);
		lblRemove.setBounds(36, 34, 184, 16);
		
		tFieldBarcode.setColumns(8);
		tFieldBarcode.setBounds(135, 195, 130, 26);
		tFieldPName.setColumns(50);
		tFieldPName.setBounds(135, 245, 130, 26);
		tFieldPrice.setColumns(50);
		tFieldPrice.setBounds(135, 295, 130, 26);
		tFieldInven.setColumns(50);
		tFieldInven.setBounds(135, 345, 130, 26);
		tFieldSize.setColumns(50);
		tFieldSize.setBounds(135, 395, 130, 26);
		tFieldColor.setColumns(50);
		tFieldColor.setBounds(135, 445, 130, 26);
		tFieldCategory.setColumns(50);
		tFieldCategory.setBounds(135, 495, 130, 26);
		tFieldEdit.setColumns(50);
		tFieldEdit.setBounds(237, 32, 206, 26);
		tFieldSearch.setColumns(50);
		tFieldSearch.setBounds(165, 32, 273, 26);
		
		btnBack.addActionListener(this);
		btnRegister.addActionListener(this);
		btnRegister.setBounds(135, 555, 130, 29);
		btnEdit.setBounds(455, 32, 100, 29);
		btnEdit.addActionListener(this);
		btnSearch.setBounds(554, 29, 61, 29);
		btnSearch.addActionListener(this);
		btnRemove.setBounds(455, 29, 100, 29);
		btnRemove.addActionListener(this);
		btnSearchAll.setBounds(440, 29, 117, 29);
		btnSearchAll.addActionListener(this);

		model = new DefaultTableModel(contents, header) { // 더블클릭으로 내용 수정 불가로 설정해줌
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(400, 300)); // ?? 공부 필
		table.setFillsViewportHeight(true); // 컨테이너의 전체 높이 테이블이 전부 사용하도록 설정한다.
		table.getTableHeader().setReorderingAllowed(false); // 열 이동 불가
		table.getTableHeader().setResizingAllowed(false); // 열 넓이 조절 불가
		tableColumnSize();
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(30, 70, 585, 300);
		
		cbEdit.setModel(new DefaultComboBoxModel(new String[] { "-", "바코드", "의류명", "가격", "재고량", "사이즈", "색상", "카테고리" }));
		cbEdit.setBounds(97, 33, 113, 27);
		// 추가
		panel_Top.add(btnBack);
		panel_Center.add(panel_Left);
		panel_Center.add(panel_Right);

		panel_Left.add(lblBarcode);
		panel_Left.add(lblPName);
		panel_Left.add(lblPrice);
		panel_Left.add(lblInven);
		panel_Left.add(lblSize);
		panel_Left.add(lblColor);
		panel_Left.add(lblCategory);
		panel_Left.add(tFieldBarcode);
		panel_Left.add(tFieldPName);
		panel_Left.add(tFieldPrice);
		panel_Left.add(tFieldInven);
		panel_Left.add(tFieldSize);
		panel_Left.add(tFieldColor);
		panel_Left.add(tFieldCategory);
		panel_Left.add(btnRegister);
		panel_Left.add(lblExplain);

		panel_Right.add(scrollpane);
		panel_Right.add(panel_Edit);
		panel_Edit.add(lblEdit);
		panel_Edit.add(cbEdit);
		panel_Edit.add(btnEdit);
		panel_Edit.add(tFieldEdit);
		panel_Right.add(tFieldSearch);
		panel_Right.add(btnSearch);
		panel_Right.add(panel_Remove);
		panel_Remove.add(btnRemove);
		panel_Remove.add(lblRemove);
		panel_Right.add(btnSearchAll);
		
		contentPane.add(panel_Top, BorderLayout.NORTH);
		contentPane.add(panel_Center, BorderLayout.CENTER);

		setContentPane(contentPane);
	}

	public void tableColumnSize() {
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		table.getColumnModel().getColumn(6).setPreferredWidth(20);

	}
	public void register() {
		// 컴포넌트들로부터 값을 가져옴
		String tf1 = tFieldBarcode.getText();
		String tf2 = tFieldPName.getText();
		String tf3 = tFieldPrice.getText();
		String tf4 = tFieldInven.getText();
		String tf5 = tFieldSize.getText();
		String tf6 = tFieldColor.getText();
		String tf7 = tFieldCategory.getText();

		Boolean tf1B = tf1.equals(""); // 공백일 경우 true
		Boolean tf2B = tf2.equals("");
		Boolean tf3B = tf3.equals("");
		Boolean tf4B = tf4.equals("");
		Boolean tf5B = tf5.equals("");
		Boolean tf6B = tf6.equals("");
		Boolean tf7B = tf7.equals("");

		if (tf1B || tf2B || tf3B || tf4B || tf5B || tf6B || tf7B) {
			showMessage.showMessageDialog(null, "빈칸없이 입력하세요!", "알림", showMessage.PLAIN_MESSAGE);
			return;
		}
		try {
			String query = "INSERT INTO product(id, p_name, price, inventory, size, color, category)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			dbConnect();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, tf1);
			pstmt.setString(2, tf2);
			pstmt.setString(3, tf3);
			pstmt.setString(4, tf4);
			pstmt.setString(5, tf5);
			pstmt.setString(6, tf6);
			pstmt.setString(7, tf7);
			pstmt.executeUpdate();
			
			showMessage.showMessageDialog(null, "상품등록이 완료되었습니다.", "알림", showMessage.PLAIN_MESSAGE);
			dbDis();
		} catch (Exception e) {
			showMessage.showMessageDialog(null, "중복되는 바코드가 존재하거나 형식에 맞지 않습니다! \n정확하게 입력하십시오.", "알림", showMessage.PLAIN_MESSAGE);
			System.out.println("등록 오류");
			e.printStackTrace();
		}
		tFieldBarcode.setText("");
		tFieldPName.setText("");
		tFieldPrice.setText("");
		tFieldInven.setText("");
		tFieldSize.setText("");
		tFieldColor.setText("");
		tFieldCategory.setText("");
	}
	public void search() {
		try {
			String search = tFieldSearch.getText();
			String query = "select * from product";

			model.setNumRows(0); // 테이블 초기화
			testList.clear(); // 배열 내 요소 전부 clear
			arrayIndex = 0; // clear 됐기 때문에 인덱스도 0부터
			
			dbConnect();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			if (search.equals("")) {
				showMessage.showMessageDialog(null, "상품정보를 입력하세요!", "알림", showMessage.PLAIN_MESSAGE);
				return;
			}
			while (rs.next()) {
				if (search.equals(rs.getString("id")) || search.equals(rs.getString("price"))
						|| search.equals(rs.getString("inventory")) || search.equals(rs.getString("size"))
						|| search.equals(rs.getString("color")) || search.equals(rs.getString("category"))
						|| (rs.getString("p_name")).contains(search) // 앞에 문자열에 뒤 문자열이 포함될 때 true 반환
				) {
					model.addRow(new Object[] { 
							rs.getString("id"), 
							rs.getString("p_name"),
							rs.getString("price"), 
							rs.getString("inventory"), 
							rs.getString("size"),
							rs.getString("color"), 
							rs.getString("category") });
					testList.add(rs.getString("id"));
					System.out.println(testList); // 조회된바코드로 상품 수정 및 삭제하기
					arrayIndex++;
				}
			}
			dbDis();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	public void searchAll() {
		try {
			String query = "select * from product";
			model.setNumRows(0); // 테이블 초기화
			testList.clear(); // 배열 내 요소 전부 clear
			arrayIndex = 0; // clear 됐기 때문에 인덱스도 0부터
			
			dbConnect();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString("id"), 
						rs.getString("p_name"),
						rs.getString("price"),
						rs.getString("inventory"),
						rs.getString("size"),
						rs.getString("color"),
						rs.getString("category")});
				arrayIndex++;
			}
			dbDis();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void edit() {
		String cb = cbEdit.getSelectedItem().toString();
		String col = null;
		String tf = tFieldEdit.getText();
		if (cb.equals("-")) {
			showMessage.showMessageDialog(null, "수정할 항목을 선택하세요.", "알림", showMessage.PLAIN_MESSAGE);
			return;
		}
		if (tf.equals("")) {
			showMessage.showMessageDialog(null, "변경할 내용을 입력하세요,", "알림", showMessage.PLAIN_MESSAGE);
			return;
		}
		if (testList.isEmpty()) {
			showMessage.showMessageDialog(null, "검색된 상품이 없습니다!", "알림", showMessage.PLAIN_MESSAGE);
			return;
		}
		switch (cb) {
		case "바코드":
			col = "id";
			break;
		case "의류명":
			col = "p_name";
			break;
		case "가격":
			col = "price";
			break;
		case "재고량":
			col = "inventory";
			break;
		case "사이즈":
			col = "size";
			break;
		case "색상":
			col = "color";
			break;
		case "카테고리":
			col = "category";
			break;
		}
		dbConnect();
		for (int i = 0; i < arrayIndex; i++) {
			String bcode = testList.get(i);
			System.out.println(col);
			System.out.println(tf);
			System.out.println(bcode);
			String query = "update product set " + col + " = ? where id = ?"; 	
			try {
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, tf);
				pstmt.setString(2, bcode);
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		dbDis();
		model.setNumRows(0);
		showMessage.showMessageDialog(null, "상품이 수정됐습니다.", "알림", showMessage.PLAIN_MESSAGE);
	}
	public void remove() {
		if (testList.isEmpty()) {
			showMessage.showMessageDialog(null, "검색된 상품이 없습니다!", "알림", showMessage.PLAIN_MESSAGE);
			return;
		}
		int m = showMessage.showConfirmDialog(null, "검색된 상품은 전부 삭제됩니다. \n그래도 삭제하시겠습니까?", "알림", showMessage.YES_NO_OPTION, showMessage.PLAIN_MESSAGE);
		if (m == showMessage.CLOSED_OPTION)
			return;
		else if (m == showMessage.YES_OPTION) {
			try {
				dbConnect();
				for (int i = 0; i < arrayIndex; i++) {
					String bcode = testList.get(i);
					String query = "delete from product where id = ?";
					pstmt = conn.prepareStatement(query);
					pstmt.setString(1, bcode);
					pstmt.executeUpdate();
				}
				dbDis();
				model.setNumRows(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			return;
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
		if (e.getSource() == btnBack) {
			new MainScreen().setVisible(true);
			setVisible(false);
		}
		if (e.getSource() == btnRegister) {
			register();
		}
		if (e.getSource() == btnSearch) {
			search();
		}
		if (e.getSource() == btnSearchAll) {
			searchAll();
		}
		if (e.getSource() == btnEdit) {
			edit();
		}
		if (e.getSource() == btnRemove) {
			remove();
		}
		
	}
}
