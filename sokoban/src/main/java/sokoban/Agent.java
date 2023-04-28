package sokoban;

import java.io.IOException;

public class Agent {
    public static void main(String[] args) throws IOException {

        String commands = "java -cp pddl4j-4.0.0.jar -server -Xms2048m -Xmx2048m fr.uga.pddl4j.planners.statespace.HSP Domain.pddl Problem.pddl" ;
        try {
            java.util.Scanner st = new java.util.Scanner(Runtime.getRuntime().exec(commands).getInputStream()).useDelimiter("\\A");
            
            String solution = "";
            String s = null;
            if(st.hasNext())
                s = st.next() ;

            String sp[] = s.split("\n");
            int i = 0 ;
            if(i<sp.length){
                while(!(sp[i].split(" ")[0]).equals("00:")){
                    i++;
                }
            }

            while(!(sp[i].split(" ")[0]).equals("time")){
                if(sp[i].length() >= 6)
                    solution += sp[i].toUpperCase().charAt(sp[i].length() - 6);
                i++;
            }

            for (char c : solution.toCharArray()) 
                    System.out.println(c);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
    }    
}
