package src.java.sat;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.planners.InvalidConfigurationException;

public class Main {

    private static void retrieveData(Plan plan, SATplanner planner, long time){
        try {
            FileWriter data = new FileWriter("benchmark_data.csv", true);
            List<String> row = Arrays.asList(
                "SAT planner",
                planner.getDomainFile().getPath(),
                planner.getProblemFile().getPath(),
                Long.toString(time)
            );
            
            data.append(String.join(", ", row)+"\n");
            data.flush();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        final SATplanner planner = new SATplanner();
        planner.setDomain(args[0]);
        planner.setProblem(args[1]);
        try {
            long t0 = System.currentTimeMillis();
            Plan plan = planner.solve();
            long time = System.currentTimeMillis() - t0;
            retrieveData(plan, planner, time);    
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
