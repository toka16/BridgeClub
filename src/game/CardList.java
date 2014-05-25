package game;

import java.util.ArrayList;

/*
 * am klass dokumentacia ar davuwere radgan 
 * sheidzleba saertod ar aris sawire am klasis kamoyeneba.
 */
public class CardList {
	private ArrayList<Card> list;
	
	public CardList(){
	}
	
	public void add(Card c){
		list.add(c);
	}
	
	public void addAt(Card c, int index){
		if(index>=list.size())
			list.add(c);
		else if(index>=0)
			list.add(index, c);
	}
	
	public int size(){
		return list.size();
	}
	
	public Card getAt(int index){
		return list.get(index);
	}
	
	public Card removeAt(int index){
		return list.remove(index);
	}
	
	public boolean remove(Card c){
		return list.remove(c);
	}
	
	public ArrayList<Card> getAll(){
		return list;
	}
	
	public boolean hasCard(Card c){
		return list.contains(c);
	}
}
