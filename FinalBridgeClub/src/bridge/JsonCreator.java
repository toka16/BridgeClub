package bridge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import bridge.ConvertStringToEnumerator.Suit;
import bridge.Table.PlayingStage;
import bridge.Table.Side;
import bridge.Table.Vulnerable;

public class JsonCreator {
	private Table table;
	private Gson json;
	public JsonCreator(Table table){
		this.table = table;
		json = new Gson();
	}
	
	public String newUserSit(Side side, User user){
		String res = "new user "+user.getUsername()+" sits at "+side.name();
		return res;
	}
	
	public String userIsReady(Side side){
		String res = "side "+side.name()+" is ready to play";
		return res;
	}
	
	public String userAway(Side side){
		String res = "side "+side.name()+" is away";
		return res;
	}
	
	public String atNewDeal(){
		String res = "new deal";
		return res;
	}
	
	public String newBidForObserver(Side side, Bid bid){
		String res = "side "+side.name()+" made new bid: "+bid.getBidNominal().name()+bid.getBidSuit().name();
		return res;
	}
	
	public String newBidForPlayer(Side side, Bid bid, Side forWho){
		String res = "side "+side.name()+" made new bid: "+bid.getBidNominal().name()+bid.getBidSuit().name();
		return res;
	}
	
	public String contractDeclared(PlayingDeal pdeal){
		String res = "contract is FIVECLUB";
		return res;
	}
	
	public String newCardForObserver(Side side, Card card){
		String res = "side "+side.name()+" moved card "+card.getNominal().name()+card.getSuit().name();
		return res;
	}
	
	public String newCardForPlayer(Side side, Card card, Side forWho){
		String res = "side "+side.name()+" made new bid: "+card.getNominal().name()+card.getSuit().name();
		return res;
	}
	
	public String playingDealResults(PlayingDeal pdeal){
		String res = "result is "+pdeal.getTakenHand()+"/"+pdeal.getLostHand();
		return res;
	}

	
	public String tableDescription(){
		Map<String, Object> mapTable = new HashMap<String, Object>();
		mapTable.put("center", createCenterMap());
		mapTable.put("tablo", createTabloMap());
		mapTable.put("side", createSideMap());
		mapTable.put("arrow", createArrowMap());
		mapTable.put("just", createJustMap());
		mapTable.put("middle", createMiddleMap());
		mapTable.put("last", createLastMap());
		
		return json.toJson(mapTable);
	}
	
	
	public String tableDescriptionForSide(Side side){
		Map<String, Object> mapTable = new HashMap<String, Object>();
		mapTable.put("center", createCenterMap());
		mapTable.put("tablo", createTabloMap());
		mapTable.put("side", createSideMapForSide(side));
		mapTable.put("arrow", createArrowMapForSide(side));
		mapTable.put("just", createJustMapForSide(side));
		mapTable.put("middle", createMiddleMapForSide(side));
		mapTable.put("last", createLastMapForSide(side));
		
		return json.toJson(mapTable);
	}

	
	private Map<String, Object> createLastMap(){
		Map<String, Object> lastMap = new HashMap<String, Object>();
		lastMap.put("top", createLastMapForObserver(Side.NORTH));
		lastMap.put("right", createLastMapForObserver(Side.EAST));
		lastMap.put("bottom", createLastMapForObserver(Side.SOUTH));
		lastMap.put("left", createLastMapForObserver(Side.WEST));
		return lastMap;
	}
	
