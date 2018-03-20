package cn.edu.ujs.component;

import org.springframework.data.annotation.AccessType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Created by DELL on 2017/12/25.
 */
@Component
public class Task1 {

    public static Random random = new Random();

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(1000000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时："+(end-start)+"毫秒");
    }

    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(1000000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时："+(end-start)+"毫秒");
    }

    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(1000000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时："+(end-start)+"毫秒");
    }
}
