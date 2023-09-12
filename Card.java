import java.awt.Image;
import java.io.*;
import javax.imageio.*;

/**
 * Card represents a standard playing card from a 52 card deck
 *
 * @author Charles Almond
 * @version 2020.06.18.04
 */
public class Card
{
    // instance variables - replace the example below with your own
    private Suit suit;
    private int value;
    private boolean visible;
    private String name = new String();
    
    private Image frontOfCard;
    private Image backOfCard;

    /**
     * Constructor for objects of class Card 
     * @param suit The suit of the card (Clubs, Spades, Diamonds, Hearts)
     * @param value The point value of the card, between 1 and 11, inclusive
     * @param visible The setting to show or hide the card when dealt, change with show() or hide()
     * @param name The name of the card
     */
    public Card(Suit suit, int value, boolean visible, String name) throws FileNotFoundException
    {
        this.suit = suit;
        if (value >= 1 && value <= 11)
        {
            this.value = value;
        }
        this.visible = visible;
        // Should do some validation on this
        this.name = name;
        
        
        
        try{
            File backCard = new File("./png/back.png");
            backOfCard=ImageIO.read(backCard);
        }catch(IOException exc){
            
            throw new FileNotFoundException("The file for the back of the card was not found");
        }
        
        
        try{
            
            File frontCard = new File("./png/"+name+"_of_"+suit.toString()+".png");
            frontOfCard=ImageIO.read(frontCard);
        }catch(IOException exc){
            throw new FileNotFoundException("The file for the front of the card: "+
                                name+" of "+suit.toString()+" was not found.");
        }
    }

    /**
     * Get the card's suit
     * @return The card's suit
     */
    public Suit getSuit()
    {
        return suit;
    }

    /**
     * Get the card's point value
     * @return A number 2 - 11 of the card's point value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Get the card's name
     * @return The name of the card ("2 - 10, Jack, Queen, King, or Ace")
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns its image based on its visiblity.
     */
    public Image getImage(){
        if(visible){
            return frontOfCard;
        }
        
        return backOfCard;
        
    }

    /**
     * Report if the card is facec-up or face-down
     * @return true if the card is face-up, false if face-down
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Set the card as visible, so the front of the card is shown
     */
    public void show()
    {
        visible = true;
    }

    /**
     * Set the card as hidden, so the back of the card is shown
     */
    public void hide()
    {
        visible = false;
    }

    /*
     * Display the front or back of the card, based on the visible field's value
     */
    @Override
    public String toString()
    {
        String cardDescription = new String();

        if (visible)
        {
            cardDescription = name + " of " + suit.name();
        }
        else
        {
            cardDescription = "Hidden Card";
        }

        return cardDescription;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else if (!(obj instanceof Card))
        {
            return false;
        }
        // It's a card, transform it into one
        Card that = (Card) obj;
        // Compare suit, value, and name
        if (this.getValue() == that.getValue() && 
            this.getSuit() == that.getSuit() && 
            this.getName().equals(that.getName()))
        {
            return true;
        }
        return false;
    }
    
    public String getMemoryAddress()
    {
        return super.toString();
    }
}
