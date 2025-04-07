#!/bin/bash

echo "🔧 폴더 및 파일 자동 구성 시작..."

mkdir -p scripts
mkdir -p .github/workflows
mkdir -p src/main/java/com/example/demo
mkdir -p src/main/resources

# appspec.yml
cat > appspec.yml <<EOF
version: 0.0
os: linux
files:
  - source: /
    destination: /home/ec2-user/deploy

hooks:
  BeforeInstall:
    - location: scripts/beforeInstall.sh
      timeout: 300
      runas: ec2-user
  AfterInstall:
    - location: scripts/deploy.sh
      timeout: 300
      runas: ec2-user
EOF

# buildspec.yml
cat > buildspec.yml <<EOF
version: 0.2

phases:
  install:
    runtime-versions:
      java: corretto17
  build:
    commands:
      - echo "🔨 Maven Build 시작"
      - mvn clean package
  post_build:
    commands:
      - echo "📦 배포 zip 생성 중"
      - mkdir -p deploy
      - cp target/project1.war deploy/
      - cp appspec.yml deploy/
      - cp -r scripts deploy/
      - cd deploy
      - zip -r deploy-package.zip .
      - echo "🚀 S3로 업로드 중"
      - aws s3 cp deploy-package.zip s3://app-deploy-jp/team5-tokyo-build/deploy-package.zip --acl private

artifacts:
  files:
    - deploy/deploy-package.zip
EOF

# scripts
cat > scripts/beforeInstall.sh <<EOF
#!/bin/bash
echo "🧹 이전 배포 파일 제거 중..."
rm -rf /home/ec2-user/deploy
mkdir -p /home/ec2-user/deploy
EOF

cat > scripts/deploy.sh <<EOF
#!/bin/bash
echo "🚀 WAR 파일을 Tomcat 경로로 이동 중..."
cp /home/ec2-user/deploy/project1.war /opt/tomcat/webapps/
sudo systemctl restart tomcat
EOF

# GitHub Actions 워크플로우 자동 생성
cat > .github/workflows/deploy.yml <<EOF
name: Upload WAR Package to S3 (Tokyo)

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v3

      - name: Build with Maven
        run: |
          mvn clean package -DskipTests

      - name: Prepare deploy folder
        run: |
          mkdir deploy
          cp target/project1.war deploy/
          cp appspec.yml deploy/
          cp -r scripts deploy/
          cd deploy
          zip -r deploy-package.zip .

      - name: Upload to S3
        run: |
          aws s3 cp deploy/deploy-package.zip s3://app-deploy-jp/team5-tokyo-build/deploy-package.zip --acl private
        env:
          AWS_ACCESS_KEY_ID: \${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: \${{ secrets.AWS_SECRET_ACCESS_KEY }}
EOF

# 실행 권한 부여
chmod +x scripts/*.sh

echo "✅ 프로젝트 기본 구조가 완성되었습니다!"
