
docker build -t custom-mongo -f Dockerfile.mongo .

docker run -d --name jams-mongodb -p 27017:27017 -v "$(pwd)/../mongo_data:/data/db" custom-mongo
