import java.util.Scanner;
import java.awt.Image;
import java.util.ArrayList;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Stack;
import javax.swing.JPanel;

/**
 * The table will run the Blackjack game
 *
 * @author Charles Almond
 * @version 2020.06.30.01
 */
public class Table
{
    // instance variables - 2 players, the pot of money from the bet, and a way to get input
    Player player;
    Dealer dealer;
    
    int bet = 0;
    private int pot;
    private int wins=0;
    private ArrayList<Integer> moneyChange;
    
    private Scanner scan = new Scanner(System.in);
    
    
    private JButton betting;
    private JButton hit;
    private JButton check;
    
    private JPanel playerInfo;
    private JPanel dealerInfo;
    
    private JPanel playerCards;
    private JPanel dealerCards;
    
    private JTextField amount;
    
    private JLabel tableMoney; //Stay
    private JLabel gamePhase; //Stay
    
    
    /**
     * Constructor for objects of class Table
     */
    public Table() 
    {
        pot = 0;
        player = new Player();
        moneyChange=new ArrayList<>();
        // Create the dealer with 5x the money of the player
        try{
            dealer = new Dealer(player.getStash() * 5, null);
        } catch(FileNotFoundException e){
            System.err.println(e.toString());
            quit();
        }
        
    }

    /**
     * Starts the game, putting the player in a loop starting with getBets. 
     * The game is exited by reaching the end.
     */
    public void play()
    {
        
        makeTable();
        gameStatus("Starting up");
        welcome(); //Prints a welcome message.
        setPlayerName(); //Prompts player to display their name.
        
        displayPlayersInfo();
        
        gameStatus("Gathering bets");
        enableControls(false); //No controls now.
        bettingVisibility(true);// Betting is available now. Allowing for getBets to be triggered.
    }

    /**
     * Get a valid bet from the player, and match it or go all-in by the dealer
     */
    private void getBets() throws IllegalBetException
    {
        // Get the bet for this hand from the player.  Validate, and take from player's stash and put in the pot
        String betString = amount.getText().trim(); //Grabs text. Trims for leniency
        
        //Checks if a number can be formed from the input.
        if(betString != null && betString.matches("[0-9]+") ){ //https://www.tutorialkart.com/java/how-to-check-if-string-contains-only-digits-in-java/
            //Checks that the string isn't null and that it only contains numbers.
            try{ //Still try-catch for funny business.
                bet=Integer.parseInt(betString);
                
            } catch( NumberFormatException e){
                popUp("That's not a number");
                return;
            }
    
        }else{
            popUp("Error with your bet String");
            return;
        }
        
        //Checks if the number is valid.
        if (bet > player.getStash() || bet < 0 ){
            popUp("You entered a number; but, it wasn't a good one");
            throw new IllegalBetException(bet,player.getStash());
            

        } else if(player.getStash()==bet){
            gameStatus("ALL-IN MATCH"); //Show the all-in
        } else{
            gameStatus("Playing");//Normal game.
        }

        pot = bet;
        player.setStash(player.getStash() - bet);

        // Dealer will match the bet or go all-in if not enough
        if (dealer.getStash() >= bet)
        {
            pot += bet;
            dealer.setStash(dealer.getStash() - bet);
            
        }
        else
        {
            //System.out.println("Dealer can't match, going all in");
            pot += dealer.getStash();
            dealer.setStash(0);
            gameStatus("ALL-IN MATCH"); //Another path to all-in.
        }
        
        tableMoney.setText("Pot: "+pot);
        displayPlayersInfo();
        bettingVisibility(false); // No need for betting now.
        startMatch(); //Next Phase vrrorroom.
    }
    
    /**
     *  Starts a match of blackjack
     */
    private void startMatch(){
            
            // Deal 2 cards to each player, with first card of dealer hidden.  Print both players
            player.receiveCard(dealer.deal() , true);
            dealer.receiveCard(dealer.deal() , false);
            player.receiveCard(dealer.deal() , true);
            dealer.receiveCard(dealer.deal() , true);
            
            playerCards.setVisible(true);
            dealerCards.setVisible(true);
            
            displayCards();
            
            displayPlayersInfo();
            // Check for Blackjack
            if (player.scoreHand() == 21)
            {
                gameStatus("BlackJack! Player won!");
                popUp("Player Blackjack!");
                endMatch();
            }
            else if (dealer.scoreHand() == 21)
            {
                gameStatus("Dealer Blackjack!");
                popUp("Dealer Blackjack!"); 
                endMatch();
            }
            else // If not Blackjack, ask the player to hit or check.  Loop until check or bust, dealing a card for each hit
            {
                //Activate buttons
                enableControls(true); //Enables buttons allowing for playerTurn to be triggered.
            }
                        
    }
    
