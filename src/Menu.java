import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu implements ActionListener {
	private JPanel Mpanel = new JPanel();
	private JPanel menu = new JPanel();
    private JLabel title = new JLabel("메뉴");
    public static JButton[] menu_items = new JButton[20];
	public static String[] menu_name = new String[20];
	public static int[] menu_price = new int[20];
	public static int[] menu_sold = new int [20];
	
    public static int total;
    
    public JPanel menu() throws SQLException{
    	Mpanel.setLayout(new BorderLayout());
    	title.setBounds(20, 0, 60, 30);
    	Mpanel.add(title, "North"); 
    	Mpanel.add(menu, "Center");
    	Mpanel.setSize(340,340);
    	
    	menu.setBorder(BorderFactory.createLineBorder(Color.black));
		menu.setLayout(new GridLayout(10, 2));
		for(int i=0;i<10;i++){
    		menu.add(menu_items[i] = new JButton());
    		menu_items[i].addActionListener(this);
    		menu.add(menu_items[i+10] = new JButton());
    		menu_items[i+10].addActionListener(this);
		}
		for(int i=0;i<20;i++){
			menu_sold[i] =0;
		}
		

    	return Mpanel;
    }
    
    public void actionPerformed(ActionEvent e){
    	Order order = new Order();
    	Table table = new Table();
    	
    	for(int i=0;i<menu_items.length;i++){
    		if(e.getSource() == menu_items[i] && menu_name[i] !=null){
    			menu_sold[i] ++;
    			total += menu_price[i];
    			order.ta1.setText(order.ta1.getText() + menu_name[i] + "  "+ menu_price[i] + "\n");
    			order.ta2.setText(("총 합계\n") + total+"원");
    		}
    	}
    }
}
