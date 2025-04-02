#!/bin/bash
echo "🧹 [BeforeInstall] 기존 파일 정리 중..."

# Tomcat에 배포된 boot.war 삭제
sudo rm -f /opt/tomcat/webapps/boot.war

echo "✅ [BeforeInstall] 완료!"
