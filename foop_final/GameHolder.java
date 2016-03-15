package foop_final;

public class GameHolder{
	public Card on_table = new Card(-1, -1);
	public boolean over = false;
	public int pnum = 1;
	public int dir = 1;
	public boolean skip = false;
	public int draw = 0;
	
	public boolean gameover(){
		return over;
	}
	
	public void updatePlayerNum(){
		pnum += dir;
		if(pnum == 5)
			pnum = 1;
		if(pnum == 0)
			pnum = 4;
	}
	
	public void dealACard(Shuffler deck, Player i){
		set set = new set();
		Card tmp = set.setCard(deck.getNext());
		i.get_card(tmp);
	}
	public void AIplay(int playerNum, Player i, Shuffler deck, Table table){
		while(draw > 0){
				draw--;
				dealACard(deck, i);
			}
		table.update(i.hand, playerNum);
		if(skip){
			skip = false;
			return;
		}
		int times = 0;
		
		while(true){
			table.update(i.hand, playerNum);
			//show message to player
			if(playerNum == 1){
				times ++;
				if(times == 1){}
					//table.show("It's your turn !!!");
				else if(times == 3) table.show("Still you QQ");
				else if(times >= 5) table.show("Poor you, Haha");
			}
			//
			Card choose = i.play_card(on_table);
			if(choose.color == -1 && choose.value == -1){
				if(playerNum == 1){
					table.show("Get new card from deck.");
					try{
						Thread.sleep(500);
					}
					catch(Exception e){}
				}
				dealACard(deck, i);
				table.update(i.hand, playerNum);
			}
			else{
				on_table = choose;
				table.on_table(on_table);
				table.update(i.hand, playerNum);
				if(choose.value == 10){
					skip = true;
				}
				if(choose.value == 11){
					draw += 2;
					skip = true;
				}
				if(choose.value == 12){
					dir *= -1;
				}
				if(choose.value == 14){
					draw += 4;
					skip = true;
				}
				if(choose.value == 13 || choose.value == 14){
					if(playerNum == 1){
						table.show("Please select a color");
						choose.color = table.getColor();
						while(choose.color == -1){
							try{
								Thread.sleep(5);
							}
							catch(Exception e){}
							choose.color = table.getColor();
						}
					}
					else
						choose.color = i.getColor(table);
				}
				
				on_table = choose;
				table.on_table(on_table);
				break;
			}
		}
		if(i.hand.size() == 0)
			over = true;
	}
	public static void main(String[] args) {
		
		GameHolder holder = new GameHolder();
		Table table = new Table();
		//init Players
		Player com1 = new Player();
		Player com2 = new Player();
		Player com3 = new Player();
		User player = new User(table);
		//Create a deck
		Shuffler deck = new Shuffler();
		deck.setSize(108);
		//Create a setter
		set set = new set();
		//deal Card to Players
		for(int i = 0 ; i < 7 ; i++){
			Card tmp = set.setCard(deck.getNext());
			player.get_card(tmp);

			holder.dealACard(deck, com1);
			holder.dealACard(deck, com2);
			holder.dealACard(deck, com3);
		}
		//init first card
		holder.on_table = set.setCard(deck.getNext());
		while(holder.on_table.color == 4){
			holder.on_table = set.setCard(deck.getNext());
		}
		table.on_table(holder.on_table);
		
		//init playing order
		holder.pnum = 1;
		holder.dir = 1;
		//Start play
		
		while(true){
			if(holder.pnum == 1){
				table.showNow(1);
				holder.AIplay(1, player, deck, table);
				if(player.hand.size() == 0){
					table.showWinner(1);
					break;
				} 
			}
			else if(holder.pnum == 2){
				table.showNow(2);
				try{
					Thread.sleep(750);
				}
				catch(InterruptedException e){};
				holder.AIplay(2, com1, deck, table);
				if(holder.gameover()){
					table.showWinner(2);
					break;
				} 
			}
			else if(holder.pnum == 3){
				table.showNow(3);
				try{
					Thread.sleep(750);
				}
				catch(InterruptedException e){};
				holder.AIplay(3, com2, deck, table);
				if(holder.gameover()){
					table.showWinner(3);
					break;
				} 
			}
			else if(holder.pnum == 4){
				table.showNow(4);
				try{
					Thread.sleep(750);
				}
				catch(InterruptedException e){};
				holder.AIplay(4, com3, deck, table);
				if(holder.gameover()){
					table.showWinner(4);
					break;
				} 
			}
			holder.updatePlayerNum();
		}
	}
}
