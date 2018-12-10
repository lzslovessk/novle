package novle.quartz;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class ExtendsJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("aaa:"+new Date());
	}
	

}
