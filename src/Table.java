import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Table implements ActionListener {
	private JPanel Tpanel = new JPanel();
	private JLabel title = new JLabel("테이블 현황");
	private JPanel table = new JPanel();
	public static JButton[] tableitems = new JButton[20];
	Order order = new Order();
	public JPanel table(){
		title.setBounds(20, 0, 60, 30);
		
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setLayout(new GridLayout(4, 5, 5, 5));
		
		for(int i=0;i<tableitems.length;i++){
			table.add(tableitems[i] = new JButton());
			tableitems[i].setEnabled(false);
			tableitems[i].setText(""+(i+1));
			tableitems[i].addActionListener(this);
			tableitems[i].setHorizontalAlignment(SwingConstants.CENTER);
			tableitems[i].setBorder(BorderFactory.createLineBorder(Color.black));
			tableitems[i].setBackground(Color.WHITE);
		}
		
		Tpanel.setLayout(new BorderLayout());
		Tpanel.add(title, "North");
		Tpanel.add(table, "Center");
		Tpanel.setSize(350,300);
		
		return Tpanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int i=0;i<20;i++){
			if(e.getSource() == tableitems[i]){
				order.o_combo.setSelectedIndex(i);
			}
		}

		
	}
}
