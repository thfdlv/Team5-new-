#!/bin/bash

echo "✅ S3에서 WAR 파일 다운로드 중..."
aws s3 cp s3://app-deploy-jp/jsonExam-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/

echo "✅ Tomcat 재시작 중..."
systemctl restart tomcat
