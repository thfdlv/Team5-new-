#!/bin/bash

echo "[🚀 DEPLOY] WAR 파일 실행 준비 중..."

echo "[🛑] Tomcat 프로세스 종료 중..."
sudo pkill -f 'org.apache.catalina.startup.Bootstrap'

echo "[🧹] 기존 WAR 및 디렉터리 삭제 중..."
sudo rm -f /opt/tomcat/webapps/project1.war
sudo rm -rf /opt/tomcat/webapps/project1

echo "[📦] 새 WAR 복사"
sudo cp /home/ec2-user/app/project1.war /opt/tomcat/webapps/

echo "[🔁] Tomcat 재시작 중..."
sudo /opt/tomcat/bin/startup.sh

echo "[✅] 배포 완료!"
