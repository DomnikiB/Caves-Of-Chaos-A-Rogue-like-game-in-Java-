/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cavesofchaos.main;

import cavesofchaos.enemies.Enemy;
import cavesofchaos.entity.player.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author domniki
 */
public class BattleManager {
    GamePanel gp;
    EnemySetter enemyS;
    
    //battle flag
    private boolean battleOn = false;
    //list of enemies' indeces near player's tile
    private final List<Integer> enemiesIndex = new ArrayList<>();
    //time flags and variables for enemy attack
    private boolean waitingForPlayer = false;
    private long nextAttackTime = 0;
    private int currentEnemyIndex = 0;

    public BattleManager(GamePanel gp, EnemySetter enemyS) {
        this.gp = gp;
        this.enemyS = enemyS;
        
    }
    
    public boolean isBattleOn() {
        return battleOn;
    }

    public void setBattleOn(boolean battleOn) {
        this.battleOn = battleOn;
    }

    public List<Integer> getEnemiesIndex() {
        return enemiesIndex;
    }

    //method to collect enemies at distance = 1 from player
    public void addEnemiesIndex(int index) {
        if (index != 999) {
            //change battle flag
            setBattleOn(true);
            
            //add enemy's index to list if it is not already there
            if (getEnemiesIndex().contains(index) == false) {
                enemiesIndex.add(index);
                gp.ui.showMessage("Enemy " + gp.enemyS.getFullEnemies()[index].getName() + " is close!");
            }
        }
    }
    
    private void removeEnemiesIndex(int index) {
        enemiesIndex.remove(index);
    }
    
    private void enemyAttack() {
        //if there are not any enemies nearby player
        if (enemiesIndex == null || enemiesIndex.isEmpty()) return;
        
        //counting time
        long now = System.currentTimeMillis();
        
        //if wait time period has pasted and index is in range of enemiesIndex, then attack
        if (waitingForPlayer == false && now >= nextAttackTime) {
            if (currentEnemyIndex < enemiesIndex.size()) {
                //find enemy
                Enemy enemy = enemyS.getFullEnemies()[enemiesIndex.get(currentEnemyIndex)];

                //check if enemy is alive
                if (enemy != null && enemy.isAlive() == true) {
                    //attack player
                    int damage = enemy.doDamage();
                    gp.player.takeDamage(damage);
                    gp.ui.showMessage(enemy.getName() + " attacks you for " + damage + " HP damage !");
                }
                //change index and waiting flag
                currentEnemyIndex++;
                waitingForPlayer = true;
            }
        }
        else { //all enemies have attacked player
            currentEnemyIndex = 0;
        }
        
        //handle next attack time and flag
        if (waitingForPlayer == true) {
            waitingForPlayer = false;
            nextAttackTime = System.currentTimeMillis() + 5000; //5sec waiting period between enemies' attack to player
        }
    } 
    
    //method for player's attack modeling
    public void playerAttack() {
        int damage;
        
        switch (gp.player) {
            case Duelist duelist -> {
                int index = 999;
                int lowestHP = 1500;
                
                //find enemy of those near, with lowest HP
                for (int i = 0; i < enemiesIndex.size(); ++i) {
                    if (enemyS.getFullEnemies()[enemiesIndex.get(i)] != null) {
                        if (enemyS.getFullEnemies()[enemiesIndex.get(i)].getHP() <= lowestHP) {
                            index = enemiesIndex.get(i);
                            lowestHP = enemyS.getFullEnemies()[enemiesIndex.get(i)].getHP();
                        }
                    }
                    
                }

                //attack enemy
                if (index != 999 && enemyS.getFullEnemies()[index] != null) {
                    damage = duelist.attack();
                    enemyS.getFullEnemies()[index].takeDamage(damage);
                }
            }
            case Wizard wizard -> {
                //if there are enemies with distance = 1 from player
                if (!enemiesIndex.isEmpty()) {
                    int index = 999;
                    
                    //find enemy that is close
                    for (int i = 0; i < enemyS.getFullEnemies().length; ++i) {
                        if (enemiesIndex.contains(i)) {
                            index = i;
                            break;
                        }
                    }
                    
                    //attack enemy
                    if (index != 999 && enemyS.getFullEnemies()[index] != null) {
                        damage = wizard.attack();
                        enemyS.getFullEnemies()[index].takeDamage(damage);
                    }
                }
                else {
                    int dist = wizard.getVisibilityRadius();
                    int index = 999;
                    
                    //find enemy nearest Wizard in Visibility Radius
                    for (int i = 0; i < enemyS.getFullEnemies().length; ++i) {
                        if (enemyS.getFullEnemies()[i] != null) {
                            int enemyXTile = enemyS.getFullEnemies()[i].x /gp.getTileSize();
                            int enemyYTile = enemyS.getFullEnemies()[i].y /gp.getTileSize();

                            int wizardXTile = wizard.x /gp.getTileSize();
                            int wizardYTile = wizard.y /gp.getTileSize();

                            if (Math.abs(enemyXTile - wizardXTile) + Math.abs(enemyYTile - wizardYTile) <= dist) {
                                dist = Math.abs(enemyXTile - wizardXTile) + Math.abs(enemyYTile - wizardYTile);
                                index = i;
                            }
                        }
                    }
                    
                    
                    //attack enemy
                    if (index != 999 && enemyS.getFullEnemies()[index] != null) {
                        damage = wizard.attack();
                        enemyS.getFullEnemies()[index].takeDamage(damage);
                    }
                }
            }
            default -> {
            }
        }
    }
    
    public void update() {
        
        for (int i = 0; i < enemiesIndex.size(); ++i) {
            //remove dead enemies from enemiesIndex list 
            if (enemyS.getFullEnemies()[enemiesIndex.get(i)] == null) {
                removeEnemiesIndex(i);
                battleOn = false;
            }
            //remove alive enemies that don't collide with player anymore
            else if (!enemyS.getFullEnemies()[enemiesIndex.get(i)].isPlayerCollisionFlag()) {
                removeEnemiesIndex(i);
                battleOn = false;
            }
        }
        
        //empty enemiesIndex list if cave changes        
        if (gp.player.isChangeLevel()) {
            enemiesIndex.clear();
        }
        
        //update enemies attack
        if (battleOn == true) {
            enemyAttack();
        }
    }
}
