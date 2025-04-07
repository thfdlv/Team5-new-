#!/bin/bash
echo "[🧽] 기존 파일 삭제 시작"

sudo systemctl stop tomcat
rm -rf /home/ec2-user/app/*