    /**
     * Activates when the "hit" button is pressed.
     * Processes player's actions.
     */
    private void hit(){
        player.receiveCard(dealer.deal(), true);
        displayCards();
        
        displayPlayersInfo();
        if(player.scoreHand() >21){
            gameStatus("Game over, player busted");
            
            endMatch(); //Player busted, skip dealer's turn and end match.
            
        }else if(player.scoreHand()==21){
            
            dealerTurn();//I've got a blackjack, try and stop me.
        }
        
        //Actually just do nothing and await next button press.
    }

    /**
     *  This code covers the dealer's turn. Multiple paths may reach this destination,
     *  which is why it is its own method.
     */
    private void dealerTurn(){
        
        dealer.showAllCards();
        enableControls(false);// Turn off controls during dealer's turn.
        
        if(dealer.dealerTurn(null)){
            gameStatus("Dealer busted.");
        }
        displayCards();
        displayPlayersInfo();
        
        /*
        while(dealer.scoreHand() < 17){
            
            dealer.receiveCard(dealer.deal(), true);
            // System.out.println(player);
            displayCards();
            displayPlayersInfo();
        }
        
        if (dealer.scoreHand() > 21)
        {
                gameStatus("Dealer busted.");
                pause();
        }
        */
       
       
        endMatch();
    }
    
    /**
     * The end point of every match.
     * Winner declared.
     * Money resent.
     * Ask for a rematch.
     */
    private void endMatch(){ 
        gameStatus("Game over");
        enableControls(false);
        
        dealer.showAllCards();
        displayCards();
        scoreGame();
        player.clearHand();
        dealer.clearHand();
        
        try{
            dealer.resetDeck();
        } catch(FileNotFoundException e){
            quit();
        }

        // Ask if they want to play again, as long as both players have money to bet
        if(player.getStash() != 0 && dealer.getStash() != 0)
        {
            //Grab input.
            int choice =JOptionPane.showConfirmDialog(null,
                        "Would you like to play again?");
            
            if(choice==0){ //Yes
                resetMatch();//Activate the reset.
                    
            } else{// choice should be 1 or 2. (no, cancel)
                popUp("Thank you for playing Blackjack!  You finished with $"
                    + player.getStash()+". You won "+wins+" times. "+
                    averageMoney() );
                quit();
            }
        }
        else // No more money
        {
            popUp("You are out of money."+"You won "+wins+" times."+
                 averageMoney() );
            quit(); // Begone 
        }           
    }
    
    /**
     * Starts another match;
     */
    private void resetMatch(){
        
        playerCards.removeAll();
        dealerCards.removeAll();
        
        playerCards.setVisible(false);
        dealerCards.setVisible(false);
        
        tableMoney.setText("Pot: 0"); 
        bettingVisibility(true);
    }
    
    /*
     * Score the game based on the two player's hands and declare a winner
     */
    private void scoreGame()
    {
        int mod=0; //The modifier of the pot
        
        // Compare both player's score and declare winner.  Award pot to winner and clear player's hands
        if (player.scoreHand() > 21 || (dealer.scoreHand() > player.scoreHand() && dealer.scoreHand() <= 21))
        {
            popUp("Dealer wins");
            mod= -1;
            dealer.setStash(dealer.getStash() + pot);
        }
        else if (dealer.scoreHand() > 21 || (player.scoreHand() > dealer.scoreHand() && player.scoreHand() <= 21))
        {
            popUp("Player wins");
            wins++;
            mod =1;
            player.setStash(player.getStash() + pot);           
        }
        else
        {
            popUp("There was a push (tie)"); 
            // Return money to both players
            if (pot == bet * 2)
            {
                player.setStash(player.getStash() + bet);
                dealer.setStash(dealer.getStash() + bet);
            }
            else // Dealer went all-in
            {
                player.setStash(player.getStash() + bet);
                dealer.setStash(dealer.getStash() + (pot - bet));
            }
        }
        
        tableMoney.setText("Pot : 0");
        displayPlayersInfo();
        moneyChange.add(pot*mod);
        pot = 0;
    }

    /*
     * Pause for 2 seconds
     */
    private static void pause()
    {
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /*
     * Clear the screen by running the system console's cls/clear command
     */
    private static void clearScreen()
    {  

        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            // System.err.println(e.getMessage()); // Causes error in IDE
        }
    }
    
    /**
     * Quits the application
     */
    private void quit(){
        System.exit(0);
    }
    
