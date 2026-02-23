### MONGO DB  
docker build -f docker/Dockerfile.mongo -t mongo-db .  
docker run -d --name mongo-db --env-file .env -p 27017:27017 -v ./data-base/mongo/init-user.js:/docker-entrypoint-initdb.d/init-user.js mongo-db  
### MYSQL DB  
docker build -t jams-mysqldb -f docker/Dockerfile.mysql .  
docker run -d --name jams-mysqldb-container -p 3306:3306 --env-file .env -v jams_mysql_data:/var/lib/mysql jams-mysqldb  
