package inquire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import main.MainScreen;
import sell.SellScreen;

public class DayInquireScreen extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JPanel panel_Top;
	private JPanel panel_Buttons;
	private JPanel panel_Table;
	private JPanel panel_Analysis;
	
	private JLabel lblYear;
	private JLabel lblMonth;
	private JLabel lblDay;
	private JLabel lblCount1;
	private JLabel lblAllPrice1;
	private JLabel lblCount2;
	private JLabel lblAllPrice2;
	
	private JComboBox cbYear;
	private JComboBox cbMonth;
	private JComboBox cbDay;
	
	private JButton btnBack;
	private JButton btnSearch;
	private JButton btnDayInquire;
	private JButton btnReset;
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Thread thread;
	
	//JTable
	private JScrollPane scrollpane;
	private JTable table;
	private DefaultTableModel model;
	private String header[] = {"바코드", "의류명", "가격", "사이즈", "색상", "카테고리", "거래시간"};
	private Object contents[][];
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DayInquireScreen frame = new DayInquireScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public DayInquireScreen() {
		setTitle("판매 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		
		//컴포넌트 객체 선언
		contentPane = new JPanel();
		panel_Top = new JPanel();
		panel_Table = new JPanel();
		panel_Buttons = new JPanel();
		panel_Analysis = new JPanel();
		
		lblYear = new JLabel("Year");
		lblMonth = new JLabel("Month");
		lblDay = new JLabel("Day");
		lblCount1 = new JLabel("판매 개수");
		lblCount1.setForeground(Color.WHITE);
		lblCount2 = new JLabel("0");
		lblCount2.setForeground(Color.WHITE);
		lblAllPrice1 = new JLabel("판매 총액");
		lblAllPrice1.setForeground(Color.WHITE);
		lblAllPrice2 = new JLabel("0");
		lblAllPrice2.setForeground(Color.WHITE);
		
		cbYear = new JComboBox();
		cbMonth = new JComboBox();
		cbDay = new JComboBox();
		
		btnBack = new JButton("메인 화면");
		btnSearch = new JButton("검색");
		btnDayInquire = new JButton("<html>당일 판매<br>내역 조회<html>");
		btnDayInquire.addActionListener(this);
		btnReset = new JButton("초기화");
		btnReset.addActionListener(this);
		
		//컴포넌트 설정
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setBackground(Color.DARK_GRAY);
		panel_Analysis.setBackground(Color.GRAY);
		panel_Top.setBackground(Color.LIGHT_GRAY);
		panel_Top.setLayout(new BoxLayout(panel_Top, BoxLayout.X_AXIS));
		panel_Table.setBackground(Color.DARK_GRAY);
		panel_Buttons.setBackground(Color.LIGHT_GRAY);
		panel_Buttons.setLayout(new GridLayout(0, 1, 0, 0));
		panel_Analysis.setBounds(28, 430, 804, 173);
		
		lblCount1.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblCount1.setBounds(58, 53, 137, 67);
		lblCount2.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblCount2.setBounds(192, 61, 113, 51);
		lblAllPrice1.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblAllPrice1.setBounds(388, 61, 123, 51);
		lblAllPrice2.setFont(new Font("Lucida Grande", Font.PLAIN, 30));
		lblAllPrice2.setBounds(522, 65, 265, 42);
		
		cbYear.setModel(new DefaultComboBoxModel(new String[] {"2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"}));
		cbMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		cbDay.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"}));
		
		btnBack.addActionListener(this);
		btnSearch.addActionListener(this);
		
		//테이블 영역 순서 이대로 두는게 중요
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
		scrollpane.setBounds(28, 5, 804, 400);
		
		//컴포넌트 추가
		panel_Top.add(btnBack);
		panel_Top.add(lblYear);
		panel_Top.add(cbYear);
		panel_Top.add(lblMonth);
		panel_Top.add(cbMonth);
		panel_Top.add(lblDay);
		panel_Top.add(cbDay);
		panel_Top.add(btnSearch);
		panel_Buttons.add(btnDayInquire);
		panel_Buttons.add(btnReset);
		panel_Table.setLayout(null);
		panel_Table.add(scrollpane);
		panel_Table.add(panel_Analysis);
		panel_Analysis.setLayout(null);
		
		panel_Analysis.add(lblCount1);
		panel_Analysis.add(lblAllPrice1);
		panel_Analysis.add(lblCount2);
		panel_Analysis.add(lblAllPrice2);
		
		contentPane.add(panel_Top, BorderLayout.NORTH);	
		contentPane.add(panel_Table, BorderLayout.CENTER);
		contentPane.add(panel_Buttons, BorderLayout.WEST);
		
		setContentPane(contentPane);
	}
	
	public void tableColumnSize() {
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);
		
	}
	public void search() {
		try {
			String cby = cbYear.getSelectedItem().toString();
			String cbm = cbMonth.getSelectedItem().toString();
			String cbd = cbDay.getSelectedItem().toString();
			int price = 0;
			model.setNumRows(0);
			lblCount2.setText(Integer.toString(0));
			lblAllPrice2.setText(Integer.toString(price));
			
			dbConnect();
			String query = "select p.id, p.p_name, p.price, p.size, p.color, p.category, r.created_at from product p inner join relation r " 
					+"on p.id = r.barcode where r.trans_id in "
					+"(select id from transaction where year(created_at) = ? and month(created_at) = ? and day(created_at) = ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cby);
			pstmt.setString(2, cbm);
			pstmt.setString(3, cbd);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	//각각 값을 가져와 테이블값들을 추가
				model.addRow(new Object[] {
					rs.getString("p.id"),
					rs.getString("p.p_name"),
					rs.getString("p.price"),
					rs.getString("p.size"),
					rs.getString("p.color"),
					rs.getString("p.category"),
					rs.getString("r.created_at")
				});
				price += rs.getInt("p.price");
			}
				
			lblCount2.setText(Integer.toString(model.getRowCount()));
			lblAllPrice2.setText(Integer.toString(price));
			dbDis();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void dayInquire() {
		try {
			int price = 0;
			model.setNumRows(0);
			lblCount2.setText(Integer.toString(0));
			lblAllPrice2.setText(Integer.toString(price));
			
			dbConnect();
			String query = "select p.id, p.p_name, p.price, p.size, p.color, p.category, r.created_at from product p inner join relation r " 
					+"on p.id = r.barcode where r.trans_id in "
					+"(select id from transaction where date(created_at) = curdate())";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	//각각 값을 가져와 테이블값들을 추가
				model.addRow(new Object[] {
					rs.getString("p.id"),
					rs.getString("p.p_name"),
					rs.getString("p.price"),
					rs.getString("p.size"),
					rs.getString("p.color"),
					rs.getString("p.category"),
					rs.getString("r.created_at")
				});
				price += rs.getInt("p.price");
			}
			lblCount2.setText(Integer.toString(model.getRowCount()));
			lblAllPrice2.setText(Integer.toString(price));
			dbDis();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
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
		if(e.getSource() == btnSearch) {
			search();
		}
		if(e.getSource() == btnDayInquire) {
			dayInquire();
		}
		if(e.getSource() == btnReset) {
			model.setNumRows(0);
			lblCount2.setText(Integer.toString(0));
			lblAllPrice2.setText(Integer.toString(0));
		}
	}
	
}
