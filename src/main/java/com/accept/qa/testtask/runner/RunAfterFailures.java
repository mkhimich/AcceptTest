package com.accept.qa.testtask.runner;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of execution after failure.
 * Gives ability to manipulate with failures: Skip, rerun etc...
 *
 * Created by mkhimich on 30.03.2017.
 */
public class RunAfterFailures extends Statement {
    private Statement nextStatement;
    private List<FrameworkMethod> afterFailures;
    private Object target;

    public RunAfterFailures(Statement statement, List<FrameworkMethod> failures, Object target) {
        this.nextStatement = statement;
        this.afterFailures = failures;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        ArrayList<Throwable> errors = new ArrayList<Throwable>();
        try {
            nextStatement.evaluate();
        } catch (Throwable th) {
            errors.add(th);
            for (FrameworkMethod m : afterFailures) {
                try {
                    m.invokeExplosively(target, th);
                } catch (Throwable th1) {
                    errors.add(th1);
                }
            }
        }
        if (errors.isEmpty()) {
            return;
        }
        if (errors.size() == 1) {
            throw errors.get(0);
        }
        throw new MultipleFailureException(errors);
    }
}