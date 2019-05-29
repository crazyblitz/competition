###AI比赛宣传网站后台。本项目是自己独立完成。

####技术框架选型:
springboot+mybatis+mysql+druid+swagger+dozer+jasypt+easyexcel
>使用dozer大大减少了POJO之间的转换,swagger对restful api文档进行管理。
>jasypt配置文件敏感信息进行加密。

####难点解决

(1):数据库阅读量字段实时更新解决。
采用缓存的方法，实现的时候使用采用ConcurrentHashMap做缓存。
```
    public static int getInfoReadCount(String infoId, Information information) {
        int readCount = doGetReadCount0(information.getInfoReadCount(), infoId);
        return readCount;
    }


    private static int doGetReadCount0(Integer readCount, String id) {

        int readCountReal;
        try {

            //如果是第一次启动项目,则从数据库拿数据
            if (isFirstStartProject) {
                //如果数据库中有,则先从数据中取(数值型字段,需要有默认值,一般为0),防止空指针异常
                //特别是集合,要确定里面的存在的值是否能为null.
                if (readCount != 0) {
                    ReadCountUtils.put(id, readCount);
                } else {
                    ReadCountUtils.put(id, 1);
                }
                isFirstStartProject = false;

            } else {
                //否则,从缓存中阅读量
                if (ReadCountUtils.containsKey(id)) {
                    //如果缓存有,从缓存中取
                    int readCountCache = ReadCountUtils.get(id);
                    readCountCache = readCountCache + 1;
                    ReadCountUtils.put(id, readCountCache);
                } else {
                    //新添加的,防止抛出NPE异常
                    ReadCountUtils.put(id, 1);
                }
            }

            //获取最新的阅读量
            readCountReal = ReadCountUtils.get(id);

        } catch (Exception e) {
            //发生异常则从数据库读取15秒以前的阅读量
            readCountReal = readCount;
            log.warn("更新阅读量失败: {}", e);
        }
        return readCountReal;
    }
```
(2):密码的加密和解密(md5加盐)
参见AdminController的login和register方法。

相关加密类:

- com.ley.innovation.contest.utils.ByteHexUtils
- com.ley.innovation.contest.utils.SecurityUtils

####学到的东西

(1):Spring定时任务的配置
(2):dozer和jasypt的使用
(3):全局异常的配置
(4):druid配置
(5):如何将本地的包打入到classpath下。