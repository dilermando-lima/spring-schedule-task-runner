package example.spring.config;

import java.util.function.LongSupplier;

public interface ITaskRunner {

    public default LongSupplier nextExecutionDelayInMilis(){
        return null;
    }

    public Runnable runner();

    public default void handleError(Throwable t){ 
        throw new TaskRunnerException(t);
    }

    public class TaskRunnerException extends RuntimeException{
        public TaskRunnerException(Throwable throwable){
            super(throwable);
        }
        public TaskRunnerException(String error, Throwable throwable){
            super(error, throwable);
        }
    }

}
