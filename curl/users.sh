# find all password of user 2
curl --location --request GET 'https://password-safe-fatec.herokuapp.com/api/users/2/password'

# find password hello of user 2
curl --location --request GET 'https://password-safe-fatec.herokuapp.com/api/users/2/password/hello'

# create user
curl --location --request POST 'https://password-safe-fatec.herokuapp.com/api/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "User",
    "email": "new@email.com",
    "masterPassword": "bad_pwd",
    "password": {
        "name": "hello",
        "password": "hrk123"
    }
}'

# find user
curl --location --request GET 'https://password-safe-fatec.herokuapp.com/api/users/2'

# add password to user 1
curl --location --request POST 'https://password-safe-fatec.herokuapp.com/api/users/1/password' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "siga",
    "password": "nao_consigo_ler_nada"
}'