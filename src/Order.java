import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Order implements ActionListener{
	ResultSet rs;
	Menu mn = new Menu();
	Enroll enroll = new Enroll();
    private String[] table = new String[20];
    public static boolean [] order_done = new boolean [20];
    public static String [] saved_order_list = new String [20];
    public static String [] saved_total = new String [20];
    public static String [] saved_customer = new String [20];
    String logoned_employee;
    public static int[] total_cost = new int[20];

	private JPanel Opanel = new JPanel();
	private JPanel order = new JPanel();
	private JLabel title = new JLabel("주문내역");
	public static Connection dbTest;

    public static JTextArea ta1 = new JTextArea();
    public static JTextArea ta2 = new JTextArea();
    private JTextField o_tf = new JTextField();    
    public static JComboBox o_combo;
    private JLabel o_name = new JLabel("고객명");
    private JLabel o_table = new JLabel("테이블명");
    public static JButton o_button1 = new JButton("주문");
    public static JButton o_button2 = new JButton("취소");
    public static JButton o_button3 = new JButton("결제");  
    JScrollPane scroll = new JScrollPane();

    public static int sold_today=0;
   

    JPanel OrderListPanel(){
        for(int i=0;i<20;i++){
        	order_done[i] = false;
            saved_order_list[i] = null;
            saved_total[i] = null;
            saved_customer[i] = null;
        }


    	order.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	order.setLayout(null);

    	o_name.setBounds(270, 5, 60, 30);
    	o_tf.setBounds(270, 45, 60, 30);
    	o_table.setBounds(70, 80, 60, 30);
    	ta1.setBounds(10, 5,250 , 135);
    	ta1.setEditable(false);
    	scroll.getViewport().add(ta1);
    	scroll.setBounds(10, 5, 250, 135);
    	ta2.setText("총 합계\t");
    	ta2.setBounds(10, 140,250, 135);
    	ta2.setEditable(false);
    	for(int i=0;i<table.length;i++){	
    		table[i] = Integer.toString(i+1);
		}
    	o_combo = new JComboBox(table);
    	
    	o_button1.addActionListener(this);
    	o_button1.setBounds(270, 170, 60, 30);
    	o_button2.addActionListener(this);
    	o_button2.setBounds(270, 205, 60, 30);
    	o_button3.addActionListener(this);
    	o_button3.setBounds(270, 240, 60, 30);
    	o_combo.addActionListener(this);
    	o_combo.setBounds(270, 130, 60, 30);
    	order.add(scroll);
    	order.add(ta2);
    	
    	order.add(o_name);
    	order.add(o_tf);
    	order.add(o_table);
    	order.add(o_combo);
    	order.add(o_button1);
    	order.add(o_button2);
    	order.add(o_button3);
    	
    	order.setSize(300, 270);
    	
    	Opanel.setLayout(new BorderLayout());
    	Opanel.add(title, "North");
    	Opanel.add(order, "Center");
		o_button1.setEnabled(false);
		o_button2.setEnabled(false);
		o_button3.setEnabled(false);
		o_combo.setEnabled(false);
    	return Opanel;
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		PreparedStatement stmt = null;
		String sqlStr = null;
		Table table = new Table();
		Menu menu = new Menu();
		Main m = new Main();
		dbTest = m.connectDB("system", "system");
		if(e.getSource() == o_combo && order_done[o_combo.getSelectedIndex()] == true){
			o_tf.setText(saved_customer[o_combo.getSelectedIndex()]);
			ta1.setText(saved_order_list[o_combo.getSelectedIndex()]);
			ta2.setText(saved_total[o_combo.getSelectedIndex()]);
		}
		else if(e.getSource() == o_combo && order_done[o_combo.getSelectedIndex()] == false){
			menu.total =0;
			o_tf.setText("");
			ta1.setText("");
			ta2.setText(("총 합계\n") + 0+"원");	
		}
		else if(e.getSource() == o_button1 && ta1.getText().equals("") ==true){
			JOptionPane.showMessageDialog(null, (String) "메뉴를 선택해주세요.",
					"pay denied", 2);//예외처리11
		}
		else if(e.getSource() == o_button1 && ta1.getText().equals("") ==false){
			boolean customer_found= true;
			if(o_tf.getText().equals("") ==false){
				customer_found= false;
				sqlStr = "select customer_name from customer";
				 try {
					stmt = dbTest.prepareStatement(sqlStr);
					rs = stmt.executeQuery(sqlStr);
					while(rs.next()){
						if(o_tf.getText().equals( rs.getString(1))){
							customer_found = true;
							break;
						}
					}
				} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
			if(customer_found ==false){
				JOptionPane.showMessageDialog(null, (String) "존재하지 않는 고객입니다.",
						"order denied", 2);//예외처리9
			}
			else{
				if(order_done[o_combo.getSelectedIndex()] == true ){
					saved_customer[o_combo.getSelectedIndex()] = o_tf.getText();
					saved_order_list[o_combo.getSelectedIndex()] = ta1.getText();
					saved_total[o_combo.getSelectedIndex()] = ta2.getText();
					sqlStr = "update ordered set order_price = "+ menu.total+" where (table_num = "+(o_combo.getSelectedIndex()+1)+")";
							 
							 try {
								stmt = dbTest.prepareStatement(sqlStr);
								stmt.execute(sqlStr);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					sqlStr = "update ordered set customer_name = '"+ o_tf.getText()+"' where (table_num = "+(o_combo.getSelectedIndex()+1)+")";
							 
							 try {
								stmt = dbTest.prepareStatement(sqlStr);
								stmt.execute(sqlStr);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				}
				else{
					table.tableitems[o_combo.getSelectedIndex()].setBackground(Color.yellow);
					order_done[o_combo.getSelectedIndex()] = true;
					saved_customer[o_combo.getSelectedIndex()] = o_tf.getText();
					saved_order_list[o_combo.getSelectedIndex()] = ta1.getText();
					saved_total[o_combo.getSelectedIndex()] = ta2.getText();
					sqlStr = "insert into ordered values ("
							+(o_combo.getSelectedIndex()+1)
							+", "
							+menu.total
							+", '"
							+logoned_employee
							+"', '"
							+ o_tf.getText()
							+"')"
							;
							 
							 try {
								stmt = dbTest.prepareStatement(sqlStr);
								stmt.execute(sqlStr);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				}
			}
			
		}else if(e.getSource() == o_button2){
			menu.total = 0;
			order_done[o_combo.getSelectedIndex()] = false;
			o_tf.setText("");
			ta1.setText("");
			ta2.setText(("총 합계\n") + 0+"원");	

			table.tableitems[o_combo.getSelectedIndex()].setBackground(Color.white);
			
			sqlStr = "delete from ordered where table_num ="
					+(o_combo.getSelectedIndex() +1);
					 
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				stmt.execute(sqlStr);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		else if (e.getSource() == o_button3 && order_done[o_combo.getSelectedIndex()] == false){
			JOptionPane.showMessageDialog(null, (String) "주문 먼저 해주세요.",
					"pay denied", 2);//예외처리10
		}
		
		else if (e.getSource() == o_button3 && order_done[o_combo.getSelectedIndex()] == true){
			String c_name = null;
			String grade = null;
			String s_name = null;
			int order_price = 0;
			int c_acc_cost = 0;
			//to get c_name
			sqlStr = "select customer_name from ordered where table_num ="
					+ (o_combo.getSelectedIndex()+1)
					+ "";
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				ResultSet rs = stmt.executeQuery(sqlStr);
				rs.next();
				c_name= rs.getString(1);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			if(c_name!= null){
				//to get grade
				sqlStr = "select customer_grade from customer where customer_name ='"
						+ c_name
						+ "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs = stmt.executeQuery(sqlStr);
					rs.next();
					grade = rs.getString(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				sqlStr = "select customer_acc_cost from customer where customer_name ='"
						+ c_name
						+ "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					ResultSet rs = stmt.executeQuery(sqlStr);
					rs.next();
					c_acc_cost = rs.getInt(1);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			else{
				grade = "Normal";
			}
			
			//to get s_name
			sqlStr = "select staff_name from ordered where table_num ="
					+ (o_combo.getSelectedIndex()+1)
					+ "";
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				ResultSet rs = stmt.executeQuery(sqlStr);
				rs.next();
				s_name= rs.getString(1);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//to get order_price
			sqlStr = "select order_price from ordered where table_num ="
					+ (o_combo.getSelectedIndex()+1)
					+ "";
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				ResultSet rs = stmt.executeQuery(sqlStr);
				rs.next();
				order_price = rs.getInt(1);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//discount
			if(grade.equals("Gold") ==true){
				order_price = (int) (order_price * (0.7));
			}
			else if(grade.equals("Silver") ==true){
				order_price = (int) (order_price * (0.8));
			}
			else if(grade.equals("Bronze") ==true){
				order_price = (int) (order_price * (0.9));
			}
			
			//update customer
			String new_grade = null;
			int check_new_grade = c_acc_cost + order_price;
			if(check_new_grade >= 1000000){
				new_grade = "Gold";
			}
			else if(check_new_grade >= 500000){
				new_grade = "Silver";
			}
			else if(check_new_grade >= 300000){
				new_grade = "Bronze";
			}
			else{
				new_grade = "Normal";
			}

			
			
			if(c_name !=null){
				sqlStr = "update customer set customer_acc_cost = customer_acc_cost +"+ order_price +" where customer_name = '"
						+ c_name
						+ "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					stmt.execute(sqlStr);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}

			if(c_name !=null){
				sqlStr = "update customer set customer_grade = '"+ new_grade +"' where customer_name = '"
						+ c_name
						+ "'";
				try {
					stmt = dbTest.prepareStatement(sqlStr);
					stmt.execute(sqlStr);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
			
			//update employee
			sqlStr = "update employee set total = total +"+ order_price +" where employee_name = '"
					+ s_name
					+ "'";
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				stmt.execute(sqlStr);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			sold_today = sold_today + order_price;
			
			//여기부턴 취소와 동일
			menu.total = 0;
			order_done[o_combo.getSelectedIndex()] = false;
			o_tf.setText("");
			ta1.setText("");
			ta2.setText(("총 합계\n") + 0+"원");	

			table.tableitems[o_combo.getSelectedIndex()].setBackground(Color.white);
			
			sqlStr = "delete from ordered where table_num ="
					+(o_combo.getSelectedIndex() +1);
					 
			try {
				stmt = dbTest.prepareStatement(sqlStr);
				stmt.execute(sqlStr);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,
					(String) "결제 완료", "Pay finished", 2);
		}
	}
}
