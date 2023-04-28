package src.java.sat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import fr.uga.pddl4j.util.*;
import fr.uga.pddl4j.problem.Fluent;
import fr.uga.pddl4j.problem.InitialState;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.problem.operator.Condition;

public class SATencoding {
    
    //clauses
    private List<int[]> dimacs;
    private final List<Fluent> fluents;
    private final Problem problem;
    private HashMap<Integer, ArrayList<Integer>> transitions;
    private Condition goal;
    private List<int[]> objectives;
    private int counter;
    


    public SATencoding(Problem problem, int counter) {
        this.dimacs = new ArrayList<>();
        this.transitions = new HashMap<>();
        this.counter = counter;
        this.problem = problem;
        this.goal = this.problem.getGoal();
        this.fluents = this.problem.getFluents();
        this.objectives = this.constructObjectives();
        this.init();
    }

    private void init(){
        InitialState init = this.problem.getInitialState();
        for (int i = 0; i < init.getPositiveFluents().length(); i++) {
            List<Integer> list = new ArrayList<>();
            list.add(pair(i, this.counter));
            addClause(list); 
        }
        
        for (int i = 0; i < init.getNegativeFluents().length(); i++) {
            List<Integer> list = new ArrayList<>();
            list.add(-pair(i, this.counter));
            addClause(list); 
            
        }

        for (int i = 1; i < this.counter; i++) {
            doTransition();
        }

    }

    private void addAction(int bitnum) {
        final Action action = this.problem.getActions().get(bitnum);
        int operation = pair(bitnum + this.fluents.size(), this.counter);
        generateDisjunctionClauses(operation, bitnum);
        generateClausesForAction(operation,
                                action.getPrecondition().getPositiveFluents(), 
                                action.getUnconditionalEffect().getPositiveFluents(), 
                                action.getUnconditionalEffect().getNegativeFluents());
    }
    

    private void generateDisjunctionClauses(int actionNum, int bitnum) {
        for (int i = bitnum + 1; i < this.problem.getActions().size(); i++) {
            int action2 = pair(i + this.fluents.size(), this.counter);
            int[] clause = {-actionNum, -action2};
            dimacs.add(clause);
        }
    }
    
    private void generateClausesForAction(int operation, BitVector precondpos, BitVector positive, BitVector negative) {
        for (int i = 0; i < this.fluents.size(); i++) {
            if (precondpos.get(i)) {
                int[] clause = {-operation, pair(i, this.counter)};
                dimacs.add(clause);
            }
    
            if (positive.get(i)) {
                int[] clause = {-operation, pair(i, this.counter + 1)};
                dimacs.add(clause);
                this.transitions.putIfAbsent(i, new ArrayList<>(Arrays.asList(operation)));
            }
    
            if (negative.get(i)) {
                int[] clause = {-operation, -pair(i, this.counter + 1)};
                dimacs.add(clause);
                this.transitions.putIfAbsent(-i, new ArrayList<>(Arrays.asList(operation)));
            }
        }
    }


    public void doTransition() {
        for (int i = 0; i < this.problem.getActions().size(); i++) {
            addAction(i);
        }
        generateTransition();
        this.counter++;
    }

    private List<int[]> constructObjectives(){
        List<int[]> ret = new ArrayList<>(this.dimacs);
        for(int i=0; i<this.goal.getPositiveFluents().length(); i++){
            int[] clause = new int[1];
            clause[0] = pair(i, this.counter + 1);
            ret.add(clause);
        }
        for(int i=0; i<this.goal.getNegativeFluents().length(); i++){
            int[] clause = new int[1];
            clause[0] = -pair(i, this.counter + 1);
            ret.add(clause);
        }
        return ret;
    }


    private void generateTransition() {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : this.transitions.entrySet()) {
            int fact = entry.getKey();
            ArrayList<Integer> clause = new ArrayList<>(entry.getValue());
            clause.add(fact > 0 ? pair(fact, this.counter) : -pair(-fact, this.counter));
            clause.add(fact > 0 ? -pair(fact, this.counter + 1) : pair(-fact, this.counter + 1));
            addClause(clause);
        }
    }

    private void addClause(List<Integer> clause) {
        int[] clauseTab = clause.stream().mapToInt(Integer::intValue).toArray();
        dimacs.add(clauseTab);
    }



    public static int pair(int bit, int step){
        return (int)(0.5*(bit+step) * (bit+step+1) +step);
    }


    public static int[] unpair(int a){
        int x = (int)(Math.floor((Math.sqrt((a*8) + 1)-1)/2));
        return new int[]{(x*(x+3)/2 -a),(a-x*(x+1)/2)};
    }

    public Condition getGoal(){
        return this.goal;
    }

    public List<int[]> getObjectives(){
        return this.objectives;
    }
}
