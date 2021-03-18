#!/bin/sh

#Expected parameters:
# $1 - name of webhook URL
# $2 - pipeline URL
# $3 - commit branch

MESSAGE="\"Pipeline Failed.<br> Commit Branch: $3 <br> Pipeline details: $2 \""
curl -H 'Content-Type: application/json' -d "{\"text\": $MESSAGE }" "$1"

