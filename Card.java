import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A card is responsible for holding information like the type and value. A card can be revealed in 
 * the game world, but is not revealed by default.
 * 
 * @author Mickie Enad
 * @version 5-3-19
 */
public class Card extends Actor
{
    private final GreenfootImage CARD_BACK_IMAGE = new GreenfootImage("cards/misc/blue_back.png");
    
    private String type;
    private String value;
    private boolean revealed;
    GreenfootImage cardImage;
    
    /**
     * Constructor of objects of the class Card
     * 
     * @param type The type of the card (clubs, spades, diamonds, hearts)
     * @param value The value of the card (ace to king)
     */
    public Card(String type, String value)
    {
        this.type = type;
        this.value = value;
        this.revealed = false;
        
        cardImage = new GreenfootImage("cards/" + this.type + "/" + this.value + "_of_" + this.type + ".png");
        cardImage.scale(100, 145);
        
        CARD_BACK_IMAGE.scale(100, 145);
        setImage(CARD_BACK_IMAGE);
    }
    
    /**
     * Get the type of this card
     */
    public String getType()
    {
        return this.type;
    }
    
    /**
     * Get the value of this card
     */
    public String getValue()
    {
        return this.value;
    }
    
    /**
     * Get if the card is revealed or not.
     */
    public boolean isRevealed()
    {
        return this.revealed;
    }
    
    /**
     * Reveal the card
     */
    public void revealCard()
    {
        this.revealed = true;
        
        GreenfootImage image = getImage();
        image.clear();
        image.drawImage(cardImage, 0, 0);
    }
}
