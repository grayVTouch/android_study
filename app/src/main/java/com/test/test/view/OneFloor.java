package com.test.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.test.test.lib.Tool;

public class OneFloor extends LinearLayout
{
    public OneFloor(Context context)
    {
        super(context);
    }

    public OneFloor(Context context , AttributeSet attrs)
    {
        super(context , attrs);
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        // 执行 super.dispatchTouchEvent 则事件向下分发
        // 如果返回 true 表示事件向下分发
//        boolean consume = false;
//        if (this.onInterceptTouchEvent(event)) {
//            // 拦截了事件，那么请触发当前视图上监听的相关事件
//            consume = this.onTouchEvent(event);
//        } else {
//            // 这边如果当前的元素没有做任何拦截操作
//            // 那么，让子视图 触发相关
////            consume =
//        }
        // 你可以决定事件是否继续分发下去
        // 如果决定事件继续分发下去，请 返回 true 的同时
        // 调用 super.dispatchTouchEvent 方法执行分发动作
        // 否则，请返回 false
        // 表示不执行事件分发

        // 安卓事件相关的规则有四个
        // 1. 在未形成销售链之前，零售商无法向总代理请求销售权
        // 2. 形成销售链之后，重复的事件不会再次询问
        // 3. 每个 viewgroup 拥有两次选择权（第一次：事件分发的时候你可以拒绝执行分发 | 第二次：当你的下级都拒绝执行分发的时候，你可以再次考虑是否执行事件）
        // 4. 下级可以制约上级

        // 事件的传播机制 activity -> viewgroup -> view
        // 然后仅 activity / view 没有 onInterceptTouchEvent 事件
        //

//        Tool.log("当前执行的用户动作：" + event.getAction());
//        int action = event.getAction();
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                Tool.log("action_down handler");
//                break;
//            case MotionEvent.ACTION_UP:
//                Tool.log("action_up handler");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Tool.log("action_move handler");
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                Tool.log("action_cacel handler");
//                break;
//            case MotionEvent.ACTION_BUTTON_PRESS:
//                Tool.log("action_button_press handler");
//                break;
//            case MotionEvent.ACTION_BUTTON_RELEASE:
//                Tool.log("action_button_release handler");
//                break;
//            default:
//                Tool.log("未知的 action handler: " + action);
//        }
        // 唯有调用 super.dispatchEvent，事件才会继续往下走
//        super.dispatchTouchEvent(event);
//        return true;

        int action = event.getAction();
        String actionName = Tool.getEventName(action);
//        Tool.log("当前事件分发类名：" + this.getClass().getName() + "; 事件分发，不支持事件继续分发; 当前事件：" + actionName);

        // 当返回 false 的时候，当前事件将不再分发
        // 当返回 true 的时候，当前事件将继续分发

        // 这个方法是所有事件触发的必由之路，无论何种类型的事件都会触发该方法
        // 而这个事件影响着事件的分发
        //
//        return true;
        // 由于其他事件的产生都依赖于 action_down
        // 而这个方法接收所有的事件
        // 如果这边阻止了分发，那么当第一个事件触发的时候 action_down
        // 这边做了终止分发的动作，消费链 生成失败
        // 其他后续依赖于 action_down 的事件都会无效
        // 如果需要依赖于
//        switch (action)
//        {
//            case MotionEvent.ACTION_DOWN:
//                // 我这边调用了 super.
//                super.dispatchTouchEvent(event);
//                Tool.log("当前视图触发了 action_down 事件");
//                break;
//            case MotionEvent.ACTION_UP:
//                super.dispatchTouchEvent(event);
//                Tool.log("当前视图触发了 action_up 事件");
//                break;
//            default:
//                Tool.log("当前事件触发了其他类型事件：" + actionName);
//                return false;
//        }
//        super.dispatchTouchEvent(event);
//        return true;
        // 停止进行分发
        // 当 viewgroup 停止分发的时候
        //
//        super.dispatchTouchEvent(event);
        super.dispatchTouchEvent(event);
        return true;
    }

    // 事件拦截
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        // true  - 拦截事件
        // false - 不拦截事件
        Tool.log("one_floor: 当前拦截的事件： " + Tool.getEventName(event.getAction()) + "； 未拦截");
        // 这边不进行拦截
        return false;
    }

    // 事件消耗
    public boolean onTouchEvent(MotionEvent event)
    {
//        Tool.log(Tool.getEventName(event.getAction()) + "; 当前 viewgroup 是否消费了事件");

        // 这边如果返回 true ，表示当前 视图消费了事件，那么 其子视图上注册的事件便不会触发
        //
        // 如果要将事件消费往下传递
        // 请调用父类的方法
//        super.onTouchEvent(event);
        Tool.log("one_floor: 当前消费的事件：" + Tool.getEventName(event.getAction()) + "； 已消费");
        return true;
    }
}
