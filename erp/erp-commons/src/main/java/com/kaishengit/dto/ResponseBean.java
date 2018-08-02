package com.kaishengit.dto;

/**
 * @Author: chuzhaohui
 * @Date: Created in 15:18 2018/7/27
 */
public class ResponseBean {

    private static final String STATE_SUCCESS = "success";
    private static final String STATE_ERORR = "erorr";

    private String state;
    private String message;
    private Object data;

    public static ResponseBean success(){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setState(STATE_SUCCESS);
        return responseBean;
    }
    public static ResponseBean success(Object obj){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setState(STATE_SUCCESS);
        responseBean.setData(obj);
        return responseBean;
    }
    public static ResponseBean error(String message){
        ResponseBean responseBean = new ResponseBean();
        responseBean.setState(STATE_ERORR);
        responseBean.setMessage(message);
        return responseBean;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
