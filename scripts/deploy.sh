#!/bin/bash
echo "[🚀] 배포 스크립트 시작"

WAR_FILE="/home/ec2-user/app/project1.war"
TOMCAT_DIR="/opt/tomcat"

if [ -f "$WAR_FILE" ]; then
  echo "WAR 파일 복사 중..."
  cp "$WAR_FILE" "$TOMCAT_DIR/webapps/"
else
  echo "WAR 파일이 존재하지 않습니다: $WAR_FILE"
  exit 1
fi

sudo systemctl start tomcat
