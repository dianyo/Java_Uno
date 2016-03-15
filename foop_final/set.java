package foop_final;

public class set{
	public Card setCard(int theCard){
		int color = theCard / 25;
		int num;
		if(color == 4) //black
			num = (theCard % 25) / 4 + 13;
		else{
			if(theCard % 25 == 0) num = 0;
			else num = (theCard % 25 + 1) / 2;
		}
		Card c = new Card(color, num);
		return  c;
	}
}