import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The player can choose to 'stand', which means that he wants no more cards. If the player has 21 
 * or higher, he will automatically 'stand'.
 * 
 * @author Mickie Enad
 * @version 5-3-19
 */
public class Stand extends Button
{
    /**
     * Act - do whatever the Stand wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        GameWorld world = (GameWorld) getWorld();
        
        if ( Greenfoot.mouseClicked(this) && !world.isGameOver() )
        {
            stand();
        }
    }
    
    /**
     * Stand at the current score, when the player hits the stand button.
     */
    private void stand()
    {
        GameWorld world = (GameWorld) getWorld();
        world.switchTurn();
    }
}
