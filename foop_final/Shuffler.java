package foop_final;

public class Shuffler{
    //DATA:
    public int[] index;
    public int count = 0;

    //ACTIONS:
    public void setSize(int N){
		if (index == null || N != index.length){
		    index = new int[N];
		    initializeIndex();
		    permuteIndex();
		}
    }
    
    public void initializeIndex(){
		for(int i=0;i<index.length;i++)
		    index[i] = i;
    }

    public void permuteIndex(){
		java.util.Random rnd = new java.util.Random();
		for(int i=index.length-1;i>=0;i--){
		    int j = rnd.nextInt(i+1);
		    int tmp = index[j];
		    index[j] = index[i];
		    index[i] = tmp;
		}
    }
    public void backToDeck(int card){
    	index[card] = card;
    }
    public int getNext(){
	    int res = 0;
	    while(index[count] < 0)
	    	count++;
	    if(index[count] >= 0){
	    	res = index[count];
	    	index[count] = -1;
	    	count ++ ;
	    }
		if (count == index.length){
		    permuteIndex();
		    count = 0;
		}
		return res;
    }
}