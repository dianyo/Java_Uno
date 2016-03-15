package foop_final;

public class User extends Player
{
	private Table table;
	
	public User(Table t){
		super();
		this.table = t;
	}
	public boolean have_card_to_play(Card tablecard)
	{
		for(int i=0;i<this.hand.size();i++)
		{
			if(checkcanplay(this.hand.get(i), tablecard))
			{
				return true;
			}
		}
		return false;
	}
	public Card play_card(Card tablecard)
	{
		Card c = new Card(-1, -1);
		if(have_card_to_play(tablecard))
		{
			while(c.color == -1 && c.value == -1){
				try{
					Thread.sleep(5);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				c = table.ask_card();
			}
			while(!checkcanplay(c, tablecard))
			{
				table.card_validity(false);
				
				c = new Card(-1, -1);
				while(c.color == -1 && c.value == -1){
					try{
						Thread.sleep(5);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					c = table.ask_card();
				}
			}
			table.card_validity(true);
			int find_card = -1;
			for(int i=0;i<this.hand.size();i++)
			{
				if(hand.get(i).color == c.color && hand.get(i).value == c.value)
				{
					find_card = i;
					break;
				}
			}
			if(find_card != -1){
				this.hand.remove(find_card);
			}
			return c;
		}
		else
		{
			c.color=-1;
			c.value=-1;
			return c;
		}

	}

}