#!/bin/sh

#Expected parameters:
# $1 - name of webhook URL
# $2 - commit Message
# $3 - pipeline details

MESSAGE="\"New changes on branch: master.<br> Message:<br> $2 <br> Pipeline details: $3 \""
curl -H 'Content-Type: application/json' -d "{\"text\": $MESSAGE }" "$1"

