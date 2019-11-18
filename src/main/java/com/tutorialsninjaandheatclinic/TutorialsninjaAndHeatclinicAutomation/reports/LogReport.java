package com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.reports;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.tutorialsninjaandheatclinic.TutorialsninjaAndHeatclinicAutomation.constant.FileConstant;
import com.relevantcodes.extentreports.LogStatus;

/**
 * 
 * @author arjun.santra This class give the log report of testscripts
 *
 */
public class LogReport {

	public Logger logger = null;

	public LogReport() {
		getlogger();
		logger = Logger.getLogger(LogReport.class.getName());
	}

	public void getlogger() {
		PropertyConfigurator.configure(FileConstant.LOG4J_FILE);
	}

	/**
	 * the method takes input as string message
	 * 
	 * @param message is printed on the console
	 */
	public void info(String message) {
		logger.info(message);
		ThreadPool.getTest().log(LogStatus.INFO, message);
	}

}
