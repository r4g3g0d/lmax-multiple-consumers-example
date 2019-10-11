package ro.softvision.lmaxpoc.util;

import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorsProvider class provides static access to application shared ExecutorServices to be used by asynchronous
 * methods (tasks implemented using CompletableFutures that run asynchronously).
 *
 * @author Razvan Prichici
 */
@Service
public final class ExecutorsProvider {

    //private static final int APP_EXECUTOR_POOL_SIZE = 8;
    private ExecutorService executorService;

    public ExecutorsProvider() {

        this.executorService = Executors.newCachedThreadPool();//Executors.newFixedThreadPool(APP_EXECUTOR_POOL_SIZE);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public ExecutorService currentThreadExecutorService() {
        return new ThreadPoolExecutor(0, 1, 0L, TimeUnit.SECONDS, new SynchronousQueue<>(), new CallerRunsPolicy()) {
            @Override
            public void execute(final Runnable runnable) {
                getRejectedExecutionHandler().rejectedExecution(runnable, this);
            }
        };
    }
}
