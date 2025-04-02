#!/bin/bash
echo "🧹 BeforeInstall - 기존 디렉토리 정리 중"

# 기존 디렉토리 삭제
if [ -d /home/ec2-user/app ]; then
  sudo rm -rf /home/ec2-user/app
fi

# 새로 생성
sudo mkdir -p /home/ec2-user/app

echo "✅ BeforeInstall 완료"

