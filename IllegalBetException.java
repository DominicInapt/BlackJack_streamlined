
/**
 * Write a description of class IllegalBet here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class IllegalBetException extends Exception
{
    private int bet; //How much they bet.
    private int stash; //How much they had.
    
    /**
     * Constructor for this exception.
     */
    public IllegalBetException(int bet, int stash){
        this.bet=bet;
        this.stash=stash;
    }
    
    /**
     * Explains what went wrong.
     */
    public String toString(){
        if(bet<0){
            return "Your bet was negative and therefore invalid.";
        } else{
            return "Your bet("+bet+") was greater than what you have("+stash+").";
        }
    }
}
