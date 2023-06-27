#!/usr/bin/env bash
set -e

# 变量
action=$1
version=$2

function info() {
  echo -e "\033[32m$1\033[0m"
}

function error() {
  echo -e "\033[31m$1\033[0m"
}

# 如果 action 为 version, 则执行版本号相关操作
# 如果 action 为 publish, 则执行发布相关操作
# 否则,打印action
if [ "${action}" == "version" ]; then
  info "参数校验..."
  if [ -z "${version}" ] ;then
      error "请输入版本号! 示例: 1.0.0"
      exit
  fi
  info "参数校验...OK!\n"

  # 修改 README.md 版本号
  info "修改 README.md 版本号..."
  sed -i '' "s/<version>.*<\/version>/<version>${version}<\/version>/g" README.md
  info "修改 README.md 版本号...OK!\n"

  # 更新子模块 pom.xml 中 parent.version 版本号
  info "更新子模块 pom.xml 中 parent.version 版本号..."
  mvn versions:set -DnewVersion=${version}
  info "更新子模块 pom.xml 中 parent.version 版本号...OK!\n"

  info "更新 pom.xml 中 mono.version 版本号..."
  sed -i '' "s/<mono\.version>.*<\/mono\.version>/<mono\.version>${version}<\/mono\.version>/g" pom.xml
  info "更新 pom.xml 中 mono.version 版本号...OK!\n"

  # 进行${version}版本的开发...
  echo "进行${version}版本的开发..."
elif [ "${action}" == "publish" ]; then
  info "参数校验..."
  if [ -z "${version}" ] ;then
      error "请输入版本号! 示例: 1.0.0"
      exit
  fi
  info "参数校验...OK!\n"

  info "检查本地是否有代码未提交..."
  if [ -n "$(git status -s)" ] ;then
      error "本地有未提交的代码!"
      exit
  fi
  info "检查本地是否有代码未提交...OK!\n"

  info "切换到 master 分支..."
  git checkout master
  info "切换到 master 分支...OK!\n"

  info "拉取 master 分支..."
  git pull origin master
  info "拉取 master 分支...OK!\n"

  info "合并 develop 分支到 master 分支..."
  git merge develop
  info "合并 develop 分支到 master 分支...OK!\n"

  info "修改 README.md 版本号..."
  sed -i '' "s/<version>.*<\/version>/<version>${version}<\/version>/g" README.md
  info "修改 README.md 版本号...OK!\n"

  info "更新子模块 pom.xml 中 parent.version 版本号..."
  mvn versions:set -DnewVersion=${version}
  info "更新子模块 pom.xml 中 parent.version 版本号...OK!\n"

  info "更新 pom.xml 中 mono.version 版本号..."
  sed -i '' "s/<mono\.version>.*<\/mono\.version>/<mono\.version>${version}<\/mono\.version>/g" pom.xml
  info "更新 pom.xml 中 mono.version 版本号...OK!\n"

  info "发布到 maven..."
  mvn clean deploy -DskipTests
  info "发布到 maven...OK!\n"

  info "提交代码并推送..."
  git add .
  git commit -m "发布 ${version}" ||
  info "提交代码并推送...OK!\n"

  info "推送分支..."
  git push origin master
  info "推送分支...OK!\n"

  info "检查tag是否存在..."
  if git rev-parse "$version" >/dev/null 2>&1; then
    info "tag存在!"
    info "删除本地旧的 tag..."
    git tag -d "${version}"
    info "删除本地旧的 tag...OK!\n"

    info "删除远程旧的 tag..."
    git push --delete origin "${version}"
    info "删除远程旧的 tag...OK!\n"
  fi

  info "创建 tag..."
  git tag -a "${version}" -m "${version}"
  info "创建 tag...OK!\n"

  info "推送 tag..."
  git push origin "${version}"
  info "推送 tag...OK!\n"

  info "切换到 develop 分支..."
  git checkout develop
  info "切换到 develop 分支...OK!\n"

  info "将 master 分支合并到 develop 分支..."
  git merge master
  info "将 master 分支合并到 develop 分支...OK!\n"

  info "推送 develop 分支..."
  git push origin develop
  info "推送 develop 分支...OK!\n"

  echo "进行下一版本的开发,请设置新版本version..."
fi
