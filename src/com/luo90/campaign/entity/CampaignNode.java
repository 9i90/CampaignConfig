package com.luo90.campaign.entity;

public class CampaignNode {
    private Integer id;

    private String node;

    private String name;

    private String aviFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node == null ? null : node.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAviFlag() {
        return aviFlag;
    }

    public void setAviFlag(String aviFlag) {
        this.aviFlag = aviFlag == null ? null : aviFlag.trim();
    }
}