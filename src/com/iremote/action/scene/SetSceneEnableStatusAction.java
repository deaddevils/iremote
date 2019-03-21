package com.iremote.action.scene;

import com.iremote.common.ErrorCodeDefine;
import com.iremote.domain.Scene;
import com.iremote.service.SceneService;
import com.opensymphony.xwork2.Action;

public class SetSceneEnableStatusAction {
    private int resultCode = ErrorCodeDefine.SUCCESS ;
    private Integer scenedbid;
    private Integer enablestatus;
    private Scene scene;
    private SceneService ss = new SceneService();

    public String execute()
    {
        if(scenedbid == null || enablestatus == null){
            resultCode = ErrorCodeDefine.PARMETER_ERROR;
            return Action.SUCCESS;
        }
        scene = ss.query(scenedbid);
        if(scene == null){
            resultCode = ErrorCodeDefine.SCENE_NOT_EXIST;
            return Action.SUCCESS;
        }
        scene.setEnablestatus(enablestatus);
        return Action.SUCCESS;
    }

    public void setScenedbid(Integer scenedbid) {
        this.scenedbid = scenedbid;
    }

    public void setEnablestatus(Integer enablestatus) {
        this.enablestatus = enablestatus;
    }

    public int getResultCode() {

        return resultCode;
    }
}
