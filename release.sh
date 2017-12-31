#!/bin/bash
# 前提：
# ビルドは通っていること
# 環境変数が定義されていること(これは内緒情報なのでtravisで定義が良い)
# GIT_USER
# GIT_PASSWORD
# BINTRAY_KEY

increment () {
    ver=$1
    major=$(echo $ver | sed 's/\.[0-9]*\.[0-9]*$//')
    minor=$(echo $ver | sed -e 's/^[0-9]*\.//' -e 's/\.[0-9]*$//')
    release=$(echo $ver | sed 's/^[0-9]*\.[0-9]*\.//')
    echo "$major.$(expr $minor + 1).$release"
}

BASE=$(dirname $0)
VERSION=$(cat $BASE/src/VERSION)

case $VERSION in
*-SNAPSHOT)
    echo SNAPSHOT
    exit 0
    ;;
*)
    echo RELEASE
    # github tag打ち
    echo git tag -a $VERSION

    # bintrayにpush
    echo jfrog bt u "testfile" tkhr-sait/test/$VERSION

    # マイナーバージョン上げる
    NEW_VER=$(increment $VERSION)
    # githubにコミット
    echo $NEW_VER"-SNAPSHOT"
    echo git add src/VERSION
    echo git commit -m "ci"
    echo git push
    exit 0
    ;;
esac

