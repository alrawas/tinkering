package com.alrawas.streamingrequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StreamingController {

    private final Logger logger = LoggerFactory.getLogger(StreamingController.class);

    // 1. In this endpoint /stream-custom-async
    //    I Used Async annotation to instruct Spring Web to use our taskExecutor we defined in AsyncConfiguration.class
    //    instead of it picking up the simple http-nio-8080-exec executor

    // 2. Wrapped ResponseEntity with CompletableFuture response. Without it
    //    the endpoint is not going to function properly

    @Async("taskExecutor")
    @RequestMapping("/stream-custom-async")
    public CompletableFuture<ResponseEntity<StreamingResponseBody>> handleStreamingOurAsyncRequest(HttpServletRequest httpServletRequest) {

        logger.info("asyncSupported: " + httpServletRequest.isAsyncSupported());
        logger.info(Thread.currentThread().getName());
        logger.info(Thread.currentThread().getThreadGroup().getName());

        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {

                Map<String, BigInteger> map = new HashMap<>();
                map.put("one", BigInteger.ONE);
                map.put("ten", BigInteger.TEN);
//                try(ObjectOutputStream oos = new ObjectOutputStream(out)){
//                    oos.writeObject(map);
//                }
                out.write(convertMapToString(map).getBytes());

//                for (int i = 0; i < 2000; i++) {
//                    out.write((Integer.toString(i) + " - ").getBytes());
//                    out.flush();
//                    try {
//                        Thread.sleep(5);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        };

        return CompletableFuture.supplyAsync(() -> new ResponseEntity(responseBody, HttpStatus.OK));
    }

    private String convertMapToString(Map<?, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

    // This endpoint executes asynchronously by default because the response type is StreamingResponseBody
    // The taskExecutor used by default is http-nio- executor
    @RequestMapping("/stream-simple-async")
    public ResponseEntity<StreamingResponseBody> handleStreamingRequest(HttpServletRequest httpServletRequest) {

        logger.info("asyncSupported: " + httpServletRequest.isAsyncSupported());
        logger.info(Thread.currentThread().getName());
        logger.info(Thread.currentThread().getThreadGroup().getName());

        StreamingResponseBody responseBody = new StreamingResponseBody() {
            @Override
            public void writeTo(OutputStream out) throws IOException {
                for (int i = 0; i < 1000; i++) {
                    out.write((Integer.toString(i) + " - ").getBytes());
                    out.flush();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

}