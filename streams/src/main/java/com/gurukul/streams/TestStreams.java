package com.gurukul.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Suppressed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class TestStreams {


    public static void main(String[] args) throws Exception {
    	
    	//Thread.sleep(180000L);
    	
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "messagecounter");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka-source:29092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        String inputTopic = "message_topic3";
        String outputTopic = "message_count";

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(inputTopic, Consumed.with(Serdes.String(), Serdes.String()))
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                .count().mapValues(value -> "count is " + Long.toString(value))
                //.suppress(Suppressed.untilWindowCloses(Suppressed.BufferConfig.unbounded()))
                .toStream().to(outputTopic);
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
         try {
            streams.cleanUp();
            streams.start();
            //Thread.sleep(60000L);
            //streams.close();
        } catch (final Throwable e) {
            e.printStackTrace();
        }

        }
}
