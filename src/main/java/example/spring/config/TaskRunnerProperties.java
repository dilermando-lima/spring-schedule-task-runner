package example.spring.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "core.taskrunner")
@Component
public class TaskRunnerProperties{

    private long nextExecutionDelayInMilisDefault = 10000;
    private long nextExecutionDelayInMilisOnTotalAvailableError = 60000;
    private int threadPoolSize = 2;
    
    public long getNextExecutionDelayInMilisDefault() {
        return nextExecutionDelayInMilisDefault;
    }
    public void setNextExecutionDelayInMilisDefault(long nextExecutionDelayInMilisDefault) {
        this.nextExecutionDelayInMilisDefault = nextExecutionDelayInMilisDefault;
    }
    public long getNextExecutionDelayInMilisOnTotalAvailableError() {
        return nextExecutionDelayInMilisOnTotalAvailableError;
    }
    public void setNextExecutionDelayInMilisOnTotalAvailableError(long nextExecutionDelayInMilisOnTotalAvailableError) {
        this.nextExecutionDelayInMilisOnTotalAvailableError = nextExecutionDelayInMilisOnTotalAvailableError;
    }
    public int getThreadPoolSize() {
        return threadPoolSize;
    }
    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
    
   
    
   
}