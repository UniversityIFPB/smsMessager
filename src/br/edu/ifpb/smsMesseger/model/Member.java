package br.edu.ifpb.smsMesseger.model;

/**
 * Created by root on 17/06/16.
 */
public class Member {
    private String ip;
    private String name;

    public Member(String ip, String name){
        this.ip = ip;
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
