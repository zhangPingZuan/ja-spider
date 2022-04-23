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



#### 碰到的问题记录
- 泛型方法什么时候用<?>，什么时候用具体的泛型类。
```
看这个方法能不能在运行阶段能不能够处理多个泛型类。
void function(泛型类<?> parm);
这个方法就能同时处理泛型类<A>,泛型类<B>。

void function(泛型类<A> parm);
这个方法就只能处理泛型类<A>。
```