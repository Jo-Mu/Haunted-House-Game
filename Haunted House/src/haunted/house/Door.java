/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

/**
 *
 * @author ted
 */
public abstract class Door 
{
    /*All doors will have variables for a pos, entering pos, exiting pos, 
    floor number (0 by default), and whether it's locked or not*/
    protected Vector2 position;
    protected Vector2 enteringPosition;
    protected Vector2 exitingPosition;
    
    protected int floorNum = 0;
    
    protected boolean locked = false;
    
    //Absctract door methods
    
    //Returns true if player can enter door
    public abstract boolean playerCanEnterDoor(final Player player);
    //Updates player pos and returns floor num of exiting location
    public abstract int enterDoorAndGetFloor(final Player player);
    //Returns true and sets floor number if dooe falls within given dimension of floor
    public abstract boolean checkAndSetValidDoorFloor(int floorWidth, int floorHeight, int floorNumber);
    
    //Locks door
    public void lock()
    {
        locked = true;
    }
    
    //Unlocks door
    public void unlock()
    {
        locked = false;
    }
    
    //Returns floor number
    public int getFloorNum()
    {
        return floorNum;
    }
    
    //Returns door pos
    public Vector2 getPos()
    {
        return position.clone();
    }
    
    //Returns door entering pos
    public Vector2 getEnteringPos()
    {
        return (enteringPosition == null) ? null : enteringPosition.clone();
    }
    
    //Returns exiting pos
    public Vector2 getExitingPos()
    {
        return (exitingPosition == null) ? null : exitingPosition.clone();
    }
}
