# usefull commands

## compile and build the docker image
docker build -t hubapi .

## run the API service
docker run -p 9001:9001 hubapi

## api URL

http://localhost:9001/hub