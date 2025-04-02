#!/bin/bash
set -e

echo "[BeforeInstall] 앱 디렉토리 초기화 중..."

if [ -d /home/ec2-user/app ]; then
  rm -rf /home/ec2-user/app/*
fi

echo "[BeforeInstall] 완료!"

