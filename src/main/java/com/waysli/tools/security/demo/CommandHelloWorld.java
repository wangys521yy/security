package com.waysli.tools.security.demo;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandHelloWorld extends HystrixCommand<String> {

    private final String name;

    public CommandHelloWorld(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"), Integer.MAX_VALUE);

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(Integer.MAX_VALUE)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withExecutionIsolationThreadInterruptOnFutureCancel(true)));

//        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));


//        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
//                // since we're doing work in the run() method that doesn't involve network traffic
//                // and executes very fast with low risk we choose SEMAPHORE isolation
//                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
//                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));

        this.name = name;
    }

    @Override
    protected String run() {
        // todo 芋艿：注释掉原始
        if (true) {
            System.out.println("Hello " + name + "!");
            return "Hello " + name + "!";
        }

        if (true) {
            try {
                System.out.println("Hello " + name + "!begin");
                Thread.sleep(Long.MAX_VALUE);
                System.out.println("Hello " + name + "!end");
                return "Hello " + name + "!success";
            } catch (Throwable e) {
                System.out.println("Hello " + name + "!666");

                e.printStackTrace();
                return "Hello " + name + "!failure";
            }
        }
        return "Hello " + name + "!unknown";
    }

    @Override
    protected String getFallback() {
        System.out.println("getFallback " + name + "!");
        return "qqq";
    }

    public static class UnitTest {

        @Test
        public void testSynchronous() throws InterruptedException {
            assertEquals("Hello World!", new CommandHelloWorld("World").execute());

//            Thread.sleep(Long.MAX_VALUE);

//            assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
        }

        @Test
        public void testSynchronousMore() throws InterruptedException {
            while (true) {
                new CommandHelloWorld("World").execute();
                Thread.sleep(1000L);
            }
        }

        @Test
        public void testSynchronousMore2() throws InterruptedException {
            for (int i = 0; i < 20; i++) {
                new CommandHelloWorld("World").execute();
                Thread.sleep(1000L);
//
            }
            Thread.sleep(10000L);
        }

        @Test
        public void testSynchronousMore3() throws InterruptedException {
            for (int i = 0; i < 1; i++) {
                new CommandHelloWorld("World").execute();
//                Thread.sleep(1000L);
//
            }
            Thread.sleep(11000L);
        }

        @Test
        public void testSynchronousMore4() throws InterruptedException {
            for (int i = 0; i < 30; i++) {
                new CommandHelloWorld("World").execute();
//                Thread.sleep(1000L);
//
            }
            Thread.sleep(11000L);
        }

        @Test
        public void testAsynchronous1() throws Exception {
            assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
            assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
        }

        @Test
        public void testAsynchronous2() throws Exception {

            Future<String> fWorld = new CommandHelloWorld("World").queue();
            Future<String> fBob = new CommandHelloWorld("Bob").queue();

            assertEquals("Hello World!", fWorld.get());
            assertEquals("Hello Bob!", fBob.get());
        }

        /**
         *
         * @throws InterruptedException
         */
        @Test
        public void testAsynchronous3() throws InterruptedException {
            final Future<String> future = new CommandHelloWorld("World").queue();
            Thread.sleep(1000L); // {@link HystrixCommand#isExecutedInThread()} 返回true 因为，HystrixCommand 第399行。
//            future.cancel(true);
//            System.out.println("状态：" + future.isCancelled());

//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    future.cancel(true);
//                }
//            }).start();
            future.cancel(true); // 强制取消

            Thread.sleep(Long.MAX_VALUE);
        }

        @Test
        public void testObservable() throws Exception {

            Observable<String> fWorld = new CommandHelloWorld("World").observe();
            Observable<String> fBob = new CommandHelloWorld("Bob").observe();

            // blocking
            assertEquals("Hello World!", fWorld.toBlocking().single());
            assertEquals("Hello Bob!", fBob.toBlocking().single());

            // non-blocking
            // - this is a verbose anonymous inner-class approach and doesn't do assertions
            fWorld.subscribe(new Observer<String>() {

                @Override
                public void onCompleted() {
                    // nothing needed here
                    System.out.println("onCompleted: ");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String v) {
//                    try {
//                        Thread.sleep(Long.MAX_VALUE);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("onNext: " + v);
                }

            });
            System.out.println("fWorld");

            // non-blocking
            // - also verbose anonymous inner-class
            // - ignore errors and onCompleted signal
            fBob.subscribe(new Action1<String>() {

                @Override
                public void call(String v) {
                    System.out.println("call : " + v);
                }

            });
            System.out.println("fBob");

            // non-blocking
            // - using closures in Java 8 would look like this:

            //            fWorld.subscribe((v) -> {
            //                System.out.println("onNext: " + v);
            //            })

            // - or while also including error handling

            //            fWorld.subscribe((v) -> {
            //                System.out.println("onNext: " + v);
            //            }, (exception) -> {
            //                exception.printStackTrace();
            //            })

            // More information about Observable can be found at https://github.com/Netflix/RxJava/wiki/How-To-Use

        }

        @Test
        public void testObservable2() throws Exception {
            Observable<String> fWorld = new CommandHelloWorld("World").observe();

            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Test
        public void testToObservable() {
            Observable<String> fWorld = new CommandHelloWorld("World").toObservable();

            fWorld.subscribe(new Observer<String>() {

                @Override
                public void onCompleted() {
                    // nothing needed here
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(String v) {
                    System.out.println("onNext: " + v);
                }

            });

            // tips ：此处 sleep 的意图，订阅是异步执行处理结果，避免没执行就结束了。
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
