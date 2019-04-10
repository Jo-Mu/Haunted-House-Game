/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haunted.house;
import javax.swing.*;
import java.awt.Font;

//********************************************************************************
// PANTHERID:  5047584
// CLASS: COP 2210 – Spring 2019
// ASSIGNMENT #3
// DATE: 3/27/19
//
// I hereby swear and affirm that this work is solely my own, and not the work 
// or the derivative of the work of someone else.
//********************************************************************************

public class HauntedHouse 
{
    public static void main(String[] args) 
    {
        Game game = new Game();
        boolean playing = true;
        JLabel label = new JLabel();
        label.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        //Play until user quits game
        while(playing)
        {
            String playerName = "";
            
            //Ask user for name
            while(playerName.equals("") || playerName.length() > 12)
            {
                playerName = JOptionPane.showInputDialog(null, "Enter your name (within 1-12 characters)");
                if(playerName == null)
                {
                    playing = false;
                    break;
                }
            }
            
            if(!playing)
            {
                break;
            }
            
            //Play game an show the result
            label.setText("<html>" + game.playHauntedHouse(playerName).replaceAll("\n", "<br/>") + "</html>");
            JOptionPane.showMessageDialog(null, label, "Game Result", JOptionPane.PLAIN_MESSAGE);
            
            //Ask user if they want to play again
            int playAgain = JOptionPane.showOptionDialog(null, "Do you want to play again?", "Play Again?", 0, JOptionPane.PLAIN_MESSAGE, null, new String[]{"YES", "NO"}, null);
            
            if(playAgain != 0)
            {
                playing = false;
            }
        }
    }
}
