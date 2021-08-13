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
  
 


