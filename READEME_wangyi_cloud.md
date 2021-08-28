
```xml
    <androidx.appcompat.widget.Toolbar
            android:id="@+id/tool_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/purple_200"
            app:title="今日资讯"
            app:subtitle="河南洪灾最新报道"
            app:navigationIcon="@drawable/icon_back"/>
```

添加样式：

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- 也可以用<item name="windowActionBar">false</item> -->
    <style name="Theme.AndUITalk" parent="Theme.MaterialComponents.Light.NoActionBar">
        <item name="android:textColorPrimary">@android:color/white</item>
        <item name="android:textColorSecondary">@android:color/white</item>
        ...
    <style/>
    <style name="Theme.Toolbar" parent="ThemeOverlay.AppCompat.ActionBar"/>

    <style name="Theme.Toolbar.Title">
        <item name="android:textSize">14sp</item>
        <item name="android:textColor">@color/colorWhite</item>
    </style>

    <style name="Theme.Toolbar.SubTitle">
        <item name="android:textSize">12sp</item>
        <item name="android:textColor">@color/colorWhite</item>
    </style>
</resources>
```


设置样式：
```xml
   <androidx.appcompat.widget.Toolbar
            android:id="@+id/tool_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/purple_200"
            app:title="今日资讯"
            app:subtitle="河南洪灾最新报道"
            app:navigationIcon="@drawable/icon_back"
            app:titleTextAppearance="@style/Theme.Toolbar.Title"
            app:subtitleTextAppearance="@style/Theme.Toolbar.SubTitle"/>
```
添加菜单：menu menu.xml
```kotlin
  override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu ,menu)
        return super.onCreatePanelMenu(featureId, menu)
    }
```

menu覆盖了toolbar，设置样式并设置给Toolbar
```xml
   <style name="OverflowMenuStyle" parent="Widget.AppCompat.Light.PopupMenu.Overflow">
        <item name="overlapAnchor">false</item>  
        <!--把该属性改为false即可使menu位置位于toolbar之下-->
    </style>
```


```xml
 <androidx.appcompat.widget.Toolbar>
    <!--...-->
    app:popupTheme="@style/OverflowMenuStyle"
</androidx.appcompat.widget.Toolbar>
```


设置菜单入口按钮的颜色
  ```kotlin
     private fun initView() {
        toolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolBar.overflowIcon?.setTint(getColor(R.color.white))
        }else{
            toolBar.overflowIcon = ContextCompat.getDrawable( this,R.drawable.more);
        }
    }
  ```

沉浸式

如何开启沉浸式
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- 开启沉浸式状态栏 -->
        <item name="android:windowTranslucentStatus">true</item>
        <item name="android:windowTranslucentNavigation">true</item>
</style>

加上下面，又不透明了，是因为灰色是decorview的颜色,添加如下效果后，该属性设置为true时，表示view会根据系统栏自动设置Padding值来适配，即为屏幕自动加入padding
```
  android:fitsSystemWindows="true"
  android:minHeight="?attr/actionBarSize"
```
```kotlin
   val root = findViewById<ViewGroup>(android.R.id.content)
   root.getChildAt(O)?.fitsSystemWindows = true
```
如果达不到效果，改为给Toolbar父布局加入 `android:fitsSystemWindows="true"`

单独设置状态栏颜色








  反射

