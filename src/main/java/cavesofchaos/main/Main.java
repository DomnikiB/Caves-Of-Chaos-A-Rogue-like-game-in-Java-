/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package cavesofchaos.main;

import java.awt.*;
import javax.swing.*;
/**
 *
 * @author domniki
 */
public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Caves Of Chaos");
        
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
