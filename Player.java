import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * The Player class represents a Blackjack player in the game
 *
 * @author Charles Almond
 * @version 2020.06.29.01
 */
public class Player
{
    protected ArrayList<Card> hand;
    private String name;
    private int stash;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        // initialise instance variables
        hand = new ArrayList<Card>();
        name = "Player";
        setStash(500);
    }

    /**
     * Overloaded constructor for custom name and amount of money to bet with
     */
    public Player(String name, int stash)
    {
        hand = new ArrayList<Card>();
        this.name = name;
        setStash(stash);
    }

    /**
     * Get a card from the dealer
     * @param card A card from the deck of cards
     * @param visibility Set the card face-up (true) or face-down (false)
     */
    public void receiveCard(Card card, boolean visibility)
    {
        if (visibility)
        {
            hand.add(card);
        }
        else
        {
            card.hide();
            hand.add(card);
        }
    }

    /**
     * Set the amount of money the player has available to bet, must be >= 0
     */
    public void setStash(int stash)
    {
        if (stash >= 0)
        {
            this.stash = stash;
        }
    }
    
    /**
     *  Sets the player's name.
     */
    public void setName(String name){
        if (name != null){
            this.name=name;
        } 
    }
    

    /**
     * Get the amount of money the player has available to bet
     * @return The amount of money the player has to bet
     */
    public int getStash()
    {
        return stash;
    }
    
    /**
     * Returns the player's name
     */
    public String getName(){
        return name;
    }

    /**
     * Clear the player's hand, for use after one round of Blackjack
     */
    public void clearHand()
    {
        hand.clear();
    }

    /**
     * Score the cards in the player's hand for Blackjack
     * If the score is more than 21, the method reduces the score by 10 for each Ace present 
     * until the score is less than or equal to 21 or all cards are examined
     * @return The score of all cards in the player's hand
     */
    public int scoreHand()
    {
        int score = 0;

        //Check for Blackjack!
        if (hand.size() == 2)
        {
            for(Card card : hand)
            {
                // Look at the hidden card also to see if they are 21 points
                score += card.getValue();
            }
            // If Blackjack, flip cards face up and return 21!
            if (score == 21)
            {
                showAllCards();
                return score;
            }
        }
        // If not Blackjack, score the hand normally
        score = 0;
        for(Card card : hand)
        {
            if (card.isVisible())
            {
                score += card.getValue();
            }
        }

        // Now need to account for an Ace being worth 11 or 1
        if (score > 21)
        {
            for(Card card : hand)
            {
                if (card.getValue() == 11 && card.isVisible()) // Ace
                {
                    // Use as a 1 pointer, not an 11 pointer
                    score -= 10; 
                    // If this makes the score valid, get out so remaining Ace's are still 11 points
                    if (score <= 21)
                    {
                        break; 
                    }
                }
            }            
        }

        return score;
    }
    
    /**
     *  Returns a panel with all of the images inside of it. 
     */
    public JPanel getCardPanel(){
        
        JPanel cardPanel= new JPanel();
        //cardPanel.setLayout(new FlowLayout() );
        
        hand.stream()
            .map(I -> new ImageIcon(I.getImage() ) ) //Image to imageicon
            .map(icon -> new JLabel(icon) ) //imageicon to label
            .forEach(icon -> cardPanel.add(icon) ); //label into a panel
        
        return cardPanel;
    }
    
    /**
     * Returns a Jpanel of JLabels with the player's info on them.
     */
    public ArrayList<String> getInfo(){
        //ArrayList<JLabel> info = new ArrayList<>();
        //JPanel info = new JPanel();
        ArrayList<String> info = new ArrayList<>();
        
        //info.setLayout(new BoxLayout(info,BoxLayout.Y_AXIS));
        
        info.add(name);
        info.add("$"+stash);
        
        if(hand.size() ==0){
            info.add("No cards");
        } else{
            info.add("Score: "+scoreHand()) ;
        }
        
        //System.out.println(info.size() );
        
        return info;
    }

    /**
     * Flips all cards face-up
     */
    public void showAllCards()
    {
        for (Card card : hand)
        {
            card.show();
        }
    }
      

    /**
     * Return a string representing the player and the amount of money they have available
     */
    @Override
    public String toString()
    {
        String player = name + " has $" + stash + "\n";
        player += "Current points: " + scoreHand() + "\n";
        for(Card c : hand)
        {
            player += c.toString() + "\n";
        }
        return player;
    }
}
