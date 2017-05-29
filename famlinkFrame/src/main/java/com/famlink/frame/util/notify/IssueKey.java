package com.famlink.frame.util.notify;

/**
 * Created by CG on 14-1-3.
 * 订阅的主题
 * @author ll
 * @version 3.4.0
 */

public enum IssueKey {

	ISLIVINGNOW,

	/**
	 * 登录成功页面更新头像
	 */
	UPDATEHEAD,

	/**
	 * 登录成功并绑定成功
	 */
	LOGIN_ADD_BIND_SCCESS,

	/**
	 * 关闭登录页面
	 */
	CLOSE_LOGIN_ACTIVITY,

	/**
	 * 退出登录
	 */
	USER_LOGOUT,
	/**
	 * 登录界面按了返回键
	 */
	BACK_LOGOUT,

	/**
	 * 设置完星座之后首页做刷新
	 */
	STAR_UPDATE,
	/**
	 * 发表完评论和回复完评论之后评论界面刷新
	 */
	RECOMMEND_REFRESH,
	/**
	 * 取消完我的收藏的某一条回调刷新我的收藏
	 */
	FAVIRATE_REFRESH,

	/**
	 * 从详情页退出
	 */
	DETAIL_LOGOUT,

	/**
	 * umeng推送的开关通知
	 */
	SWITCHPUSH,

	/**
	 * umeng推送关闭通知
	 */
	UMENG_PUSH_CLOSE,

	/**
	 * umeng推送开启通知
	 */
	UMENG_PUSH_OPEN,

	/**
	 * 意见反馈成功通知
	 */
	FEEDBACK_SUCCESS,
	/**
	 * 友盟消息红点的通知
	 */
	UMENG_BEDGE_NUMBER,
	/**
	 * 清除友盟消息红点的通知
	 */
	UMENG_BEDGE_NUMBER_CLEAR,
	/**
	 * 文章是否被查看过(是否进入过文章详情页)
	 */
	ARTICLE_IS_SHOW;
//	/**
//	 *刷新
//	 */
//	USER_LOGOUT

}
