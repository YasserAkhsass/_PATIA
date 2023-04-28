package sokoban;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.codingame.gameengine.runner.SoloGameRunner;

public class SokobanMain {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the file name (example : test7.json )");
        String path = sc.nextLine();
        
        PddlGenerator pg = new PddlGenerator("config/"+path);
        pg.generate("problem.pddl");
        

        SoloGameRunner gameRunner = new SoloGameRunner();
        gameRunner.setAgent(Agent.class);
        gameRunner.setTestCase(path);
        gameRunner.start();
    }
}
