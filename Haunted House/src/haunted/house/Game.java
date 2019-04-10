/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Game 
{
    public String playHauntedHouse(String playerName)
    {
        final int NUM_FLOORS = 2;
        final int FLOOR_WIDTH = 17;
        final int FLOOR_HEIGHT = 24;
        final int STARTING_FLOOR = 1;
        Vector2 startingPos = new Vector2(14, 1);        
        HashMap<Integer, Floor> house = new HashMap<>();
        
        //Build map of floors with predetermined dimensions
        for(int i = 1; i <= NUM_FLOORS; i++)
        {
            house.put(i, new Floor(FLOOR_WIDTH, FLOOR_HEIGHT, i));
        }
        
        //Create Items
        Item chest = new Item("Chest", "Ghost escapes and scares you to death.");
        Item candelabra = new Item("Candelabra", "Light up by themselves and see a death shadow.");
        Item refrigerator = new Item("Refrigerator", "Open it and find some delicious food.");
        Item cabinet = new Item("Cabinet", "The dishes and glasses start flying at you as soon as you open the door "
                                            + "\nYou get hit in the head and start moving toward a light.");
        Item dustyBox = new Item("Dusty Recipie Box", "You open it up and a recipe for \nchocolate devils food cake appears our of no where.");
        Item broom = new Item("Broom", "Flies up in the air as soon as you touch it.");
        Item mirror = new Item("Mirror", "See a bloody face looking back at you.");
        Item shower = new Item("Shower", "Room suddenly steams up and \nyou feel fingers touching the back of your neck.");
        Item rockingChair = new Item("Rocking Chair", "Chair starts rocking by itself \nwith no one in it.");
        Item window = new Item("Window", "See a child outside \non a swing who suddenly disappears.");
        Item dollHouse = new Item("Doll House", "The dolls start dancing on their own.");
        Item dresser = new Item("Dresser", "A ghost flies out of the dresser \nas soon as you open it and goes right though your body.");
        Item jewelryBox = new Item("Jewelry Box", "You find the cursed Hope Diamond\n and feel your doom.");
        Item oilLamp = new Item("Intricate Oil Lamp", "Rub the lamp and a genie pops out\n who says he’ll grant you 3 wishes.");
        
        //Create, link, and add stairs between floors
        Stairs f1Stairs = new Stairs(new Vector2(14, 3), 2, 10, new Vector2(14, 2));
        Stairs f2Stairs = new Stairs(new Vector2(14, 6), 2, 8, new Vector2(14, 14));
        f1Stairs.linkStairs(f2Stairs);
        
        house.get(1).addStairs(f1Stairs);
        house.get(2).addStairs(f2Stairs);
        
        //Build floor 1 floor plan
        Room f1StairCase = new Room("Staircase", null, new Vector2(13, 3), 4, 11);
        Room f1LivingRoom = new Room("Living Room", new Item[]{chest}, new Vector2(0, 0), 11, 11);
        Room f1Pantry = new Room("Pantry", new Item[]{dustyBox, broom}, new Vector2(0, 10), 6, 4);
        Room f1Bathroom = new Room("Bathroom", new Item[]{mirror}, new Vector2(5, 10), 6, 4);
        Room f1Kitchen = new Room("Kitchen", new Item[]{refrigerator, cabinet}, new Vector2(0, 13), 9, 11);
        Room f1DiningRoom = new Room("Dining Room", new Item[]{candelabra}, new Vector2(8, 13), 9, 11);
        StaticDoor frontDoor1 = new StaticDoor(new Vector2(13, 0));
        StaticDoor frontDoor2 = new StaticDoor(new Vector2(14, 0));
        StaticDoor frontDoor3 = new StaticDoor(new Vector2(15, 0));
        TwoWayDoor f1DiningRmDr1 = new TwoWayDoor(new Vector2(11, 13), new Vector2(11, 12), new Vector2(11, 14));
        TwoWayDoor f1DiningRmDr2 = new TwoWayDoor(new Vector2(12, 13), new Vector2(12, 12), new Vector2(12, 14));
        TwoWayDoor f1LivingRmDr1 = new TwoWayDoor(new Vector2(10, 2), new Vector2(11, 2), new Vector2(9, 2));
        TwoWayDoor f1LivingRmDr2 = new TwoWayDoor(new Vector2(10, 3), new Vector2(11, 3), new Vector2(9, 3));
        TwoWayDoor f1BathRmDr = new TwoWayDoor(new Vector2(6, 10), new Vector2(6, 9), new Vector2(6, 11));
        TwoWayDoor f1PantryDr = new TwoWayDoor(new Vector2(1, 13), new Vector2(1, 14), new Vector2(1, 12));
        TwoWayDoor f1KitchenDr1 = new TwoWayDoor(new Vector2(8, 20), new Vector2(9, 20), new Vector2(7, 20));
        TwoWayDoor f1KitchenDr2 = new TwoWayDoor(new Vector2(8, 21), new Vector2(9, 21), new Vector2(7, 21));
        
        //Add rooms and doors of floor 1 floor plan to the floor
        house.get(1).addMultipleRooms(new Room[]{f1StairCase, f1LivingRoom, f1Pantry, f1Bathroom, f1Kitchen, f1DiningRoom});
        house.get(1).addMultipleDoors(new Door[]{frontDoor1, frontDoor2, frontDoor3, 
                                f1DiningRmDr1, f1DiningRmDr2, f1LivingRmDr1, f1LivingRmDr2,
                                f1BathRmDr, f1PantryDr,f1KitchenDr1, f1KitchenDr2});
        
        //Build floor 2 floor plan
        Room f2StairCase = new Room("Staircase", null, new Vector2(13, 5), 4, 9);
        Room f2MasterBedRoom = new Room("Master Bedroom", new Item[]{jewelryBox}, new Vector2(0, 0), 11, 13);
        Room f2MasterBathRoom = new Room("Master Bathroom", new Item[]{oilLamp, shower}, new Vector2(10, 0), 7, 6);
        Room f2BedRoom1 = new Room("Bedroom 1", new Item[]{rockingChair, window}, new Vector2(8, 16), 9, 8);
        Room f2BedRoom2 = new Room("Bedroom 2", new Item[]{dollHouse, dresser}, new Vector2(0, 16), 9, 8);
        Room f2BathRoom = new Room("Bathroom", new Item[]{shower}, new Vector2(5, 20), 7, 4);
        TwoWayDoor f2MasterBedRmDr1 = new TwoWayDoor(new Vector2(7, 12), new Vector2(7, 13), new Vector2(7, 11));
        TwoWayDoor f2MasterBedRmDr2 = new TwoWayDoor(new Vector2(8, 12), new Vector2(8, 13), new Vector2(8, 11));
        TwoWayDoor f2BedRm1Dr = new TwoWayDoor(new Vector2(6, 16), new Vector2(6, 15), new Vector2(6, 17));
        TwoWayDoor f2BedRm2Dr = new TwoWayDoor(new Vector2(10, 16), new Vector2(10, 15), new Vector2(10, 17));
        TwoWayDoor f2BathRmDr1 = new TwoWayDoor(new Vector2(5, 21), new Vector2(4, 21), new Vector2(6, 21));
        TwoWayDoor f2BathRmDr2 = new TwoWayDoor(new Vector2(11, 21), new Vector2(12, 21), new Vector2(10, 21));
        TwoWayDoor f2MasterBathRmDr = new TwoWayDoor(new Vector2(10, 2), new Vector2(9, 2), new Vector2(11, 2));
        
        //Add rooms and doors of floor 2 floor plan to the floor
        house.get(2).addMultipleRooms(new Room[]{f2StairCase, f2MasterBedRoom, f2MasterBathRoom,
                                f2BedRoom1, f2BedRoom2});
        //Inner rooms are added second so to make sure all rooms that they overlap know of them
        house.get(2).addInnerRoom(f2BathRoom);
        house.get(2).addMultipleDoors(new Door[]{f2MasterBedRmDr1, f2MasterBedRmDr2, f2BedRm1Dr,
                                f2BedRm2Dr, f2BathRmDr1, f2BathRmDr2, f2MasterBathRmDr});
        
        //return result of playing game with constructed house,starting floor and pos, and player name.
        return PlayGame(house, startingPos, STARTING_FLOOR, playerName);
    }
    
    private String PlayGame(final HashMap<Integer, Floor> house, final Vector2 startingPos, int startingFloor, String playerName)
    {
        //Set initial current floor to given startinf floor number
        Floor currentFloor = house.get(startingFloor);
        Player player = new Player(playerName, startingPos);
        
        JLabel label = new JLabel();
        label.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Welcome player and show instructions
        String introMessage = "Welcome " + player.getPlayerName() + "!\nWander a haunted house and search rooms for items.\n"
                            + "You -> @\nWalls -> #\nStairs -> ==\nFloor -> *\n\nHAVE FUN!";
        
        label.setText("<html>" + introMessage.replaceAll("\n", "<br/>") + "</html>");
        JOptionPane.showMessageDialog(null, label, "Haunted House Game", JOptionPane.PLAIN_MESSAGE);
        
        String resultMessage = ""; //End game message
        String statusMessage = ""; //Mid game satus message
        boolean isGameOver = false;
        boolean itemAcquired = false;
        
        //Play until player quits or some Game Over condition is achieved
        while(!isGameOver)
        {
            //Check current room at player's location
            Room currentRoom = currentFloor.getCurrentRoomAt(player.getPos());
            
            String guiText = "Name: " + player.getPlayerName() +"\nFloor: " + currentFloor.getFloorNumber() + "\nCurrent Room: "+ ((currentRoom != null)? currentRoom.getRoomName(): "Hallway")+"\n\n";
            String gameText = guiText + statusMessage + currentFloor.drawFloorWithPlayer(player);
            
            label.setText("<html>" + gameText.replaceAll("\n", "<br/>") + "</html>");
            statusMessage = "";
            
            String[] options;
            
            //Player cannot search if thery are not in a room
            if(currentRoom != null)
            {
                options = new String[]{"LEFT", "UP", "DOWN", "RIGHT", "SEARCH"};
            }
            else
            {
                options = new String[]{"LEFT", "UP", "DOWN", "RIGHT"};
            }
            
            int input = JOptionPane.showOptionDialog(null, label, null, 0, JOptionPane.PLAIN_MESSAGE, null, options, null);
            
            //if player quits then game ends immediately and the resulting message it "GAME QUIT"
            if(input < 0)
            {
                resultMessage = "GAME QUIT";
                isGameOver = true;
            }
            //If player chooses a direction then the player will move towards that direction if a valid move
            else if(input <= 3)
            {
                Vector2 deltaPos;
                
                switch(input)
                {
                    case 0:
                        deltaPos = new Vector2 (-1, 0);
                        break;
                    case 1:
                        deltaPos = new Vector2(0, -1);
                        break;
                    case 2:
                        deltaPos = new Vector2(0, 1);
                        break;
                    case 3:
                        deltaPos = new Vector2(1, 0);
                        break;
                    default:
                        deltaPos = new Vector2(0, 0);
                        break;
                }
                
                if(currentFloor.isValidPosition(player.getPos().add(deltaPos)))
                {
                    Floor.State stateAtNextPos = currentFloor.getStateAt(player.getPos().add(deltaPos));
                    
                    //If next pos has a wall player bumps into it an doesn't move
                    if(stateAtNextPos == Floor.State.WALL)
                    {
                        statusMessage = "You run face first into a wall.\n\n";
                    }
                    /*If next pos has a door then player will enter it if they can.
                      If they can't then message the player that the door is locked.
                      If the door goes to nowhere, the void, then players falls into the void
                      and immediate Game Over*/
                    else if(stateAtNextPos == Floor.State.DOOR)
                    {
                        Door door = currentFloor.getDoorAt(player.getPos().add(deltaPos));
                        
                        if(door.playerCanEnterDoor(player))
                        {
                            int nextFloor = door.enterDoorAndGetFloor(player);
                            
                            if(nextFloor != 0)
                            {
                                currentFloor = house.get(nextFloor);
                            }
                            else
                            {
                                resultMessage = "You fell into an endless void... GAME OVER";
                                isGameOver = true;
                            }
                        }
                        else
                        {
                            statusMessage = "Door is locked.\n\n";
                        }
                    }
                    /*If next pos has stair then player will enter it if they can.
                      If the door goes to nowhere, the void, then players falls into the void
                      and immediate Game Over*/
                    else if(stateAtNextPos == Floor.State.STAIRS)
                    {
                        Stairs stairs = currentFloor.getStairsAt(player.getPos().add(deltaPos));
                        
                        int nextFloor = stairs.enterStairsAndGetFloor(player);
                        
                        if(nextFloor != 0)
                        {
                            currentFloor = house.get(nextFloor);
                        }
                        else
                        {
                            resultMessage = "You fell into an endless void... GAME OVER";
                            isGameOver = true;
                        }
                    }
                    else
                    {
                        player.moveBy(deltaPos);
                    }
                }
            }
            /*If player chooses to search room then they will be asked which item to search
              or they can choose not to search, after searching the game ends. 
              If room has no items then message player that it doesn't*/
            else if(input == 4)
            {
                Item[] items = currentRoom.getItems();
                
                if(items.length <= 0)
                {
                    statusMessage = "Room is empty.\n\n";
                }
                else
                {
                    String[] itemOptions = new String[items.length];
                
                    for(int index = 0; index < items.length; index++)
                    {
                        itemOptions[index] = items[index].getItemName();
                    }
                
                    int itemSelect = JOptionPane.showOptionDialog(null, "Choose item to search.", null, 0, JOptionPane.PLAIN_MESSAGE, null, itemOptions, null);
                
                    if(itemSelect < 0)
                    {
                        statusMessage = "Room not searched.\n\n";
                    }
                    else
                    {
                        player.storeItem(items[itemSelect]);
                        itemAcquired = true;
                        isGameOver = true;
                    }
                }
            }
        }
        
        /*If Game Over and an item was searched then show the player the item
          and the floor and room they ended in.*/
        if(itemAcquired)
        {
            Item item = player.getItemInBag();
            resultMessage = "Floor: " + currentFloor.getFloorNumber() + "\nRoom: " 
                            + currentFloor.getCurrentRoomAt(player.getPos()).getRoomName()
                            + "\n\nItem found: " + item.getItemName() + "\n" + item.getItemMessage() 
                            + "\n\nGAME OVER\n\n" + currentFloor.drawFloorWithPlayer(player); 
        }
        
        return resultMessage;
    }
}
