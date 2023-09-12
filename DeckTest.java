

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DeckTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DeckTest
{
    /**
     * Default constructor for test class DeckTest
     */
    public DeckTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void deckConstructorTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Deck deck = new Deck();

        // Act
        
        // Assert
        assertTrue(deck.cardsLeftInDeck() == 52);
    }
    
    @Test
    public void deckDealTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Deck deck = new Deck();

        // Act
        Card c1 = deck.deal();
        // Assert
        assertTrue(c1 instanceof Card);
    }
    
    @Test
    public void deckShuffleTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();
        boolean cardsEqual = true;
        
        // Act
        for (int i = 0; i < 52; i++)
        {
            Card c1 = deck1.deal();
            Card c2 = deck2.deal();
            cardsEqual = c1.equals(c2);
            if (cardsEqual == false)
            {
                break;
            }
        }
        
        // Assert
        // Without shuffling, this will be 2, 3, 4, 5 of Clubs.  While still possible, odds are astronomically small (but not zero)
        assertFalse(cardsEqual);
    }
}