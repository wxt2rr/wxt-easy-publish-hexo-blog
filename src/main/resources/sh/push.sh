#!/bin/sh
echo "begin"
# shellcheck disable=SC2164
cd D:\自己\images
git pull
git add .
git commit -m "commit_message"
git push origin main

echo "end"