# Classifier
    使用Spark NaiveBayes 实现中文文本分类
    原项目地址：<https://github.com/Decrain/SparkTextClassifier>
    
# 程序大致流程
    1、准备数据
    2、数据进行一些处理（比如处理停用词，空格，标点符号）
    3、文本分词
    4、计算词向量
    5、然后就是训练模型
    6、保存模型
    7、测试
    

#### 0、DataFactory.java
    0、读取 classPath -> data -> NewsData 目录下的数据 （通过文件夹分类） 
    1、把数据分词
    2、分割数据(分为测试数据和训练数据)
    3、写入数据到 classPath -> data 目录下的 data-test.txt 和 data-train.txt 文件中
    4、保存标签数据到 classPath -> data -> models ->  labels.txt 文件中
        
#### 1、NaiveBayesTrain.java
    0、读取 classPath -> data -> data-train.txt 文件
    1、训练模型...
    2、保存模型到 classPath -> data -> models -> category-4 目录
    3、保存tf文件到  classPath -> data -> models -> tf 目录
    4、保存idf文件到  classPath -> data -> models -> idf 目录
        
#### 2、NaiveBayesTest.java
    0、加载模型 
    1、加载tf、idf
    2、读取 classPath -> data -> data-test.txt 文件
    3、对数据进行打分
    
### 目录结构
    -resources
        --data
            ---NewsData             语料目录
            ---data-test.txt        测试数据
            ---data-train.txt       训练数据
            ---models
                ----category-4      模型目录
                ----idf             idf文件
                ----tf              tf文件
                ----labels.txt      标签数据
                ---data-train.txt       训练数据了
                ---data-train.txt       训练数据
                
                
   # 增加内容
   ```
        0、原项目地址：<https://github.com/Decrain/SparkTextClassifier><br/>感谢 Maweiming同学的共享;
        1、增加了语料库，实现了中、日、韩、台等语言的识别，训练及分类等；其中对于繁体，通过简译繁的方法，得到了繁体语料；对于英文语料及韩文语料，未找到免费的且能满足要求的数据，头疼;
        2、增加了字符编码的转换问题等;
        3、基本解决了原项目存在的一些bug;
        4、增加了对mongodb数据库的支持，处理结果得到了保存，避免了数据重复处理等问题；
        5、增加了汉语、繁体等语言的停用词处理，提高了分类结果的准确率；
        6、增加了数据统计结果的输出。
   ```

           
