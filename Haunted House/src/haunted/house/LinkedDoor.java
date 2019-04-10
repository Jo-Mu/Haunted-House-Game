/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class LinkedDoor extends Door
{
    private Door linkedDoor = null;
    
    public LinkedDoor(final Vector2 pos, final Vector2 enteringPos)
    {
        assert pos != null && enteringPos != null : "LINKED DOOR VECTOR PARAMETERS CANNONT BE NULL";
        assert pos.x >= 0 && pos.y >= 0 : "LINKED DOOR POSITION X:" + pos.x + " Y:" + pos.y + " IS NOT POSSIBLE!";
        assert enteringPos.x >= 0 && enteringPos.y >= 0 : "LINKED DOOR ENTERING POSITION X:" + enteringPos.x + " Y:" + enteringPos.y + " IS NOT POSSIBLE!";
        
        position = pos.clone();
        enteringPosition = enteringPos.clone();
        exitingPosition = null;
    }
    
    /*Can enter door if there is a another door linked to this one, this door isn't locked,
      and if the player is in entering pos of door*/
    @Override public boolean playerCanEnterDoor(final Player player)
    {
        return !locked && enteringPosition.equals(player.getPos()) && linkedDoor != null;
    }
    
    /*Player enters door and exits out the entering pos of the linked door and 
      the floor of the linked door is returned*/
    @Override public int enterDoorAndGetFloor(final Player player)
    {
        if(playerCanEnterDoor(player))
        {
            player.relocatePlayerToPos(linkedDoor.getEnteringPos());
            return linkedDoor.getFloorNum();
        }
        else
        {
            return floorNum;
        }
    }
    
    //returns true and sets floor number if pos and entering pos are within the given floor dimensions
    @Override public boolean checkAndSetValidDoorFloor(int floorWidth, int floorHeight, int floorNumber)
    {
        if(position.x >= 0 && position.x < floorWidth 
            && position.y >= 0 && position.y < floorHeight
            && enteringPosition.x >= 0 && enteringPosition.x < floorWidth
            && enteringPosition.y >= 0 && enteringPosition.y < floorHeight)
        {
            floorNum = floorNumber;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Links door one way to any other kind of door besides a static door
    public void oneWayLinkToNonStaticDoor(final Door exitDoor)
    {
        if(!(exitDoor instanceof StaticDoor))
        {
            linkedDoor = exitDoor;
        }
    }
    
    //Links door both ways to another linked door
    public void twoWayLinkedDoorLink(final LinkedDoor exitDoor)
    {
        oneWayLinkToNonStaticDoor(exitDoor);
        exitDoor.oneWayLinkToNonStaticDoor(this);
    }
    
    //Returns door linked to this one
    public Door getLinkedDoor()
    {
        return linkedDoor;
    }
}