    /**
     * Sets up the GUI for the game.
     */
    private void makeTable(){
        /*
         * Initializes all the components.
         */
        JFrame frame = new JFrame("BlackJackTable");
        
        Container contentPane = frame.getContentPane();
        
        JPanel gameStatus = new JPanel(); //North
        dealerInfo = new JPanel(); //East
        playerInfo = new JPanel(); //West
        JPanel buttons = new JPanel(); //South
        JPanel playField = new JPanel(); //Center
        
        playerCards = new JPanel(); //Center Bottom
        dealerCards= new JPanel(); //Center TOP
        
        
        tableMoney = new JLabel("");
        gamePhase = new JLabel("");
        
        /*Set Locations and Layouts
         * 
         */
        frame.setLayout( new BorderLayout() );
        
        gameStatus.setLayout( new FlowLayout() ); //N
        dealerInfo.setLayout( new BoxLayout(dealerInfo,BoxLayout.Y_AXIS) ); //E
        buttons.setLayout   ( new FlowLayout() ); //S
        playerInfo.setLayout( new BoxLayout(playerInfo,BoxLayout.Y_AXIS) ); //W
        playField.setLayout ( new GridLayout(2,1) ); //C
        
        
        //Add them all.
        //Add all sections to pane.
        contentPane.add(gameStatus, BorderLayout.NORTH);
        contentPane.add(dealerInfo, BorderLayout.EAST);
        contentPane.add(buttons, BorderLayout.SOUTH);
        contentPane.add(playerInfo, BorderLayout.WEST);
        contentPane.add(playField, BorderLayout.CENTER);
        
        
        
        //Make buttons!
        makeButtons(buttons);
        makePlayersInfo();
        displayPlayersInfo();
        
        playField.add(dealerCards); //Add the card rows.
        playField.add(playerCards);
        
        //Top of screen pot and status
        gameStatus.add(tableMoney);
        gameStatus.add(gamePhase);
        
        
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        frame.setVisible(true); 
        
    }
    
    /**
     * Class to organize the creation of the buttons panel.
     */
    private void makeButtons(JPanel panel){
        
        betting = new JButton("Bet");
        amount = new JTextField("Your bet here");
        hit = new JButton("Hit");
        check = new JButton("Check"); 
        
        
        betting.addActionListener(e->
        {
            try
            {
                 getBets();
            }
            catch (IllegalBetException t)
            {
                t.toString();
            }
        });
        hit.addActionListener (e-> hit() );
        check.addActionListener(e-> dealerTurn() );
        
        panel.add(betting);
        panel.add(amount);
        panel.add(hit);
        panel.add(check);
        
        
    }
    
    /**
     *  Toggles visibility of betting buttons.
     */
    private void bettingVisibility(boolean vis){
        betting.setVisible(vis);
        amount.setVisible(vis);        
    }
    
    /**
     *  Toggles the controls on and off when the player
     *  is and isn't supposed to be playing.
     */
    private void enableControls(boolean enable){
        hit.setEnabled(enable);
        check.setEnabled(enable);
    }
    
    /**
     *  Updates the player and dealer's cards.
     */
    private void displayCards(){
        
        if(dealerCards.getComponentCount() ==0 &&
           playerCards.getComponentCount() ==0){ //Check if both are empty.
               
            dealerCards.add( dealer.getCardPanel());
            playerCards.add( player.getCardPanel());  
            
        } else{
            playerCards.removeAll();
            dealerCards.removeAll();
            
            dealerCards.add( dealer.getCardPanel());
            playerCards.add( player.getCardPanel());
        }
        
        dealerCards.repaint();
        playerCards.repaint();
    }
    
    private void makePlayersInfo(){
        for(int i =0; i<3; i++){
            playerInfo.add(new JLabel() );
            dealerInfo.add(new JLabel() );
        }
    }
    
    /**
     * Updates the player and dealer's info. It accomplishes this by getting
     * an arraylist of the values to display and reassigning the currently existing
     * labels to display these new values.
     */
    private void displayPlayersInfo(){
        ArrayList<String> pInfo =player.getInfo();
        ArrayList<String> dInfo =dealer.getInfo();
        
        for(int i =0; i<pInfo.size();i++){
            JLabel pLabel = (JLabel) playerInfo.getComponent(i);
            pLabel.setText(pInfo.get(i));
            
            JLabel dLabel = (JLabel) dealerInfo.getComponent(i);
            dLabel.setText(dInfo.get(i));
            
        }
        dealerInfo.repaint();
        playerInfo.repaint();
    }
    
    /**
     * Greeting message when the game starts playing.
     */
    private void welcome(){
        JOptionPane greeting = new JOptionPane();
        greeting.showMessageDialog(null, "Welcome to blackjack! \n"+
                                   "A game where you try to total up the \n"+
                                   "value of your cards to 21. \n \n"+
                                   "Please enter your name to get started");
    }
    
    /**
     *  Pops up a message. A tad bit annoying. But very noticeable.
     */
    private void popUp(String message){
        JOptionPane.showMessageDialog(null,message);
    }
    
    /**
     * Changes the phase status. Like popup but more discrete.
     */
    private void gameStatus(String status){
        gamePhase.setText(status);
    }
    
    /**
     *  Sets the player's name by opening a pane for input.
     */
    private void setPlayerName(){
        JOptionPane getName = new JOptionPane();
        //https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#input
        String name= (String)getName.showInputDialog(
                                     null,
                                     "Please enter your name.");
        if (name != null){
            player.setName(name);
        }
        JLabel pName = new JLabel(player.getName() );
                                  
    }
    
    private void saveGame(String saveLocation){
        
    }
    
    private String averageMoney(){
        int total =0;
        for(int money :moneyChange){
            total+=money;
        }
        total=total/moneyChange.size();
        return "During the game, you won/lost an average of $"+total+".";
    }
}