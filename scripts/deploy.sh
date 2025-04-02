#!/bin/bash
echo "✅ 배포 시작"

# 기존 WAR 삭제
rm -f /opt/tomcat/webapps/*.war

# 최신 WAR 다운로드 (이름과 상관없이 최신 1개)
aws s3 cp s3://app-deploy-jp/*.war /opt/tomcat/webapps/

# Tomcat 재시작
sudo systemctl restart tomcat

echo "✅ 배포 완료"
