#!/bin/bash

echo "🧹 [BeforeInstall] 기존 파일 정리 중..."

# 이전에 있던 앱 디렉토리 삭제
if [ -d "/home/ec2-user/app/Team5" ]; then
  echo "🗑️ 기존 Team5 폴더 삭제"
  rm -rf /home/ec2-user/app/Team5
fi

# Tomcat WAR 디렉토리 초기화 (옵션)
if [ -d "/opt/tomcat/tomcat-10/webapps/Team5" ]; then
  echo "🧼 이전에 배포된 WAR 제거"
  sudo rm -rf /opt/tomcat/tomcat-10/webapps/Team5
  sudo rm -f /opt/tomcat/tomcat-10/webapps/*.war
fi

echo "✅ [BeforeInstall] 완료!"
