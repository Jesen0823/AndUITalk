

![get](./images/READEME_customView-1630224675423.png)


![recyclerview](./images/READEME_customView-1630225416679.png)


![recyclerview加载过程](./images/READEME_customView-1630229545491.png)


需要声明哪些成员变量呢
1 List<View> viewList :缓存已经加载到屏幕上的View 这些View不存在回收池中，需要集合表示，
方便后续查找和移除
2 int currentY::记录在Y轴上滑动的距离
3int rowCount::记录在RecyclerView加载的总数据，比如1w条
4int firstRow::记录在屏幕中第一个View在数据内容中的位置，比如目前是第34个元素在屏幕
的一个位置
5Recycler recycler: :持有一个回收池的引用
6 int srollY: RecyclerView中第一个View的左上顶点力离屏幕的距离


### SVG
SVG的使用：
    1、App图标:能SDK23后, APP的图标都是由SVG来表示
    2、自定义控件:不规则的控件，复杂的交互，子控件重叠判断，图表等都可以用SVG来做
    3、复杂动画:如根据用户滑动动态显示动画，路径动画.
SVG基本语法：
    M = moveto(M X,Y) :将画笔移动到指定的坐标位置
    L = lineto(L X,Y) :画直线到指定的坐标位置
    H = horizontal lineto(H X):画水平线到指定的X坐标位置
    V = vertical lineto(VY):画垂直线到指定的Y坐标位置
    C= curveto(C X1,Y1,X2,Y2,ENDX,ENDY):三次贝赛曲线
    S= smooth curveto(S X2,Y2,ENDX,ENDY)
    Q = quadratic Belzier curve(Q X,Y,ENDX,ENDY):二次贝赛曲线
    Z = closepath():关闭路径

[SVG资源下载](https://www.amcharts.com/download/)
