package tests.api.BPMConcurrency;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import com.shaft.io.ExcelFileManager;

public class InvocationTransformer implements IAnnotationTransformer {
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		System.setProperty("testDataFilePath",
				System.getProperty("testDataFolderPath") + "BPMConcurrency/TestData.xlsx");
		ExcelFileManager testDataReader = new ExcelFileManager(System.getProperty("testDataFilePath"));

		int insightInvocationCount = Integer.valueOf(testDataReader.getCellData("InsightInvocationCount"));
		int insightThreadPoolSize = Integer.valueOf(testDataReader.getCellData("InsightThreadPoolSize"));

		if ("getInsight".equals(testMethod.getName())) {
			annotation.setInvocationCount(insightInvocationCount);
			annotation.setThreadPoolSize(insightThreadPoolSize);
		}
	}
}