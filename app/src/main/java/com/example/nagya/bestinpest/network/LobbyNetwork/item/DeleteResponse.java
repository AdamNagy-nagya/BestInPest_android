package com.example.nagya.bestinpest.network.LobbyNetwork.item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteResponse {

@SerializedName("timestamp")
@Expose
private Integer timestamp;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("error")
@Expose
private String error;
@SerializedName("exception")
@Expose
private String exception;
@SerializedName("message")
@Expose
private String message;
@SerializedName("path")
@Expose
private String path;

public Integer getTimestamp() {
return timestamp;
}

public void setTimestamp(Integer timestamp) {
this.timestamp = timestamp;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getError() {
return error;
}

public void setError(String error) {
this.error = error;
}

public String getException() {
return exception;
}

public void setException(String exception) {
this.exception = exception;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public String getPath() {
return path;
}

public void setPath(String path) {
this.path = path;
}

}