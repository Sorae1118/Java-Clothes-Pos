package inquire;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.MainScreen;
import java.awt.Color;
import java.awt.Font;

public class SelectInquire extends JFrame implements ActionListener{

	private JPanel contentPane;

	private JButton btnTransInquire;
	private JButton btnDayInquire;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectInquire frame = new SelectInquire();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public SelectInquire() {
		setTitle("선택");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(400,200);
		setLocationRelativeTo(null); 
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		btnTransInquire = new JButton("상세 조회");
		btnTransInquire.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		btnDayInquire = new JButton("기간별 조회");
		btnDayInquire.setFont(new Font("Lucida Grande", Font.BOLD, 24));
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnTransInquire.addActionListener(this);
		btnDayInquire.addActionListener(this);
		
		contentPane.add(btnTransInquire);
		contentPane.add(btnDayInquire);
		setContentPane(contentPane);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnTransInquire) {
			new TransInquireScreen().setVisible(true);
			new MainScreen().setVisible(false);
			setVisible(false);
		}
		if(e.getSource() == btnDayInquire) {
			new DayInquireScreen().setVisible(true);
			new MainScreen().setVisible(false);
			setVisible(false);
		}
	}

}