	private Map<String, Object> createLastMapForSide(Side side){
		Map<String, Object> lastMap = new HashMap<String, Object>();
		if(table.getStage().ordinal() < PlayingStage.Playing.ordinal()){
			boolean valid = side.equals(table.getCurrentSide());
			lastMap.put("bottom", createLastMap(side, true, valid));
			side = side.nextSide();
			valid = side.equals(table.getCurrentSide());
			lastMap.put("left", createLastMap(side, false, valid));
			side = side.nextSide();
			valid = side.equals(table.getCurrentSide());
			lastMap.put("top", createLastMap(side, false, valid));
			side = side.nextSide();
			valid = side.equals(table.getCurrentSide());
			lastMap.put("right", createLastMap(side, false, valid));
		}else if(table.getStage().equals(PlayingStage.Playing)){
			Side contracter = table.getBidding().getContracter();
			Side dummy = contracter.nextSide().nextSide();
			Side current = table.getCurrentSide();
			lastMap.put("bottom", createLastMap(side, true, (side.equals(current) && !side.equals(dummy))));
			side = side.nextSide();
			lastMap.put("left", createLastMap(side, side.equals(dummy), false));
			side = side.nextSide();
			lastMap.put("top", createLastMap(side, side.equals(dummy), current.equals(dummy)));
			side = side.nextSide();
			lastMap.put("right", createLastMap(side, side.equals(dummy), false));
		}else{
			lastMap.put("bottom", createLastMap(side, true, false));
			side = side.nextSide();
			lastMap.put("left", createLastMap(side, true, false));
			side = side.nextSide();
			lastMap.put("top", createLastMap(side, true, false));
			side = side.nextSide();
			lastMap.put("right", createLastMap(side, true, false));
		}
		return lastMap;
	}
	
	
	private Map<String, Object> createLastMap(Side side, boolean visible, boolean valid) {
		Map<String, Object> last = new HashMap<String, Object>();
		last.put("cardsSection", createCardsSection(side, visible, valid));
		if(visible && table.getStage().equals(PlayingStage.Bidding)){
			last.put("bidsBoxSection", createBidBoxMap(side));
		}else{
			last.put("bidsBoxSection", clearBidBox());
		}
		return last;
	}
	
