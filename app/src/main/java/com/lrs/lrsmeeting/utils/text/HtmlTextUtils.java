package com.lrs.lrsmeeting.utils.text;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 作用:
 * @date: 2020/5/25
 * @author: 卢融霜
 */
public class HtmlTextUtils {
    /**
     * 定义script的正则表达式
     */
    private final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private final String regEx_html = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private final String regEx_space = "\\s*|\t|\r";
    private final String regEx_space_n = "\n";

    public static HtmlTextUtils textUtils;

    public static HtmlTextUtils getInstance() {
        if (textUtils == null) {
            textUtils = new HtmlTextUtils();
        }
        return textUtils;
    }

    /**
     * @param htmlStr
     * @return 删除Html标签
     */
    public String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        // 过滤script标签
        htmlStr = m_script.replaceAll("");

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        // 过滤style标签
        htmlStr = m_style.replaceAll("");

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        // 过滤html标签
        htmlStr = m_html.replaceAll("");


//        Pattern p_space1 = Pattern.compile(regEx_space_n, Pattern.CASE_INSENSITIVE);
//        Matcher m_space1 = p_space1.matcher(htmlStr);
//        // 过滤空格回车标签
//        htmlStr = m_space1.replaceAll("\n");
//
//        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
//        Matcher m_space = p_space.matcher(htmlStr);
//        // 过滤空格回车标签
//        htmlStr = m_space.replaceAll(" ");
        // 返回文本字符串
        return htmlStr.trim();
    }

    public String getTextFromHtml(String htmlStr) {
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll(" ", "");
        return htmlStr;
    }


    /**
     * html 编码
     *
     * @param source
     * @return
     */
    public String htmlEncode(String source) {
        if (source == null) {
            return "";
        }
        String html = "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            switch (c) {
                case '<':
                    buffer.append("&lt;");
                    break;
                case '>':
                    buffer.append("&gt;");
                    break;
                case '&':
                    buffer.append("&amp;");
                    break;
                case '"':
                    buffer.append("&quot;");
                    break;
                case ' ':
                    buffer.append("&nbsp;");
                    break;

                default:
                    buffer.append(c);
            }
        }
        html = buffer.toString();
        return html;
    }


    /**
     * html 解码
     *
     * @param source
     * @return
     */
    public String htmlDecode(String source) {
        if (TextUtils.isEmpty(source)) {
            return "";
        }
        source = source.replace("&lt;", "<");
        source = source.replace("&gt;", ">");
        source = source.replace("&amp;", "&");
        source = source.replace("&quot;", "\"");
        source = source.replace("&nbsp;", " ");
        source = source.replace("&ldquo;", "\"");
        source = source.replace("&rdquo;", "\"");

        return getTextFromHtml(source);
    }

}
