# 思路
1、关闭Activity的默认动画，在style里设置，并在代码中添加overridePendingTransition(0, 0);  
2、将windowBackground设置为透明，同样在style中设置  
3、获取被点击view的在以屏幕为坐标系下的坐标，并将之传递给下一个页面B  
4、在B页面一打开时将与A页面中的同类型的View进行位移及缩放达到与在页面A中的大小及位置  
5、执行属性动画，将B页面中的view进行位移及缩放和透明度的变化  
6、在关闭B页面时，再逆向执行动画  
