import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A player can choose 'Hit', which means to get another card. If the player has a score of 21 
 * or higher, he can't hit anymore.
 * 
 * 
 * @author Mickie Enad
 * @version 5-3-19
 */
public class Hit extends Button
{
    /**
     * Act - do whatever the Hit wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        GameWorld world = (GameWorld) getWorld();
        
        if ( Greenfoot.mouseClicked(this) && !world.isGameOver() )
        {
            getAnotherCard();
        }
    }
    
    /**
     * Turn over a new card when the player clicks the hit button
     */
    private void getAnotherCard()
    {
        GameWorld world = (GameWorld) getWorld();
        world.turnNewCard(true);
    }
}
