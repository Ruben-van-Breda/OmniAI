echo Maven clean and compiling
mvn clean install

echo Maven packaging
mvn package

echo Dockerizing the application

docker-compose down
docker-compose up --build

