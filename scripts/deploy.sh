#!/bin/bash

echo "🚀 [ApplicationStart] 배포 시작"

WAR_SOURCE="/home/ec2-user/app/project1.war"
WAR_DEST="/opt/tomcat/tomcat-10/webapps/project1.war"

# WAR 복사
echo "📦 WAR 복사: $WAR_SOURCE → $WAR_DEST"
sudo cp "$WAR_SOURCE" "$WAR_DEST"

# Tomcat 재시작
echo "🔁 Tomcat 재시작"
sudo systemctl restart tomcat

echo "✅ [ApplicationStart] 배포 완료"
