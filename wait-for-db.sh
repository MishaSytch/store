# wait-for-db.sh
#!/bin/bash
until nc -z -v -w30 $SPRING_DATASOURCE_HOST $SPRING_DATASOURCE_PORT
do
  echo "Waiting for database connection..."
  sleep 5
done
echo "Database is up and running!"