import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * A label is a text piece which can be shown in the game world.
 * 
 * @author Mickie Enad
 * @version 5-3-19
 */
public class Label extends Actor
{
    private String labelText;
    
    /**
     * Constructor of label objects.
     * @param labelText The text of this label
     */
    public Label(String labelText)
    {
        this.labelText = labelText;
        setLabelImage();
    }
    
    /**
     * Update the text in this label
     * @param labelText the text of this label
     */
    public void updateLabel(String labelText)
    {
        this.labelText = labelText;
        setLabelImage();
    }
    
    private void setLabelImage()
    {
        setImage(  new GreenfootImage( this.labelText, 24, Color.WHITE, new Color( 0, 0, 0, 0 ) ));
    }
}
