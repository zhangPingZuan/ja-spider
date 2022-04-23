# ja-spider
仿照scrapy做一个java版本的爬虫框架。


## 学习到的知识
- java泛型知识。
- spring监听模式。
```
多播器是怎么精准的将事件推送到相关的listener的，原来是经过筛选的。
筛选出相应的事件类型监听器，然后消除warning。学到了。
```

## todo
- 实现去重处理。从文件或者redis出数据。
- finalProcessor的优化。
- 如何变成可扩展



## 整体架构图
![img](./img/ja-spider-structure.png)



