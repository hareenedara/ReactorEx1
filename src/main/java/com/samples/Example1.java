package com.samples;

import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

public class Example1 {
    public static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1,2,3,4,5,6,7,8,9,10,11,12);
        Flux<String> sFlux = Flux.just("one","two","three","four","five","six","seven");
        ex3(flux);
        sleep(5000);
        System.out.println("done");
    }

    public static void ex1(Flux<String> flux){
        flux.log()
                .map(Example1::operation1);
 //               .subscribe(1);

    }

    public static void ex2(Flux<String> flux){
        flux.log()
                .map(Example1::operation1)
                .log()
                .map(Example1::operation2)
                .log()
                .map(Example1::operation3)
                .log()
                .subscribeOn(Schedulers.parallel())
                .subscribe();

    }

    public static void ex3(Flux<Integer> flux){
        flux.log().parallel(50)
                .flatMap(v->Flux.just(v).subscribeOn(Schedulers.elastic()))
                .map(Example1::operation4)
                .map(Example1::operation5)
//                .log()
                .subscribe(x->System.out.println(x));
    }
    public static void ex4(Flux<Integer> flux){
        ParallelFlux.from(flux,20)
                .map(Example1::operation4)
                .map(Example1::operation5)
                .subscribe(x->System.out.println(x));

//        flux.log().parallel(50)
//                .flatMap(v->Flux.just(v).subscribeOn(Schedulers.elastic()))
//                .map(Example1::operation4)
//                .map(Example1::operation5)
////                .log()
//                .subscribe(x->System.out.println(x));
    }


    // operation 1
public static String operation1(String str) {
    sleep(100);
    return str.substring(0,1).toUpperCase()+str.substring(1);


}

// operation 2
public static String operation2(String str) {
        sleep(100);
        return str.toUpperCase();
}

// operation 3
public static String operation3(String str) {
        sleep(100);

        return str.toLowerCase();
}

public static int operation4(int i){
    sleep(100);

    System.out.println(Thread.currentThread().getName()+"-"+i);
    return i;
}


    public static int operation5(int i){
        sleep(100);
        System.out.println(Thread.currentThread().getName()+"-"+i+"-"+i);

        return i;
    }

    public static void sleep(int msecs) {
        try {
            int n =0;
            while(n++ < 10){
                Thread.sleep(msecs);
                //System.out.println(Thread.currentThread().getName());
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
