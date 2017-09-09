# SparkTextClassifier
    使用Spark NaiveBayes 实现中文文本分类
    
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
