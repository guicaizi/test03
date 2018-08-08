package com.yun.software.shangcheng.ui.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yun.software.shangcheng.R;
import com.yun.software.utils.ScreenUtil;


public class ListViewEmptyUtils {
	 public static final  String TAG="ListViewEmptyUtils";
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 空视图
	 */
	private View emptyView;
	/**
	 * 空视图的提示文字
	 */
	private TextView tvEmpty;
	/**
	 * 空视图的提示图片
	 */
	private ImageView ivEmpty;
	/**
	 * pullList 刷新组件
	 */
	private PullToRefreshListView pullList;
	/**
	 * pullGridList 刷新组件
	 */
	private PullToRefreshGridView pullGridList;
	/**
	 * ListView组件
	 */
	private ListView listView;
	/**
	 * GridView组件
	 */
	private GridView gridView;

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param pullGridList
	 *            GridView
	 */

	public ListViewEmptyUtils(Context context, PullToRefreshGridView pullGridList) {
		this.context = context;
		this.pullGridList = pullGridList;
		initEmptyView();
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param pullList
	 *            ListView
	 */
	public ListViewEmptyUtils(Context context, PullToRefreshListView pullList) {
		this.context = context;
		this.pullList = pullList;
		initEmptyView();
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param listView
	 *            ListView
	 */
	public ListViewEmptyUtils(Context context, ListView listView) {
		this.context = context;
		this.listView = listView;
		initEmptyView();
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param GridView
	 *            GridView
	 */
	public ListViewEmptyUtils(Context context, GridView gridView) {
		this.context = context;
		this.gridView = gridView;
		initEmptyView();
	}

	/**
	 * 初始化 空布局
	 */
	private void initEmptyView() {
		emptyView = LayoutInflater.from(context).inflate(R.layout.list_empty_view, null);
		tvEmpty = (TextView) emptyView.findViewById(R.id.empty_tv);
		ivEmpty = (ImageView) emptyView.findViewById(R.id.iv_empty_list);
	}

	/**
	 * 设置空布局 的 提示文字
	 * 
	 * @param emptyText
	 */
	public void setEmptyText(String emptyText) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyText);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.GONE);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}
	/**
	 * 设置空布局 的 提示文字
	 *
	 * @param emptyText
	 */
	public void setEmptyText(String emptyText,int paddindtop) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyText);
		tvEmpty.setPadding(0, ScreenUtil.dip2px(paddindtop),0,0);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.GONE);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}

	}


	/**
	 * 设置空布局 的 提示文字 只提示文字
	 *
	 */
	public void setEmptyText(int emptyTextId) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyTextId);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.GONE);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}

	/**
	 * 设置空布局 的 提示图片 只提示图片
	 * 
	 * @param resId
	 */
	public void setEmptyImage(int resId) {
		tvEmpty.setVisibility(View.GONE);
		ivEmpty.setVisibility(View.VISIBLE);
		ivEmpty.setImageResource(resId);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}

	/**
	 * 设置空布局 的 提示文字和图片
	 *
	 * @param resId
	 */
	public void setEmptyTextAndImage(String emptyText, int resId) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyText);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.VISIBLE);
		ivEmpty.setImageResource(resId);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}

	/**
	 * 设置空布局 的 提示文字和图片
	 *
	 * @param resId
	 */
	public void setTextAndImg(String emptyText) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyText);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.VISIBLE);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}


	/**
	 * 设置空布局 的 提示图片 只提示图片
	 * 
	 * @param resId
	 */
	public void setEmptyTextAndImage(int emptyTextId, int resId) {
		tvEmpty.setVisibility(View.VISIBLE);
		tvEmpty.setText(emptyTextId);
		tvEmpty.setBackgroundResource(0);
		ivEmpty.setVisibility(View.VISIBLE);
		ivEmpty.setImageResource(resId);
		if (this.pullList instanceof PullToRefreshListView) {
			pullList.setEmptyView(emptyView);
		} else if (this.pullGridList instanceof PullToRefreshGridView) {
			pullGridList.setEmptyView(emptyView);
		} else if (this.listView instanceof ListView) {
			addEmptyView();
			listView.setEmptyView(emptyView);
		} else if (this.gridView instanceof GridView) {
			addEmptyView();
			gridView.setEmptyView(emptyView);
		}
	}

	/**
	 * listview/gridview 设置空视图时必须添加到父视图
	 * 
	 * @param emptyView要添加的空视图
	 */
	private void addEmptyView() {
		if (null != emptyView) {
			ViewParent newEmptyViewParent = emptyView.getParent();
			if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
				MyLogUtils.i(TAG,"11111");
				((ViewGroup) newEmptyViewParent).removeView(emptyView);
			}
			if (this.listView instanceof ListView) {
				MyLogUtils.i(TAG,"走了吗 2222");
				((ViewGroup) listView.getParent()).addView(emptyView);
			} else if (this.gridView instanceof GridView) {
				((ViewGroup) gridView.getParent()).addView(emptyView);
			}
		}
	}

	public View getEmptyView() {
		return emptyView;
	}

}