	private Map<String, Object> createLastMapForObserver(Side side) {
		Map<String, Object> last = new HashMap<String, Object>();
		last.put("cardsSection", createCardsSection(side, true, false));
		last.put("bidsBoxSection", clearBidBox());
		return last;
	}
	
	
	private Map<String, Object> clearBidBox(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("empty", "1");
		return map;
	}
	
	
	private Map<String, Object> createBidBoxMap(Side side){
		Bid lastBid = table.getBidding().getLastBid();
		Side lastSide = table.getBidding().getLastSide();
		Map<String, Object> bidBoxMap = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal))
			bidBoxMap.put("empty", "1");
		else{
			bidBoxMap.put("empty", "0");

			if(table.getCurrentSide().equals(side)){
				if(lastBid!=null){
					bidBoxMap.put("nominal", lastBid.getBidNominal().ordinal()+"");
					bidBoxMap.put("suit", lastBid.getBidSuit().ordinal()+"");
					
					String doubleValue;
					if(side.isOposite(lastSide)){
						if(lastBid.getBidDoubledFlag()==0){
							doubleValue = "1";
						}else if(lastBid.getBidDoubledFlag()==1){
							doubleValue = "2";
						}else{
							doubleValue = "0";
						}
					}else{
						doubleValue = "0";
					}
					bidBoxMap.put("double", doubleValue);
				}else{
					bidBoxMap.put("nominal", "0");
					bidBoxMap.put("suit", "0");
					bidBoxMap.put("double", "0");
				}
			}else{
				bidBoxMap.put("nominal", "8");
				bidBoxMap.put("suit", "0");
				bidBoxMap.put("double", "0");
			}
		}
		
		return bidBoxMap;
	}
	
	
	private Map<String, Object> createCardsSection(Side side, boolean visible, boolean valid){
		Map<String, Object> cards = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal) ||
				table.getStage().equals(PlayingStage.WaitForAccept)){
			cards.put("empty", "1");
			cards.put("clubs", new Object[]{});
			cards.put("hearts", new Object[]{});
			cards.put("diams", new Object[]{});
			cards.put("spades", new Object[]{});
			cards.put("shirt", new Object[]{});
		}else{
			List<Card> remainCards;
			PartOfTable part = table.getPart(side);
			if(part!=null){
				cards.put("empty", "0");
				if(table.getStage().ordinal() < PlayingStage.PlayingOver.ordinal()){
						remainCards = part.getCardList();
				}else
					remainCards = table.getPlayingDeal().getDeal().getSideCards(side);
			
				if(visible){
					List<Card> validCards;
					if(valid && (table.getStage().equals(PlayingStage.BiddingOver) || 
								 table.getStage().equals(PlayingStage.Playing))){
					
						if( !table.getPlayHand().allDone()){
							PlayHand hand = table.getPlayHand();
							Card leadCard = hand.getCard(hand.getSideLeader());
							validCards = part.getValidCardsList(leadCard);
						}else
							validCards = remainCards;
					}else{
						validCards = new ArrayList<Card>();
					}
					
					cards.put("clubs", createCardsMapArray(remainCards, validCards, Suit.CLUB));
					cards.put("diams", createCardsMapArray(remainCards, validCards, Suit.DIAMOND));
					cards.put("hearts", createCardsMapArray(remainCards, validCards, Suit.HEART));
					cards.put("spades", createCardsMapArray(remainCards, validCards, Suit.SPADE));
					cards.put("shirt", new String[]{"0"});
				}else{
					cards.put("shirt", new String[]{remainCards.size()+""});
					cards.put("clubs", new Object[]{});
					cards.put("hearts", new Object[]{});
					cards.put("diams", new Object[]{});
					cards.put("spades", new Object[]{});
				}
			}else
				cards.put("empty", "1");
		}
		return cards;
	}
	
	
	
	private List<Map<String, Object>> createCardsMapArray(List<Card> remainCardsList, List<Card> validCards, Suit suit){
		List<Map<String, Object>> cardsMapArray = new ArrayList<Map<String, Object>>();
		Card tempCard;
		Object[] remainCards = remainCardsList.toArray();
		Arrays.sort(remainCards);
		for(int i=0; i<remainCards.length; i++){
			tempCard = (Card)remainCards[i];
			if(tempCard.getSuit().equals(suit)){
				Map<String, Object> curCard = new HashMap<String, Object>();
				curCard.put("nominal", tempCard.getNominal().ordinal()+"");
				if(validCards.contains(tempCard)){
					curCard.put("valid", "1");
				}else{
					curCard.put("valid", "0");
				}
				cardsMapArray.add(curCard);
			}
		}
		return cardsMapArray;
	}

	private Map<String, Object> createMiddleMap(){
		return createMiddleMapForSide(Side.SOUTH);
	}
	
	private Map<String, Object> createMiddleMapForSide(Side side){
		Map<String, Object> middle = new HashMap<String, Object>();
		middle.put("bottom", createMiddle(side));
		middle.put("left", createMiddle(side.nextSide()));
		middle.put("top", createMiddle(side.nextSide().nextSide()));
		middle.put("right", createMiddle(side.nextSide().nextSide().nextSide()));
		return middle;
	}
	
	
	private Map<String, Object> createMiddle(Side side){
		Map<String, Object> middle = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal) ||
				table.getStage().equals(PlayingStage.WaitForAccept)){
			middle.put("empty", "1");
		}else{
			middle.put("empty", "0");
			if(table.getStage().equals(PlayingStage.Bidding)||
					table.getStage().equals(PlayingStage.BiddingOver) ||
					table.getStage().equals(PlayingStage.PlayingOver)){
				List<Bid> bids = table.getBidding().getSideBiddingSet(side);
				ArrayList<Map<String, Object>> bidsMap = bidListToMaps(bids);
				middle.put("dealedBids", bidsMap);
			}else{
				Card card = table.getPlayHand().getCard(side);
				if(card!=null){
					middle.put("playedCard", cardToMap(card));
				}else{
					middle.put("empty", "1");
				}
			}
		}
		return middle;
	}
	
	private Map<String, Object> cardToMap(Card card){
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("suit", card.getSuit().ordinal()+"");
		cardMap.put("nominal", card.getNominal().ordinal()+"");
		return cardMap;
	}
	
	private ArrayList<Map<String, Object>> bidListToMaps(List<Bid> bids) {
		ArrayList<Map<String, Object>> bidsMap = new ArrayList<Map<String, Object>>();
		for(int i=0; i<bids.size(); i++){
			bidsMap.add(bidToMap(bids.get(i)));
		}
		return bidsMap;
	}

	private Map<String, Object> bidToMap(Bid bid){
		Map<String, Object> bidMap = new HashMap<String, Object>();
		bidMap.put("suit", bid.getBidSuit().ordinal()+"");
		bidMap.put("nominal", bid.getBidNominal().ordinal()+"");
		bidMap.put("double", bid.getBidDoubledFlag()+"");
		return bidMap;
	}

	
	private Map<String, Object> createJustMap(){
		
		return createJustMapForSide(Side.SOUTH);
	}
	
	private Map<String, Object> createJustMapForSide(Side side){
		Map<String, Object> justMap = new HashMap<String, Object>();
		justMap.put("top", createTopJustForSide(side));
		justMap.put("right", createRightJustForSide(side));
		justMap.put("bottom", createBottomJustForSide(side));
		justMap.put("left", createLeftJustForSide(side));
		
		return justMap;
	}
	
	
	private Map<String, Object> createTopJustForSide(Side side){
		PartOfTable part = table.getPart(side.nextSide().nextSide());
		Map<String, Object> just;
		if(part!=null){
			just = createJust(part);
			just.put("empty", "0");
		}else{
			just = new HashMap<String, Object>();
			just.put("empty", "1");
		}
		return just;
	}

	
	private Map<String, Object> createRightJustForSide(Side side){
		PartOfTable part = table.getPart(side.nextSide().nextSide().nextSide());
		Map<String, Object> just;
		if(part!=null){
			just = createJust(part);
			just.put("empty", "0");
		}else{
			just = new HashMap<String, Object>();
			just.put("empty", "1");
		}
		return just;
	}
	
	
	private Map<String, Object> createBottomJustForSide(Side side){
		PartOfTable part = table.getPart(side);
		Map<String, Object> just;
		if(part!=null){
			just = createJust(part);
			just.put("empty", "0");
		}else{
			just = new HashMap<String, Object>();
			just.put("empty", "1");
		}
		return just;
	}

	
	private Map<String, Object> createLeftJustForSide(Side side){
		PartOfTable part = table.getPart(side.nextSide());
		Map<String, Object> just;
		if(part!=null){
			just = createJust(part);
			just.put("empty", "0");
		}else{
			just = new HashMap<String, Object>();
			just.put("empty", "1");
		}
		return just;
	}
	
	private Map<String, Object> createJust(PartOfTable part){
		Map<String, Object> just = new HashMap<String, Object>();
		String active = "0";
		if(table.getStage().ordinal() >= PlayingStage.Bidding.ordinal() && 
				table.getStage().ordinal() <= PlayingStage.Playing.ordinal()){
			if(table.getCurrentSide().equals(part.getUser().getSide())){
				active = "1";
			}
		}
		just.put("timer", createTimerMap(part.getTimer(), active));
		just.put("nickname", part.getUser().getUsername());
		return just;
	}
	
	private Map<String, Object> createTimerMap(MyTimer t, String active){
		Map<String, Object> timerMap = new HashMap<String, Object>();
		timerMap.put("time", t.getTimeLefted()+"");
		timerMap.put("active", active);
		return timerMap;
	}
	
	private Map<String, Object> createArrowMap(){
		Map<String, Object> arrowMap = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal) || table.getStage().equals(PlayingStage.PlayingOver)){
			if(table.getPart(Side.NORTH)==null)
				arrowMap.put("top", createArrow("0", "1"));
			if(table.getPart(Side.EAST)==null)
				arrowMap.put("right", createArrow("0", "1"));
			if(table.getPart(Side.SOUTH)==null)
				arrowMap.put("bottom", createArrow("0", "1"));
			if(table.getPart(Side.WEST)==null)
				arrowMap.put("left", createArrow("0", "1"));{
			}
		}else if(table.getStage().ordinal() >= PlayingStage.Bidding.ordinal() &&
				 table.getStage().ordinal() <= PlayingStage.Playing.ordinal()){
			Side curSide = table.getCurrentSide();
			if(curSide.equals(Side.EAST)){
				arrowMap.put("right", createArrow("0", "0"));
			}else if(curSide.equals(Side.WEST)){
				arrowMap.put("left", createArrow("0", "0"));
			}else if(curSide.equals(Side.NORTH)){
				arrowMap.put("top", createArrow("0", "0"));
			}else if(curSide.equals(Side.SOUTH)){
				arrowMap.put("bottom", createArrow("0", "0"));
			}
		}else{
			fillAcceptedArrowMap(arrowMap);
		}
		return arrowMap;
	}
	
	private void fillAcceptedArrowMap(Map<String, Object> arrowMap){
		if(table.playerAccepted(Side.NORTH))
			arrowMap.put("top", createArrow("1", "0"));
		else
			arrowMap.put("top", createArrow("0", "0"));
		if(table.playerAccepted(Side.EAST))
			arrowMap.put("right", createArrow("1", "0"));
		else
			arrowMap.put("right", createArrow("0", "0"));
		if(table.playerAccepted(Side.SOUTH))
			arrowMap.put("bottom", createArrow("1", "0"));
		else
			arrowMap.put("bottom", createArrow("0", "0"));
		if(table.playerAccepted(Side.WEST))
			arrowMap.put("left", createArrow("1", "0"));
		else
			arrowMap.put("left", createArrow("0", "0"));
	}
	
	private Map<String, Object> createArrowMapForSide(Side side){
		Map<String, Object> arrowMap = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal)){
			if(table.getPart(side.nextSide().nextSide())==null)
				arrowMap.put("top", createArrow("0", "0"));
			if(table.getPart(side.nextSide().nextSide().nextSide())==null)
				arrowMap.put("right", createArrow("0", "0"));
			if(table.getPart(side)==null)
				arrowMap.put("bottom", createArrow("0", "0"));
			if(table.getPart(side.nextSide())==null)
				arrowMap.put("left", createArrow("0", "0"));{
			}
				
		}else if(table.getStage().equals(PlayingStage.WaitForAccept) ||
				table.getStage().equals(PlayingStage.PlayingOver)){
			fillAcceptedArrowMapForSide(arrowMap, side);
		}else if(table.getStage().ordinal() >= PlayingStage.Bidding.ordinal() && 
				table.getStage().ordinal() <= PlayingStage.Playing.ordinal()){
			Side curSide = table.getCurrentSide();
			if(curSide.equals(side.nextSide().nextSide().nextSide())){
				arrowMap.put("right", createArrow("0", "0"));
			}else if(curSide.equals(side.nextSide())){
				arrowMap.put("left", createArrow("0", "0"));
			}else if(curSide.equals(side.nextSide().nextSide())){
				arrowMap.put("top", createArrow("0", "0"));
			}else if(curSide.equals(side)){
				arrowMap.put("bottom", createArrow("0", "0"));
			}
		}
		return arrowMap;
	}
	
	private void fillAcceptedArrowMapForSide(Map<String, Object> arrowMap, Side side){
		if(table.playerAccepted(side.nextSide().nextSide()))
			arrowMap.put("top", createArrow("1", "0"));
		else
			arrowMap.put("top", createArrow("0", "0"));
		if(table.playerAccepted(side.previousSide()))
			arrowMap.put("right", createArrow("1", "0"));
		else
			arrowMap.put("right", createArrow("0", "0"));
		if(table.playerAccepted(side))
			arrowMap.put("bottom", createArrow("1", "0"));
		else
			arrowMap.put("bottom", createConfirm());
		if(table.playerAccepted(side.nextSide()))
			arrowMap.put("left", createArrow("1", "0"));
		else
			arrowMap.put("left", createArrow("0", "0"));
	}
	
	
	private Map<String, Object> createConfirm(){
		Map<String, Object> confirm = new HashMap<String, Object>();
		confirm.put("confirm", "1");
		confirm.put("empty", "0");
		return confirm;
	}
	
	private Map<String, Object> createSideMap(){
//		Map<String, Object> sideMap = new HashMap<String, Object>();
//		sideMap.put("top", createSide("N", "1"));
//		sideMap.put("right", createSide("E", "0"));
//		sideMap.put("bottom", createSide("S", "1"));
//		sideMap.put("left", createSide("W", "0"));
		return createSideMapForSide(Side.SOUTH);
	}
	
	private Map<String, Object> createSideMapForSide(Side side){
		Map<String, Object> sideMap = new HashMap<String, Object>();
		String index = side.name().substring(0, 1);
		String topBottom;
		String leftRight;
		if(table.getStage().equals(PlayingStage.WaitForDeal) ||
				table.getStage().equals(PlayingStage.WaitForAccept)){
			topBottom = "0";
			leftRight = "0";
		}else{
			Vulnerable vul = table.getPlayingDeal().getDeal().getVulnerable();
			if(vul.equals(Vulnerable.BOTHLINE)){
				topBottom = leftRight = "1";
			}else if(vul.equals(Vulnerable.NOLINE)){
				topBottom = leftRight = "0";
			}else if(vul.equals(Vulnerable.EW)){
				if(side.equals(Side.EAST) || side.equals(Side.WEST)){
					topBottom = "1";
					leftRight = "0";
				}else{
					topBottom = "0";
					leftRight = "1";
				}
			}else{
				if(side.equals(Side.EAST) || side.equals(Side.WEST)){
					topBottom = "0";
					leftRight = "1";
				}else{
					topBottom = "1";
					leftRight = "0";
				}
			}
		}
		
		sideMap.put("bottom", createSide(index, topBottom));
		side=side.nextSide();
		index = side.name().substring(0, 1);
		sideMap.put("left", createSide(index, leftRight));
		side=side.nextSide();
		index = side.name().substring(0, 1);
		sideMap.put("top", createSide(index, topBottom));
		side=side.nextSide();
		index = side.name().substring(0, 1);
		sideMap.put("right", createSide(index, leftRight));
		return sideMap;
	}
	
	private Map<String, Object> createSide(String value, String vulnerable){
		Map<String, Object> side = new HashMap<String, Object>();
		side.put("value", value);
		side.put("vulnerable", vulnerable);
		return side;
	}
	
	
	private Map<String, Object> createArrow(String empty, String valid){
		Map<String, Object> arrow = new HashMap<String, Object>();
		arrow.put("empty", empty);
		arrow.put("valid", valid);
		return arrow;
	}
	
	
	private Map<String, Object> createCenterMap(){
		Map<String, Object> mapDealMark = new HashMap<String, Object>();
		Map<String, Object> mapCenter = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.WaitForDeal) ||
				table.getStage().equals(PlayingStage.WaitForAccept))
			mapDealMark.put("empty", "1");
		else{
			String numDeal = ""+table.getPlayingDeal().getDeal().getDealNumber();
			mapDealMark.put("numDeal", numDeal);
			mapDealMark.put("empty", "0");
			mapCenter.put("dealMark", mapDealMark);
		}
		return mapCenter;
	}
	
	
	private Map<String, Object> createTabloMap(){
		Map<String, Object> tabloMap = new HashMap<String, Object>();
		tabloMap.put("top", createTopTablo());
		tabloMap.put("bottom", createBottomTablo());
		return tabloMap;
	}
	
	

	private Object createBottomTablo() {
		Map<String, Object> bottomTablo = new HashMap<String, Object>();
		if(table.getStage().equals(PlayingStage.PlayingOver)){
			PlayingDeal deal = table.getPlayingDeal();
			bottomTablo.put("empty", "0");
			bottomTablo.put("pointPlus", deal.getPointPlus());
			bottomTablo.put("compensation", 0-deal.getCompensation());
			bottomTablo.put("imp", deal.getImps());
		}else
			bottomTablo.put("empty", "1");
		return bottomTablo;
	}

	private Object createTopTablo() {
		Map<String, Object> topTablo = new HashMap<String, Object>();
		if(table.getStage().ordinal() < PlayingStage.BiddingOver.ordinal())
			topTablo.put("empty", "1");
		else{
			Bid contract = table.getBidding().getContract();
			topTablo.put("empty", "0");
			if(contract!=null){
				topTablo.put("nominal", contract.getBidNominal().ordinal()+"");
				topTablo.put("suit", contract.getBidSuit().ordinal()+"");
				topTablo.put("double", contract.getBidDoubledFlag()+"");
				String declarer;
				Side decSide = table.getBidding().getContracter();
				declarer = decSide.name().substring(0, 1);
				topTablo.put("declarer", declarer);
				topTablo.put("plus", table.getPlayingDeal().getTakenHand()+"");
				topTablo.put("minus", table.getPlayingDeal().getLostHand()+"");
			}else{
				topTablo.put("nominal", "-");
				topTablo.put("suit", "-");
				topTablo.put("double", "0");
				topTablo.put("declarer", "-");
				topTablo.put("plus", "0");
				topTablo.put("minus", "0");
			}
		}
		return topTablo;
	}
	
	
}
