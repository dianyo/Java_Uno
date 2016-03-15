package foop_final;

import java.util.*;
public class Player{
	public ArrayList<Card> hand = new ArrayList<Card>();
	private int[] colornum={0,0,0,0,0};
	protected boolean checkcanplay(Card c,Card tablecard)
	{
		if(c.color==tablecard.color || c.value==tablecard.value || c.value==13 ||c.value==14 )
			return true;
		else
			return false;
	}
	public Card play_card(Card tablecard)
	{
		Card c = new Card(-1, -1);
		int index=-1;
		for(int i=0;i<this.hand.size();i++)
		{
			if(checkcanplay(this.hand.get(i), tablecard))
			{
				if(index==-1|| colornum[this.hand.get(i).color]>colornum[c.color])
				{
					c.color=this.hand.get(i).color;
					c.value=this.hand.get(i).value;
					index=i;
				}
			}
		}
		if(index != -1)
		{
			colornum[this.hand.get(index).color]--;
			this.hand.remove(index);
			if(c.color==4)
			{
				c.color=0;
				for(int i=1;i<=3;i++)
				{
					if(colornum[i]>colornum[c.color])
						c.color=i;
				}
			}
		}
		return c;
	} 
	public void get_card(Card get)
	{
		this.hand.add(get);
		colornum[get.color]++;
		Collections.sort(this.hand);
		return;
	}
	public ArrayList<Card> show_hand()
	{
		return this.hand;
	}
	public boolean win()
	{
		return this.hand.isEmpty();
	}
	public int getColor(Table table){
		Random r = new Random();
		int color = r.nextInt(4);
		return color;
	}
}




