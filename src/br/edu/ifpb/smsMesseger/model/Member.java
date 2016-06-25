package br.edu.ifpb.smsMesseger.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/06/16.
 */
public class Member {
    private String ip;
    private String name;
    private List msg;

    public Member(String ip, String name){
        this.ip = ip;
        this.name = name;
        this.msg = new ArrayList<String>();
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public void setMsg(String msg) {
        this.msg.add(msg);
    }

    public String getMsg() {
        return (String) this.msg.get(this.msg.size() - 1);
    }

    public void setName(String name) {
        this.name = name;
    }
}
