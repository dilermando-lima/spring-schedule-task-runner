package example.spring.runner;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.spring.config.ITaskRunner;

public class LoggerStatusTaskRunner implements ITaskRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerStatusTaskRunner.class);

    private final Map<String, Object> storage;

    @Override
    public LongSupplier nextExecutionDelayInMilis() {
        return () -> TimeUnit.MILLISECONDS.toMillis(700L);
    }

    public LoggerStatusTaskRunner(Map<String, Object> storage) {
        this.storage = storage;
    }

    @Override
    public Runnable runner() {
        return () -> LOGGER.info("\n ============ current database size itens = {} ", storage.size());
    }
    
}
