/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class Player 
{
    private final String PLAYER_NAME;
    private Item item = null;
    private Vector2 position = new Vector2(0, 0);
    
    public Player(String playerName, final Vector2 starterPos)
    {
        PLAYER_NAME = playerName;
        position.copyFrom(starterPos);
    }
    
    //Sets item to given item
    public void storeItem(final Item newItem)
    {
        if(newItem != null)
        {
            item = newItem.clone();
        }
    }
    
    //Returns pos pf player
    public Vector2 getPos()
    {
        return position.clone();
    }
    
    //Moves player by a given delta vector
    public void moveBy(final Vector2 deltaVec2)
    {
        position = position.add(deltaVec2);
    }
    
    //Teleports player to given pos
    public void relocatePlayerToPos(final Vector2 vec2)
    {
        position.copyFrom(vec2);
    }
    
    //Returns player name
    public String getPlayerName()
    {
        return PLAYER_NAME;
    }
    
    //Returns item player has in his bag
    public Item getItemInBag()
    {
        return item.clone();
    }
}
