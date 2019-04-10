/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

import java.util.ArrayList;

public class Floor 
{   
    private static final int MIN_DIMENSIONS = 4; //Minimum possible dimensions of floor
    private final int FLOOR_NUMBER;
    private final int WIDTH;
    private final int HEIGHT;
    
    private State[] floorMap; //The floor map comprised of States that player will travel through
    private ArrayList<Room> rooms;
    private ArrayList<Door> doors;
    private ArrayList<Stairs> floorStairs;
    
    public enum State
    {
        FLOOR,
        DOOR,
        WALL,
        STAIRS
    };
    
    public Floor(int width, int height, int floorNumber)
    {       
        FLOOR_NUMBER = floorNumber;
        //If given dimensions are smaller than the minimum then the minimum is used instead
        WIDTH = (width <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : width;
        HEIGHT = (height <= MIN_DIMENSIONS) ? MIN_DIMENSIONS : height;
        rooms = new ArrayList<>();
        doors = new ArrayList<>();
        floorStairs = new ArrayList<>();
        floorMap = new State[WIDTH * HEIGHT];
        
        //Edges of floor are set to WALL by default while the the inner pos are set to FLOOR
        for(int y = 0; y < HEIGHT; y++)
        {
            for(int x = 0; x < WIDTH; x++)
            {
                if(y == 0 || x == 0 || y == (HEIGHT - 1) || x == (WIDTH - 1))
                {
                    floorMap[(y * WIDTH) + x] = State.WALL;
                }
                else
                {
                    floorMap[(y * WIDTH) + x] = State.FLOOR;
                }
            }
        }
    }
    
    //Returns a string of the floor map represented as ASCII art
    public String drawFloor()
    {
        String floorString = "";
        
        for(int y = 0; y < HEIGHT; y++)
        {
            for(int x = 0; x < WIDTH; x++)
            {
                switch(floorMap[(y * WIDTH) + x])
                {
                    case FLOOR:
                        floorString += "*";
                        break;
                    case DOOR:
                        floorString += "O";
                        break;
                    case WALL:
                        floorString += "#";
                        break;
                    case STAIRS:
                        floorString += "=";                       
                    default:
                        break;
                }
                
                floorString += "  ";
            }
            
            floorString += "\n";
        }
        
        return floorString;
    }
    
    //Returns a string of the floor including the player at it's location both as ASCII art
    public String drawFloorWithPlayer(final Player player)
    {
        assert isValidPosition(player.getPos()) : "CANNOT DRAW PLAYER OUTSIDE FLOOR" + FLOOR_NUMBER + "!";
        
        String floorString = "";
        
        for(int y = 0; y < HEIGHT; y++)
        {
            for(int x = 0; x < WIDTH; x++)
            {
                if(player.getPos().equals(new Vector2(x, y)))
                {
                    floorString += "@";
                }
                else
                {
                    switch(floorMap[(y * WIDTH) + x])
                    {
                        case FLOOR:
                            floorString += "*";
                            break;
                        case DOOR:
                            floorString += "O";
                            break;
                        case WALL:
                            floorString += "#";
                            break;
                        case STAIRS:
                            floorString += "=";                       
                        default:
                            break;
                    }
                }
                
                floorString += "  ";
            }
            
            floorString += "\n";
        }
        
        return floorString;
    }
    
    /*Returns true if given object with a given topLeft corner pos and dimensions 
      is within the boundaries of the floor*/
    public boolean isInFloor(final Vector2 topLeft, int width, int height)
    {
        return (topLeft.x >= 0 && topLeft.y >= 0 
                && (topLeft.x + width) <= WIDTH && topLeft.y + height <= HEIGHT);
    }
    
    public boolean isInFloor(final Vector2 topLeft, final Vector2 dimensions)
    {
        return isInFloor(topLeft, dimensions.x, dimensions.y);
    }
    
    /*Adds a room to the floor if it is within the boundaries of the floor and is not
      overlapping with any other floors excluding edges as room can share walls.*/
    public void addRoom(final Room roomToAdd)
    {
        if(roomToAdd != null && isInFloor(roomToAdd.getTopLeft(), roomToAdd.getWidth(), roomToAdd.getHeight()))
        {
            Room newRoom = roomToAdd.clone();
            boolean noOverlap = true;
            
            for(final Room room : rooms)
            {
                if(room.isOverlappingRoomPastEdges(newRoom))
                {
                    noOverlap = false;
                    break;
                }
            }
            
            if(noOverlap)
            {
                final Vector2 topLeft = newRoom.getTopLeft();
                final int roomWidth = newRoom.getWidth();
                final int roomHeight = newRoom.getHeight();
                
                //Pos of the room edges that are FLOOR will be set to WALL
                for(int y = topLeft.y; y < (topLeft.y + roomHeight); y++)
                {
                    for(int x = topLeft.x; x < (topLeft.x + roomWidth); x++)
                    {
                        if(y == topLeft.y || x == topLeft.x 
                           || y == (topLeft.y + roomHeight - 1) || x == (topLeft.x + roomWidth - 1))
                        {
                            if(getStateAt(new Vector2(x, y)) == State.FLOOR)
                            {
                                setStateAt(new Vector2(x, y), State.WALL);
                            }
                        }
                    }
                }
                
                rooms.add(newRoom);
            }
        }
    }
    
    /*Adds an inner room as long as its within the boundaries of the floor
      it will also be added to the inner rooms of the rooms it overlaps with*/
    public void addInnerRoom(final Room roomToAdd)
    {
        if(roomToAdd != null && isInFloor(roomToAdd.getTopLeft(), roomToAdd.getWidth(), roomToAdd.getHeight()))
        {
            Room newInnerRoom = roomToAdd.clone();
            
            for(final Room room : rooms)
            {
                room.addInnerRoomIfOverlap(newInnerRoom);
            }
        
            final Vector2 topLeft = newInnerRoom.getTopLeft();
            final int roomWidth = newInnerRoom.getWidth();
            final int roomHeight = newInnerRoom.getHeight();
            
            //Pos of edges will be set WALL if the pos is FLOOR and ther reverse for inner pos
            for(int y = topLeft.y; y < (topLeft.y + roomHeight); y++)
            {
                for(int x = topLeft.x; x < (topLeft.x + roomWidth); x++)
                {
                    if(y == topLeft.y || x == topLeft.x 
                        || y == (topLeft.y + roomHeight - 1) || x == (topLeft.x + roomWidth - 1))
                    {
                        if(getStateAt(new Vector2(x, y)) == State.FLOOR)
                        {
                            setStateAt(new Vector2(x, y), State.WALL);
                        }
                    }
                    else
                    {
                        if(getStateAt(new Vector2(x, y)) == State.WALL)
                        {
                            setStateAt(new Vector2(x, y), State.FLOOR);
                        }
                    }
                }
            }
        
            rooms.add(newInnerRoom);
        }
    }
    
    //Adds a door if its within the boundaries of the floor
    public void addDoor(final Door doorToAdd)
    {
        if(doorToAdd != null && doorToAdd.checkAndSetValidDoorFloor(WIDTH, HEIGHT, FLOOR_NUMBER))
        {
            doors.add(doorToAdd);
            setStateAt(doorToAdd.getPos(), State.DOOR);
        }
    }
    
    //Adds stairs if its within the boundaries of the floor and doesn't overlap with other stairs
    public void addStairs(final Stairs stairsToAdd)
    {
        if(stairsToAdd != null 
            && isInFloor(stairsToAdd.getTopLeft().subtract(new Vector2(1, 1)), stairsToAdd.getWidth() + 2, stairsToAdd.getHeight() + 2)
            && isValidPosition(stairsToAdd.getExitingPos()))
        {
            boolean noOverlap = true;
            
            for(final Stairs stairs : floorStairs)
            {
                if(stairs.isOverlappingWithStairs(stairsToAdd))
                {
                    noOverlap = false;
                    break;
                }
            }
            
            //All pos the stairs cover will becom STAIRS unless they're a door
            if(noOverlap)
            {
                final Vector2 topLeft = stairsToAdd.getTopLeft();
                final int roomWidth = stairsToAdd.getWidth();
                final int roomHeight = stairsToAdd.getHeight();
                
                for(int y = topLeft.y; y < (topLeft.y + roomHeight); y++)
                {
                    for(int x = topLeft.x; x < (topLeft.x + roomWidth); x++)
                    {
                        if(getStateAt(new Vector2(x, y)) != State.DOOR)
                        {
                            setStateAt(new Vector2(x, y), State.STAIRS);
                        }
                    }
                }
                
                stairsToAdd.setFloorNum(FLOOR_NUMBER);
                floorStairs.add(stairsToAdd);
            }
        }
    }
    
    //Calls addRoom() for a given array of rooms
    public void addMultipleRooms(final Room[] roomsToAdd)
    {
        for(final Room room : roomsToAdd)
        {
            addRoom(room);
        }
    }
    
    //Calls addInnerRoom() for a given array of rooms
    public void addMultipleInnerRooms(final Room[] innerRoomsToAdd)
    {
        for(final Room innerRoom: innerRoomsToAdd)
        {
            addInnerRoom(innerRoom);
        }
    }
    
    //Calls addDoor() for a given array of doors
    public void addMultipleDoors(final Door[] doorsToAdd)
    {
        for(final Door door : doorsToAdd)
        {
            addDoor(door);
        }
    }
    
    //Calls addStairs() for a given array of stairs
    public void addMultipleStairs(final Stairs[] stairsToAdd)
    {
        for(final Stairs stairs : stairsToAdd)
        {
            addStairs(stairs);
        }
    }
    
    //Returns true if given pos is within floor boundaries
    public boolean isValidPosition(final Vector2 pos)
    {
        return pos.x >= 0 && pos.x < WIDTH
                && pos.y >= 0 && pos.y < HEIGHT;
    }
    
    
    //Returns the state at a given pos on the floor map
    public State getStateAt(final Vector2 pos)
    {
        assert isValidPosition(pos) : "CANNOT GET STATE OUTSIDE FLOOR " + FLOOR_NUMBER +" MAP!";
                
        return floorMap[(pos.y * WIDTH) + pos.x];
    }
    
    /*Returns the room in the floor that contains the given pos, inner rooms have higher priority,
      or null if no such room exists*/
    public Room getCurrentRoomAt(final Vector2 pos)
    {
        if(rooms.isEmpty())
        {
            return null;
        }
        else
        {
            Room currentRoom = null;
            
            for(final Room room : rooms)
            {
                if(room.containsPos(pos))
                {
                    Room innermostRoom = room.getInnermostRoomContainingPos(pos);
                    
                    if(innermostRoom != null)
                    {
                        currentRoom = innermostRoom;
                    }
                    else
                    {
                        currentRoom = room;
                    }
                    
                    break;
                }
            }
            
            return currentRoom;
        }
    }
    
    //Returns the door at the given pos or null if no such door exists
    public Door getDoorAt(final Vector2 pos)
    {   
        if(doors.isEmpty())
        {
            return null;
        }
        else
        {
            for(final Door door : doors)
            {
                if(door.getPos().equals(pos))
                {
                    return door;
                }
            }
            
            return null;
        }
    }
    
    //Returns the stairs containing the given pos or null if no such stairs exists
    public Stairs getStairsAt(final Vector2 pos)
    {
        if(floorStairs.isEmpty())
        {
            return null;
        }
        else
        {
            for(final Stairs stairs : floorStairs)
            {
                if(stairs.containsPos(pos))
                {
                    return stairs;
                }
            }
            
            return null;
        }
    }
    
    //Returns floor number
    public int getFloorNumber()
    {
        return FLOOR_NUMBER;
    }
    
    //Sets state at given pos to the given state
    private void setStateAt(final Vector2 pos, State state)
    {
        assert isValidPosition(pos) : "POSITION X:" + pos.x + " Y:" + pos.y + " IS OUTSIDE FLOOR!";
        
        floorMap[(pos.y * WIDTH) + pos.x] = state;
    }
}
