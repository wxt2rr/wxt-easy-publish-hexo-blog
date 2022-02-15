# wxt-easy-publish-hexo-blog
# 功能
> 一键发布hexo博客，自动上传MD文档中的本地图片到github，并替换图片地址

## 原理
* Parser 负责解析md文件
* Replacer 负责替换md中的图片，复制图片到本地github图床项目的指定路径下
* Pusher 负责将替换之后的md文档和本地github图床中复制的图片推送到github

# 使用
## 准备工作
### 安装git
~~~sh
记录git安装目录中 D:\software\Git\bin 下的sh.exe
~~~

### 创建根目录
~~~sh
D:\my-project
~~~

### 克隆自己的blog项目到根目录
~~~sh
D:\my-project\my-blog
~~~

### 克隆自己的github图床项目到根目录
~~~sh
D:\my-project\images
~~~

### 克隆本项目
~~~sh
git clone https://github.com/wxt1471520488/wxt-easy-publish-hexo-blog.git
~~~

### 修改本项目中配置文件中的参数
~~~java
# 根路径
push.rp = D:\\my-project
# github账号
push.gn = wxt1471520488
# 博客项目路径
push.bp = my-blog
# github图床项目路径(博客中的图片会存在该路径下)
push.ip = images\\hexo
# md文件名称
push.mfn = wxt.md
# git sh.exe文件路径
push.gp = D:\\software\\Git\\bin\\sh.exe
~~~

### 也可以以jar包运行时传递，默认以传递参数为准
~~~java
java -jar test.jar rp=D:\\my-project gn=wxt1471520488 bp=my-blog ip=images\\hexo mfn=wxt.md gp=D:\\software\\Git\\bin\\sh.exe
~~~

## 使用流程
### 写博客
将博客保存到本地博客项目中的 D:\my-project\my-blog\source\_posts 路径下

### 运行本项目

## 代办
* 支持全网图片资源替换(目前仅支持比较正规的网络链接替换)
* 支持多文件并行操作
* 支持md其它格式解析(目前仅支持图片)
* 内置sh，目前window下需要使用Git运行sh脚本
