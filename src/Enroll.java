import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Enroll implements ActionListener {
	int menu_count=0;
	Connection dbTest= null;
	private JPanel setting = new JPanel();
	private JLabel title = new JLabel("등록/조회");
	
    private JTabbedPane T = new JTabbedPane();
  
    private JPanel customer = new JPanel();
    private JLabel c_label = new JLabel("고객명");
    private JTextField c_tf = new JTextField(5);
    public JButton c_enroll = new JButton("가입");
    public JButton c_search = new JButton("조회");
    private JTextArea c_ta = new JTextArea(15, 20);
    
    private JFrame cs_frame = new JFrame("회원등록");
    private JPanel CSpanel = new JPanel();
    private JLabel cs_name	= new JLabel("이름");
    private JTextField cs_tf1 = new JTextField(5);
    private JTextField cs_tf2 = new JTextField(5);
    private JTextField cs_tf3 = new JTextField(5);
    private JLabel cs_birth	= new JLabel("생일");
    private JLabel cs_phone	= new JLabel("연락처");
    public JButton cs_enroll = new JButton("가입신청");
    public JButton cs_cancel = new JButton("취소");

	Menu mn = new Menu();
    private JPanel Ipanel = new JPanel();
    private JLabel i_date = new JLabel("기간");
    public JComboBox i_combo;
    public JTextArea i_ta = new JTextArea(15, 20);

    private JPanel Epanel = new JPanel();
    private JLabel e_label = new JLabel("직원명");
    private JTextField e_tf = new JTextField(5);
    public JButton e_enroll = new JButton("직원등록");
    public JButton e_search = new JButton("조회");
    private JTextArea e_ta = new JTextArea(15, 20);

    private JFrame ee_frame = new JFrame("직원등록");
    private JPanel EEpanel = new JPanel();
    private JLabel ee_name	= new JLabel("직원명");
    private JTextField ee_tf = new JTextField();
    private JLabel ee_lev	= new JLabel("직급");
    private JComboBox ee_combo;
    public JButton ee_enroll = new JButton("등록");
    public JButton ee_cancel = new JButton("취소");
    
    private JPanel Mpanel = new JPanel();
    private JLabel m_name = new JLabel("메뉴명");
    private JTextField m_tf = new JTextField(11);
    public JButton m_enroll = new JButton("메뉴등록");
    public JButton m_search = new JButton("조회");
    private JTextArea m_ta = new JTextArea(15, 20);
    
    private JFrame me_frame = new JFrame("메뉴등록");
    private JPanel MEpanel = new JPanel();
    private JLabel me_name	= new JLabel("메뉴명");
    private JTextField me_tf = new JTextField();
    private JLabel me_cost	= new JLabel("가격");
    private JTextField me_tf2 = new JTextField();
    public JButton me_enroll = new JButton("등록");
    public JButton me_cancel = new JButton("취소");
    
    
    JPanel EnrollSearchPanel(){
 
    	T.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	customer.setLayout(null);
    	
    	c_label.setBounds(9, 9, 60, 30);
    	c_tf.setBounds(9, 40, 80, 30);
    	c_enroll.setBounds(180, 40, 60, 30);
    	c_search.setBounds(260, 40, 60, 30);
    	c_ta.setBounds(9, 100, 295,240);
    	customer.add(c_label);
    	customer.add(c_tf);
    	customer.add(c_enroll);
    	c_enroll.addActionListener(this);
    	customer.add(c_search);
    	c_search.addActionListener(this);
    	customer.add(c_ta);
    	
    	Ipanel.setLayout(null);
    	
    	i_date.setBounds(9, 40, 80, 30);
    	i_combo = new JComboBox();
    	i_combo.setBounds(70, 40, 120, 30);
    	i_combo.setEnabled(false);
    	i_combo.addActionListener(this);
    	i_ta.setBounds(9, 100, 295, 240);
    	i_ta.setVisible(false);
    	
    	Ipanel.add(i_date);
    	Ipanel.add(i_combo);
    	Ipanel.add(i_ta);
    	
    	Epanel.setLayout(null);
    	
    	e_label.setBounds(9, 9, 60, 30);
    	e_tf.setBounds(9, 40, 80, 30);
    	e_enroll.setBounds(160, 40, 90, 30);
    	e_search.setBounds(260, 40, 60, 30);
    	e_ta.setBounds(9, 100, 295, 240);
    	
    	Epanel.add(e_label);
    	Epanel.add(e_tf);
    	Epanel.add(e_enroll);
    	e_enroll.addActionListener(this);
    	Epanel.add(e_search);
    	e_search.addActionListener(this);
    	Epanel.add(e_ta);
    	
    	Mpanel.setLayout(null);
    	
    	m_name.setBounds(9, 9, 60, 30);
    	m_tf.setBounds(9, 40, 150, 30);
    	m_enroll.setBounds(160, 40, 90, 30);
    	m_search.setBounds(260, 40, 60, 30);
    	m_ta.setBounds(9, 100, 295, 240);
    	
    	Mpanel.add(m_name);
    	Mpanel.add(m_tf);
    	Mpanel.add(m_enroll);
    	m_enroll.addActionListener(this);
    	Mpanel.add(m_search);
    	m_search.addActionListener(this);
    	Mpanel.add(m_ta);
    	
    	T.addTab("고객", customer);
    	T.addTab("매출", Ipanel);
    	T.addTab("직원", Epanel);
    	T.addTab("메뉴", Mpanel);
    	
    	setting.setLayout(new BorderLayout());
    	setting.add(title, "North");
    	setting.add(T, "Center");
    	
		ee_enroll.setEnabled(false);
		ee_cancel.setEnabled(false);
		m_enroll.setEnabled(false);
		m_search.setEnabled(false);
		me_enroll.setEnabled(false);
		me_cancel.setEnabled(false);
		c_enroll.setEnabled(false);
		c_search.setEnabled(false);
		cs_enroll.setEnabled(false);
		cs_cancel.setEnabled(false);
		e_enroll.setEnabled(false);
		e_search.setEnabled(false);
		i_combo.setEnabled(false);
		//info
		i_combo.addItem("2016-06-31");
		
    	
    	//customer enroll
		CSpanel.setLayout(null);

        cs_name.setBounds(20, 9, 60, 30);
        cs_birth.setBounds(20, 70, 60, 30);
        cs_phone.setBounds(20, 130, 60, 30);
        
        cs_tf1.setBounds(150, 9, 80, 30);
        cs_tf2.setBounds(150, 70, 80, 30);
        cs_tf3.setBounds(150, 130, 80, 30);
        
        cs_enroll.setBounds(20, 190, 120, 35);
        cs_enroll.addActionListener(this);

        cs_cancel.setBounds(170, 190, 120, 35);
        cs_cancel.addActionListener(this);

        CSpanel.add(cs_name);
        CSpanel.add(cs_birth);
        CSpanel.add(cs_phone);
        CSpanel.add(cs_tf1);
        CSpanel.add(cs_tf2);
        CSpanel.add(cs_tf3);
        CSpanel.add(cs_enroll);
        CSpanel.add(cs_cancel);
        
        cs_frame.add(CSpanel);

        cs_frame.setSize(320, 280);
        cs_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	//employee enroll
		EEpanel.setLayout(null);

        ee_name.setBounds(20, 9, 60, 30);
        ee_lev.setBounds(20, 70, 60, 30);
        
        ee_tf.setBounds(150, 9, 120, 30);
        String grade[] = {"Supervisor", "staff"};
        ee_combo = new JComboBox(grade);
        ee_combo.setBounds(150, 70, 120, 30);
        
        ee_enroll.setBounds(20, 130, 120, 35);
        ee_enroll.addActionListener(this);
        
        ee_cancel.setBounds(170, 130, 120, 35);
        ee_cancel.addActionListener(this);

        EEpanel.add(ee_name);
        EEpanel.add(ee_lev);
        EEpanel.add(ee_tf);
        EEpanel.add(ee_combo);
        EEpanel.add(ee_enroll);
        EEpanel.add(ee_cancel);
        ee_frame.add(EEpanel);
        
        ee_frame.setSize(320, 220);
        ee_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	
    	//menu enroll
		MEpanel.setLayout(null);

        me_name.setBounds(20, 9, 60, 30);
        me_cost.setBounds(20, 70, 60, 30);
        
        me_tf.setBounds(150, 9, 120, 30);
        me_tf2.setBounds(150, 70, 120, 30);
        
        me_enroll.setBounds(20, 130, 120, 35);
        me_enroll.addActionListener(this);

        me_cancel.setBounds(170, 130, 120, 35);
        me_cancel.addActionListener(this);

        MEpanel.add(me_name);
        MEpanel.add(me_cost);
        MEpanel.add(me_tf);
        MEpanel.add(me_tf2);
        MEpanel.add(me_enroll);
        MEpanel.add(me_cancel);
        
        me_frame.add(MEpanel);

        me_frame.setSize(320, 220);
        me_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
    	return setting;
    }
    
    private void CEnroll(){
        cs_frame.setVisible(true);
	}
    
    private void MEnroll(){

        me_frame.setVisible(true);
	}
	
	private void EEnroll(){

        ee_frame.setVisible(true);
	}

	@SuppressWarnings("null")
	@Override
	public void actionPerformed(ActionEvent e) {
		String sqlStr = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;				
		Main m = new Main();
		dbTest = m.connectDB("system", "system");
		if(e.getSource() == c_enroll){
			CEnroll();
		}else if(e.getSource() == c_search){
			boolean customer_found= false;	
			if(c_tf.getText().equals("") ==false){
				sqlStr = "select customer_name from customer";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(c_tf.getText().equals( rs2.getString(1))){
							customer_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(customer_found== false){
				JOptionPane.showMessageDialog(null, (String) "검색 결과 없음.",
						"search denied", 2);
			}
			else{

				
				sqlStr = "select customer_name, customer_id, customer_birth, customer_phone_number, customer_grade, customer_acc_cost from customer where customer_name = '" + c_tf.getText() + "'";
				try {

					stmt = dbTest.prepareStatement(sqlStr);
					rs = stmt.executeQuery();
					
					String name, grade;
					int id, birth, phone, total;
					
					rs.next();
					
					name = rs.getString("customer_name");
					id = rs.getInt("customer_id");
					birth = rs.getInt("customer_birth");
					phone = rs.getInt("customer_phone_number");
					grade = rs.getString("customer_grade");
					total = rs.getInt("customer_acc_cost");
					
					c_ta.setText("이름: " + name
							+ "\nI D:" + id
							+ "\n생일:" + birth
							+ "\n전화번호:" + phone
							+ "\n등급:" + grade
							+ "\n총 구매금액:" + total);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		
			
			
		}else if(e.getSource() == cs_enroll){
			boolean customer_found= false;
			if(cs_tf1.getText().equals("") ==false){
				sqlStr = "select customer_name from customer";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(cs_tf1.getText().equals( rs2.getString(1))){
							customer_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(customer_found== true){
				JOptionPane.showMessageDialog(null, (String) "이미 존재하는 이름 입니다.",
						"enroll denied", 2);
			}
			else{
				String name = cs_tf1.getText();
				String birth = cs_tf2.getText();
				String contact = cs_tf3.getText();
				Random random = new Random();
				sqlStr = "insert into customer values ("
						+ random.nextInt(9999)
						+ ", '"
						+ name + "', "
						+ Integer.parseInt(birth) + ", "
						+ Integer.parseInt(contact) + ", "
						+ "'Normal', "
						+ 0
						+")";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					stmt.executeQuery(sqlStr);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cs_frame.setVisible(false);
			}

		}else if(e.getSource() == cs_cancel){
			cs_frame.setVisible(false);
		}else if(e.getSource() == m_enroll){
			MEnroll();
		}else if(e.getSource() == m_search){
			
			boolean menu_found= false;	
			if(m_tf.getText().equals("") ==false){
				sqlStr = "select menu_name from menu";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(m_tf.getText().equals( rs2.getString(1))){
							menu_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(menu_found== false){
				JOptionPane.showMessageDialog(null, (String) "검색 결과 없음.",
						"search denied", 2);
			}
			
			else{

				sqlStr = "select menu_name, menu_price from menu where menu_name = '" + m_tf.getText() + "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					rs = stmt.executeQuery();
					
					String menuname;
					int price;
					
					rs.next();
					
					menuname = rs.getString("menu_name");
					price = rs.getInt("menu_price");
					
					m_ta.setText("메뉴명: " + menuname 
							+ "\n가  격:" + String.valueOf(price));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}else if(e.getSource() == me_enroll){
			
			boolean menu_found= false;
			if(me_tf.getText().equals("") ==false){
				sqlStr = "select menu_name from menu";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(me_tf.getText().equals( rs2.getString(1))){
							menu_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(menu_found== true){
				JOptionPane.showMessageDialog(null, (String) "이미 존재하는 이름 입니다.",
						"enroll denied", 2);
			}
			else if(menu_count>19){
				JOptionPane.showMessageDialog(null, (String) "메뉴가 꽉찼습니다.",
						"enroll denied", 2);
			}
			else{

				String menu = me_tf.getText();
				String price = me_tf2.getText();
				
				sqlStr = "insert into menu values ("
						+ "'" + menu + "', "
						+ price
						+ ")";
				
				try{
					stmt = dbTest.prepareStatement(sqlStr);
					stmt.execute(sqlStr);

					stmt.close();
					dbTest.close();
				}catch(SQLException ex){
					System.out.println(ex);
				}
				mn.menu_items[menu_count].setText(menu);
				mn.menu_name[menu_count] = me_tf.getText();
				mn.menu_price[menu_count] = Integer.parseInt(me_tf2.getText());
				menu_count++;
				me_frame.setVisible(false);
			}
			
			
			
			
		}else if(e.getSource() == me_cancel){
			me_frame.setVisible(false);
		}else if(e.getSource() == e_enroll){
			EEnroll();
		}else if(e.getSource() == e_search){
			
			boolean employee_found= false;	
			if(e_tf.getText().equals("") ==false){
				sqlStr = "select employee_name from employee";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(e_tf.getText().equals( rs2.getString(1))){
							employee_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(employee_found== false){
				JOptionPane.showMessageDialog(null, (String) "검색 결과 없음.",
						"search denied", 2);
			}
			
			else{

				sqlStr = "select employee_name, employee_position, total from employee where employee_name = '" + e_tf.getText() + "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					rs = stmt.executeQuery();
					
					String name, position;
					int total;
					
					rs.next();
					name = rs.getString("employee_name");
					position = rs.getString("employee_position");
					total = rs.getInt("total");

					e_ta.setText("직원명: " + name 
							+ "\n직  급:" + position
							+ "\n총실적:" + total);
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
		}else if(e.getSource() == ee_enroll){
			boolean employee_found= false;
			if(ee_tf.getText().equals("") ==false){
				sqlStr = "select employee_name from employee";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs2 = stmt.executeQuery(sqlStr);
					while(rs2.next()){
						if(ee_tf.getText().equals( rs2.getString(1))){
							employee_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			
			if(employee_found== true){
				JOptionPane.showMessageDialog(null, (String) "이미 존재하는 이름 입니다.",
						"enroll denied", 2);
			}
			else{

				String name = ee_tf.getText();
				String position = (String) ee_combo.getSelectedItem();
				Random random = new Random();
				sqlStr = "insert into employee values ("
						+ random.nextInt(9999)+ ", '" + name + "' , '"
						+ position + "', 0)";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					stmt.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ee_frame.setVisible(false);
			}
			
		}else if(e.getSource() == ee_cancel){
			ee_frame.setVisible(false);
		}else if(e.getSource() == i_combo && mn.menu_name[0] != null){
			int max=mn.menu_sold[0];
			int max_index=0;
			int min=mn.menu_sold[0];
			int min_index=0;
			for(int i=0;i<19;i++){
				if(max < mn.menu_sold[i+1]){
					max = mn.menu_sold[i+1];
					max_index = i+1;
				}
			}
			for(int i=0;i<19;i++){
				if(min > mn.menu_sold[i+1]){
					min = mn.menu_sold[i+1];
					min_index = i+1;
				}
			}
			int acc_total=0;
			sqlStr = "select sum(total) from employee";
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				rs = stmt.executeQuery(sqlStr);
				rs.next();
				acc_total = rs.getInt(1);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			if(acc_total ==0){
				i_ta.setText("일 매출 : 0\n"
						+ "-------------------------------------------------------------------------\n"
						+ "가장 많이 팔린 메뉴\n"
						+ ": 없음"  
						+ "\n"
						+ "가장 적게 팔린 메뉴\n"
						+ ": 없음" 
						+"\n-------------------------------------------------------------------------\n"
						+ "누적 매출 : 0");
			}
			else{
				i_ta.setText("일 매출 :"+ Order.sold_today +"\n"
						+ "-------------------------------------------------------------------------\n"
						+ "가장 많이 팔린 메뉴\n"
						+ ":" + mn.menu_name[max_index]
						+ "\n"
						+ "가장 적게 팔린 메뉴\n"
						+ ":" + mn.menu_name[min_index]
						+"\n-------------------------------------------------------------------------\n"
						+ "누적 매출 : "+ acc_total);
			}



		}else{
			
		}

	}
}
