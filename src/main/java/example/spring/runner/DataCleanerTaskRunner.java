package example.spring.runner;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.spring.config.ITaskRunner;

public class DataCleanerTaskRunner  implements ITaskRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCleanerTaskRunner.class);

    private final Map<String, Object> storage;

    public DataCleanerTaskRunner(Map<String, Object> storage) {
        this.storage = storage;
    }

    @Override
    public LongSupplier nextExecutionDelayInMilis() {
        return () ->  storage.size() > 10 ?  TimeUnit.MILLISECONDS.toMillis(1000L) : TimeUnit.MILLISECONDS.toMillis(10000L);
    }

    @Override
    public Runnable runner() {
        return () -> {
            var sizeBeforeCleaning = storage.size();
            storage.clear();
            var removedDataSize = sizeBeforeCleaning - storage.size();
            LOGGER.info("Clenning {} itens from database ", removedDataSize);
        };
    }
    
    @Override
    public void handleError(Throwable t) {
        LOGGER.error(t.getMessage(), t);
    }
}
