/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class Stairs 
{
    private static final int MIN_DIMENSIONS = 1; // Min possible dimensions of stairs
    private final int WIDTH;
    private final int HEIGHT;
    
    private final Vector2 TOP_LEFT_POSITION;
    private final Vector2 EXITING_POSITION;
    
    private int floorNum = 0;
    private Stairs linkedStairs = null; //Stairs linked to this one
    
    public Stairs(final Vector2 topLeft, int width, int height, final Vector2 exitingPos)
    {
        assert topLeft != null && exitingPos != null : "STAIRS VECTOR PARAMETERS CANNONT BE NULL";
        assert topLeft.x >= 0 && topLeft.y >= 0 : "STAIRS TOP LEFT POSITION X:" + topLeft.x + " Y:" + topLeft.y + " NOT POSSIBLE!";
        
        //If given dimensions are smaller than the minimum then the minimum is used instead
        WIDTH = (width <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : width;
        HEIGHT = (height <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : height;
        TOP_LEFT_POSITION = topLeft.clone();
        EXITING_POSITION = exitingPos.clone();
    }
    
    //Returns true if given stairs overlaps with this one
    public boolean isOverlappingWithStairs(final Stairs compare)
    {
        final Vector2 compareTopLeft = compare.getTopLeft();
        
        return compareTopLeft.x < (TOP_LEFT_POSITION.x + WIDTH) 
                && TOP_LEFT_POSITION.x < (compareTopLeft.x + compare.getWidth())
                && compareTopLeft.y < (TOP_LEFT_POSITION.y + HEIGHT)
                && TOP_LEFT_POSITION.y < (compareTopLeft.y + compare.getHeight());
    }
    
    //Returns true if stairs contains given pos
    public boolean containsPos(final Vector2 pos)
    {
        return pos.x >= TOP_LEFT_POSITION.x && pos.x < (TOP_LEFT_POSITION.x + WIDTH)
                && pos.y >= TOP_LEFT_POSITION.y && pos.y < (TOP_LEFT_POSITION.y + HEIGHT);
    }
    
    //Sets floor number
    public void setFloorNum(int floor)
    {
        floorNum = floor;
    }
    
    /*Player enters stairs and exits out exiting pos of the linked set of stairs
      and returns the floor number of the linked set of stairs*/
    public int enterStairsAndGetFloor(final Player player)
    {
         if(linkedStairs != null)
        {
            player.relocatePlayerToPos(linkedStairs.getExitingPos());
            return linkedStairs.getFloorNum();
        }
        else
        {
            return floorNum;
        }
    }
    
    //Links stairs one way
    public void linkStairsOneWay(final Stairs exitStairs)
    {
        linkedStairs = exitStairs;
    }
    
    //Links stairs both ways
    public void linkStairs(final Stairs exitStairs)
    {
        linkStairsOneWay(exitStairs);
        exitStairs.linkStairsOneWay(this);
    }
    
    //Returns floor number
    public int getFloorNum()
    {
        return floorNum;
    }
    
    //Returns stairs width
    public int getWidth()
    {
        return WIDTH;
    }
    
    //Returns stairs height
    public int getHeight()
    {
        return HEIGHT;
    }
    
    //Returns pos of top left corner of stairs
    public Vector2 getTopLeft()
    {
        return TOP_LEFT_POSITION.clone();
    }
    
    //Returns exiting pos of stairs
    public Vector2 getExitingPos()
    {
        return EXITING_POSITION.clone();
    }
}


