package foop_final;

public class Card implements Comparable<Card>{
	public int color;
	public int value;
	public Card(int c,int v)
	{
		color=c;
		value=v;
	}
	public int compareTo(Card compareCard)
	{
		if(this.color<compareCard.color)
			return 1;
		else if(this.color==compareCard.color && this.value<compareCard.value)
			return 1;
		else if(this.color==compareCard.color && this.value==compareCard.value)
			return 0;
		else 
			return -1;
	} 
}