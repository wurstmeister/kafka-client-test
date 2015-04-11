package org.wurstmeister;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.List;
import java.util.Properties;


public class TestCase {

    private static enum Mode {
        PER_PARTITION,
        TOPIC
    }

    public static void main(String[] args) throws InterruptedException {
        Mode mode = Mode.valueOf(args[0]);

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.59.103:49093,192.168.59.103:49094");
        props.put("group.id", "my-test-2");
        props.put("enable.auto.commit", "false");
        props.put("session.timeout.ms", "30000");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        String topic = "topic";

        List<PartitionInfo> partitionInfos = consumer.partitionsFor("topic");

        if (mode.equals(Mode.PER_PARTITION) ) {
            for (PartitionInfo partitionInfo : partitionInfos) {
                TopicPartition partition = new TopicPartition(topic, partitionInfo.partition());
                consumer.subscribe(partition);
            }
        } else {
            consumer.subscribe(topic);
        }

        System.out.println(consumer.subscriptions());

        while (true) {
            System.out.println("Checking partitions ---------------");
            consumer.poll(100);
            partitionInfos = consumer.partitionsFor("topic");
            for (PartitionInfo partitionInfo : partitionInfos) {
                System.out.println(partitionInfo + " leader " + partitionInfo.leader());
            }
            Thread.sleep(2000);
        }
    }
}
