# gurukul
Test Projects in Microservices etc..

## compile and build the docker image for hub api
cd hub

docker build -t hubapi .

## compile and build the docker image for streams job
cd streams

docker build -t streamjob .


## start the application stack

docker-compose up -d

## hub api URL

http://localhost:9001/hub

## example curl command to send request to API
curl -X POST  -H "Content-Type: application/json" --data '{ "messageId":"79", "senderId": "shortcode0003", "MDN": "9898989857", "body": "Testing Shortcode3" }' localhost:9001/hub/

## Validate stack

### login to broker docker container

docker exec -it kafka-source bash

### get list of topics

kafka-topics --list  --zookeeper zookeeper-source:2181

Should list below 2 topics along with few other Kafka topics
	message_count
	message_topic3

### start consumer for message_topic3 where messages are stored

kafka-console-consumer --bootstrap-server localhost:9092 --topic message_topic3 --from-beginning

### start consumer for message_count where message count are stored

kafka-console-consumer --bootstrap-server localhost:9092 --topic message_count --from-beginning

## ELKK

### To get index from elastic search

 http://localhost:9200/_cat/indices
 
 Index name will be count_test-*
 
 ### To view index 
 
 http://localhost:9200/<index name>/_search
 
 ### Kibana dashboard
 
  http://localhost:5601/
  
## K8s
  
  all files available in K8s folder
  
  ### create namespace
  
  kubectl apply -f gurukul-ns.yaml
  
  ### install kafka strimzi operator
  
  kubectl apply -n gurukul -f strimzi-operator-gurukul.yaml
  
  ### create source kafka cluster
  
  kubectl apply -n gurukul -f source-cluster.yaml
  
  will create single node kafka cluster. Wait for few min to cluster to be created.
  
  ### create topic in source cluster
  
  it will create below 2 topics in source cluster:
  - message_topic3
  - message_count
  
  kubectl apply -n gurukul -f topic-source-cluster-message.yaml
  
  kubectl apply -n gurukul -f topic-source-cluster-count.yaml

  ### test the source cluster and topic 
  
  list the topics in source cluster
  
  kubectl -n gurukul run source-topic -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-topics.sh --list --bootstrap-server source-kafka-bootstrap:9092
  
  start producer for source cluster message_topic3 topic
  
  kubectl -n gurukul run source-producer -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --bootstrap-server source-kafka-bootstrap:9092 --topic message_topic3
  
  Start consumer for source cluster message_topic3 topic
  
  kubectl -n gurukul run source-consumer -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server source-kafka-bootstrap:9092 --topic message_topic3  --from-beginning
  
  
  kubectl -n gurukul run source-consumer-count -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server source-kafka-bootstrap:9092 --topic message_count  --from-beginning
  
  
  ### create target cluster 
  
  kubectl apply -n gurukul -f target-cluster.yaml 
  
  will create single node kafka cluster. Wait for few min to cluster to be created.
  
  ### no need to create topic in target cluster, it will be created auto
  
  ### MM2 creation
  
  kubectl apply -n gurukul -f mm2.yaml
  
  Will create MM2 service. Wait for few min to MM2 service to be created.
  
  ### test the replication in target cluster
  
  start consumer for target cluster message_topic3 topic 
  
  kubectl -n gurukul run target-consumer -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server target-kafka-bootstrap:9092 --topic gurukul-cluster-source.message_topic3  --from-beginning
  
  list the topics in target cluster
  
  kubectl -n gurukul run target-topic -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-topics.sh --list --bootstrap-server target-kafka-bootstrap:9092
  
  ### deploy hub api
  
  update the hubapi-deployment.yaml file to correct docker registry for image
  
	image: njoshi/hubapi  ---> change to correct registry and image
	
  Start hubapi deployment 
  
  kubectl apply -n gurukul -f hubapi-deployment.yaml
  
  kubectl apply -n gurukul -f hubapi-service.yaml
  
  The REST API available at:
  
  http://localhost:30007/hub
  
  ###prepare the stream job
  
  update the kafka cluster details in java file : TestStreams.java
  
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "<provide source kafka boot strap server>");
  
  ex: props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "source-kafka-bootstrap:9092");
  
  compile and push to docuker registry
  
   ### deploy stream job
   
  update the streamjob-deployment.yaml file to correct docker registry for image
  
	image: njoshi/streamjob  ---> change to correct registry and image
	
  Start stream job deployment 
  
  kubectl apply -n gurukul -f streamjob-deployment.yaml 
  
  ### test complete flow
  
  Start consumer for source cluster message_count topic
  
  kubectl -n gurukul run source-consumer -ti --image=quay.io/strimzi/kafka:0.25.0-kafka-2.8.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server source-kafka-bootstrap:9092 --topic message_topic3  --from-beginning
  
  
  ### ELK deployment
  
  ### create elastic  cluster
  
  kubectl apply -n gurukul -f elasticsearch-deployment.yaml
  
  kubectl apply -n gurukul -f elasticsearch-service.yaml
  
  ### create kibana cluster
	
  kubectl create configmap -n gurukul gurukul-kibana-config --from-file ./config/kibana.yml
  
  kubectl apply -n gurukul -f kibana-deployment.yaml
  
  kubectl apply -n gurukul -f kibana-service.yaml
  
  
  ### Access elactic search
  
  http://localhost:30008

  
  ### Access kibana
  
  http://localhost:30009
  
  
  ## deploying logstash
  
  
  kubectl create configmap -n gurukul gurukul-logstash-config --from-file ./config/logstash.conf
  
  kubectl apply -n gurukul -f logstash-deployment.yaml
  
  
  ## testing
  
  ### send request to below URL:
  
  http://localhost:30007/hub
  
  

  
 


