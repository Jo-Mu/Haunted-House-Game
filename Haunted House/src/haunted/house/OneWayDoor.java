/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class OneWayDoor extends Door
{
    public OneWayDoor(final Vector2 pos, final Vector2 enteringPos, final Vector2 exitingPos)
    {
        assert pos != null && enteringPos != null && exitingPos != null : "ONE WAY DOOR VECTOR PARAMETERS CANNONT BE NULL";
        assert pos.x >= 0 && pos.y >= 0 : "ONE WAY DOOR POSITION X:" + pos.x + " Y:" + pos.y + " IS NOT POSSIBLE!";
        assert enteringPos.x >= 0 && enteringPos.y >= 0 : "ONE WAY DOOR ENTERING POSITION X:" + enteringPos.x + " Y:" + enteringPos.y + " IS NOT POSSIBLE!";
        assert exitingPos.x >= 0 && exitingPos.y >= 0 : "ONE WAY DOOR EXITING POSITION X:" + exitingPos.x + " Y:" + exitingPos.y + " IS NOT POSSSIBLE!";
        
        position = pos.clone();
        enteringPosition = enteringPos.clone();
        exitingPosition = exitingPos.clone();
    }
     
    //Player can enter door if its not locked and player is in entering pos of door
    @Override public boolean playerCanEnterDoor(final Player player)
    {
        return !locked && enteringPosition.equals(player.getPos());
    }
     
    /*Player enters door and exits out the exiting pos of door and 
      the floor number of the door is returned*/
    @Override public int enterDoorAndGetFloor(final Player player)
    {
        if(playerCanEnterDoor(player))
        {
            player.relocatePlayerToPos(exitingPosition);
        }
        
        return floorNum;
    }
    
    /*Returns true and sets floor numberif door pos, entering pos, and exiting pos, 
      and exiting pos fall within given dimensions of floor*/
    @Override public boolean checkAndSetValidDoorFloor(int floorWidth, int floorHeight, int floorNumber)
    {
        if(position.x >= 0 && position.x < floorWidth 
            && position.y >= 0 && position.y < floorHeight
            && enteringPosition.x >= 0 && enteringPosition.x < floorWidth
            && enteringPosition.y >= 0 && enteringPosition.y < floorHeight
            && exitingPosition.x >= 0 && exitingPosition.x < floorWidth
            && exitingPosition.y >= 0 && exitingPosition.y < floorHeight)
        {
            floorNum = floorNumber;
            return true;
        }
        else
        {
            return false;
        }
    }
}
