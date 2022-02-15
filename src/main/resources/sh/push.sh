#!/bin/sh
echo "begin"
# shellcheck disable=SC2164
cd $1
git pull
git add .
git commit -m $2
git push origin $3

echo "end"