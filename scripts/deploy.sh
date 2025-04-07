#!/bin/bash
echo "🚀 WAR 파일을 Tomcat 경로로 이동 중..."
cp /home/ec2-user/deploy/target/project1.war /opt/tomcat/webapps/
sudo systemctl restart tomcat
