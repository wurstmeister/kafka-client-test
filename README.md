# kafka-client-test

Temporary test project for validating kafka 0.8.3 client for apache storm

Dependencies: 

- docker compose 
- boot2docker (if you run docker natively update KAFKA_ADVERTISED_HOST_NAME and KAFKA_ZOOKEEPER_CONNECT properties in the docker compose yml files)
- https://raw.githubusercontent.com/wurstmeister/kafka-docker/master/start-kafka-shell.sh
- Kafka dev image built with: https://github.com/wurstmeister/kafka-docker/blob/dev/Dockerfile (available on docker hub)


#Common setup for tests:

```docker-compose -f zk.yml up -d```

```docker-compose -f broker-1.yml up -d```

```docker-compose -f broker-2.yml up -d```


```start-kafka-shell.sh 192.168.59.103 192.168.59.103:49181```

```$KAFKA_HOME/bin/kafka-topics.sh --create --topic topic --partitions 2 --zookeeper $ZK --replication-factor 2```

```$KAFKA_HOME/bin/kafka-topics.sh --describe --topic topic --zookeeper $ZK```

#Test cleanup:

```docker-compose stop```

```docker-compose rm```


#Test case 1 (Per topic subscription):

```java -jar target/kafka-client-test-1.0-SNAPSHOT-jar-with-dependencies.jar TOPIC```


Kill Broker 1:

```docker-compose -f broker-1.yml stop kafka```

New leader will be identified 

Restart Broker 1

```docker-compose -f broker-1.yml up -d```

Kill Broker 2: 

```docker-compose -f broker-2.yml stop kafka2```

New leader will be identified

Restart Broker 2:

```docker-compose -f broker-2.yml up -d```

Kill Broker 1:

```docker-compose -f broker-1.yml stop kafka```

New leader will be identified

--> OK


#Test case 2 (Per partition subscription):

```java -jar target/kafka-client-test-1.0-SNAPSHOT-jar-with-dependencies.jar PER_PARTITION```

Kill Broker 1:

```docker-compose -f broker-1.yml stop kafka```

New leader will be identified 

Restart Broker 1

```docker-compose -f broker-1.yml up -d```

Kill Broker 2: 

```docker-compose -f broker-2.yml stop kafka2```

--> New leader is not identified




