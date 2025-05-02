#!/bin/bash

URL="http://localhost:8080/api/data"
RATE=0.2  # seconds
COUNT=50

for ((i=1; i<=COUNT; i++))
do
  echo "Request #$i"
  curl -H "X-User-Id:Saurabh" -s -o /dev/null -w "%{http_code}\n" $URL
  sleep $RATE
done
