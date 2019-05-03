import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is responsible for holding the deck of cards. A deck of cards consist of four card 
 * types (clubs, diamonds, spades and hearts), where each type has 13 unique values.
 * 
 * @author Mickie Enad
 * @version 5-3-19
 */
public class Deck extends Actor
{
    private final String[] CARD_NAMES = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};
    private final String[] CARD_TYPES = {"clubs", "diamonds", "spades", "hearts"};
    private ArrayList<Card> deck;
    
    public Deck()
    {
        this.deck = new ArrayList<Card>();
        
        shuffleDeck();
    }
    
    private void resetDeck()
    {
        int typeCount = 0;   //Used to do the switch of card types every time it reaches 13.
        int typeIndex = 0;   //Used to retrieve the card type from the CARD_TYPES array.
        
        for (int i = 0; i < 52; i++)
        {
            //If typeCount has reached 13, reset it, and switch to the next card type.
            if (typeCount == 13)
            {
                typeCount = 0;
                typeIndex++;
            }
            
            this.deck.add( new Card( this.CARD_TYPES[typeIndex], this.CARD_NAMES[typeCount] ) );
            typeCount++;
        }
    }
    
    /**
     * Shuffle the deck of cards.
     */
    public void shuffleDeck()
    {
        resetDeck();
        Collections.shuffle(this.deck);
    }
    
    /**
     * Get a random card object from the deck. The card object is then removed from the deck.
     */
    public Card getCard()
    {
        int index = Greenfoot.getRandomNumber( this.deck.size() );   //generate a random number, based on the number of cards in the deck.
        Card card = this.deck.get(index);   //Grab the card object from the random generated index.
        this.deck.remove(index);   //remove the card object from the random generated index.
        
        return card;
    }
}
