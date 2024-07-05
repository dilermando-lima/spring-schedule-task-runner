# spring-schedule-task-runner

* [About](#about)
* [Creating a new task implementation](#creating-a-new-task-implementation)
* [Intancing and Starting tasks](#intancing-and-starting-tasks)
* [Set default ThreadPoolTaskSchedule properties](#set-default-threadpooltaskschedule-properties)


## About
This project has a configuration and implementation to running scheduled taks on delay management beetwen each runnning


## Creating a new task implementation

Create a new implementation of managed task, create a class thats implements `ITaskRunner` interface.

```java
public class MyNewTaskRunner implements ITaskRunner {

    @Override
    public LongSupplier nextExecutionDelayInMilis() {
        // it is possible to manage delay of execution getting any data or processing from external services or database.
        
        return () ->  TimeUnit.MILLISECONDS.toMillis(1000L); // running every second. 
    }

    @Override
    public Runnable runner() {
        return () -> {  
            // doAnythin();
        }
    }
    
    @Override
    public void handleError(Throwable t) {
        // handle error on running
        LOGGER.error(t.getMessage(), t);
    }
}

```

## Intancing and Starting tasks

It's possible to instance many tasks implementations

```java
@Configuration
public class TaskRunnerRegister {

    @Bean
    public ITaskRunner taskRunner1(){
        return new MyNewTaskRunner1();
    }

    @Bean
    public ITaskRunner taskRunner2(){
        return new MyNewTaskRunner1();
    }

    @Bean
    public ITaskRunner taskRunner3(){
        return new MyNewTaskRunner1();
    }
}
```

## Set default ThreadPoolTaskSchedule properties

It's possible to change default settings in `application.properties`

```properties
core.taskrunner.nextExecutionDelayInMilisDefault = 10000;
core.taskrunner.nextExecutionDelayInMilisOnTotalAvailableError = 60000;
core.taskrunner.threadPoolSize = 5;
```






