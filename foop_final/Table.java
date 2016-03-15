	package foop_final;

	import java.awt.Graphics;
	import java.util.*;

	import javax.swing.JFrame;
	import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JButton;
	import javax.swing.JComponent;
	import javax.swing.ImageIcon;
	import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
	import java.awt.event.ActionListener;
	import java.awt.event.ActionEvent;
	import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;

	public class Table extends JFrame implements ActionListener{

		static final long serialVersionUID = 1L;
		private int width, height;
		private resizableButton[][][] buttons = new resizableButton[5][15][2];
		private resizableButton[] chooseColors = new resizableButton[4];
		private ImageLabel[] opp_cards = new ImageLabel[3];
		private ImageLabel card_open;
		private ImageLabel card_panel;
		private ImageLabel color_panel;
		private ImageLabel color_now_text;
		private ImageLabel color_now;
		private ImageIcon[][] deck_icons = new ImageIcon[5][15];
		private ImageIcon[][] opp_icons = new ImageIcon[3][11];
		private ImageLabel[] arrow = new ImageLabel[4];
		private ImageLabel[] player_pic = new ImageLabel[3];
		private boolean can_select = false;
		private Card ret_card = new Card(-1, -1);
		private int ret_choose = -1;
		private ArrayList<Card> my_hand = new ArrayList<Card>();
		private int page_num = 0;
		

		/**
		 * Create the frame.
		 */
		public Table() {

			init_frame();
			
			setup_menu();

			setup_icons();
			
			setup_buttons();
			
			setup_labels();
			
			setup_panel();
			
			setVisible(true);
		}
		
		private void init_frame(){
			setTitle("Game preview");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			double zoom = (double) Math.min((double)screenSize.getWidth() /(double)1220, (double)screenSize.getHeight() / (double)955); 
			int width = (int) (zoom * 1220 * 0.9);
			int height = (int) (zoom * 955 * 0.9);
			this.width = width;
			this.height = height;
			//setBounds(0, 0, width, height);
			setBounds(0, 0, 800, 600);
			ImageLabel panel = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/background.png")), true);
			setContentPane(panel);

			
			GridBagLayout gridBagLayout = new GridBagLayout();
			getContentPane().setLayout(gridBagLayout);
		}
		
		private void setup_menu(){
			Menu menu = new Menu("menu");
			MenuItem item = new MenuItem("I don't wanna play alone");
			MenuItem item_re = new MenuItem("I want to play alone");
			MenuItem item1 = new MenuItem("previous page");
			MenuItem item2 = new MenuItem("next page");
			
			item.addActionListener(this);
			item_re.addActionListener(this);
			item1.addActionListener(this);
			item2.addActionListener(this);
			menu.add(item);
			menu.add(item_re);
			menu.add(item1);
			menu.add(item2);
			
			MenuBar menubar = new MenuBar();
			menubar.add(menu);
			this.setMenuBar(menubar);
		}
		
		private void setup_icons(){
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 15; j++){
					deck_icons[i][j] = new ImageIcon(Table.class.getResource("/foop_final/img/cards/deck/resized/card_" + i + "_" + j + ".png"));
				}
			}
			
			for(int i = 0; i < 11; i++){
				opp_icons[0][i] = new ImageIcon(Table.class.getResource("/foop_final/img/cards/card_left/card_back_left_" + i + ".png"));
				opp_icons[1][i] = new ImageIcon(Table.class.getResource("/foop_final/img/cards/card_top/card_top_" + i + ".png"));
				opp_icons[2][i] = new ImageIcon(Table.class.getResource("/foop_final/img/cards/card_right/card_back_right_" + i + ".png"));
			}
		}

		private void setup_buttons(){
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 15; j++){
					for(int k = 0; k < 2; k++){
						buttons[i][j][k] = new resizableButton(deck_icons[i][j], this.width, this.height, getContentPane(), new Color(65, 143, 50), 0);
						buttons[i][j][k].setBorderPainted(false);
						buttons[i][j][k].setBorder(null);
						buttons[i][j][k].addActionListener(this);
						buttons[i][j][k].setActionCommand(i + "_" + j + "_" + k);
					}
				}
			}
			
			color_panel = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank_300x300.png")), false);
			color_panel.setLayout(new GridLayout(2, 2));
			
			for(int i = 0 ; i < 4 ; i ++){
				String filepath = "/foop_final/img/choose" + i + ".png";
				ImageIcon color_icon = new ImageIcon(Table.class.getResource(filepath));
				chooseColors[i] = new resizableButton(color_icon, this.width, this.height, getContentPane(), new Color(65, 143, 50), i+1);
				chooseColors[i].setBorderPainted(false);
				chooseColors[i].setBorder(null);
				chooseColors[i].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						String cmd = e.getActionCommand();
						ret_choose = Integer.parseInt(cmd);
					}
				});
				chooseColors[i].setActionCommand(i+"");
			}
			
			ImageLabel[] color_panels = new ImageLabel[4];
			for(int i = 0; i < 4; i++){
				color_panels[i] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank_300x300.png")), false);
				color_panels[i].setLayout(new BorderLayout());
			}
			
			color_panels[0].add(chooseColors[0], BorderLayout.EAST);
			color_panels[1].add(chooseColors[2], BorderLayout.WEST);
			color_panels[2].add(chooseColors[3], BorderLayout.EAST);
			color_panels[3].add(chooseColors[1], BorderLayout.WEST);
			
			for(int i = 0; i < 4; i++){
				color_panel.add(color_panels[i]);
			}
			
			GridBagConstraints gbc_color_panel = new GridBagConstraints();
			gbc_color_panel.weightx = 1;
			gbc_color_panel.weighty = 1;
			gbc_color_panel.fill = GridBagConstraints.BOTH;
			gbc_color_panel.gridx = 2;
			gbc_color_panel.gridy = 5;
			getContentPane().add(color_panel, gbc_color_panel);
			color_panel.setVisible(false);
			
		}

		private void setup_labels(){
			
			color_now_text = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/nowcolor.png")), false);
			GridBagConstraints gbc_text = new GridBagConstraints();
			gbc_text.fill = GridBagConstraints.BOTH;
			gbc_text.weightx = 0.5;
			gbc_text.weighty = 0.5;
			gbc_text.gridx = 0;
			gbc_text.gridy = 0;
			gbc_text.ipady = 50;

			getContentPane().add(color_now_text, gbc_text);
			
			color_now = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			GridBagConstraints gbc_color = new GridBagConstraints();
			gbc_color.fill = GridBagConstraints.BOTH;
			gbc_color.weightx = 0.5;
			gbc_color.weighty = 0.5;
			gbc_color.gridx = 1;
			gbc_color.gridy = 0;
			gbc_color.gridwidth = 2;
			gbc_color.ipady = 50;
			
			getContentPane().add(color_now, gbc_color);
			
			GridBagConstraints d = new GridBagConstraints();
			d.fill = GridBagConstraints.BOTH;
			d.insets = new Insets(0, 0, 0, 0);
	        d.weightx = 1.0;
	        d.weighty = 1.0;
	        d.gridx = 0;
	        d.gridy = 0;
	        d.gridwidth = 5;
			
	        ImageLabel blank_panel = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank_300x500.png")), true);
			getContentPane().add(blank_panel, d);
			
			d.insets = new Insets(0, 0, 0, 0);
			d.gridwidth = 1;
			d.gridx = 0;
			d.gridy = 5;
			
			opp_cards[0] = new ImageLabel(opp_icons[0][7], false);
			getContentPane().add(opp_cards[0], d);
			
			d.ipady = 80;
			d.insets = new Insets(70, 0, 0, 0);
			d.gridx = 0;
			d.gridy = 3;
			d.gridwidth = 5;
			
			opp_cards[1] = new ImageLabel(opp_icons[1][7], false);
			getContentPane().add(opp_cards[1], d);
			
			d.ipady = 0;
			d.insets = new Insets(0, 0, 0, 0);
			d.gridwidth = 1;
			d.gridx = 4;
			d.gridy = 5;
			
			opp_cards[2] = new ImageLabel(opp_icons[2][7], false);
			getContentPane().add(opp_cards[2], d);
			
			arrow[0] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.ipady = 30;
			d.gridy = 6;
			d.gridx = 2;
			d.weighty = 0.4;
			d.insets = new Insets(10, 0, 10, 0);
			getContentPane().add(arrow[0], d);
			
			arrow[1] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.gridy = 5;
			d.gridx = 1;
			d.weighty = 0.1;
			d.weightx = 0.1;
			d.insets = new Insets(10, 0, 10, 0);
			getContentPane().add(arrow[1], d);
			
			
			arrow[2] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.gridy = 4;
			d.gridx = 2;
			d.ipady = 0;
			d.ipadx = 0;
			d.insets = new Insets(10, 0, 10, 0);
			getContentPane().add(arrow[2], d);
			
			arrow[3] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.gridy = 5;
			d.gridx = 3;
			d.ipady = 0;
			d.insets = new Insets(0, 0, 0, 0);
			getContentPane().add(arrow[3], d);
			
			player_pic[0] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.ipady = 50;
			d.gridy = 4;
			d.gridx = 0;
			d.anchor = GridBagConstraints.WEST;
			getContentPane().add(player_pic[0], d);
			
			player_pic[1] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.gridy = 2;
			d.gridx = 0;
			d.gridwidth = 5;
			d.anchor = GridBagConstraints.CENTER;
			getContentPane().add(player_pic[1], d);
			
			player_pic[2] = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), false);
			d.gridwidth = 1;
			d.gridy = 4;
			d.gridx = 4;
			d.anchor = GridBagConstraints.EAST;
			getContentPane().add(player_pic[2], d);
			
		}
		
		private void setup_panel(){
			
			card_open = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/cards/deck/card_back.png")), false);
			GridBagConstraints gbc_card_open = new GridBagConstraints();
			gbc_card_open.weightx = 0.8;
			gbc_card_open.weighty = 0.8;
			gbc_card_open.fill = GridBagConstraints.BOTH;
			gbc_card_open.gridx = 2;
			gbc_card_open.gridy = 5;
			gbc_card_open.ipady = 80;
			getContentPane().add(card_open, gbc_card_open);
			
			card_panel = new ImageLabel(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")), true);
			card_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			GridBagConstraints gbc_card_panel = new GridBagConstraints();
			gbc_card_panel.insets = new Insets(0, 0, 0, 0);
			gbc_card_panel.ipady = 100;
			gbc_card_panel.fill = GridBagConstraints.BOTH;
			gbc_card_panel.gridwidth = 5;
			gbc_card_panel.weightx = 1;
			gbc_card_panel.weighty = 1;
			gbc_card_panel.gridx = 0;
			gbc_card_panel.gridy = 8;
			getContentPane().add(card_panel, gbc_card_panel);
		}
		
		public void show(String message){
			JOptionPane.showMessageDialog(this, message);
		}
		
		public void on_table(Card open){
			int color = open.color;
			color_now.setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/nowcolor" + color + ".png")));
			int value = open.value;
			card_open.setIcon(deck_icons[color][value]);
			card_open.revalidate();
			card_open.repaint();
		}
		
		public void showWinner(int i){
			JOptionPane.showMessageDialog(this, "The game is over, congratulation to the winner: player no." + i);
		}
		
		public int getColor(){
			int ret = -1;
			color_panel.setVisible(true);
			if(ret_choose != -1){
				color_panel.setVisible(false);
				ret = ret_choose;
				ret_choose = -1;
			}
			return ret;
		}
		
		public void showNow(int i){
			String filepath = "/foop_final/img/arrow" + i + ".png";
			for(int j = 0; j < 4; j++)
				arrow[j].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")));
			
			arrow[i-1].setIcon(new ImageIcon(Table.class.getResource(filepath)));
			revalidate();
			repaint();
		}
		
		public void update(ArrayList<Card> hand, int pno){
			if(pno == 1){
				my_hand = hand;
				card_panel.removeAll();
				card_panel.revalidate();
				card_panel.repaint();
				for(int i = page_num * 8; i < hand.size() && i < page_num * 8 + 8; i++){
					Card tmp = hand.get(i);
					boolean twice_flag = false;
					for(int j = 0; j < i; j++){
						if(hand.get(j).color == tmp.color && hand.get(j).value == tmp.value){
							twice_flag = true;
							break;
						}
					}
					int color = tmp.color;
					int value = tmp.value;
					if(!twice_flag){
						card_panel.add(buttons[color][value][0]);
					}
					else{
						card_panel.add(buttons[color][value][1]);
					}
				}
				card_panel.revalidate();
				card_panel.repaint();
			}
			else{
				if(hand.size() <= 10){
					opp_cards[pno-2].setIcon(opp_icons[pno-2][hand.size()]);
				}
				else{
					opp_cards[pno-2].setIcon(opp_icons[pno-2][10]);
				}
				
				opp_cards[pno-2].revalidate();
				opp_cards[pno-2].repaint();
			}
		}
		
		public Card ask_card(){
			Card ret = new Card(-1, -1);
			can_select = true;
			if(ret_card.color != -1 && ret_card.value != -1){
				ret = ret_card;
				ret_card = new Card(-1, -1);
				can_select = false;
			}
			return ret;
		}
		
		public void card_validity(boolean valid){
			/*if(valid){
				JOptionPane.showMessageDialog(this, "You have played a card.");
			}*/
		}
		
		class ImagePanel extends JComponent {
			static final long serialVersionUID = 2L;
		    private Image image;
		    public ImagePanel(Image image) {
		        this.image = image;
		    }
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(image, 0, 0, this);
		    }
		}
		
		public static class ImageLabel extends JPanel {
	        private static int counter = 1;
	        private ImageIcon icon;
	        private int id = counter++;
	        private boolean is_back = false;
	        private static final long serialVersionUID = 3L;

	        public ImageLabel(ImageIcon icon, boolean is_back) {
	            super();
	            setOpaque(false);
	            this.icon = icon;
	            this.is_back = is_back;
	        }
	        
	        public void setIcon(ImageIcon icon){
	        	this.icon = icon;
	        }

	        @Override
	        public Dimension getPreferredSize() {
	            return new Dimension(icon.getIconWidth(), icon.getIconHeight());
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            int width = 0, height = 0;
	            if(this.is_back){
		            double zoom_width = (double) getWidth() / icon.getIconWidth();
		            double zoom_height = (double) getHeight() / icon.getIconHeight();
		            width = (int) (zoom_width * icon.getIconWidth());
		            height = (int) (zoom_height * icon.getIconHeight());
		        }
	            else{
	            	double zoom = Math.min((double) getWidth() / icon.getIconWidth(), (double) getHeight() / icon.getIconHeight());
	            	width = (int) (zoom * icon.getIconWidth());
	            	height = (int) (zoom * icon.getIconHeight());
	            }
	            g.drawImage(icon.getImage(), (getWidth() - width) / 2, (getHeight() - height) / 2, width, height, this);
	            g.setFont(g.getFont().deriveFont(36.0f));
	            //g.drawString(String.valueOf(id), getWidth() / 2, getHeight() / 2);
	        }
	    }
		
		public static class resizableButton extends JButton{
			private static final long serialVersionUID = 4L;
			private ImageIcon icon;
			private int width, height;
			private Container panel;
			private Color color;
			private int alignment;
			
			public resizableButton(ImageIcon icon, int width, int height, Container panel, Color color, int alignment) {
	            super();
	            setOpaque(false);
	            setIcon(icon);
	            this.icon = icon;
	            this.width = width;
	            this.height = height;
	            this.panel = panel;
	            this.color = color;
	            this.alignment = alignment;
	        }
			
			public void setIcon(ImageIcon icon){
				this.icon = icon;
			}
			
			@Override
	        public Dimension getPreferredSize() {
				double zoom = Math.min((double)panel.getWidth() / this.width,(double) panel.getHeight() / this.height);
	            return new Dimension((int)(icon.getIconWidth()*zoom),(int) (icon.getIconHeight()*zoom));
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            int width = 0, height = 0;
	            
	           	double zoom = Math.min((double) getWidth() / icon.getIconWidth(), (double) getHeight() / icon.getIconHeight());
	           	width = (int) (zoom * icon.getIconWidth());
	           	height = (int) (zoom * icon.getIconHeight());
	           	
	           	//System.out.println(zoom + "");
	            
	           	//g.clearRect(0, 0, getWidth(), getHeight());
	           	g.setColor(color);
	           	g.fillRect(0, 0, getWidth(), getHeight());
	           	
	           	if(this.alignment == 0)
	           		g.drawImage(icon.getImage(), (getWidth() - width) / 2, (getHeight() - height) / 2, width, height, this);
	           	else if(this.alignment == 1)
	           		g.drawImage(icon.getImage(), (getWidth() - width), (getHeight() - height), width, height, this);
	           	else if(this.alignment == 2)
	           		g.drawImage(icon.getImage(), 0, 0, width, height, this);
	           	else if(this.alignment == 3)
	           		g.drawImage(icon.getImage(), 0, (getHeight() - height), width, height, this);
	           	else if(this.alignment == 4)
	           		g.drawImage(icon.getImage(), getWidth() - width, 0, width, height, this);
	           	g.setFont(g.getFont().deriveFont(36.0f));
	            //g.drawString(String.valueOf(id), getWidth() / 2, getHeight() / 2);
	        }
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof MenuItem){
				MenuItem m = (MenuItem) e.getSource();
				if (m.getLabel() == "I don't wanna play alone"){
					player_pic[0].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/player_left.png")));
					player_pic[1].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/player_mid.png")));
					player_pic[2].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/player_right.png")));
					revalidate();
					repaint();
				}
				else if(m.getLabel() == "I want to play alone"){
					player_pic[0].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")));
					player_pic[1].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")));
					player_pic[2].setIcon(new ImageIcon(Table.class.getResource("/foop_final/img/blank.png")));
					revalidate();
					repaint();
				}
				else if(m.getLabel() == "previous page"){
					if(page_num > 0){
						page_num--;
						update(my_hand, 1);
					}
				}
				else if(m.getLabel() == "next page"){
					if(page_num < (my_hand.size()-1)/8){
						page_num++;
						update(my_hand, 1);
					}
				}
			}
			else{
				if(!can_select)
					return;
				String cmd = e.getActionCommand();
				String[] parsed_cmd = cmd.split("_");
				int color = Integer.parseInt(parsed_cmd[0]);
				int value = Integer.parseInt(parsed_cmd[1]);
				
				ret_card = new Card(color, value);
			}
		}
	}
