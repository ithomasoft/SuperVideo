package com.thomas.video.fragment.contract;

import com.thomas.core.mvp.IBaseMvpModel;
import com.thomas.core.mvp.IBaseMvpView;
import com.thomas.video.entity.HistoryEntity;

import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.util.List;

/**
 * @author Thomas
 * @describe
 * @date 2020/1/9
 * @updatelog
 * @since
 */
public interface HistoryContract {
    interface Model extends IBaseMvpModel{
        void getData(FindMultiCallback<HistoryEntity> callback);
        void deleteHistory(String videoId, UpdateOrDeleteCallback callback);

    }

    interface View extends IBaseMvpView{
        void getDataSuccess(List<HistoryEntity> succeed);
        void getDataEmpty();
        void deleteSuccess(int position);
    }

    interface Presenter{
        void getData();

        void deleteHistory(int position,String videoId);
    }
}
