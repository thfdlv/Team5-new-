#!/bin/bash
echo "✅ 배포 시작"

# 기존 WAR 삭제
rm -f /opt/tomcat/webapps/*.war

LATEST_WAR=$(aws s3 ls s3://app-deploy-jp/ | sort | tail -n 1 | awk '{print $4}')
aws s3 cp s3://app-deploy-jp/$LATEST_WAR /opt/tomcat/webapps/

# Tomcat 재시작
sudo systemctl restart tomcat

echo "✅ 배포 완료"
