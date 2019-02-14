package com.example.administrator.lx_butterknife;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.lx_butterknife.bean.UserBean;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


@UserBean(name = "Hello world") //自定义注解
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_circle_x)
    Button btnCircleX;
    @BindView(R.id.btn_circle_j)
    Button btnCircleJ;
    @BindView(R.id.btn_widthandheight)
    Button btnWidthandheight;
    @BindView(R.id.btn_gifimg)
    Button btnGifimg;
    @BindView(R.id.simple_img)
    SimpleDraweeView simpleImg;
    @BindView(R.id.btn_zhu)
    Button btnZhu;
    private Unbinder butterknife;
    private Uri uri;
    private GenericDraweeHierarchyBuilder builder;
    private RoundingParams parames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //图片路径方法
        initImageUrl();
        //使用ButterKnife绑定
        butterknife = ButterKnife.bind(this);

    }



    //圆型图
    @OnClick(R.id.btn_circle_x)
    public void onBtnCircleXClicked() {
        // 设置圆形图片
        // 设置形状对象,形状为圆形
        RoundingParams parames = RoundingParams.asCircle();
        //创建设置参数,设置一个形状,把形状对象塞入
        GenericDraweeHierarchy roundness = builder.setRoundingParams(parames).build();
        //将参数对象设置给图片控件
        simpleImg.setHierarchy(roundness);
        //控件加载图片
        simpleImg.setImageURI(uri);
    }

    //圆角
    @OnClick(R.id.btn_circle_j)
    public void onBtnCircleJClicked() {
        // 设置圆角图片
        //设置边角的弧度,使其为圆角
        parames = RoundingParams.fromCornersRadius(50f);
        //这里的代码和设置圆形图片这一块代码是一种的,唯一不同就是对parames的设置.
        GenericDraweeHierarchy circularBead = builder.setRoundingParams(parames).build();
        simpleImg.setHierarchy(circularBead);
        // 加载图片
        simpleImg.setImageURI(uri);
    }

    //宽高比
    @OnClick(R.id.btn_widthandheight)
    public void onBtnWidthandheightClicked() {
        simpleImg.setImageURI(uri);
    }

    //动图
    @OnClick(R.id.btn_gifimg)
    public void onBtnGifimgClicked() {
        //请求GIF动画,采用MVC的设计模式(注意加载GIF动画还要添加依赖)

        //GIF动画网址,加载需要一段时间
        Uri uri = Uri.parse("http://www.zhaoapi.cn/images/girl.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)//设置GIF网址
                .setAutoPlayAnimations(false)//是否自动播放动画,false为不播放
                .setOldController(simpleImg.getController())//内存优化
                .build();
        simpleImg.setController(controller);

        // 动画停止
        //拿到动画对象
        Animatable animatableStop = simpleImg.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStop != null && animatableStop.isRunning()) {
            //动画在播放,停止动画播放
            animatableStop.stop();
        }

        // 动画开始
        //拿到动画对象
        Animatable animatableStart = simpleImg.getController().getAnimatable();
        //进行非空及是否动画在播放判断
        if(animatableStart != null && !animatableStart.isRunning()) {
            //动画停止播放,播放动画
            animatableStart.start();
        }

    }

    //获取注解值
    @OnClick(R.id.btn_zhu)
    public void onBtnZhuClicked() {
        Field[] fields = MainActivity.class.getDeclaredFields();
        for (Field filed:fields ) {
            if(filed.isAnnotationPresent(UserBean.class)){
                UserBean annotation = filed.getAnnotation(UserBean.class);
                Toast.makeText(this,annotation.name(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initImageUrl() {
        //加载图片的网址
        uri = Uri.parse("http://img4q.duitang.com/uploads/item/201304/27/20130427043538_wAfHC.jpeg");
        //builder对象用一个即可,在这里创建出成员变量
        builder = new GenericDraweeHierarchyBuilder(getResources());
    }

    //取消注解方法
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注解
        butterknife.unbind();
    }

}
