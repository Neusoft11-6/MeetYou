package com.meetyou.cn.vo.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by admin on 2018/4/20.
 */

public class MeetYUser extends BmobUser {

    private String leve;
    private String membertime;
    private String nickname;
    private BmobDate validtime;

    public String getLeve() {
        return leve;
    }

    public void setLeve(String leve) {
        this.leve = leve;
    }

    public String getMembertime() {
        return membertime;
    }

    public void setMembertime(String membertime) {
        this.membertime = membertime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobDate getValidtime() {
        return validtime;
    }

    public void setValidtime(BmobDate validtime) {
        this.validtime = validtime;
    }
}
