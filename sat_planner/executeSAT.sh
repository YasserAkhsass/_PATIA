for tests in pddl/*; do
    echo "Running SAT solver..."
    for problem in $tests/p*.pddl; do
        filename=`basename $problem`
        echo "Testing $tests/$filename :"
        java -cp classes:lib/pddl4j-4.0.0.jar:lib/sat4j-sat.jar src.java.sat.Main $tests/domain.pddl $problem 
        echo "Done solving $filename."
    done
done
