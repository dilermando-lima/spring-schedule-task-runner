package example.spring.config;


import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

@Configuration
@EnableScheduling
public class TaskRunnerConfiguration implements SchedulingConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRunnerConfiguration.class);

    private TaskRunnerProperties taskRunnerProperties;
    private List<ITaskRunner> taskRunnerList;
    
    public TaskRunnerConfiguration(@Autowired TaskRunnerProperties taskRunnerProperties, @Autowired List<ITaskRunner> taskRunnerList) {
        if(taskRunnerList.isEmpty()) throw new IllegalArgumentException("Implementation of %s not found. Try to create at least one bean of it".formatted(ITaskRunner.class.getName()));
        this.taskRunnerProperties = taskRunnerProperties;
        this.taskRunnerList = taskRunnerList;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(taskRunnerProperties.getThreadPoolSize());
        return threadPoolTaskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        getTriggerTaskList().forEach(taskRegistrar::addTriggerTask);
    }


    private long calculateDelayMilis(LongSupplier nextExecutionDelayInMilis){
        try{
            if( nextExecutionDelayInMilis == null ) 
                return taskRunnerProperties.getNextExecutionDelayInMilisDefault();
            else
                return nextExecutionDelayInMilis.getAsLong();
        }catch(Exception e){
            LOGGER.error("error when retriving totalAvaliable. error = %s".formatted(e.getLocalizedMessage()), e);
            return taskRunnerProperties.getNextExecutionDelayInMilisOnTotalAvailableError();
        }
    }

    private List<TriggerTask> getTriggerTaskList(){
        return taskRunnerList.stream().map( t ->
            new TriggerTask(
                () -> {
                    try{
                        t.runner().run();
                    }catch(Exception e){
                        t.handleError(e);
                    }
                }, 
                triggerContext -> Optional
                    .ofNullable(triggerContext.lastCompletion())
                    .orElse(Instant.now())
                    .plusMillis(calculateDelayMilis(t.nextExecutionDelayInMilis()))
            )
        ).toList();
    }
    
}
