package com.github.mkolisnyk.cucumber.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.testng.annotations.Test;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;


public class ExtendedTestNGRunner extends AbstractTestNGCucumberTests {
    private Class<?> clazz;
    private ExtendedRuntimeOptions[] extendedOptions;

    private void runPredefinedMethods(Class<?> annotation) throws Exception {
        if (!annotation.isAnnotation()) {
            return;
        }
        Method[] methodList = this.clazz.getMethods();
        for (Method method : methodList) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation item : annotations) {
                if (item.annotationType().equals(annotation)) {
                    method.invoke(null);
                    break;
                }
            }
        }
    }

    /* (non-Javadoc)
     * @see cucumber.api.testng.AbstractTestNGCucumberTests#run_cukes()
     */
    @Test(groups = "cucumber", description = "Runs Cucumber Features")
    public void runCukes() throws Exception {
        extendedOptions = ExtendedRuntimeOptions.init(clazz);
        clazz = this.getClass();
        try {
            runPredefinedMethods(BeforeSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new TestNGCucumberRunner(clazz).runCukes();
        try {
            runPredefinedMethods(AfterSuite.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (ExtendedRuntimeOptions extendedOption : extendedOptions) {
            if (extendedOption.isUsageReport()) {
                ReportRunner.runUsageReport(extendedOption);
            }
            if (extendedOption.isOverviewReport()) {
                ReportRunner.runOverviewReport(extendedOption);
            }
            if (extendedOption.isFeatureOverviewChart()) {
                ReportRunner.runFeatureOverviewChartReport(extendedOption);
            }
            if (extendedOption.isDetailedReport()) {
                ReportRunner.runDetailedReport(extendedOption);
            }
            if (extendedOption.isDetailedAggregatedReport()) {
                ReportRunner.runDetailedAggregatedReport(extendedOption);
            }
            if (extendedOption.isCoverageReport()) {
                ReportRunner.runCoverageReport(extendedOption);
            }
            if (extendedOption.isBreakdownReport()) {
                ReportRunner.runBreakdownReport(extendedOption);
            }
            if (extendedOption.isKnownErrorsReport()) {
                ReportRunner.runKnownErrorsReport(extendedOption);
            }
            if (extendedOption.isConsolidatedReport()) {
                ReportRunner.runConsolidatedReport(extendedOption);
            }
        }
    }
}
