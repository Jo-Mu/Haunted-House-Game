/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class StaticDoor extends Door
{
    public StaticDoor(final Vector2 pos)
    {
        assert pos != null : "STATIC DOOR VECTOR PARAMETER CANNONT BE NULL";
        assert pos.x >= 0 && pos.y >= 0 : "STATIC DOOR POSITION X:" + pos.x + " Y:" + pos.y + " IS NOT POSSIBLE!";
        
        position = pos.clone();
        enteringPosition = null;
        exitingPosition = null;
        locked = true;
    }
    
    //Player can never enter a static door
   @Override public boolean playerCanEnterDoor(final Player player)
   {
       return false;
   }
   
   //Returns floor number of door but doesn't move player
   @Override public int enterDoorAndGetFloor(Player player)
   {
       return floorNum;
   }
   
   //Returns true and sets floor number if door pos is within given dimensions of door
   @Override public boolean checkAndSetValidDoorFloor(int floorWidth, int floorHeight, int floorNumber)
    {
        if(position.x >= 0 && position.x < floorWidth  && position.y >= 0 && position.y < floorHeight)
        {
            floorNum = floorNumber;
            return true;
        }
        else
        {
            return false;
        }
    }
   
   //Nothing happens when attempting to lock a static door as it's always locked
   @Override public void lock(){}
   
   //Nothing happens when attemnpting to unlock a static door as it's always locked
   @Override public void unlock(){}
}
