echo Maven clean and compiling
call mvn clean install

echo Maven packaging
call mvn package

echo Dockerizing the application

docker-compose down
docker-compose up --build

