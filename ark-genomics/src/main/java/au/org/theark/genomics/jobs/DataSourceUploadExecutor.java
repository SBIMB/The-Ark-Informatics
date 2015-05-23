package au.org.theark.genomics.jobs;

import static org.quartz.DateBuilder.nextGivenSecondDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import au.org.theark.core.model.spark.entity.DataSource;
import au.org.theark.genomics.service.IGenomicService;


public class DataSourceUploadExecutor {

	private DataSource dataSource;
	
	private String processUID;
	
	private IGenomicService iGenomicService;

	public DataSourceUploadExecutor(DataSource dataSource, String processUID, IGenomicService iGenomicService) {
		super();
		this.dataSource = dataSource;
		this.processUID = processUID;
		this.iGenomicService = iGenomicService;
	}
	
	
	
	public void run() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail studyUploadJob = newJob(DataSourceUploadJob.class).withIdentity("DataSourceUploadJob"+processUID, "group1").build();
		// pass initialization parameters into the job
		studyUploadJob.getJobDataMap().put(DataSourceUploadJob.GENOMICSSERVICE, iGenomicService);
		studyUploadJob.getJobDataMap().put(DataSourceUploadJob.DATASOURCE, dataSource);
		studyUploadJob.getJobDataMap().put(DataSourceUploadJob.PROCESSUID, processUID);
		Date startTime = nextGivenSecondDate(null, 1);
		SimpleTrigger trigger1 = newTrigger().withIdentity("DataSourceUploadJobTrigger"+processUID, "group1").startAt(startTime).withSchedule(simpleSchedule()).build();
		sched.scheduleJob(studyUploadJob, trigger1);
		sched.start();
	}
	
	
}
