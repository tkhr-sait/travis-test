#!/bin/bash
# 前提：
# ビルドは通っていること
# 環境変数が定義されていること(これは内緒情報なのでtravisで定義が良い)
# GH_USER
# GH_APIKEY
# BINTRAY_USER
# BINTRAY_ORG
# BINTRAY_REPO
# BINTRAY_PKG
# BINTRAY_APIKEY
# 環境変数が定義された場合のみ動作する
# RUN_IN_TRAVIS

function incrementVersion() {
  ver=$1
  major=$(echo $ver | sed 's/\.[0-9]*\.[0-9]*$//')
  minor=$(echo $ver | sed -e 's/^[0-9]*\.//' -e 's/\.[0-9]*$//')
  release=$(echo $ver | sed 's/^[0-9]*\.[0-9]*\.//')
  echo "$major.$(( $minor + 1 )).0"
}

BASE=$(cd $(dirname $0)/..; pwd)
VERSION=$(cat $BASE/src/VERSION)
cur_filename=$BASE/dist/testfile-$VERSION.txt
mkdir -p $BASE/dist
echo $VERSION > $cur_filename

if [ "$RUN_IN_TRAVIS" = "" ]; then
  exit 0
fi

case $VERSION in
  *SNAPSHOT)
    echo SNAPSHOT
    exit 0
    ;;
  *)
    echo RELEASE
    git config --global user.name  "${GH_USER}"
    git config --global user.email "${GH_USER}@users.noreply.github.com"
    git config --global credential.helper "store --file=/home/travis/.config/git-credential"

    mkdir -p /home/travis/.config
    echo "https://${GH_APIKEY}:@github.com" > /home/travis/.config/git-credential
    git config -l

    # github tag打ち

    # bintrayにpush
    echo bintray push

    curl \
      --request PUT \
      --upload-file "${cur_filename}" \
      --user ${BINTRAY_USER}:${BINTRAY_APIKEY} \
      "https://api.bintray.com/content/${BINTRAY_ORG}/${BINTRAY_REPO}/${BINTRAY_PKG}/${VERSION}/${cur_filename}?publish=1"

    sleep 10

    curl \
      --request PUT \
      --header "Content-Type: application/json" \
      --data-binary '{ "list_in_downloads" : true }' \
      --user ${BINTRAY_USER}:${BINTRAY_APIKEY} \
      "https://api.bintray.com/file_metadata/${BINTRAY_ORG}/${BINTRAY_REPO}/${cur_filename}"

    # タグ打ち
    git tag -a $VERSION -m "tagging from ci"
    git push origin $VERSION

    # マイナーバージョン上げる
    NEW_VER=$(incrementVersion $VERSION)
    echo $NEW_VER"-SNAPSHOT" > $BASE/src/VERSION

    # githubにコミット
    git add $BASE/src/VERSION
    git commit -m "commit from ci"
    git push -u origin master
    date

    git tag


    exit 0
    ;;
esac

