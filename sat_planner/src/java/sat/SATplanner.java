package src.java.sat;


import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.InvalidConfigurationException;
import fr.uga.pddl4j.problem.DefaultProblem;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.operator.Action;

import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;


public final class SATplanner extends AbstractPlanner {

    @Override
    public Problem instantiate(DefaultParsedProblem p) {
        Problem problem = new DefaultProblem(p);
        problem.instantiate();
        return problem;
    }


    @Override
    public Plan solve(Problem problem) {
        final Plan plan = new SequentialPlan();

        if(problem.isSolvable()){

            final ISolver solver = SolverFactory.newDefault();
            solver.setTimeout(Config.TIME);

            SATencoding encoding = new SATencoding(problem, 1);

            for (int i = 1; i <= Config.STEPS; i++) {
                List<int[]> clauses = encoding.getObjectives();

                try {
                    solver.reset();
                    solver.newVar(clauses.size() * 2);
                    solver.setExpectedNumberOfClauses(clauses.size());
                    for (int[] clause : clauses) {
                        solver.addClause(new VecInt(clause));
                    }

                    if (solver.isSatisfiable()) {
                        final int factSize = problem.getFluents().size();
                        final Action[] sortingActions = new Action[i];
                        for (int variable : solver.model()) {
                            final int[] coupleSolution = SATencoding.unpair(variable);
                            final int bitnum = coupleSolution[0];
                            final int step = coupleSolution[1];
                            if (bitnum >= factSize) {
                                sortingActions[step] = problem.getActions().get(bitnum - factSize);
                            }
                        }

                        for (int j = 1; j < i; j++) {
                            plan.add(0, sortingActions[j]);
                        }
                        return plan;
                    }
                } catch (Exception e) {
                    System.err.print(e.getMessage());
                    return null;
                }
            }

        }
        return null;
    }

    @Override
    public boolean isSupported(Problem arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'isSupported'");
    }

}