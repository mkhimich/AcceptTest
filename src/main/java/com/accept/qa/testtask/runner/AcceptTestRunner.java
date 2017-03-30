package com.accept.qa.testtask.runner;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.List;

/**
 * Base runner with ability to run before and after test initialization.
 * Gives flexibility in execution implementations.
 * <p>
 * Created by mkhimich on 30.03.2017.
 */
public class AcceptTestRunner extends BlockJUnit4ClassRunner {
    public AcceptTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement withAfters(FrameworkMethod frameworkMethod, Object testInstance, Statement statement) {
        Statement withFailuresStatement = withAfterFailures(frameworkMethod, testInstance, statement);
        return super.withAfters(frameworkMethod, testInstance, withFailuresStatement);
    }

    protected Statement withAfterFailures(FrameworkMethod method, Object target, Statement statement) {
        List<FrameworkMethod> failures = getTestClass().getAnnotatedMethods(AfterFailure.class);
        return new RunAfterFailures(statement, failures, target);
    }

}
