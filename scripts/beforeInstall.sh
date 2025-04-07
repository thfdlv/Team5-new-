#!/bin/bash

echo "[🚧 BEFORE INSTALL] 이전 배포 파일 제거 중..."

WAR_FILE="/opt/tomcat/webapps/project1.war"
WAR_DIR="/opt/tomcat/webapps/project1"

# WAR 제거
if [ -f "$WAR_FILE" ]; then
  echo "👉 WAR 파일 삭제: $WAR_FILE"
  rm -f "$WAR_FILE"
fi

# explode 디렉토리 제거
if [ -d "$WAR_DIR" ]; then
  echo "👉 explode 디렉토리 삭제: $WAR_DIR"
  rm -rf "$WAR_DIR"
fi

# 이전 스크립트 및 war 파일 제거
rm -rf /home/ec2-user/app/scripts/
rm -f /home/ec2-user/app/project1.war

echo "[✅ BEFORE INSTALL] 정리 완료!"
