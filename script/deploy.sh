#!/bin/sh
set -e # Exit on first error

source ../private/script.conf

# Handle params
[ -z $1 ] && (echo "App version is missing"; exit 1)
APP_VERSION=$1

echo "Building jar..."
(cd .. && ./gradlew clean build)
JAR_FILE=$(cd ../build/libs && ls -F | grep *.jar)

echo "Uploading $JAR_FILE to S3..."
aws s3 cp ../build/libs/$JAR_FILE s3://$S3_BUCKET_NAME

echo "Creating new app version..."
aws elasticbeanstalk create-application-version \
--application-name $APP_NAME \
--version-label $APP_VERSION \
--source-bundle S3Bucket="$S3_BUCKET_NAME",S3Key="$JAR_FILE"

echo "Deploying new app version..."
aws elasticbeanstalk update-environment --environment-name $BEANSTALK_ENVIRONMENT --version-label $APP_VERSION

