package com.weiyi.reader.common;

public class Constant {
	// 分页常量
	public final static int FIRST_ROW_NUM = 5;// 第一次显示条数/页
	public final static int LOAD_ROW_TIME = 5;// 每次加载几条记录
	// Handler消息类型常数
	public final static int LOAD_MORE_FINISH = 3;// 加载完成标志,handler消息
	public final static int LOAD_BLOG_AFTER_CLICK = 4;// 切换到博客列表界面。提示开始加载
	// 解析Html
	public final static String ITBLOG_TITLE_CLASS = "link_title";// ＩＴＢＬＯＧ标题class属性
	public final static String ITBlOG_DATE_CLASS = "link_postdate";// ITBLOG日期
																	// 的class属性
	public final static String ITBlOG_CONTENT_ID = "article_content";// ITBLOG内容的ID属性
	public final static String HREF_SELECT = "a[href]";// 超链接选择器
	public final static String ITBLOG_HREF_FILTER = "/weiyirong/article";// 超链接过滤
	// URL
	public final static String ITBLOG_URL = "http://blog.csdn.net";// IT阅读主地址
	public final static String DISGRACE_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228567";// 糗事
	public final static String SEDUCTIVE_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228568";// 养眼美图
	public final static String STORY_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228570";// 奇闻异事
	public final static String PEOPLE_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228571";// 人物周刊
	public final static String HOT_NEWS_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228569";// 环球热点
	public final static String CAR_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228572";// 名车赏析
	// 底部菜单URL
	public final static String CARTOON_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1228833";// 漫画

	public final static String ITRELAX_CATEGORY_URL = "http://blog.csdn.net/weiyirong/article/category/1133874";// 轻松一刻

	public final static String IT_UPDATE_URL = "http://apka.mumayi.com/3/35240/renzhewuji_V1.1.2_mumayi_443da.apk";
	// 分类文章的标题名称
	public final static String DISGRACE_CATEGORY_TITLE = "糗事";
	public final static String SEDUCTIVE_CATEGORY_TITLE = "美图赏析";
	public final static String STORY_CATEGORY_TITLE = "奇闻异事";
	public final static String PEOPLE_CATEGORY_TITLE = "人物周刊 ";
	public final static String HOT_NEWS_CATEGORY_TITLE = "军事纵横";
	public final static String CAR_CATEGORY_TITLE = "名车一览";
	// 分类文章的简介
	public final static String DISGRACE_CATEGORY_SUMMARY = "糗事";
	public final static String SEDUCTIVE_CATEGORY_SUMMARY = "养眼美图";
	public final static String STORY_CATEGORY_SUMMARY = "奇闻异事";
	public final static String PEOPLE_CATEGORY_SUMMARY = "人物周刊 ";
	public final static String HOT_NEWS_CATEGORY_SUMMARY = "环球热点";
	public final static String CAR_CATEGORY_SUMMARY = "名车赏析";
	// 底部菜单名
	public final static String CARTOON_CATEGORY_TITLE = "漫画";
	public final static String COLLECT_CATEGORY_TITLE = "收藏";
	// 底部便签菜单标志
	public final static String IT_MAIN_PAGE = "IT_Main_Page";// 首页。即主界面
	public final static String IT_HELP_PAGE = "IT_Help_Page";// 帮主页面，关于
	// 缩放图片的用途
	public final static String FOR_BLOG_ICON = "forBlogIcon";// 为Blog标头的ICON缩放，使其自适应大小
	public final static String FOR_FOOTER_MENU_BG = "forFooterMenuBg";// 为底部选择菜单背景的设置缩放
	// 发送广播，广播类型
	public final static String SOCKETTIMEOUT = "com.weiyi.reader.sockettimeout";// 链接超时
	public final static String UPDATE_ITREADER_NOTIFY = "com.weiyi.reader.update";// 软件更新广播
	// 一些公共常量
	public final static String CURRENT_RUN_APP = "天鹰乐阅，愉悦你我...";// 特殊的文章标题，更新软件
	public final static String IT_APP = "天鹰乐阅";

	// 解析Html可识别标示符
	public final static String BLOG_TITLE_CLASS = "link_title";// blog标题class属性
	public final static String BlOG_DATE_CLASS = "link_postdate";// BLOG日期的class属性
	public final static String BLOG_DESCRIPTION = "article_description";// 博文简述的class属性
	public final static String BlOG_CONTENT_ID = "article_content";// BLOG内容的ID属性

	public static final String[] fontList = new String[] { "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
			"24", "25" };
	public static final String FONTKEY = "fontsize";
	// 下载地址
	public static final String hAnZhi = "http://a.apk.anzhi.com/apk/201210/19/com.weiyi.reader.ui_29214500_0.apk";
	public static final String iAnZhi = "http://a.apk.anzhi.com/apk/201210/15/com.weiyi.itreader.ui_24572900_0.apk";
	public static final String hAnZhuo = "http://cdn.market.hiapk.com/data/upload/2012/10_18/14/com.weiyi.reader.ui_145024.apk";
	public static final String iAnZhuo = "http://cdn.market.hiapk.com/data/upload/2012/10_15/16/com.weiyi.itreader.ui_165103.apk";
	public static final String iYingHui = "http://static.nduoa.com/apk/434/434393/1151620/com.weiyi.itreader.ui.apk";
	public static final String hYingHui = "http://static.nduoa.com/apk/440/440154/1157385/com.weiyi.reader.ui.apk";
	public static final String hNduo = "http://img.yingyonghui.com/apk/405074/com.weiyi.reader.ui.1350527074026.apk";
	public static final String iNduo = "http://img.yingyonghui.com/apk/396323/com.weiyi.itreader.ui.1349674917283.apk";
	public static final String[] DOWNLOADURL = new String[] { hAnZhi, iAnZhi,
			hAnZhuo, iAnZhuo};
}
