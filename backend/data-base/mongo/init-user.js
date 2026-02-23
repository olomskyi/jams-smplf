db = db.getSiblingDB("jamsdb");
db.createUser({
user: "user",
pwd: "pass",
roles: [
        { role: "readWrite", db: "jamsdb" }
    ]
});