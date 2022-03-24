# PASSWORD SAFE
Example spring + postgres application.

# SCRIPTS
run source cli.sh. Then run functions as commands

# ENV VARIABLES
Required environment variables:
```sh
export DATABASE_URL="postgresql://localhost:5430/passwordSafe"
```
```bat
set DATABASE_URL="postgresql://localhost:5430/passwordSafe"
```
# DEPLOY
Steps to deploy a new version of the application(login as coastgabrielle@gmail.com)
```sh
heroku login 
git push master heroku
```

#DB
run the docker-compose to init db



