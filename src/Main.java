import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main implements ActionListener {

	public static void main(String[] argv) throws SQLException {
		Main m = new Main();
		m.show();
	}

	Node cus_id_node = new Node();
	Node staff_id_node = new Node();
	JFrame main = new JFrame();
	Table table = new Table();
	Order order = new Order();
	Menu menu = new Menu();
	Enroll enroll = new Enroll();
	JPanel temp = new JPanel();
	JMenuBar menuBar = new JMenuBar();
	JMenuItem login = new JMenuItem("Login");
	JMenuItem open = new JMenuItem("Open");
	JMenu menus = new JMenu("Menu");
	int returnValue = 0;
	File selectedFile=null;
	public static Connection dbTest;

	public static String username;
	private String password;

	private JPanel Login = new JPanel();
	private JLabel idLabel = new JLabel("이름");
	private JLabel pwdLabel = new JLabel("사원번호");
	private JTextField idInput = new JTextField();
	private JPasswordField pwdInput = new JPasswordField();
	private JButton loginButton = new JButton("로그인");
	private JFrame Loginframe = new JFrame();

	boolean isLogoned = false;

	public Main() {

	}

	public void show() throws SQLException {

		main.setLayout(null);

		menus.add(open);
		open.addActionListener(this);
		menus.add(login);
		login.addActionListener(this);
		menuBar.add(menus);
		menuBar.setBounds(0, 0, 695, 20);
		main.add(menuBar, "North");

		main.setTitle("식당 관리 시스템");
		main.setSize(695, 890);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel titles = new JLabel("식당 주문 관리", JLabel.CENTER);
		JPanel Tpanel = new JPanel();
		titles.setFont(new Font(titles.getName(), Font.PLAIN, 50));
		titles.setBorder(BorderFactory.createLineBorder(Color.black));
		Tpanel.setLayout(new BorderLayout());
		Tpanel.add(titles, "Center");
		Tpanel.setSize(680, 100);
		temp = Tpanel;

		temp.setBounds(3, 23, 673, 100);
		main.add(temp);
		temp = table.table();
		temp.setBounds(3, 130, 335, 300);
		main.add(temp);
		temp = order.OrderListPanel();
		temp.setBounds(340, 130, 335, 300);
		main.add(temp);
		temp = menu.menu();
		temp.setBounds(3, 440, 335, 400);
		main.add(temp);

		temp = enroll.EnrollSearchPanel();
		temp.setBounds(340, 440, 335, 400);
		main.add(temp);
		for(int i=0;i<20;i++){
			menu.menu_items[i].setEnabled(false);				
		}
		
		//login
		Login.setLayout(null);
		Loginframe.setTitle("사원 로그인");
		Loginframe.setSize(325, 140);
		Loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		idLabel.setBounds(20, 10, 60, 30);
		pwdLabel.setBounds(20, 50, 60, 30);
		idInput.setBounds(100, 10, 80, 30);
		pwdInput.setBounds(100, 50, 80, 30);
		loginButton.setBounds(200, 25, 80, 35);
		loginButton.addActionListener(this);

		Login.add(idLabel);
		Login.add(pwdLabel);
		Login.add(idInput);
		Login.add(pwdInput);
		Login.add(loginButton);

		Loginframe.add(Login);
		Loginframe.setDefaultCloseOperation(1);

		
		main.setVisible(true);
	}
	public void open_file(String u_name, String p_word){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sqlStr = null;
		String key = null;
		BufferedReader br = null;
		JFileChooser filechooser = new JFileChooser();
		if(u_name.equals("system")){

			returnValue = filechooser.showOpenDialog(null);
			selectedFile = filechooser.getSelectedFile();
			 
		}

		//
		username = u_name;
		password = p_word;
		dbTest = connectDB(username, password);
		//isLogoned = false;
		// 삭제 check
//		for(int i=0;i<20;i++){
//			menu.menu_items[i].setEnabled(true);		
//			table.tableitems[i].setEnabled(true);
//		}
//		order.o_button1.setEnabled(true);
//		order.o_button2.setEnabled(true);
//		order.o_button3.setEnabled(true);
//		order.o_combo.setEnabled(true);
//		enroll.ee_enroll.setEnabled(true);
//		enroll.ee_cancel.setEnabled(true);
//		enroll.m_enroll.setEnabled(true);
//		enroll.m_search.setEnabled(true);
//		enroll.me_enroll.setEnabled(true);
//		enroll.me_cancel.setEnabled(true);
//		enroll.sign_in.setEnabled(true);
//		enroll.c_search.setEnabled(true);
//		enroll.cs_enroll.setEnabled(true);
//		enroll.cs_cancel.setEnabled(true);
//		enroll.e_enroll.setEnabled(true);
//		enroll.e_search.setEnabled(true);
//		enroll.i_combo.setEnabled(true);
		
		// 여기까지

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			try {

				br = new BufferedReader(new FileReader(selectedFile));

				make_schema();
				while ((key = br.readLine()) != null) {
					String[] data = key.split("	");

					if (data.length < 2) {
					} else if (data.length == 4) {
						int c_id = rand_int(cus_id_node);
						sqlStr = "insert into customer values (" + c_id
								+ ", " + "'" + data[0] + "', " + data[1]
								+ ", " + data[2] + ", " + "'" + data[3]
								+ "', " + valued_cost(data[3]) + ")";

						stmt = dbTest.prepareStatement(sqlStr);
						stmt.execute(sqlStr);

					} else if (data.length == 2 && data[0].length() < 4) {

						int s_id = rand_int(staff_id_node);
						sqlStr = "insert into Employee values (" + s_id
								+ ", '" + data[0] + "', '" + data[1]
								+ "', 0)";
						stmt = dbTest.prepareStatement(sqlStr);
						stmt.execute(sqlStr);			

					} else {
						sqlStr = "insert into Menu values ('" + data[0]
								+ "', " + data[1] + ")";
						stmt = dbTest.prepareStatement(sqlStr);
						stmt.execute(sqlStr);
						enroll.menu_count++;
					}
				}

				sqlStr = "select menu_name, menu_price from menu";
				stmt = dbTest.prepareStatement(sqlStr);
				rs = stmt.executeQuery();
				String menu_name;

				int price;
				for (int j = 0; rs.next(); j++) {
					menu_name = rs.getString("menu_name");
					price = rs.getInt("menu_price");
					menu.menu_items[j].setText(menu_name); //check
					menu.menu_name[j] = menu_name;
					menu.menu_price[j] = price;
				}

				rs.close();
				stmt.close();
				dbTest.close();
				br.close();

			} catch (Exception ex) {// try,catch
				System.out.println(ex);
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			Login();
		} else if (e.getSource() == open) {
			open_file("system","system");
			
		} else if (e.getSource() == loginButton) {
			PreparedStatement stmt = null;
			String sqlStr = null;
			username = idInput.getText().toLowerCase();
			password = new String(pwdInput.getPassword()).toLowerCase();

			dbTest = connectDB("system", "system");
			
			sqlStr = "select employee_name from employee where employee_name ='"
					+ username
					+ "'";
			String e_name = null;
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				ResultSet rs = stmt.executeQuery(sqlStr);
				while(rs.next()){
					e_name = rs.getString(1);	
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			
			if (username.equals("system") && password.equals("system")) {
				if (dbTest != null) {
					isLogoned = true;
					JOptionPane.showMessageDialog(null,
							(String) "접속 완료(admin)", "connected", 2);

					for(int i=0;i<20;i++){
						menu.menu_items[i].setEnabled(true);		
						table.tableitems[i].setEnabled(true);
					}
					order.o_button1.setEnabled(true);
					order.o_button2.setEnabled(true);
					order.o_button3.setEnabled(true);
					order.o_combo.setEnabled(true);
					enroll.ee_enroll.setEnabled(true);
					enroll.ee_cancel.setEnabled(true);
					enroll.m_enroll.setEnabled(true);
					enroll.m_search.setEnabled(true);
					enroll.me_enroll.setEnabled(true);
					enroll.me_cancel.setEnabled(true);
					enroll.c_enroll.setEnabled(true);
					enroll.c_search.setEnabled(true);
					enroll.cs_enroll.setEnabled(true);
					enroll.cs_cancel.setEnabled(true);
					enroll.e_enroll.setEnabled(true);
					enroll.e_search.setEnabled(true);
					enroll.i_combo.setEnabled(true);
					order.sold_today=0;
					Loginframe.setVisible(false);

				} else {
					JOptionPane.showMessageDialog(null, (String) "접속 실패",
							"connect failed", 2);
				}
				

			} else {
				if (dbTest != null && e_name != null) {
					order.logoned_employee = e_name;
					isLogoned = true;
					JOptionPane.showMessageDialog(null, (String) "접속 완료",
							"connected", 2);

					for(int i=0;i<20;i++){
						menu.menu_items[i].setEnabled(true);		
						table.tableitems[i].setEnabled(true);			
					}
					order.o_button1.setEnabled(true);
					order.o_button2.setEnabled(true);
					order.o_button3.setEnabled(true);
					order.o_combo.setEnabled(true);
					enroll.ee_enroll.setEnabled(true);
					enroll.ee_cancel.setEnabled(true);
					enroll.m_search.setEnabled(true);
					enroll.me_enroll.setEnabled(true);
					enroll.me_cancel.setEnabled(true);
					enroll.c_search.setEnabled(true);
					enroll.cs_enroll.setEnabled(true);
					enroll.cs_cancel.setEnabled(true);
					enroll.e_search.setEnabled(true);
					enroll.i_combo.setEnabled(true);
					order.sold_today=0;


					
					sqlStr = "select employee_position from employee where employee_name ='"
							+ username
							+ "'";
					String position = null;
					try {
						stmt = dbTest.prepareStatement(sqlStr);
						ResultSet rs = stmt.executeQuery(sqlStr);
						rs.next();
						position = rs.getString(1);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if(position.equals("Supervisor")){
						enroll.e_enroll.setEnabled(true);
						enroll.m_enroll.setEnabled(true);
						enroll.c_enroll.setEnabled(true);
				    	enroll.i_combo.setEnabled(true);
				    	enroll.i_ta.setVisible(true);
					}
					else{
						enroll.e_enroll.setEnabled(false);
						enroll.m_enroll.setEnabled(false);
						enroll.c_enroll.setEnabled(false);
				    	enroll.i_combo.setEnabled(false);
				    	enroll.i_ta.setVisible(false);
					}

					Loginframe.setVisible(false);
					
					
				} else {
					JOptionPane.showMessageDialog(null, (String) "아이디와 패스워드를 확인해주세요.",
							"connect failed", 2); //예외처리1
				}
			}

		} else {
			System.out.println("Unexpected Error");
		}

	}

	private void Login() {
		Loginframe.setVisible(true);
	}

	public Connection connectDB(String id, String pw) {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			dbTest = DriverManager.getConnection("jdbc:oracle:thin:"
					+ "@localhost:1521:XE", id, pw);
			return dbTest;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연결에 실패하였습니다. SQLException");
			System.out.println("SQLException:" + e);
			return null;
		} catch (Exception e) {
			System.out.println("DB에 연결 할 수 없습니다. Exception");
			System.out.println("Exception:" + e);
			return null;
		}
	}

	public void make_schema() throws SQLException {

		PreparedStatement stmt = null;
		String sqlStr = null;

		//예외처리22 
		sqlStr = "create table customer (" + "customer_id number(5),"
				+ "customer_name varchar2(12)," + "customer_birth number(5),"
				+ "customer_phone_number number(5),"
				+ "customer_grade varchar2(10),"
				+ "customer_acc_cost number(9)," + "primary key(customer_name)"
				+ ")";

		try {
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
		} catch (SQLException e) {
			sqlStr = "drop table customer";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
		} finally{
			sqlStr = "create table customer (" + "customer_id number(5),"
					+ "customer_name varchar2(12)," + "customer_birth number(5),"
					+ "customer_phone_number number(5),"
					+ "customer_grade varchar2(10),"
					+ "customer_acc_cost number(9)," + "primary key(customer_name)"
					+ ")";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
		}
		

		sqlStr = "create table employee (" + "employee_id number(4),"
				+ "employee_name varchar2(10),"
				+ "employee_position varchar2(10), total number(11),"
				+ "primary key(employee_name)" + ")";
		try {
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
		} catch (SQLException e) {
			sqlStr = "drop table employee";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
		}finally{
			sqlStr = "create table employee (" + "employee_id number(4),"
					+ "employee_name varchar2(10),"
					+ "employee_position varchar2(10), total number(11),"
					+ "primary key(employee_name)" + ")";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
		}

		sqlStr = "create table menu (" + "menu_name varchar2(25),"
				+ "menu_price number(6)," + "primary key(menu_name)" + ")";
		try {
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
		} catch (SQLException e) {
			sqlStr = "drop table menu";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();
		} finally{
			sqlStr = "create table menu (" + "menu_name varchar2(25),"
					+ "menu_price number(6)," + "primary key(menu_name)" + ")";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
			
		}
		sqlStr = "create table ordered (table_num number(2),"
				+ " order_price number(20),"
				+ " staff_name varchar2(12),"
				+ " customer_name varchar2(12),"
				+ "primary key(table_num)"
				+ ")";
		try {
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
		} catch (SQLException e) {
			sqlStr = "drop table ordered";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.executeQuery();	
		}
		finally{
			sqlStr = "create table ordered (table_num number(2),"
					+ " order_price number(20),"
					+ " staff_name varchar2(12),"
					+ " customer_name varchar2(12),"
					+ "primary key(table_num)"
					+ ")";
			stmt = dbTest.prepareStatement(sqlStr);
			stmt.execute(sqlStr);
		}
	}

	public int rand_int(Node n) {
		Random random = new Random();
		Node tmp = n;
		int num = random.nextInt(9999);
		while (true) {

			if (tmp.key == num) {
				num = random.nextInt(9999);
			}
			if (tmp.next_node == null) {
				tmp.next_node = new Node();
				tmp.next_node.key = num;
				break;
			} else {
				tmp = tmp.next_node;
			}
		}
		return num;
	}

	public int valued_cost(String str) {
		int num = -1;
		if (str.equals("Gold")) {
			num = 1000000;
		} else if (str.equals("Silver")) {
			num = 500000;
		} else if (str.equals("Bronze")) {
			num = 300000;
		} else if (str.equals("Normal")) {
			num = 0;
		} else {
			System.out.println("Grade Error");
		}
		return num;
	}

}
