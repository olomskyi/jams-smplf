### MONGO DB  
docker build -t jams-mongodb -f Dockerfile.mongo .  
docker run -d --name jams-mongodb-container -p 27017:27017 --env-file .env -v jams_mongo_data:/data/db jams-mongodb  
### MYSQL DB  
docker build -t jams-mysqldb -f Dockerfile.mysql .  
docker run -d --name jams-mysqldb-container -p 3306:3306 --env-file .env -v jams_mysql_data:/var/lib/mysql jams-mysqldb  
