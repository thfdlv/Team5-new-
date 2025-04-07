#!/bin/bash
echo "🚀 배포 시작"

# 기존 war 제거
rm -f /opt/tomcat/webapps/*.war

# S3에서 새로운 WAR 파일 복사
aws s3 cp s3://app-deploy-jp/project1.war /opt/tomcat/webapps/

# Tomcat 재시작
sudo systemctl restart tomcat

echo "🎉 배포 완료"

