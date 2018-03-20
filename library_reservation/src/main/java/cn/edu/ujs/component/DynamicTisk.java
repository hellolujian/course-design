package cn.edu.ujs.component;

import cn.edu.ujs.thread.CheckSignInRunnable;
import cn.edu.ujs.thread.MyRunnable;
import org.hibernate.annotations.SortComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.security.RunAs;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by DELL on 2017/12/29.
 */
@Component
public class DynamicTisk {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> future;

    private String cronTrigger;

    private Runnable runnable;

    @Bean
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    public void setCronTrigger(String cronTrigger) {
        this.cronTrigger = cronTrigger;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void startCron() {
        future = threadPoolTaskScheduler.schedule(runnable,new CronTrigger(cronTrigger));
        System.out.println("DynamicTask.startCron()");
    }

    public void stopCron() {
        if (future != null) {
            future.cancel(true);
        }
        System.out.println("DynamicTask.stopCron()");
    }

}
