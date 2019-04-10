/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class Item 
{
    private final String ITEM_NAME;
    private final String ITEM_MESSAGE;
    
    public Item(String name, String message)
    {
        ITEM_NAME = name;
        ITEM_MESSAGE = message;
    }
    
    //Returns a clone of item
    @Override public Item clone()
    {
        return new Item(ITEM_NAME, ITEM_MESSAGE);
    }
    
    //Returns item name
    public String getItemName()
    {
        return ITEM_NAME;
    }
    
    //Returns item message
    public String getItemMessage()
    {
        return ITEM_MESSAGE;
    }
}
