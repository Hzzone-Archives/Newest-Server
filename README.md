Mostly based on Spark.    
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
post data to ```"192.168.21.100:4567/post"```,
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
get from ```"192.168.21.100:4567/post"```,
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
  "post_id": "ijejlglmslisdaybdgujhcjusdnwoe"
}
```
this will also return the next recent 3 posts, returns as:
```json
{
    "isOk":true,
    "msg":"获取成功",
    "posts:":[
        {
            "post_id":"121286547261451",
            "author_name":"Zhizhong",
            "author_id":"1141408077@qq.com",
            "title":"This is a test",
            "content":"test",
            "time":"七月 19, 2017",
            "author_pic":"http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
            "isLiked": false,
            "liked":0
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
  "post_id": "11"
}
```
returns:
```json
{
    "isOk": true,
    "msg": "返回成功",
    "PostAndComments": {
        "post_id": "11",
        "author_name": "Hzzone",
        "author_id": "zhizhonghwang@gmail.com",
        "title": "This is a test",
        "content": "test",
        "time": "七月 22, 2017",
        "comments": [
            {
                "post_id": "11",
                "from_id": "zhizhonghwang@gmail.com",
                "content": "test test",
                "date": "七月 27, 2017",
                "from_name": "Hzzone",
                "from_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            },
            {
                "post_id": "11",
                "from_id": "zhizhonghwang@gmail.com",
                "content": "mdzz",
                "date": "七月 24, 2017",
                "from_name": "Hzzone",
                "from_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg"
            }
        ],
        "author_pic": "http://omoitwcai.bkt.clouddn.com/2017-07-21-%E9%BB%98%E8%AE%A4.jpg",
        "liked": 0
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


