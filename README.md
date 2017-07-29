Mostly based on [Spark](https://github.com/perwendel/spark)(Not Apache Spark).     
[Online demo](http://119.29.162.115:8080)   
Only ten days has been taken to do this, so there may be some bugs inside.

### How to use
#### login
post ```x-www-form-urlencoded``` data to ```"192.168.21.100:4567/login"```
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "password": "1111"
}
```
returns:    
if password is not correct
````json
{
    "isOk":false,
    "msg":"密码错误"
}
````
if password is correct(token is a string which **uniquely** identify one user)
```json
{
    "isOk":true,
    "msg":"登录成功",
    "user":{
        "user_id":"zhizhonghwang@gmail.com",
        "user_name":"Hzzone",
        "password":"1111",
        "token":"121212"
    }
}
```
if user does not exists
```json
{
    "isOk":false,
    "msg":"用户不存在"
}
```

#### register
First post data to ```"192.168.21.100:4567/register-1"```
```json
{
  "user_id": "1141408077@qq.com",
  "user_name": "Hzzone",
  "password": "1111"
}
```
if user exists
```json
{
    "isOk":false,
    "msg":"用户已存在"
}
```
if not exists, send verification email to user's email address, and returns
```json
{
    "isOk":true,
    "msg":"349952"
}
```

repeatedly post same data to ```"192.168.21.100:4567/register-2"```, returns
```json
{
    "isOk":true,
    "msg":"注册成功",
    "user":{
        "user_id":"1141408077@qq.com",
        "user_name":"Hzzone",
        "password":"1111",
        "token":"eevpxwfxdlaaldyymsggqasapawydk"
    }
}
```
```json
{
    "isOk":true,
    "user":{
        "user_id":"1141408077@qq.com",
        "user_name":"Hzzone",
        "password":"1111",
        "token":"nwvwyihjyjjabdiakogqdewdjadlaz"
    }
}
```

#### Make a post
post data to ```"192.168.21.100:4567/make-new-post"```,
```json
{
  "title": "This is a test",
  "Content": "Test content",
  "user_id": "zhizhonghwang@gmail.com"
}
```
returns,
```json
{
  "isOk": true,
  "msg": "发表成功",
  "post": {
      "title": "This is a test",
      "Content": "Test content",
      "user_id": "zhizhonghwang@gmail.com",
      "post_id": "xxxxxxxxxx",
      "time": "Jul 21, 2017 2:19:06 PM"
  }
}
```
else returns,
```json
{
  "isOk": false,
  "msg": "发表失败"
}
```

#### Get recently posts
post user_id to ```"192.168.21.100:4567/post"```,
```json
{
  "post_id": "ijejlglmslisdaybdgujhcjusdnwoe",
  "category": "新鲜事"
}
```
```json
{
    "isOk": true,
    "msg": "获取成功",
    "posts": [
        {
            "post_id": "11",
            "author_name": "Hzzone",
            "author_id": "zhizhonghwang@gmail.com",
            "title": "This is a test",
            "content": "test",
            "time": "七月 22, 2017",
            "author_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "isLiked": false,
            "liked": 1
        },
        {
            "post_id": "22",
            "author_name": "Hzzone",
            "author_id": "zhizhonghwang@gmail.com",
            "title": "This is a test",
            "content": "test ",
            "time": "七月 21, 2017",
            "author_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "isLiked": false,
            "liked": 0
        },
        {
            "post_id": "33",
            "author_name": "Zhizhong",
            "author_id": "1141408077@qq.com",
            "title": "This is a test",
            "content": "test",
            "time": "七月 20, 2017",
            "author_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "isLiked": false,
            "liked": 0
        }
    ]
}
```
above will only return recent 3 posts, if you want more, post the end post id to ```"192.168.21.100:4567/get-new-post"```
```json
{
  "post_id": "ijejlglmslisdaybdgujhcjusdnwoe",
  "category": "新鲜事"
}
```
this will also return the next recent 3 posts, returns as:
```json
{
    "isOk":true,
    "msg":"获取成功",
    "posts":[
        {
            "post_id":"44",
            "author_name":"Zhizhong",
            "author_id":"1141408077@qq.com",
            "title":"4",
            "content":"test",
            "time":"七月 19, 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":1
        }
    ]
}
```
if there is no more post, it will return:
```json
{
    "isOk":false,
    "msg":"已经没有更多了"
}
```

#### Get the post details
post data to ```"192.168.21.100:4567/post-details"```
```json
{
  "post_id": "11",
  "user_id": "zhizhonghwang@gmail.com",
  "category": "新鲜事"
}
```
returns:
```json
{
    "isOk":true,
    "msg":"返回成功",
    "PostAndComments":{
        "post_id":"11",
        "author_name":"Hzzone",
        "author_id":"zhizhonghwang@gmail.com",
        "title":"1",
        "content":"test",
        "time":"七月 22, 2017",
        "comments":[
            {
                "post_id":"11",
                "from_id":"zhizhonghwang@gmail.com",
                "content":"测试测试撒",
                "date":"七月 27, 2017",
                "comment_id":"akjfdg",
                "from_name":"Hzzone",
                "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            },
            {
                "post_id":"11",
                "from_id":"zhizhonghwang@gmail.com",
                "content":"嗯",
                "date":"七月 24, 2017",
                "comment_id":"asdgxa",
                "from_name":"Hzzone",
                "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            },
            {
                "post_id":"11",
                "from_id":"zhizhonghwang@gmail.com",
                "to_id":"zhizhonghwang@gmail.com",
                "content":"嘿嘿",
                "date":"七月 14, 2017",
                "comment_id":"afdhnbgtsd",
                "to_comment_id":"asdgxa",
                "from_name":"Hzzone",
                "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
                "to_name":"Hzzone",
                "to_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            },
            {
                "post_id":"11",
                "from_id":"1141408077@qq.com",
                "to_id":"zhizhonghwang@gmail.com",
                "content":"嘻嘻",
                "date":"七月 27, 2017",
                "comment_id":"asdfasdwe",
                "to_comment_id":"akjfdg",
                "from_name":"Zhizhong",
                "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
                "to_name":"Hzzone",
                "to_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            }
        ],
        "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
        "isLiked":false,
        "liked":1
    }
}
```

#### User liked 
post data to ```"192.168.21.100:4567/like"```
```json
{
  "post_id": "11",
  "user_id": "zhizhonghwang@gmail.com",
  "liked": "-+1"
}
```
returns
```json
{
    "isOk": true,
    "msg": "取消点赞"
}
```
else
```json
{
    "isOk": true,
    "msg": "点赞"
}
```


#### delete post
post data to ```"192.168.21.100:4567/delete"```
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "password": "1111",
  "post_id": "55",
  "comment_id": "1111"
}
```
or
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "password": "1111",
  "comment_id": "1111"
}
```
if password is not correct, user not exists or post not exists, returns:
```json
{
    "isOk":false,
    "msg":"密码错误或帖子不存在"
}
```
else:
```json
{
    "isOk":true,
    "msg":"删除成功"
}
```
It will delete post and all comments and likes

#### Comment
post data to ```/comment```
if to comment:
```json
{
  "from_id": "zhizhonghwang@gmail.com",
  "post_id": "11",
  "to_id": "1141408077@qq.com",
  "to_comment_id": "asdfasdwe",
  "content": "xixixixixixi"
}
```
if to post rather than to comment
```json
{
  "from_id": "zhizhonghwang@gmail.com",
  "post_id": "11",
  "content": "xixixixixixi"    
}
```
it will return:
```json
{
    "isOk":true,
    "msg":"评论成功",
    "comment":{
        "post_id":"http://www.cnbeta.com/articles/tech/634635.htm",
        "from_id":"zhizhonghwang@gmail.com",
        "to_id":"731275785@qq.com",
        "content":"测试一下一级评论",
        "date":"Mon Jul 24 20:08:37 CST 2017",
        "comment_id":"jjokdodjoyeywgsjefnawllliiqgqj",
        "to_comment_id":"vquxghwgzqrdhmdyemgxwakqqdarue",
        "to_name":"Aliwee",
        "to_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
    }
}
```
#### at me
```/atme```
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "password": "1111"
}
```
return
```json
{
    "isOk":true,
    "msg":"成功",
    "atme":[
        {
            "post_id":"http://www.cnbeta.com/articles/tech/634635.htm",
            "from_id":"731275785@qq.com",
            "content":"车速度传输哈哈",
            "date":"Mon Jul 24 20:45:24 CST 2017",
            "comment_id":"wzjyqhxedqmpalslzhfrrqzvgqhxaj",
            "from_name":"Aliwee",
            "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
        },
        {
            "post_id":"http://www.cnbeta.com/articles/tech/634635.htm",
            "from_id":"731275785@qq.com",
            "content":"4567",
            "date":"Mon Jul 24 20:19:09 CST 2017",
            "comment_id":"aaradeiduuddseulpwftlwlfyselun",
            "from_name":"Aliwee",
            "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
        },
        {
            "post_id":"http://www.cnbeta.com/articles/tech/634635.htm",
            "from_id":"731275785@qq.com",
            "content":"2",
            "date":"Mon Jul 24 20:18:13 CST 2017",
            "comment_id":"szaklkdzyifmddyxujadfylytsyvij",
            "from_name":"Aliwee",
            "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
        },
        {
            "post_id":"http://www.cnbeta.com/articles/tech/634635.htm",
            "from_id":"zhizhonghwang@gmail.com",
            "content":"测试一下一级评论",
            "date":"Mon Jul 24 20:14:34 CST 2017",
            "comment_id":"waaqaljidguzagwkywlvllbsyeeijh",
            "from_name":"Hzzone",
            "from_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
        }
    ]
}
```
#### Change name
```/change-name```
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "password": "1111",
  "new_user_name": "Hzzone"
}
```
return
```json
{
    "isOk":true,
    "msg":"修改成功",
    "user":{
        "user_id":"zhizhonghwang@gmail.com",
        "user_name":"Hzzone",
        "password":"1111",
        "token":"1111",
        "pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
    }
}
```

#### change Passsword
```/change-password```
```json
{
  "user_id": "zhizhonghwang@gmail.com",
  "new_password": "Hzzone"
}
```
````json
{
    "isOk":true,
    "msg":"修改成功",
    "user":{
        "user_id":"zhizhonghwang@gmail.com",
        "user_name":"Hzzone",
        "password":"1111",
        "token":"1111",
        "pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
    }
}
````
#### get code
```/verify```
```json
{
  "user_id": "zhizhonghwang@gmail.com"
}
```
returns:
```json
{
  "isOk": true, 
  "msg": "644966"
}
```
#### My likes
```my-like```
```json
{
  "user_id": "zhizhonghwang@gmail.com"
}
```
return:
```json
{
    "isOk":true,
    "msg":"成功",
    "posts":[
        {
            "post_id":"http://www.cnbeta.com/articles/science/634639.htm",
            "title":"中国自治式水下机器人在南海开展首次试验性应用",
            "content":"<p><strong>我国自主研发的自治式水下机器人“探索”号，24日在南海北部开展首次试验性应用，预计水下作业时间20小时。</strong>24日上午8时，在“科学”号远洋综合科考船的后甲板上，科考队员做好了“探索”号下潜的所有准备工作。“探索”号像一条大黄鱼一样静静停在甲板上，长约3.5米，宽和高约1.5米，四个红色鱼鳍状的螺旋桨装置分别位于它的“鳃”部和靠近尾部的位置。</p> <p style="text-align: center;"><img src="http://static.cnbetacdn.com/article/2017/0724/d15b6062061b9b7.jpg"><br></p> <p>船舶抵达指定位置后，科考队员拉紧止荡绳，甲板上用于起吊大型装备的A架缓缓将“探索”号吊起，A架向外摆出船舷，并将“探索”号缓缓放入水中，科考队员抽掉缆绳和止荡绳，机器人开始自主下潜。</p> <p>中国科学院沈阳自动化研究所副研究员赵宏宇介绍，“探索”号进入水面后就和母船之间没有缆绳连接了，在水下按照预设程序自主工作。在首次试验性应用中，“探索”号将在水下工作20小时，前10小时对地形进行声学扫描，范围大概是4000米×2000米，后10个小时进行光学拍照，航行速度稍微慢一点，范围大概是600米×300米。</p> <p>据了解，此次“探索”号对南海一冷泉区进行较大范围调查后，科考队员将确定有精细研究价值的点，再用“发现”号遥控无人潜水器开展精细调查和作业。自治式水下机器人和遥控无人潜水器的区别是：自治式水下机器人无缆，调查范围较大；遥控无人潜水器则和母船之间有缆绳相连，可搭载作业设备较多，擅长开展精细调查和作业。</p> <p>“科学”号正在南海执行中国科学院战略性先导专项“热带西太平洋关键区域海洋系统物质能量交换”2017年南海综合考察航次，“探索”号是此专项下，由中国科学院沈阳自动化研究所自主研制的水下自治式机器人，最大作业深度可达4500米。</p> <p>（原标题：“探索”号自治式水下机器人在南海开展首次试验性应用）</p> <p>新华社“科学”号7月24日电（记者 张旭东）</p>",
            "time":"Mon Jul 24 18:51:29 CST 2017",
            "source":"Newest Tech",
            "category":"science",
            "isLiked":false,
            "liked":1
        },
        {
            "post_id":"mwryyqklsflheqsfbspyqjajlmkwsu",
            "title":"思考使我快乐",
            "content":"今天天气真好",
            "time":"Mon Jul 24 21:37:58 CST 2017",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":1
        }
    ]
}
```

#### Change Pic
base64 String to ```/change-pic```
```json
{
  "user_id": "zhizhonghwang@gmail.com", 
  "pic": "....."
}
```
returns user data

#### My post
```json
{
  "user_id": "zhizhonghwang@gmail.com"
}
```

```json
{
    "isOk":true,
    "msg":"获取成功",
    "posts":[
        {
            "post_id":"ymlsxmydesgypavceaxgcuwxylqeei",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"李晶怡哈哈哈哈哈哈",
            "content":"<p><img src="http://omoitwcai.bkt.clouddn.com/2017-07-18-IMG_0785-1.PNG" alt="" /> <strong><em>hahahahaha</em></strong></p> <h4>test</h4> ",
            "time":"Tue Jul 25 14:51:31 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"nxjykbiaysddjjgdaghdivszgoaalm",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"xixixixix",
            "content":"<p>content</p> ",
            "time":"Tue Jul 25 14:50:28 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"zyryoadzwsygqypaluzwaulzdwihaj",
            "author_name":"铜角大王",
            "author_id":"627055459@qq.com",
            "title":"666",
            "content":"测试",
            "time":"Tue Jul 25 10:06:16 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"qdwjgwbetjddexjyjligaadibseygs",
            "author_name":"铜角大王",
            "author_id":"627055459@qq.com",
            "title":"666",
            "content":"测试",
            "time":"Tue Jul 25 10:06:16 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"gdzddmwdhluzsdquzgndqmqycaynia",
            "author_name":"铜角大王",
            "author_id":"627055459@qq.com",
            "title":"666",
            "content":"测试",
            "time":"Tue Jul 25 10:06:16 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"uuguwzapzyylihvjzleyjdyjbluehj",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"哈哈哈哈哈",
            "content":"哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈",
            "time":"Mon Jul 24 22:48:28 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"mwryyqklsflheqsfbspyqjajlmkwsu",
            "author_name":"Aliwee",
            "author_id":"731275785@qq.com",
            "title":"思考使我快乐",
            "content":"今天天气真好",
            "time":"Mon Jul 24 21:37:58 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/FhL_2-WuvNqSjdp7thXknwfZC3hT",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":true,
            "liked":1
        },
        {
            "post_id":"usayjxdnspodfdputdqduiyueqjsij",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"测试发表",
            "content":"测试一下一级评论",
            "time":"Mon Jul 24 21:36:55 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"jeygzggllhvndxwmdgdslovoyudsyz",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"测试发表",
            "content":"测试一下一级评论",
            "time":"Mon Jul 24 21:34:49 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"asdfvcsdae",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"4",
            "content":"日期类型用java.sql.Date，时间类型用java.sql.Time，日期/时间类型用java.sql.Timestamp； 这里举个例子：假设要从oracle中获取系统时间，需要执行sql：select sysdate from dual，然后在通过ResultSet获取查询结果时，一定要调用方法：getTimestamp()，这样才可以把年月日时分秒都取出来，调用getDate()只能取出年月日，调用getTime()只能取出时分秒。 ",
            "time":"Mon Jul 24 18:54:49 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"fadbgxvdvsdf",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"3",
            "content":"日期类型用java.sql.Date，时间类型用java.sql.Time，日期/时间类型用java.sql.Timestamp； 这里举个例子：假设要从oracle中获取系统时间，需要执行sql：select sysdate from dual，然后在通过ResultSet获取查询结果时，一定要调用方法：getTimestamp()，这样才可以把年月日时分秒都取出来，调用getDate()只能取出年月日，调用getTime()只能取出时分秒。 ",
            "time":"Mon Jul 24 18:53:49 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"asdasdas",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"2",
            "content":"日期类型用java.sql.Date，时间类型用java.sql.Time，日期/时间类型用java.sql.Timestamp； 这里举个例子：假设要从oracle中获取系统时间，需要执行sql：select sysdate from dual，然后在通过ResultSet获取查询结果时，一定要调用方法：getTimestamp()，这样才可以把年月日时分秒都取出来，调用getDate()只能取出年月日，调用getTime()只能取出时分秒。 ",
            "time":"Mon Jul 24 18:52:59 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        },
        {
            "post_id":"kasjdhkajshdxsa",
            "author_name":"Hzzone",
            "author_id":"zhizhonghwang@gmail.com",
            "title":"1",
            "content":"日期类型用java.sql.Date，时间类型用java.sql.Time，日期/时间类型用java.sql.Timestamp； 这里举个例子：假设要从oracle中获取系统时间，需要执行sql：select sysdate from dual，然后在通过ResultSet获取查询结果时，一定要调用方法：getTimestamp()，这样才可以把年月日时分秒都取出来，调用getDate()只能取出年月日，调用getTime()只能取出时分秒。 ",
            "time":"Mon Jul 24 18:52:49 CST 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/Fi7WT3WYkOrNwK9KhrsWJE4NNbtb",
            "source":"Newest Tech",
            "category":"新鲜事",
            "isLiked":false,
            "liked":0
        }
    ]
}
```

#### Search
```/search```
```json
{
  "keyword": "日本人"
}
```
```json
{
    "isOk":true,
    "msg":"获取成功",
    "posts":[
        {
            "post_id":"http://www.cnbeta.com/articles/tech/634777.htm",
            "author_id":"Newest Tech@gmail.com",
            "title":"[多图]扒一扒日本那些让人不得不服的“黑科技”",
            "content":"<p>对于日本这个国家，我们多数人的情绪恐怕只有一种，但在很多方面，我们又不得不承认它走在了世界的前列。毕竟这是一个开放的世界，毕竟我们不再闭门造车，今天笔者就给大家扒一扒日本那些让人不得不服的“黑科技”。</p> <p><strong>日本机器人都有啥绝活？</strong></p> <p><a href="http://img1.mydrivers.com/img/20170725/7a32b38504b64f1fb889ee0c55386f7a.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/9dd221a5100ee7d.jpg"></a></p> <p>东京的一家机器人餐厅最大的特色有美女机器人比武、斗舞，顾客可以坐上去与对手一决高下，体验变形金刚的刺激。</p> <p><a href="http://img1.mydrivers.com/img/20170725/fc5e8eee83154e2e876265f216a87a3b.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/4481f9e7641000a.jpg"></a></p> <p>日本一家五星级酒店“聘用”机器人做前台接待，能够帮你办理入住、解答你的问题。照这趋势，以后很多服务人员要“下架”了。</p> <p><a href="http://img1.mydrivers.com/img/20170725/0f79b6faa09a443bb6241b83acbf42f2.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/ec360bf68adefea.jpg"></a></p> <p>日本一家寿司店使用机器人服务员，它能为顾客安排座位、把顾客护送到座位上，顾客可通过它胸前的触摸屏点餐，省时省力。</p> <p><a href="http://img1.mydrivers.com/img/20170725/f8547c0b548c4bd393ccb66443ed938a.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/1e7393be57c1b5d.jpg"></a></p> <p>Kirobo Mini是日本汽车制造商丰田推出的一款机器人，它不但能够做路途向导，且能够安慰和宽解那些没有孩子的女性。<strong>Kirobo Mini具有人脸表情识别功能，能够根据使用者的表情判断情绪，进行适合场景的聊天内容，当面对复杂的对话内容时，可以通过云端处理，减少对话停滞的时间</strong>，其背后的技术系统，包括丰田语音识别<a data-link="1" href="http://click.aliyun.com/m/1423/" target="_blank">服务器</a>和通信服务器被称为“丰田中心系统”。</p> <p><strong>日本人的家里会用啥黑科技？ </strong></p> <p><a href="http://img1.mydrivers.com/img/20170725/d7b8d687249049dd966f874fae490c7c.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/731081301992efb.jpg"></a></p> <p>松下全透明电视，无边框设计，关机状态下和一款普通玻璃没啥区别，采用OLED技术，因此无需背光来进行照明。开机后画面清晰，感觉是在变魔术。</p> <p><a href="http://img1.mydrivers.com/img/20170725/54571f70deb94d199276f0b547e2a464.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/c63d041f8ed75cb.jpg"></a></p> <p>日本是出了名的干净，他们的厕所也是首屈一指，除了使用智能马桶盖进行冲洗和烘干，还能测血压、分析尿液，甚至测量体重、体脂等身体参数。</p> <p><a href="http://img1.mydrivers.com/img/20170725/9b94a74207674a7b9123ffc8c017bf4a.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/c447b7ea50ad172.jpg"></a></p> <p>乍一看和普通镜子没啥区别，但人家这是智能镜子，能够检测皱纹、红肿、黑头、晒伤等肌肤问题，并个性推荐相关改善皮肤的产品。上图松下智能镜子可以虚拟化妆，如果你对镜中自己的妆容满意，还可以3D打印出来敷在脸上。</p> <p><a href="http://img1.mydrivers.com/img/20170725/774be5847b2a4eb7a8df70450a0b7b3e.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/dab20fe91979eb4.jpg"></a></p> <p>松下研发的可弯折锂离子电池，厚度仅0.55mm，试验中弯折一千次之后性能仍几乎不变，能够嵌入卡片及衣物中，未来的可弯折手机或许能用这种电池。</p> <p><a href="http://img1.mydrivers.com/img/20170725/f8794d917e0e4330b02203eb9107ab1a.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/480afaaeafdb88e.jpg"></a></p> <p>为了防止患有老年痴呆的人群走丢，日本某城市为这些老年人的手指和脚趾甲盖上粘贴了防走丢二维码，每个二维码对应此人的个人信息，如果走丢方便家人找回。</p> <p><a href="http://img1.mydrivers.com/img/20170725/a2d16bbb666f47cfbea83158dc003fb9.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/cfa70b1b7e3d2ef.jpg"></a></p> <p>经常听到医生做完十几个小时的手术瘫倒在地的新闻，真的是累虚脱了。日本人设计了一款能穿在身上的椅子。这款名叫Archelis的穿戴椅专为长期需要站立在手术台前的医护人员设计。Archelis整体由支架和绑带组成，穿上它，在需要休息的时候大胆坐下即可，缓解长时间手术过程中的肌肉劳损。</p> <p><strong>看了半天，还是日本人最会玩</strong></p> <p><a href="http://img1.mydrivers.com/img/20170725/c5d9a5a82a774bd0b02ec3ceff11fcfb.jpg" target="_blank"><img src="http://static.cnbetacdn.com/article/2017/0725/1358b390e017aeb.jpg"></a></p> <p>流星雨百年难遇，如果可以人造呢？日本一家名叫ALE的公司准备向太空发射一颗卫星，它能发射出蓝莓大小的金属粒，当这些金属粒以时速两万八千公里的高速划过天际时，就会燃烧成炫目碎片，产生壮观光迹，在半径两百公里范围内皆能观赏到星空秀。星空首秀预计在2018年上演。</p> <p><a href="http://img1.mydrivers.com/img/20170725/c601a751404845dc871aef9990a73591.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_c601a751404845dc871aef9990a73591.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>东京杜莎夫人蜡像馆除了有名人蜡像，还可以看到玛丽莲·梦露、碧昂斯这样的大牌明星现场舞蹈，用的就是全息影像技术。</p> <p><a href="http://img1.mydrivers.com/img/20170725/4e14154879084b4cba203ffe422b3626.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_4e14154879084b4cba203ffe422b3626.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>日本这几年智能穿戴也很火爆，这款炫酷的鞋子鞋底有100颗LED灯和运动传感器，灯光可随你的步伐发生变化，非常适合舞者营造视觉效果。</p> <p><a href="http://img1.mydrivers.com/img/20170725/58fabf74905e4d52ad3be31265815748.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_58fabf74905e4d52ad3be31265815748.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>灯光秀很多地方都会举办，但如此规模、如此华丽的估计只能在日本看到了。</p> <p>日本动漫和二次元文化影响全球，在日本街头，经常能看到那些衣着另类的“未来人”，没错，她们来自22世纪。</p> <p><a href="http://img1.mydrivers.com/img/20170725/3e8a78995a6f4b5a815b49ee571fe662.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_3e8a78995a6f4b5a815b49ee571fe662.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>日本人使用的交通工具有啥特色？</p> <p><a href="http://img1.mydrivers.com/img/20170725/3e0275bc6f30465f95b449f6c77c6108.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_3e0275bc6f30465f95b449f6c77c6108.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>日本的这列磁悬浮列车被称为最快列车。2015年在测试轨道上车速达到603km/h，刷新世界纪录。中国的高铁要加油啦！</p> <p><a href="http://img1.mydrivers.com/img/20170725/3221ec5f2a884153b56f378f175f5f4d.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_3221ec5f2a884153b56f378f175f5f4d.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>目前，世界上只有3家出售以氢能源作为驱动的汽车制造商，它们都来自日本。上图是本田2016年发售的一款名叫Clarity的氢燃料汽车。Clarity仅依靠氢能源驱动，其最终排放产物只有对环境无害的水。</p> <p><a href="http://img1.mydrivers.com/img/20170725/05c709de5503421d83aae699296f76a5.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_05c709de5503421d83aae699296f76a5.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>丰田这辆Concept-i概念车亮相今年CES，造型未来感十足，搭载先进的人工智能系统，具备自主学习能力。该车支持自动和手动两种驾驶模式，通过大量先进的自动化技术，可以实时监控两种模式下车轮后方所发生情况，保护驾驶员的行车安全。</p> <p><a href="http://img1.mydrivers.com/img/20170725/73e1113c6a144b1cb54540a5a2410601.jpg" target="_blank"><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/s_73e1113c6a144b1cb54540a5a2410601.jpg" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></a></p> <p>这个小巧的3轮汽车能坐2人，除了环保，还能穿梭于各种路况，主要是停车方便呀！相比咱们的三轮蹦蹦，这个明显逼格高了。</p> <p><img alt="都说日本科技领先世界几十年 看完这篇我信了" src="http://img1.mydrivers.com/img/20170725/85f856fdc5f140abbda22d256460e572.gif" style="border-top: black 1px solid; border-right: black 1px solid; border-bottom: black 1px solid; border-left: black 1px solid"></p> <p><strong>现如今，中国的人行道上基本都被共享单车占据了，给行人和车辆造成诸多不便，可以学习东京的这种地下自行车库，全部自动化，确实很强大。</strong></p> <p>写在最后：科技日新月异，未来很有可能变成机器人时代、人工智能时代，日本或许在某些方面走在了世界前列，但正在崛起的中国也不容小觑，谁又知道，再过20年世界会变成什么样呢？</p>",
            "time":"Tue Jul 25 07:16:37 CST 2017",
            "source":"Newest Tech",
            "category":"tech",
            "isLiked":false,
            "liked":0
        }
    ]
}
```

