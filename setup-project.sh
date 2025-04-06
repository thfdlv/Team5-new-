#!/bin/bash

echo "🔧 폴더 및 파일 자동 구성 시작..."

# 디렉토리 생성
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
      - echo "📦 빌드된 WAR 파일을 S3로 복사"
      - aws s3 cp target/project1.war s3://app-deploy-jp/deploy/project1.war

artifacts:
  files:
    - target/project1.war
EOF

# beforeInstall.sh
cat > scripts/beforeInstall.sh <<EOF
#!/bin/bash
echo "🧹 이전 배포 파일 제거 중..."
rm -rf /home/ec2-user/deploy
mkdir -p /home/ec2-user/deploy
EOF

# deploy.sh
cat > scripts/deploy.sh <<EOF
#!/bin/bash
echo "🚀 WAR 파일을 Tomcat 경로로 이동 중..."
cp /home/ec2-user/deploy/target/project1.war /opt/tomcat/webapps/
sudo systemctl restart tomcat
EOF

# GitHub Actions deploy.yml
cat > .github/workflows/deploy.yml <<EOF
name: Upload to S3 (Tokyo)

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v3

      - name: Make ZIP
        run: zip -r project1.zip . -x ".git/*"

      - name: Upload to S3
        uses: jakejarvis/s3-sync-action@master
        with:
          args: --acl private
        env:
          AWS_S3_BUCKET: app-deploy-jp
          AWS_REGION: ap-northeast-1
          AWS_ACCESS_KEY_ID: \${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: \${{ secrets.AWS_SECRET_ACCESS_KEY }}
EOF

# 실행 권한 부여
chmod +x scripts/*.sh

echo "✅ 프로젝트 기본 구조가 구성되었습니다!"
