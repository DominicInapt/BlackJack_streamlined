import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class CardTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CardTest
{
    /**
     * Default constructor for test class CardTest
     */
    public CardTest()
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
    public void cardConstructorTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Card c1;
        
        // Act
        c1 = new Card(Suit.Clubs, 10, true, "Queen");
        
        // Assert
        assertEquals(c1.getSuit(), Suit.Clubs);
        assertEquals(c1.getValue(), 10);
        assertEquals(c1.getName(), "Queen");
        
    }
    
    @Test
    public void cardShowHideTest()throws java.io.FileNotFoundException
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Jack");
        Card c2 = new Card(Suit.Clubs, 10, true, "Jack");
        
        // Act
        c1.hide();
        c2.show();
        
        // Assert
        assertFalse(c1.isVisible());
        assertTrue(c2.isVisible());
    }
    
    @Test
    public void cardEqualTest() throws java.io.FileNotFoundException
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Jack");
        Card c2 = new Card(Suit.Clubs, 10, true, "Jack");
        Card c3 = new Card(Suit.Diamonds, 10, true, "10");
        // Act
        
        // Assert
        assertTrue(c1.equals(c2));
        assertFalse(c1.equals(c3));
    }
}
