package com.test.test;

import android.os.Bundle;
import android.os.Handler;

import com.test.test.lib.Tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import androidx.appcompat.app.AppCompatActivity;

public class NetworkActivity extends AppCompatActivity
{

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        this.run();
    }

    public void run()
    {
        (new Thread(new Runnable() {
            @Override
            public void run()
            {
                // 网络请求要求在必须在子线程中运行
                // 现在又出现端口无法访问的情况
                try {
                    // 发起网络请求
//                    URL url = new URL("http://127.0.0.1/test.php");
                    URL url = new URL("http://test.com/test.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    /**
                     * 合法的请求方法
                     *
                     * 1. GET
                     * 2. POST
                     * 3. HEAD
                     * 4. OPTIONS
                     * 5. PUT
                     * 6. DELETE
                     * 7. TRACE
                     */
                    conn.setRequestMethod("GET");
                    // 连接的最大超时时间，单位 ms
                    conn.setConnectTimeout(60 * 1000);
                    // 读取服务端响应的最大时间 单位 ms
                    conn.setReadTimeout(60 * 1000);
                    /**
                     * 1. 字节流
                     *      InputStream()
                     *      FileInputStream(File)
                     *      BufferedInputStream(InputStream)
                     * 2. 字符流
                     *      Reader()
                     *      InputStreamReader(InputStream)
                     *      FileReader(File)
                     *      BufferedReader(Reader)
                     * 3. 文件流
                     *
                     */
                    InputStream is = conn.getInputStream();
                    InputStreamReader reader = new InputStreamReader(is);
                    BufferedReader bufReader = new BufferedReader(reader);
                    String res = "";
                    String line = "";
                    while ((bufReader.readLine()) != null)
                    {
                        res += line;
                    }
                    // 输出请求结果
                    Tool.log(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        })).start();
    }
}
