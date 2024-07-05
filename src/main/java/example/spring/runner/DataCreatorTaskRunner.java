package example.spring.runner;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.spring.config.ITaskRunner;

public class DataCreatorTaskRunner  implements ITaskRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataCreatorTaskRunner.class);

    private final Map<String, Object> storage;

    public DataCreatorTaskRunner(Map<String, Object> storage) {
        this.storage = storage;
    }

    @Override
    public LongSupplier nextExecutionDelayInMilis() {
        return () -> TimeUnit.MILLISECONDS.toMillis(500L);
    }

    @Override
    public Runnable runner() {
        return () -> {
            var itemId = UUID.randomUUID().toString();
            storage.put(itemId, new Object());
            LOGGER.info("Saving item {}", itemId);
        };
    }

    @Override
    public void handleError(Throwable t) {
        LOGGER.error(t.getMessage(), t);
    }
    
}