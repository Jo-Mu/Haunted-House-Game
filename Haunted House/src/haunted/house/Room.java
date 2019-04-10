/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

import java.util.ArrayList;
import java.util.Random;

public class Room 
{
    private final String ROOM_NAME;
    
    private static final int MIN_DIMENSIONS = 3; // Min possible dimension for rooms
    private final int WIDTH;
    private final int HEIGHT;
    private final Vector2 TOP_LEFT_POSITION;
    
    private ArrayList<Item> stockedItems;
    private ArrayList<Room> innerRooms;
    
    public Room(String roomName, final Item[] items, final Vector2 topLeft,int width, int height)
    {
        assert topLeft != null : "ROOM TOP LEFT VECTOR CANNOT BE NULL!";
        assert topLeft.x >= 0 && topLeft.y >= 0 : "ROOM TOP LEFT POSITION X:" + topLeft.x + " Y:" + topLeft.y + " NOT POSSIBLE!";
        
        ROOM_NAME = roomName;
        //If given dimensions are smaller than the minimum then the minimum is used instead
        WIDTH = (width <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : width;
        HEIGHT = (height <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : height;
        TOP_LEFT_POSITION = topLeft.clone();
        innerRooms = new ArrayList<>();
        stockedItems = new ArrayList<>();
        
        if(items != null)
        {
            for(final Item item : items)
            {
                stockedItems.add(item.clone());
            }
        }
    }
    
    //Returns true is given room overlaps with this one past edges (sharing walls is allowed)
    public boolean isOverlappingRoomPastEdges(final Room compare)
    {
        final Vector2 compareTopLeft = compare.getTopLeft();
        
        return compareTopLeft.x < (TOP_LEFT_POSITION.x + WIDTH - 1) 
                && TOP_LEFT_POSITION.x < (compareTopLeft.x + compare.getWidth() - 1)
                && compareTopLeft.y < (TOP_LEFT_POSITION.y + HEIGHT - 1)
                && TOP_LEFT_POSITION.y < (compareTopLeft.y + compare.getHeight() - 1);
    }
    
    //Returns true if room contains given position
    public boolean containsPos(final Vector2 pos)
    {
        return pos.x > TOP_LEFT_POSITION.x && pos.x < (TOP_LEFT_POSITION.x + WIDTH - 1)
                && pos.y > TOP_LEFT_POSITION.y && pos.y < (TOP_LEFT_POSITION.y + HEIGHT - 1);
    }
    
    //Returns the number of inner rooms within this room
    public int numInnerRooms()
    {
        return innerRooms.size();
    }
    
    //Recursive function returns the innermost room or null if room has no inner rooms
    public Room getInnermostRoomContainingPos(final Vector2 pos)
    {
        if(innerRooms.isEmpty())
        {
            return null;
        }
        else
        {
            for(final Room innerRoom : innerRooms)
            {
                if(innerRoom.containsPos(pos))
                {
                    Room innermostRoom = innerRoom.getInnermostRoomContainingPos(pos);
                
                    if(innermostRoom != null)
                    {
                        return innermostRoom;
                    }
                    else
                    {
                        return innerRoom;
                    }
                }
            }
            
            return null;
        }
    }
    
    /*Recursive function adds an inner room if it overlaps with this one, 
      also adds it to its inner rooms if overlapping with them*/
    public void addInnerRoomIfOverlap(final Room newInnerRoom)
    {
        if(isOverlappingRoomPastEdges(newInnerRoom))
        {
            if(!innerRooms.isEmpty())
            {
                for(final Room innerRoom : innerRooms)
                {
                    innerRoom.addInnerRoomIfOverlap(newInnerRoom);
                }
            }
            
            innerRooms.add(newInnerRoom);
        }
    }
    
    //Returns room name
    public String getRoomName()
    {
        return ROOM_NAME;
    }
    
    //Returns room width
    public int getWidth()
    {
        return WIDTH;
    }
    
    //returns Room height
    public int getHeight()
    {
        return HEIGHT;
    }
    
    //Returns the items this room contains
    public Item[] getItems()
    {   
        Item[] items = new Item[stockedItems.size()];
            
        for(int index = 0; index < stockedItems.size(); index++)
        {
            items[index] = stockedItems.get(index).clone();
        }
        
        return items;
    }
    
    //Adds a new item to room
    public void addItem(final Item item)
    {
        stockedItems.add(item.clone());
    }
    
    //Returns pos of top left corner of room
    public Vector2 getTopLeft()
    {
        return TOP_LEFT_POSITION.clone();
    }
    
    //Returns a clone of room
    @Override public Room clone()
    {
        return new Room(ROOM_NAME, getItems(), TOP_LEFT_POSITION, WIDTH, HEIGHT);
    }
}
