package com.yks.urc.vo;

import java.io.Serializable;

public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 2516742518077177675L;
    public String state;
    public String msg;
    public T data;

}