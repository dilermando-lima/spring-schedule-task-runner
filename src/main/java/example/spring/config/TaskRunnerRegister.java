package example.spring.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.spring.runner.DataCleanerTaskRunner;
import example.spring.runner.DataCreatorTaskRunner;
import example.spring.runner.LoggerStatusTaskRunner;



@Configuration
public class TaskRunnerRegister {

    private final Map<String,Object> DATA_STORAGE =  new ConcurrentHashMap<>();

    @Bean
    public ITaskRunner dataCleanerTaskRunner(){
        return new DataCleanerTaskRunner(DATA_STORAGE);
    }

    @Bean
    public ITaskRunner dataCreatorTaskRunner(){
        return new DataCreatorTaskRunner(DATA_STORAGE);
    }

    @Bean
    public ITaskRunner loggerStatusTaskRunner(){
        return new LoggerStatusTaskRunner(DATA_STORAGE);
    }
}