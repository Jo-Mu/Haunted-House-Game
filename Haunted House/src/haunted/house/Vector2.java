/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;

public class Vector2 
{
    public int x;
    public int y;
    
    public Vector2(int vecX, int vecY)
    {
        x = vecX;
        y = vecY;
    }
    
    //Returns a new vector that with given x and y added
    public Vector2 add(int addX, int addY)
    {
        return new Vector2(x + addX, y + addY);
    }
    
    //Returns a new vector that is the addition of both this and the given vector
    public Vector2 add(final Vector2 vec2)
    {
        return add(vec2.x, vec2.y);
    }
    
    //Returns a new vector that with given x and y subtracted
    public Vector2 subtract(int subX, int subY)
    {
        return new Vector2(x - subX, y - subY);
    }
    
    //Returns a new vector that is the subtraction of both this and the given vector
    public Vector2 subtract(final Vector2 vec2)
    {
        return subtract(vec2.x, vec2.y);
    }
    
    //Returns true if x and y of both this and the given vector are equal
    public boolean equals(final Vector2 vec2)
    {
        return x == vec2.x && y == vec2.y;
    }
    
    //Copies x and y from the given vector
    public void copyFrom(final Vector2 vec2)
    {
        x = vec2.x;
        y = vec2.y;
    }
    
    //Returns a new vector that is the clone of this one
    @Override public Vector2 clone()
    {
        return new Vector2(x, y);
    }
}
