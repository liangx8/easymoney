#Kotlin 学习笔记
* 以有类的成员函数扩展：
  类可以在定义后，被额外再定义函数，有点类似Go的函数定义
* 属性的 getter/setter
  1. getter
  2. setter 用关键字field代表当前的属性
* for 的变种使用

  ``` kotlin
    fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(
        destination: C, 
        transform: (T) -> R
    ): C
  ```
  Applies the given transform function to each element of the original collection and appends the results to the given destination.