package com.yr.entity;
/**
 * @author �ԕ�
 * @date 2017/11/30 ����7:04
 */
public class ResultVO<T> {
    //������
    private Integer code;
    //��ʾ��Ϣ
    private String msg;
    //���صľ�������
    private T data;
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}