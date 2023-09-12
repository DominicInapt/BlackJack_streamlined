import java.util.ArrayList;

/**
 * Write a description of class DealerBrain here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DealerBrain implements Brain
{
    // instance variables - replace the example below with your own
    private int threshold;
    
    public DealerBrain(){
        threshold=17;
    }
    
    /**
     * Constructor for objects of class DealerBrain
     */
    public DealerBrain(int limit)
    {
        // initialise instance variables
        threshold=limit;
    }

    public int makeDecision( ArrayList<Card> yourHand,  ArrayList<Card> playersHand){
        if(scoreHand(yourHand) <threshold){
            return 1; //Tell to get another card.
        } else if(scoreHand(yourHand)>21){
            return 2; //You've busted, stop.
        } else{
            return 3; //Stop, you've got a blackjack or something.
        }
    }
    
    public int scoreHand(ArrayList<Card> hand)
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
                //showAllCards();
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
}
