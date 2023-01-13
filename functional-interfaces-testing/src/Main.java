import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static ReentrantLock lock = new ReentrantLock(true);


    public static void accessResource() {
        lock.lock();
        try {

            if (lock.hasQueuedThreads()) {
                System.out.println(String.format("parallel calls to /api/transaction : %d", lock.getQueueLength()));
            }

            System.out.println("accessing resource at " + System.currentTimeMillis());
            try {

                Thread.sleep(500);
            } catch (InterruptedException ex) {
                System.err.println("error sleeping");
            }
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {

//        List<Thread> threads = new ArrayList<>();
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//        threads.add(new Thread(Main::accessResource));
//
//        threads.parallelStream().forEach(Thread::start);

//        System.out.println("Hello world!");
//
//        Consumer<String> helloPrinter = name -> {
//            System.out.println("1. Hello " + name);
//            System.out.println("2. Hello " + name);
//        };
//
//        Supplier<String> animalSupplier = () -> "cat";
//
//        String animal = animalSupplier.get();
//
//        helloPrinter.accept(animal);
//        helloPrinter.accept("abed");
//        helloPrinter.accept("mouhammad");
//        helloPrinter.accept("sameer");

//        ArrayList<List<String>> list = new ArrayList<>();
//        list.add( Arrays.asList("0one", "0two", "0three", "0four"));
//        list.add( Arrays.asList("1one", "1two", "1three", "1four"));
//        list.add( Arrays.asList("2one", "2two", "2three", "2four"));
//        list.add( Arrays.asList("3one", "3two", "3three", "3four"));
//        list.remove(0);
//        System.out.println(list.toString());


        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);

        LocalDateTime ld = LocalDateTime.now();
        System.out.println(ld);


    }


}