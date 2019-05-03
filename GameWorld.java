import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Game World is representing the actual game world. It is here, the actors live, and this class
 * is also responsible for the actual game logic.
 * 
 * @author Mickie Enad
 * @version 5 -3 - 19
 */
public class GameWorld extends World
{
    private Deck deck;
    private ArrayList<Card> dealerCards;
    private ArrayList<Card> playerCards;
    private boolean dealersTurn;
    private boolean gameOver;
    
    private int dealerScore;
    private int playerScore;
    
    //These are used to control if there are any aces revealed in either 
    //the dealers cards or the players cards
    private int dealerAces;
    private int playerAces;
    
    //Labels are only used for a visual representation of the current scores.
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private Label tryAgainLabel;

    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1);
        
        this.deck = new Deck();
        this.dealerCards = new ArrayList<Card>();
        this.playerCards = new ArrayList<Card>();
        this.dealersTurn = false;
        this.gameOver = false;
        this.dealerScore = 0;
        this.playerScore = 0;
        this.dealerAces = 0;
        this.playerAces = 0;
        this.playerScoreLabel = new Label("Score: 0");
        this.dealerScoreLabel = new Label("Score: 0");
        this.tryAgainLabel = new Label("Click here to have another try.");
        
        setupNewGame();
    }
    
    /**
     * Act method is running once every game cycle.
     */
    public void act()
    {
        if (!this.gameOver)
        {
            //If the player has exactly 21 or has exceeded 21. Switch the turn to the dealer.
            if (this.playerScore >= 21) {
                this.dealersTurn = true;
            }
            
            //If its the dealers turn run the simple AI for the dealer.
            if ( this.dealersTurn && this.dealerScore < 21 )
            {
                //First turn over all dealer cards, so we can see what the dealer has.
                for (Card card : this.dealerCards)
                {
                    card.revealCard();
                    calculateScore( this.dealerCards, false );
                    updateLabels();
                }
                
                //Keep turn a card for the dealer as long as his score os below 16 or below players score
                if ( this.dealerScore < this.playerScore && this.playerScore <= 21  )
                {
                    turnNewCard( false );
                }
                else
                {
                    this.gameOver = true;
                }
            }
            else if (this.dealersTurn && this.dealerScore >= 21)
            {
                this.gameOver = true;
            }
        }
        else
        {
            if (this.dealerScore <= 21 && this.dealerScore >= this.playerScore || this.playerScore > 21)
            {
                addObject( new Label("The dealer has beat you."), getWidth() / 2, getHeight() / 2 );
            }
            else
            {
                addObject( new Label("Congratulations. You have won."), getWidth() / 2, getHeight() / 2 );
            }
            
            addObject( this.tryAgainLabel, getWidth() / 2, (getHeight() / 2) + 20 );
            
            if ( Greenfoot.mouseClicked( this.tryAgainLabel ) )
            {
                removeGameActors();
                setupNewGame();
            }
        }
    }
    
    /**
     * Remove all the game objects. This is done so it's easier to start a new game.
     */
    private void removeGameActors()
    {
        //get all actor objects
        ArrayList<Actor> actors = (ArrayList) getObjects(null);
        
        for (Actor actor : actors)
        {
            removeObject(actor);
        }
    }
    
    /**
     * Setup a new Game.
     */
    private void setupNewGame()
    {
        resetGame();
        addButtonObjects();
        addLabelObjects();
        
        //Add two cards to the dealers cards
        Card card = this.deck.getCard();
        card.revealCard();   //Reveal only the first card
        this.dealerCards.add( card );
        
        card = this.deck.getCard();
        this.dealerCards.add( card );
        
        //calculate the dealer score
        calculateScore( this.dealerCards, false );
        
        //Add the dealer cards to the game world.
        addObject( this.dealerCards.get(0), (getWidth() / 2) - 150 , 100 );
        addObject( this.dealerCards.get(1), (getWidth() / 2) - 40 , 100 );
        
        //Add two cards to the players cards
        card = this.deck.getCard();
        card.revealCard();
        this.playerCards.add( card );
        
        card = this.deck.getCard();
        card.revealCard();
        this.playerCards.add( card );
        
        //calculate the player score
        calculateScore( this.playerCards, true );
        
        //Add the player cards to the game world
        addObject( this.playerCards.get(0), (getWidth() / 2) - 150 , getHeight() - 100 );
        addObject( this.playerCards.get(1), (getWidth() / 2) - 40, getHeight() - 100 );
        
        updateLabels();
    }
    
    /**
     * Reset the game and all its values.
     */
    private void resetGame()
    {
        //Remove the player cards and dealer cards, and then shuffle the deck again.
        this.dealerCards.clear();
        this.playerCards.clear();
        
        this.deck.shuffleDeck();
        
        //reset all scores
        this.dealersTurn = false;
        this.gameOver = false;
        this.playerScore = 0;
        this.dealerScore = 0;
        this.playerAces = 0;
        this.dealerAces = 0;
    }
    
    /**
     * Add buttons to the game world
     */
    private void addButtonObjects()
    {
        addObject( new Stand(), getWidth() - 140, (getHeight() / 2) - 20 );
        addObject( new Hit(), getWidth() - 140, (getHeight() / 2) + 20 );
    }
    
    /**
     * Add the label objects to the game world
     */
    private void addLabelObjects()
    {
        addObject( new Label("Dealer"), 50, 35 );
        addObject( this.dealerScoreLabel, 59, 60 );
        
        addObject( new Label("Player"), 50, getHeight() - 165 );
        addObject( this.playerScoreLabel, 59, getHeight() - 140 );
    }
    
    /**
     * Update the text labels with the current score.
     */
    private void updateLabels()
    {
        this.dealerScoreLabel.updateLabel("Score: " + this.dealerScore);
        this.playerScoreLabel.updateLabel("Score: " + this.playerScore);
    }
    
    /**
     * Calculate the total score of the cards attached as the parameter.
     * @param cards The cards which should be calculated to a score.
     * @param isPlayer true if we are dealing with the players cards.
     */
    private void calculateScore(ArrayList<Card> cards, boolean isPlayer)
    {
        if (isPlayer)
        {
            this.playerScore = 0;
            this.playerAces = 0;
            
            for (Card card : cards)
            {
                if ( card.getValue() == "ace" )
                {
                    this.playerAces++;
                    
                    //Adding to the score is done last (outside this loop), since aces 
                    //can be either 1 or 11.
                }
                else if ( card.getValue() == "2" )
                {
                    playerScore += 2;
                }
                else if ( card.getValue() == "3" )
                {
                    playerScore += 3;
                }
                else if ( card.getValue() == "4" )
                {
                    playerScore += 4;
                }
                else if ( card.getValue() == "5" )
                {
                    playerScore += 5;
                }
                else if ( card.getValue() == "6" )
                {
                    playerScore += 6;
                }
                else if ( card.getValue() == "7" )
                {
                    playerScore += 7;
                }
                else if ( card.getValue() == "8" )
                {
                    playerScore += 8;
                }
                else if ( card.getValue() == "9" )
                {
                    playerScore += 9;
                }
                else if ( card.getValue() == "10" )
                {
                    playerScore += 10;
                }
                else if ( card.getValue() == "jack" )
                {
                    playerScore += 10;
                }
                else if ( card.getValue() == "queen" )
                {
                    playerScore += 10;
                }
                else if ( card.getValue() == "king" )
                {
                    playerScore += 10;
                }
            }
            
            for (int i = 0; i < this.playerAces; i++)
            {
                if ( (playerScore + 11) <= 21 )
                {
                    playerScore += 11;
                }
                else
                {
                    playerScore += 1;
                }
            }
        }
        else
        {
            this.dealerScore = 0;
            this.dealerAces = 0;
            
            for (Card card : cards)
            {
                if (card.isRevealed())
                {
                    if ( card.getValue() == "ace" )
                    {
                        this.dealerAces++;
                        
                        //Adding to the score is done last (outside this loop), since aces 
                        //can be either 1 or 11.
                    }
                    else if ( card.getValue() == "2" )
                    {
                        dealerScore += 2;
                    }
                    else if ( card.getValue() == "3" )
                    {
                        dealerScore += 3;
                    }
                    else if ( card.getValue() == "4" )
                    {
                        dealerScore += 4;
                    }
                    else if ( card.getValue() == "5" )
                    {
                        dealerScore += 5;
                    }
                    else if ( card.getValue() == "6" )
                    {
                        dealerScore += 6;
                    }
                    else if ( card.getValue() == "7" )
                    {
                        dealerScore += 7;
                    }
                    else if ( card.getValue() == "8" )
                    {
                        dealerScore += 8;
                    }
                    else if ( card.getValue() == "9" )
                    {
                        dealerScore += 9;
                    }
                    else if ( card.getValue() == "10" )
                    {
                        dealerScore += 10;
                    }
                    else if ( card.getValue() == "jack" )
                    {
                        dealerScore += 10;
                    }
                    else if ( card.getValue() == "queen" )
                    {
                        dealerScore += 10;
                    }
                    else if ( card.getValue() == "king" )
                    {
                        dealerScore += 10;
                    }
                }
            }
            
            for (int i = 0; i < this.dealerAces; i++)
            {
                if ( (dealerScore + 11) <= 21 )
                {
                    dealerScore += 11;
                }
                else
                {
                    dealerScore += 1;
                }
            }
        }
    }
    
    /**
     * Turn over a new card.
     */
    public void turnNewCard( boolean isPlayer )
    {
        if (isPlayer && !this.dealersTurn)
        {
            Card card = this.deck.getCard();
            card.revealCard();
            this.playerCards.add( card );
            
            calculateScore( this.playerCards, true );
            
            int numberOfCards = this.playerCards.size() - 1;
            addObject( this.playerCards.get( numberOfCards ), (getWidth() / 2) - 150 + (110 * numberOfCards) , getHeight() - 100 );
        }
        else
        {
            Card card = this.deck.getCard();
            card.revealCard();
            this.dealerCards.add( card );
            
            calculateScore( this.dealerCards, false );
            
            int numberOfCards = this.dealerCards.size() - 1;
            addObject( this.dealerCards.get( numberOfCards ), (getWidth() / 2) - 150 + (110 * numberOfCards) , 100 );
        }
        
        updateLabels();
    }
    
    /**
     * Let the player stay at his current score.
     */
    public void switchTurn()
    {
        this.dealersTurn = true;
    }
    
    /**
     * Determine if the game is over. Returns true if the game is over.
     */
    public boolean isGameOver()
    {
        return this.gameOver;
    }
}
