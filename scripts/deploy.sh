#!/bin/bash
echo "✅ 배포 시작"

# 기존 WAR 삭제 (선택)
rm -f /opt/tomcat/webapps/*.war

# S3에서 최신 WAR 다운로드 (버킷 이름은 너희 팀의 실제 이름으로 바꿔줘)
aws s3 cp s3://app-deploy-jp/jsonExam-0.0.1-SNAPSHOT.war /opt/tomcat/webapps/

# Tomcat 재시작 (systemctl 방식, Amazon Linux 2023 기준)
sudo systemctl restart tomcat

echo "✅ 배포 완료"

