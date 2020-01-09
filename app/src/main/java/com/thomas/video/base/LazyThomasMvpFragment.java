package com.thomas.video.base;

import com.thomas.core.mvp.BaseMvpPresenter;
import com.thomas.core.mvp.IBaseMvpView;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public abstract class LazyThomasMvpFragment<P extends BaseMvpPresenter> extends LazyThomasFragment implements IBaseMvpView {
    protected P presenter;

    @Override
    public void setContentView() {
        super.setContentView();
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //解除绑定，避免内存泄露
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    protected abstract P createPresenter();
}
