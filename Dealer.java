import java.util.ArrayList;

/**
 * The Dealer class represents a dealer in a Blackjack game
 *
 * @author Charles Almond
 * @version 2020.06.29.01
 */
public class Dealer extends Player
{
    private Deck deck;
    private Brain brain;
    /**
     * Constructor for objects of class Dealer, setting the name to "Dealer" and the stash of money to bet, 
     * and initializes a new deck of cards
     */
    public Dealer(int stash, Brain brain) throws java.io.FileNotFoundException
    {
        super("Dealer", stash);
        
        if(brain == null){
            this.brain=new DealerBrain();
        }
        deck = new Deck();
    }
    
    /**
     * Takes a card from the deck and returns it
     * Used in conjunction with the Player's receiveCard method
     * Example: player.receiveCard(dealer.deal());
     * @return A card from the deck
     */
    public Card deal()
    {
        if (deck.cardsLeftInDeck() != 0)
        {   
            return deck.deal();
            
        }
        return null;
    }
    
    /**
     * Get a full, shuffled deck back to the dealer
     */
    public void resetDeck() throws java.io.FileNotFoundException
    {
        deck = new Deck();
        
    }
    
    public boolean dealerTurn( ArrayList<Card> pHand){
        int choice=0;
        do{
            choice = brain.makeDecision(hand,pHand);
            
            if(choice ==1){
                receiveCard(this.deal(), true);
            } else if(choice==2){
                return true; //Busted
            }
        } while( choice==1);
        return false;
        
    }
}
