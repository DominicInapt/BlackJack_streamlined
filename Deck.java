import java.util.ArrayList;
import java.util.Collections;

/**
 * A class to represent a deck of cards
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Deck
{
    protected ArrayList<Card> cards = new ArrayList<>();
    
    /**
     * Constructor for objects of class Deck
     */
    public Deck() throws java.io.FileNotFoundException
    {
        
        for(Suit suit : Suit.values())
        {
            for (int i = 2; i < 15; i++)
            {
                if (i < 11)
                {
                    cards.add(new Card(suit, i, true, Integer.toString(i)));
                }
                else if (i == 11)
                {
                    cards.add(new Card(suit, 10, true, "Jack"));
                }
                else if (i == 12)
                {
                    cards.add(new Card(suit, 10, true, "Queen"));
                }
                else if (i == 13)
                {
                    cards.add(new Card(suit, 10, true, "King"));
                }
                else
                {
                    cards.add(new Card(suit, 11, true, "Ace"));
                }

            }
        }
        Collections.shuffle(cards);
    }
    
        /**
     * Deal a card from the deck
     * @return A Card from the deck
     */
    public Card deal()
    {
        // By using the random number range of the length of cards, we'll get a random card from somewhere in the deck.  Equivalent to shuffling
        return cards.remove(0);
    }

    /**
     * Report on the size of the remaining cards in the deck
     * @return The number of cards left in the deck
     */
    public int cardsLeftInDeck()
    {
        return cards.size();
    }
    
    protected void check(){
        for(Card card : cards){
            if(card == null){
                System.out.println("NOOOOOOO");
            }
        }
    }
    
    protected void empty(){
        cards=new ArrayList<>();
    }
    /*
     * Returns 52 pick-up
     */
    @Override
    public String toString()
    {
        String cardString = "";
        for (Card card : cards)
        {
            cardString += card.toString() + "\n";
        }
        return cardString;
    }
}
