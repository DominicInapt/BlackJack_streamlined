

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class DealerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class DealerTest
{
    /**
     * Default constructor for test class DealerTest
     */
    public DealerTest()
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
    public void dealerDefaultConstructorTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Dealer d;
        
        // Act - this also tests the Player overloaded constructor
        d = new Dealer(500,null);
        
        // Assert
        assertEquals(d.getStash(), 500);
        assertTrue(d.deal()  instanceof Card);
    }
    
    @Test
    public void dealerResetDeckTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Dealer d = new Dealer(500,null);
        
        // Act - get all the cards, reset the deck, and get another.  If the deck isn't reset, it'll return null
        for (int i = 0; i < 52; i++)
        {
            d.deal();            
        }
        
        d.resetDeck();
        
        Card c = d.deal();
        
        // Assert
        assertNotNull(c);
    }
}
