package com.example.downloadpicturedemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DownloadPictureController
 *
 * @author QinYixuan
 * @date 2019/1/14
 */
@Controller
public class DownloadPictureController {

    private static Logger logger = LoggerFactory.getLogger(DownloadPictureController.class);

    private static final String IMGURL_REG = "<img.*?src=\".*?\".*?>";

    private static final String IMGSRC_REG = "[a-zA-z]+://[^\\s]*";

    private static final String DOWNLOAD_PATH = "E:\\images\\";

    /**
     * index
     *
     * @author QinYixuan
     * @date 2019/1/14
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * downloadPicture
     *
     * @author QinYixuan
     * @date 2019/1/14
     */
    @ResponseBody
    @RequestMapping("downloadPicture")
    public Map<String, Object> downloadPicture(HttpServletRequest httpServletRequest) {
        Map<String, Object> map = new HashMap<>(16);
        String url = httpServletRequest.getParameter("url");
        // 获取html
        String html = getHTML(url);
        // 获取图片
        List<String> list = getPictures(html);
        // 下载图片
        boolean result = download(list);
        if (result) {
            map.put("info", "success");
        } else {
            map.put("info", "failure");
        }
        return map;
    }

    /**
     * 获取html
     *
     * @author QinYixuan
     * @date 2019/1/14
     */
    private static String getHTML(String url) {
        String html = "";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            URL downloadUrl = new URL(url);
            inputStream = downloadUrl.openConnection().getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            String string;
            StringBuffer stringBuffer = new StringBuffer();
            while ((string = bufferedReader.readLine()) != null) {
                stringBuffer.append(string, 0, string.length());
                stringBuffer.append("\n");
            }
            html = stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    /**
     * 获取图片
     *
     * @author QinYixuan
     * @date 2019/1/14
     */
    private static List<String> getPictures(String html) {
        List<String> list = new ArrayList<>(16);
        // img标签
        List<String> imgList = new ArrayList<>(16);
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(html);
        while (matcher.find()) {
            imgList.add(matcher.group());
        }
        // 图片名称及路径
        for (String string : imgList) {
            Matcher srcMatcher = Pattern.compile(IMGSRC_REG).matcher(string);
            String src = "";
            while (srcMatcher.find()) {
                src = srcMatcher.group().substring(0, srcMatcher.group().length() - 1);
                // LALA的图库url处理
                if (src.contains(".md")) {
                    src = src.replace(".md", "");
                }
            }
            if (!StringUtils.isEmpty(src)) {
                list.add(src);
            }
        }
        return list;
    }

    /**
     * download
     *
     * @author QinYixuan
     * @date 2019/1/14
     */
    private boolean download(List<String> list) {
        long beginTime = System.currentTimeMillis();
        try {
            list.forEach(string -> {
                String pictureName = string.substring(string.lastIndexOf("/") + 1);
                InputStream inputStream = null;
                FileOutputStream fileOutputStream = null;
                try {
                    URL url = new URL(string);
                    inputStream = url.openConnection().getInputStream();
                    fileOutputStream = new FileOutputStream(new File(DOWNLOAD_PATH + pictureName));
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    while ((length = inputStream.read(bytes, 0, bytes.length)) != -1) {
                        fileOutputStream.write(bytes, 0, length);
                    }
                    logger.info("正在下载:" + pictureName);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        long endTime = System.currentTimeMillis();
        logger.info("下载完成!总耗时：" + (endTime - beginTime) / 1000 + "秒");
        return true;
    }
}
