export DATABASE_URL="postgresql://localhost:5430/passwordSafe"
start() {
    mvn spring-boot:run $@
}

upDB() {
    docker-compose up $@
}

downDB() {
    docker-compose down $@
}

resetDB() {
    downDB
    upDB $@
}

shellDB() {
    docker exec -it passwordSafeDB /bin/bash
}

psqlDB() {
    docker exec -it passwordSafeDB /usr/bin/psql -U postgres -d passwordSafe -W 
}
