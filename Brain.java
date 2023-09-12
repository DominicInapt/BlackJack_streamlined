import java.util.ArrayList;

/**
 * Write a description of interface Brain here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface Brain
{
    /**
     * 
     * @return 1, take a card, 2 busted, 3, do not continue.
     */
    int makeDecision( ArrayList<Card> yourHand,  ArrayList<Card> playersHand) ;
}

//WTe//WTe//WTe//WTe//WTe
