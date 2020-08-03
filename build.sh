#!/usr/bin/env bash

revision=$(git rev-list HEAD --count)
branch=$(git branch | grep \* | cut -d ' ' -f2)
version="search-${branch}-${revision}"
date=$(date -u)
echo Deploying version ${version}

#Stamp the git revision into the properties
sed -i '' "s/\version:.*/version: ${version}/" src/main/resources/application.yaml
sed -i '' "s/\timestamp:.*/timestamp: ${date}/" src/main/resources/application.yaml

mvn clean
mvn package -DskipTests


repo=starter
cluster=834254992668

#Build the container
docker build -t ${repo}:latest .
docker tag ${repo}:latest ${cluster}.dkr.ecr.us-east-1.amazonaws.com/${repo}:latest

#Auth against the repository as the current AWS user and push the container
#aws --region us-east-1 ecr get-login-password | docker login --username AWS --password-stdin ${cluster}.dkr.ecr.us-east-1.amazonaws.com
#docker push ${cluster}.dkr.ecr.us-east-1.amazonaws.com/${repo}

mvn clean




