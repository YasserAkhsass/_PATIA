package sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.Scanner;

public class PddlGenerator {
    private static String PREFACE = "(define (problem sokoban)\n"+
                                    "\t(:domain sokoban)\n"+
                                    "\t(:objects \n"+
                                    "\t\tL -direction \n"+
                                    "\t\tU -direction \n"+
                                    "\t\tR -direction \n"+
                                    "\t\tD -direction \n";
                                    
    private static String MAP_JSON =  "\t\"testIn\": \"";
    private File jSonFile;
    private ArrayList<String> map;
    private int mapLength;

    public PddlGenerator(String filePath){
        this.mapLength = 0;
        this.jSonFile = new File(filePath);
        this.processMap();
    }

    private String getMapLine(File jSonFile) throws FileNotFoundException{
        Scanner s = new Scanner(jSonFile);
        int lineNumber = 0;
        while(s.hasNextLine() && lineNumber<5){
            String line = s.nextLine();
            if(lineNumber == 4){
                return line.replace(MAP_JSON, "");
            }
            lineNumber++;
        }
        return "";
    }


    /*
     * input : testIn line in JSon file
     * sets up the map as a List of string, each string represents a row in the grid/map
     */
    private void processMap(){
        try {
            String map = this.getMapLine(this.jSonFile);
            ArrayList<String> ret = new ArrayList<>();
            int firstCharAt = 0;
            for(int i=0; i<map.length(); i++){
                if(map.substring(i, i+1).equals("\\")){
                    String line = map.substring(firstCharAt, i);
                    ret.add(line);
                    firstCharAt = i+2;
                    this.mapLength = this.mapLength<line.length()? line.length():this.mapLength;
                }
                i++;
            }
            this.map = ret;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getDirection(ArrayList<String> M, int row, int col){
        String pos = "pos-" + row + "-" + col;
        String nextPos;
        
        // Check right adjacent cell
        if(col < M.get(row).length() - 1){
            nextPos = "pos-" + row + "-" + (col+1);
            return("\t\t(adjacent " + pos + " " + nextPos + " R)");
        }
        
        // Check down adjacent cell
        if(row < M.size() - 1){
            nextPos = "pos-" + (row+1) + "-" + col;
            return("\t\t(adjacent " + pos + " " + nextPos + " D)");
        }
        
        // Check left adjacent cell
        if(col > 0){
            nextPos = "pos-" + row + "-" + (col-1);
            return("\t\t(adjacent " + pos + " " + nextPos + " L)");
        }
        
        // Check up adjacent cell
        if(row > 0){
            nextPos = "pos-" + (row-1) + "-" + col;
            return("\t\t(adjacent " + pos + " " + nextPos + " U)");
        }
        return "";
    }
    

    public void generate(String fileName) throws FileNotFoundException{
        PrintWriter printer = new PrintWriter(fileName);
        printer.println(PREFACE);
        // Define problem and objects
        ArrayList<int[]> goals = new ArrayList<>(); // goals represented as arrays of [i,j]
        for(int i=0; i<this.map.size(); i++){
            for(int j=0; j<this.mapLength; j++){
                String pos = "pos-" + i + "-" + j;
                printer.println("\t\t" + pos + " - position");
            }
        }
        printer.println("\t\t)");
        printer.println("\t(:init");
        for(int i=0; i<this.map.size(); i++){
            for(int j=0; j<this.map.get(i).length(); j++){
                printer.println(getDirection(this.map, i, j));
                char c = (i >= 0 && i < this.map.size() && j >= 0 && j < this.map.get(i).length()) ? this.map.get(i).charAt(j) : '#';
                switch(c){
                    case '.':
                        goals.add(new int[]{i, j});
                    case ' ': 
                        printer.println("\t\t(empty pos-"+Integer.toString(i)+"-"+Integer.toString(j)+")" );
                        break;
                    case '*':
                        goals.add(new int[]{i, j});
                    case '$': 
                        printer.println("\t\t(block-in pos-"+Integer.toString(i)+"-"+Integer.toString(j)+")" );
                        break;
                    case '#':
                        break ;
                    case '@': 
                        printer.println("\t\t(player-in pos-"+Integer.toString(i)+"-"+Integer.toString(j)+")" );
                        break;
                    default: 
                        goals.add(new int[]{i, j});
                        printer.println("\t\t(player-in pos-"+Integer.toString(i)+"-"+Integer.toString(j)+")" );
                        break;
                }
            }
        }
        printer.print("\t\t)");
        printer.print("\n\t(:goal");
        printer.print("\n\t\t(and\n");
        for(int i=0; i<goals.size(); i++){
            int[] goal = goals.get(i);
            printer.print("\t\t(block-in pos-"+goal[0]+"-"+goal[1]+")\n") ;
        }
        printer.print("\t\t)");
        printer.print("\n\t)");
        printer.print("\n)");
        printer.close();
    }
}
